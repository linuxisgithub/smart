package javacommon.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

/**
 * redis分布式锁：不能重入
 * @author duwufeng
 * @date 2017年3月21日 上午11:25:19
 */
@SuppressWarnings("all")
public class RedisLock {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private RedisTemplate redisTemplate;

	/**
	 * 锁key
	 */
	private String lockKey;

	/**
	 * 锁有效期：获取锁后，后续执行的业务必须在expireMsecs时间内，防止线程在入锁以后，无限的执行等待，单位毫秒
	 */
	private int expireMsecs = 60 * 1000;

	/**
	 * 锁等待时间：尝试在timeoutMsecs时间内去获取锁，防止线程饥饿，单位毫秒，即在timeoutMsecs时间内获取不到锁就返回false
	 */
	private int timeoutMsecs = 10 * 1000;
	
	/**
	 * 锁的到期时间
	 */
	private String expiresTime = null;

	private volatile boolean locked = false;

	public RedisLock(RedisTemplate redisTemplate, String lockKey) {
		if(redisTemplate == null || lockKey == null) {
			throw new IllegalArgumentException("null参数异常");
		}
		this.redisTemplate = redisTemplate;
		this.lockKey = lockKey + "_lock";
	}

	/**
	 * 
	 * @param redisTemplate
	 * @param lockKey
	 * @param timeoutMsecs 锁等待时间
	 */
	public RedisLock(RedisTemplate redisTemplate, String lockKey, int timeoutMsecs) {
		this(redisTemplate, lockKey);
		this.timeoutMsecs = timeoutMsecs;
	}

	/**
	 * 
	 * @param redisTemplate
	 * @param lockKey
	 * @param timeoutMsecs 锁等待时间
	 * @param expireMsecs 锁有效期
	 */
	public RedisLock(RedisTemplate redisTemplate, String lockKey, int timeoutMsecs, int expireMsecs) {
		this(redisTemplate, lockKey, timeoutMsecs);
		this.expireMsecs = expireMsecs;
	}

	public String getLockKey() {
		return lockKey;
	}

	public String get(final String key) {
		Object obj = null;
		try {
			obj = redisTemplate.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					StringRedisSerializer serializer = new StringRedisSerializer();
					byte[] data = connection.get(serializer.serialize(key));
					connection.close();
					if (data == null) {
						return null;
					}
					return serializer.deserialize(data);
				}
			});
		} catch (Exception e) {
			logger.error("get redis error, key : {}", key);
		}
		return obj != null ? obj.toString() : null;
	}

	/**
	 * key 不存在时，为 key 设置指定的值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setNX(final String key, final String value) {
		Object obj = null;
		try {
			obj = redisTemplate.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					StringRedisSerializer serializer = new StringRedisSerializer();
					Boolean success = connection.setNX(serializer.serialize(key), serializer.serialize(value));
					if(success) {
						connection.expire(serializer.serialize(key), 3600);
					}
					connection.close();
					return success;
				}
			});
		} catch (Exception e) {
			logger.error("setNX redis error, key : {}", key);
		}
		return obj != null ? (Boolean) obj : false;
	}

	/**
	 * 返回给定 key 的旧值。 当 key 没有旧值时，即 key 不存在时，返回 nil 。
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String getSet(final String key, final String value) {
		Object obj = null;
		try {
			obj = redisTemplate.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					StringRedisSerializer serializer = new StringRedisSerializer();
					byte[] ret = connection.getSet(serializer.serialize(key), serializer.serialize(value));
					if(ret != null && ret.length > 0) {
						connection.expire(serializer.serialize(key), 3600);
					}
					connection.close();
					return serializer.deserialize(ret);
				}
			});
		} catch (Exception e) {
			logger.error("setNX redis error, key : {}", key);
		}
		return obj != null ? (String) obj : null;
	}

	/**
	 * 获得 lock. 实现思路: 主要是使用了redis 的setnx命令,缓存了锁. reids缓存的key是锁的key,所有的共享,
	 * value是锁的到期时间(注意:这里把过期时间放在value了,没有时间上设置其超时时间) 执行过程:
	 * 1.通过setnx尝试设置某个key的值,成功(当前没有这个锁)则返回,成功获得锁
	 * 2.锁已经存在则获取锁的到期时间,和当前时间比较,超时的话,则设置新的值
	 *
	 */
	public boolean lock() throws InterruptedException {
		synchronized(lockKey.intern()) {
			int timeout = timeoutMsecs;
			while (timeout >= 0) {
				long expires = getNetWorkTime() + expireMsecs + 1;
				String expiresTime = String.valueOf(expires); // 锁到期时间
				if (this.setNX(lockKey, expiresTime)) {
					// 可以设置成功表示没人拿到锁，当前线程可以拿到锁
					locked = true;
					this.expiresTime = expiresTime;
					return true;
				}
				
				String oldValueStr = this.get(lockKey); // 上一个锁的到期时间
				if (oldValueStr != null && Long.parseLong(oldValueStr) < getNetWorkTime()) {
					// 上一个锁已经超时
					
					String lastValueStr = this.getSet(lockKey, expiresTime);
					// 设置现在的锁到期时间，并返回上一个锁到期时间(最新的时间)
					// 只有一个线程才能获取上一个锁的设置时间，因为jedis.getSet是同步的
					if (lastValueStr != null && lastValueStr.equals(oldValueStr)) {
						// 如果oldValueStr不等于lastValueStr，那边表示其他线程已经优先获取到锁
						locked = true;
						this.expiresTime = expiresTime;
						return true;
					}
				}
				int nextAttempt = this.getNextAttempt();
				timeout -= nextAttempt;
				/**
				 * 随机延迟nextAttempt 毫秒, 这里使用随机时间可能会好一点,可以防止饥饿进程的出现,即,当同时到达多个进程,
				 * 只会有一个进程获得锁,其他的都用同样的频率进行尝试,后面有来了一些进行,也以同样的频率申请锁,这将可能导致前面来的锁得不到满足.
				 * 使用随机的等待时间可以一定程度上保证公平性
				 */
				Thread.sleep(nextAttempt);
			}
		}
		return false;
	}

	/**
	 * 获取50-100随机数
	 * 
	 * @return
	 */
	public int getNextAttempt() {
		Random random = new Random();
		int number = random.nextInt(50) + 50;
		return number;
	}

	/**
	 * 释放锁
	 */
	public void unlock() {
		synchronized(lockKey.intern()) {
			if (locked && this.expiresTime != null) {
				if(Long.parseLong(this.expiresTime) >= getNetWorkTime()) {
					// 锁没超时
					redisTemplate.delete(lockKey);
					locked = false;
				} else {
					//锁超时
					String expiresTime = this.get(lockKey);
					if(this.expiresTime.equals(expiresTime)) {
						// redis里面保存的数据跟当前锁的数据一致
						redisTemplate.delete(lockKey);
						locked = false;
					}
				}
			}
		}
	}
	/**
	 * 获取网络时间戳
	 * @return
	 */
	public static long getNetWorkTime() {
		long result = 0;
		String[] webUrls = new String[]{"http://www.baidu.com", "http://www.taobao.com", "http://www.360.cn", "http://www.ntsc.ac.cn/"};
		URL url = null;
		URLConnection conn = null;
		for (String webUrl : webUrls) {
			try {
				url = new URL(webUrl);
				conn = url.openConnection();
				conn.connect();
				result = conn.getDate();
			} catch (Exception e) {
			}
			if(result != 0) {
				break;
			}
			
		}
		if(result == 0) {
			result = System.currentTimeMillis();
		}
		return result;
	}
	
	public static void main(String[] args) {
		String key = "222";
		RedisTemplate redisTemplate = null;
		RedisLock lock = new RedisLock(redisTemplate, key, 10000, 20000);
		try {
			if (lock.lock()) {
				// 需要加锁的代码
			}
		} catch (InterruptedException e) {
			
		} finally {
			lock.unlock();
		}
	}
}

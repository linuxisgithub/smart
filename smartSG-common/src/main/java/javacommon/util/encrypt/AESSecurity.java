package javacommon.util.encrypt;

import javacommon.util.MD5Util;
import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Random;

/**
 * AES加密解密工具类
 * @ClassName: AESSecurity 
 * @author duwufeng
 * @date 2015年8月17日 下午3:40:46
 */
public class AESSecurity {
	private final static Logger logger = Logger.getLogger(AESSecurity.class);
	/** 加密模式 */
	public static final int ENCRYPT_MODE = 1;
	/** 解密模式 */
	public static final int DECRYPT_MODE = 2;
	/** LENGTH = 8. */
	private static final int LENGTH = 8;
    /** AES算法. */
    private static final String ALGORITHM_AES = "AES/CBC/PKCS5Padding";
    /** AESkey. */
    private static final String KEY_AES = "AES";

    /** 算法参数,must be 16 bytes long. */
    private static String PUBLIC_KEY_16 = "1024204820481024";
    /** 用来生成密钥种. */
    private static String PUBLIC_KEY_8  = "10242048";
    /** 密钥种子，用来生成密钥. */
    private static String PRIVATE_SEED_8;
    static {
    	PRIVATE_SEED_8 = MD5Util.MD5(PUBLIC_KEY_8);
    }
    /**
     * 生成AESkey.
     *
     * @param keySize key的位数  = 128, 192 or 256
     * @param seed 随机种子
     * @return 返回base64编码后的key信息
     */
    private static String generateAESKey(int keySize,String seed) {
        try {
            if(keySize != 128 && keySize != 192 && keySize != 256) {
                keySize = 128;
            }
            KeyGenerator kgen = KeyGenerator.getInstance(KEY_AES);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(seed.getBytes());
            kgen.init(keySize, random);
            SecretKey key = kgen.generateKey();
            return TranscodeUtil.byteArrayToBase64Str(key.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 将base64编码后的密钥字符串转换成密钥对象.
     * 
     * @param key
     *            密钥字符串
     * @param algorithm
     *            加密算法
     * @return 返回密钥对象
     */
    private static Key toKey(String key, String algorithm) {
        SecretKey secretKey = new SecretKeySpec(
                TranscodeUtil.base64StrToByteArray(key), algorithm);
        return secretKey;
    }
    
    /**
     * AES加密.
     * 
     * @param data
     *            要加密的数据
     * @param key
     *            密钥
     * @param algorithmParameter
     *            算法参数，must be 16 bytes long
     * @return 返回加密数据
     */
    private static String AESEncrypt(String data, String key,
            String algorithmParameter) {
        return AESCipher(data, key, algorithmParameter, Cipher.ENCRYPT_MODE);
    }
    
    /**
     * AES解密.
     * 
     * @param data
     *            要解密的数据
     * @param key
     *            密钥
     * @param algorithmParameter
     *            算法参数,must be 16 bytes long
     * @return 返回解密数据
     */
    private static String AESDecrypt(String data, String key,
            String algorithmParameter) {
        return AESCipher(data, key, algorithmParameter, Cipher.DECRYPT_MODE);
    }
    
    /**
     * 实现AES加密解密.
     * 
     * @param data
     *            要加密或解密的数据
     * @param key
     *            密钥
     * @param algorithmParameter
     *            算法参数，16 bytes
     * @param mode
     *            加密或解密
     * @return 返回加密或解密的数据
     */
    private static String AESCipher(String data, String key,
            String algorithmParameter, int mode) {
        try {
            Key k = toKey(key, KEY_AES);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(
                    algorithmParameter.getBytes());
            Cipher ecipher = Cipher.getInstance(ALGORITHM_AES);
            ecipher.init(mode, k, paramSpec);
            return mode == Cipher.DECRYPT_MODE ? 
                    new String(ecipher.doFinal(TranscodeUtil.base64StrToByteArray(data)))
                    : TranscodeUtil.byteArrayToBase64Str(ecipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 安全验证.
     *
     * @param data 加密或解密的数据
     * @param mode 1=加密,2=解密
     * @return the string
     */
    public static String doSecurity(String data, int mode) {
        String result = data;
        switch (mode) {
        case Cipher.ENCRYPT_MODE:
        	logger.info("加密：" + data);
        	data = addRand(data);//加入随机字符串
        	result = AESEncrypt(data, generateAESKey(128, PRIVATE_SEED_8), PUBLIC_KEY_16);
            return result;
        case Cipher.DECRYPT_MODE:
        	logger.info("解密：" + data);
        	result = AESDecrypt(data, generateAESKey(128, PRIVATE_SEED_8), PUBLIC_KEY_16);
            result = removeRand(result);//取出随机字符串
            return result;
        default:
            return null;
        }
    }
    /**
     * 添加随机因子
     * @author duwufeng
     * @update
     * @param data
     * @return
     */
    private static String addRand(String data) {
    	data = invert(data);
    	String randomStr = getRands(LENGTH);
    	String result = "";
    	if(data.length() == 1) {
    		result = randomStr.substring(0, LENGTH/2) + data + randomStr.substring(LENGTH/2, LENGTH);
    	} else {
    		result = randomStr.substring(0, 1) + invert(data.substring(0, data.length()/2))
    				+ randomStr.substring(1, LENGTH/2) + invert(data.substring(data.length()/2, data.length()))
    				+ randomStr.substring(LENGTH/2, LENGTH);
    	}
    	return result;
    }
    /**
     * 去除随机因子
     * @author duwufeng
     * @update
     * @param data
     * @return
     */
    private static String removeRand(String data) {
    	String result = "";
    	if(data.length() == LENGTH + 1) {
    		result = data.charAt(5) + "";
    	} else if(data.length() > LENGTH + 1) {
    		int dataLength = data.length() - LENGTH;
    		result = invert(data.substring(1, 1+dataLength/2)) + invert(data.substring(LENGTH/2+dataLength/2, LENGTH/2+dataLength));
    	}
    	result = invert(result);
    	return result;
    }
    /**
	 * 获取反序字符串
	 * @author duwufeng
	 * @update
	 * @param str
	 * @return
	 */
	public static String invert(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = str.length() - 1; i >= 0; i--) {
			sb.append(str.charAt(i));
		}
		return sb.toString();
	}
	/**
     * 随机产生指定长度的字符串
     * @param length 产生的字符串长度
     * @return
     */
    public static String getRands(int length) {
    	if(length < 0) {
    		length = 32;
    	}
        Random rd = new Random();
        String result = "";
        int rdGet; // 取得随机数
        do {
            if (rd.nextInt() % 2 == 1) {
                rdGet = Math.abs(rd.nextInt()) % 10 + 48; // 产生48到57的随机数(0-9的键位值)
            } else {
                rdGet = Math.abs(rd.nextInt()) % 26 + 97; // 产生97到122的随机数(a-z的键位值)
            }
            char num1 = (char) rdGet; // int转换char
            String dd = Character.toString(num1);
            result += dd;
        } while (result.length() < length);
        return result;
    }
    public static void main(String[] args) {
        String original = "myredis";
        String code = doSecurity(original, AESSecurity.ENCRYPT_MODE);
        System.out.println(code);
        String ss = doSecurity(code, AESSecurity.DECRYPT_MODE);
        System.out.println(ss);
    }
}

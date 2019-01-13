package javacommon.util.sms;

import java.util.regex.Pattern;

/**
 * @功能概要： 参数合法性验证类
 * @项目名称： SmsDemo
 */
public class ValidateParamTool
{

	/**
	 * 验证账号是否合法
	 * 
	 * @param userid
	 *        账号
	 * @return true:合法;false:非法
	 */
	public static boolean validateUserId(String userid)
	{
		try
		{
			// 账号不为空并且账号长度小于等于16并且大于0，则账号合法
			if(userid != null && userid.trim().length() > 0 && userid.trim().length() <= 16)
			{
				return true;
			}
			else
			{
				// 其他情况，账号不合法
				return false;
			}
		}
		catch (Exception e)
		{
			//记录日志
			return false;
		}
	}

	/**
	 * 验证密码是否合法
	 * 
	 * @param pwd
	 *        密码
	 * @return true:合法;false:非法
	 */
	public static boolean validatePwd(String pwd)
	{
		try
		{
			// 密码不为空并且密码长度小于等于32位，则密码合法
			if(pwd != null && pwd.length() > 0 && pwd.length() <= 32)
			{
				return true;
			}
			else
			{
				String pwdStr="";
				if(pwd==null)
				{
					pwdStr="密码为空。";
				}else
				{
					pwdStr="密码长度为"+pwd.length()+"。";
				}
				// 其他情况，密码不合法
				return false;
			}
		}
		catch (Exception e)
		{
			//记录日志
			return false;
		}

	}

	/**
	 * 验证流水号是否合法
	 * 
	 * @param custId
	 *        流水号
	 * @return true:合法;false:非法
	 */
	public static boolean validateCustId(String custId)
	{
		try
		{
			// 流水号为空或者流水号大于0，小于等于64位的字符串合法
			if(custId == null || "".equals(custId))
			{
				return true;
			}else if(custId.length() > 0 && custId.length() <= 64)
			{
				//字母、数字、下划线、减号
				if(Pattern.compile("^[0-9a-zA-Z-_]+$").matcher(custId).find())
				{
					return true;
				}else
				{
					return false;
				}
			}
			// 其他情况不合法
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			//记录日志
			return false;
		}
	}

	/**
	 * 验证业务类型是否合法
	 * 
	 * @param svrType
	 *        业务类型
	 * @return true:合法;false:非法
	 */
	public static boolean validateSvrType(String svrType)
	{
		try
		{
			// 业务类型为空
			if(svrType == null || "".equals(svrType))
			{
				return true;
			}
			else if(svrType.length()>0&&svrType.length() <= 32)
			{
				//业务类型必须是字母和数字
				if(Pattern.compile("^[0-9a-zA-Z]+$").matcher(svrType).find())
				{
					// 业务类型长度最大为32
					return true;
				}else
				{
					return false;
				}
			}
			else
			{
				// 其他情况不合法
				return false;
			}
		}
		catch (Exception e)
		{
			//记录日志
			return false;
		}
	}

	/**
	 * 验证exdata是否合法
	 * 
	 * @param exData
	 *        自定义扩展参数
	 * @return true:合法;false:非法
	 */
	public static boolean validateExData(String exData)
	{
		try
		{
			// exdata为空
			if(exData == null || "".equals(exData))
			{
				return true;
			}
			else if(exData.length() > 0 && exData.length() <= 64)
			{
				//字母、数字、下划线、减号
				if(Pattern.compile("^[0-9a-zA-Z-_]+$").matcher(exData).find())
				{
					return true;
				}else
				{
					return false;
				}
			}
			else
			{
				// 其他情况不合法
				return false;
			}
		}
		catch (Exception e)
		{
			//记录日志
			return false;
		}
	}

	/**
	 * 验证信息内容是否合法
	 * 
	 * @param content
	 *        短信内容
	 * @return true:合法;false:非法
	 */
	public static boolean validateMessage(String content)
	{
		try
		{
			// 短信内容不为空
			if(content != null && !"".equals(content.trim()))
			{
				//是否是中文短信
				boolean isChinese=false;
				//去掉短信内容前后空格
				content=content.trim();
				if(content != null && content.length() > 0)
				{
					int msgLen = content.length();
					//短信内容字符ASCII码
					int msgChar;
					for(int i=0; i<msgLen; i++)
					{
						msgChar = (int)content.charAt(i);
						//存在中文字符，就是中文短信
						if(msgChar > 127)
						{
							//中文短信
							isChinese=true;
							break;
						}
					}
					//中文短信
					if(isChinese)
					{
						//长度必须小于990
						if(content.length()<=990)
						{
							return true;
						}
					}
					//英文短信
					else
					{
						//长度必须小于1980
						if(content.length()<=1980)
						{
							return true;
						}
					}
				}
			  //返回失败
			  return false;
			}
			else
			{
				//短信内容为空
				return false;
			}
		}
		catch (Exception e)
		{
			//记录日志
			return false;
		}
	}

	/**
	 * 验证扩展子号是否合法
	 * 
	 * @param strSubPort
	 *        扩展子号
	 * @return true:合法;false:非法
	 */
	public static boolean validateSubPort(String strSubPort)
	{
		try
		{
			// 扩展子号为空，合法
			if(strSubPort == null||"".equals(strSubPort.trim()))
			{
				return true;
			}
			// 扩展子号不为空并且扩展子号为小于等于6的数字 合法
			if(strSubPort != null && !"".equals(strSubPort.trim()) && isUnSignDigit(strSubPort) && strSubPort.length() < 7)
			{
				return true;
			}
			else
			{
				// 其他情况不合法
				return false;
			}
		}
		catch (Exception e)
		{
			//记录日志
			return false;
		}
	}

	/**
	 * 验证手机单个号码是否合法
	 * 手机号码可能是国际号码，暂时不验证手机号码长度
	 * 
	 * @param mobile
	 *        手机号码
	 * @return true:合法;false:非法
	 */
	public static boolean validateMobile(String mobile)
	{
		try
		{
			// 手机号码不能为空并且必须是数字且长度小于等于21位
			if(mobile != null && !"".equals(mobile) && isUnSignDigit(mobile) && mobile.length()<=21)
			{
				return true;
			}
			else
			{
				// 其他情况不合法
				return false;
			}
		}
		catch (Exception e)
		{
			//记录日志
			return false;
		}
	}

	/**
	 * 验证是否是以英文逗号隔开的1000个手机号码
	 * 手机号码可能是国际号码，暂时不验证手机号码长度
	 ** 
	 * @param strMobiles
	 *        手机号码
	 * @return success:合法;fail:检测失败;illegalFormat:格式非法;overNum:超过最大支持号码1000个
	 */
	public static String validateMobiles(String strMobiles)
	{
		try
		{
			// 手机号码字符串不能为空
			if(strMobiles != null && !"".equals(strMobiles.trim()))
			{
				String[] arrMobiles = strMobiles.split(",");
				// 手机号码个数必须大于0并且小于等于1000
				if(arrMobiles.length > 0 && arrMobiles.length <= 1000)
				{
					for (int i = 0; i < arrMobiles.length; i++)
					{
						// 手机号码不能为空并且必须是数字
						if(arrMobiles[i] == null || "".equals(arrMobiles[i]) || !isUnSignDigit(arrMobiles[i]) || arrMobiles[i].length()>21)
						{
							return "illegalFormat";
						}
					}
				}
				else
				{
					if(arrMobiles.length>1000)
					{
						return "overNum";
					}else
					{
						return "illegalFormat";
					}
				}
			}
			else
			{
				return "illegalFormat";
			}
		}
		catch (Exception e)
		{
			//记录日志
			return "fail";
		}
		// 都没有被拦截，证明手机号码符合要求
		return "success";
	}

	/**
	 * @description 检测IP地址是否合法
	 * @param ip
	 *        IP地址
	 * @return true:合法;false:非法
	 * @author tanglili <jack860127@126.com>
	 * @datetime 2016-9-22 下午03:49:25
	 */
	public static boolean validateIP(String ip)
	{
		try
		{
			// IP不为空，则进行正则表达式验证
			if(ip != null && !"".equals(ip.trim()))
			{
				return true;
			}
			else
			{
				// IP为空值，不合法
				return false;
			}
		}
		catch (Exception e)
		{
			//记录日志
			return false;
		}
	}

	/**
	 * @description 验证端口是否合法
	 * @param portStr
	 *        端口
	 * @return true:合法;false:非法
	 * @author tanglili <jack860127@126.com>
	 * @datetime 2016-9-22 下午04:35:50
	 */
	public static boolean validatePort(String portStr)
	{
		try
		{
			// 将端口字符串转成数字
			Integer.parseInt(portStr);
			return true;
		}
		catch (Exception e)
		{
			// 转换出错，端口不合法
			//记录日志
			return false;
		}
	}

	/**
	 * 无符号数字
	 * 
	 * @param str
	 *        要验证的数字
	 * @return
	 */
	private static boolean isUnSignDigit(String str)
	{
		char[] num = str.toCharArray();
		for (int i = 0; i < num.length; i++)
		{
			if(!Character.isDigit(num[i]))
			{
				return false;
			}
		}
		return true;
	}

}

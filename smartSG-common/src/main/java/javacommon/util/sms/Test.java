package javacommon.util.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @功能概要：Demo类
 */
public class Test
{

	private static BufferedReader	breader	= null;

	private static String userid=null;
	
	private static String pwd=null;
	
	// 日期格式定义
	private static SimpleDateFormat	sdf						= new SimpleDateFormat("MMddHHmmss");
	
	//密码是否加密 默认加密
    public static boolean IS_ENCRYPT_PWD=true;
	
	/**
	 * 主方法
	 * 
	 * @description
	 * @param arg
	 * @throws IOException
	 */
	public static void main(String arg[]) throws IOException
	{

		try
		{
			// 输入流
			breader = new BufferedReader(new InputStreamReader(System.in, "GBK"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		System.out.println("请输入：");
		
		
		//请求路径
		System.out.print("请求路径(如/sms/v2/std/)：");
		String requestPath = null;
		boolean flagRequestPath = false;
		do
		{
			requestPath = breader.readLine();
			if(requestPath!=null&&!"".equals(requestPath.trim()))
			{
				flagRequestPath = true;
			}
			else
			{
				System.out.print("请求路径输入不合法，请重新输入：");
			}
		} while(!flagRequestPath);
		
		
		String ipAddress1 = null;
		String ipAddress2 = null;
		String ipAddress3 = null;
		String ipAddress4 = null;

		// 添加IP和端口
		for (int i = 1; i < 5; i++)
		{
			System.out.print("IP地址" + i + ":");
			String ip = null;
			boolean flagIP = false;
			do
			{
				ip = breader.readLine();
				if(ip != null && !"".equals(ip))
				{
					flagIP = true;
				}
				else
				{
					System.out.print("IP地址" + i + "输入不合法，请重新输入：");
				}
			} while(!flagIP);

			// 端口
			System.out.print("端口" + i + ":");
			String port = null;

			boolean flagPort = false;
			do
			{
				port = breader.readLine();
				if(port != null && !"".equals(port) && isUnSignDigit(port))
				{
					flagPort = true;
				}
				else
				{
					System.out.print("端口" + i + "输入不合法，请重新输入：");
				}
			} while(!flagPort);

			if(i == 1)
			{
				ipAddress1 = ip + ":" + port;
			}
			else if(i == 2)
			{
				ipAddress2 = ip + ":" + port;
			}
			else if(i == 3)
			{
				ipAddress3 = ip + ":" + port;
			}
			else if(i == 4)
			{
				ipAddress4 = ip + ":" + port;
			}

			System.out.print("继续添加IP和端口（Y/N）：");
			boolean flag5 = false;
			boolean isContinue = false;
			do
			{
				String yn = breader.readLine();
				if("y".equals(yn) || "Y".equals(yn))
				{
					if(i == 4)
					{
						flag5 = true;
						System.out.println("IP和端口最多添加4个，无法继续添加。");
					}
					else
					{
						isContinue = true;
						flag5 = true;
					}
				}
				else if("n".equals(yn) || "N".equals(yn))
				{
					flag5 = true;
				}
				else
				{
					flag5 = false;
					System.out.println("继续添加IP和端口（Y/N）输入不合法，请重新输入");
					System.out.print("继续添加IP和端口（Y/N）：");
				}
			} while(!flag5);

			if(!isContinue)
			{
				break;
			}
		}

		// 设置IP信息
		int errorCode=ConfigManager.setIpInfo(ipAddress1, ipAddress2, ipAddress3, ipAddress4);
		if(errorCode!=0)
		{
			System.out.println("IP信息添加失败，错误码:"+errorCode);
		}
		
		//用户账号
	 	System.out.print("用户账号：");
	    boolean flagUserId = false;
		 do
		 {
			 userid = breader.readLine();			            		 
   		 if(userid!=null&&!"".equals(userid))
   		 {
   			flagUserId = true;
   		 }
   		 else
   		 {
   			 System.out.print("用户账号输入不合法，请重新输入：");
   		 }
		 }while(!flagUserId);
		 
		 
		//用户密码
		 	System.out.print("用户密码：");
		    boolean flagPwd = false;
			 do
			 {
				 pwd = breader.readLine();			            		 
	   		 if(pwd!=null&&!"".equals(pwd))
	   		 {
	   			flagPwd = true;
	   		 }
	   		 else
	   		 {
	   			 System.out.print("用户密码输入不合法，请重新输入：");
	   		 }
			 }while(!flagPwd);
			 
			 //密码是否加密
			 System.out.print("密码是否加密：（1：加密;0：不加密）");
			 boolean flagIsEncrypt = false;
			 do
			 {
				String isEncrypt= breader.readLine();			            		 
		   	    if(isEncrypt!=null&&!"".equals(isEncrypt)&&("0".equals(isEncrypt)||"1".equals(isEncrypt)))
		   		{
		   	    	flagIsEncrypt = true;
		   	    	if("1".equals(isEncrypt))
		   	    	{
		   	    		IS_ENCRYPT_PWD=true;
		   	    	}else
		   	    	{
		   	    		IS_ENCRYPT_PWD=false;
		   	    	}
		   		}
		   		else
		   		{
		   			System.out.print("密码是否加密输入不合法，请重新输入：");
		   		}
			}while(!flagIsEncrypt);

		// 实例化短信处理对象
		CHttpPost cHttpPost = new CHttpPost();

		// 以下为8个方法的调用例子
		System.out.println("请选择接口类型：");
		System.out.println("1:  单条发送");
		System.out.println("2:  相同内容群发");
		System.out.println("3:  个性化群发");
		System.out.println("4:  查询余额接口");
		System.out.println("5:  查询剩余金额或条数接口");
		System.out.println("6:  获取上行接口");
		System.out.println("7:  获取状态报告接口");
		System.out.println("8:  移除账号");
		System.out.println("9:  退出应用程序");
		System.out.print("接口类型：");
		String str;

		do
		{

			boolean flagtype = false;
			do
			{
				str = breader.readLine();
				if(str != null && !"".equals(str) && ("1".equals(str) || "2".equals(str) || "3".equals(str) || "4".equals(str) || "5".equals(str) || "6".equals(str) || "7".equals(str)|| "8".equals(str)|| "9".equals(str)))
				{
					flagtype = true;
				}
				else
				{
					System.out.println("接口类型输入错误，请重新输入！");
					System.out.print("接口类型：");
				}
			} while(!flagtype);

			if(str.equals("1"))
			{
				// 单条发送
				singleSend(cHttpPost);
			}
			else if(str.equals("2"))
			{
				// 相同内容群发
				batchSend(cHttpPost);
			}
			else if(str.equals("3"))
			{
				// 个性化群发
				multiSend(cHttpPost);
			}
			else if(str.equals("4"))
			{
				// 查询余额接口
				getBalance(cHttpPost);
			}
			else if(str.equals("5"))
			{
				getRemains(cHttpPost);
			}
			else if(str.equals("6"))
			{
				// 获取上行接口
				getMo(cHttpPost);
			}
			else if(str.equals("7"))
			{
				// 获取状态报告接口
				getRpt(cHttpPost);
			}else if(str.equals("8"))
			{
				//移除IP信息
				ConfigManager.removeAllIpInfo();
			}
			System.out.println("请选择接口类型：");
			System.out.println("1:  单条发送");
			System.out.println("2:  相同内容群发");
			System.out.println("3:  个性化群发");
			System.out.println("4:  查询余额接口");
			System.out.println("5:  查询剩余金额或条数接口");
			System.out.println("6:  获取上行接口");
			System.out.println("7:  获取状态报告接口");
			System.out.println("8:  移除账号");
			System.out.println("9:  退出应用程序");
			System.out.print("接口类型：");

		} while(!str.equals("9"));

		System.out.print("程序已退出！");
	}

	/**
	 * 相同内容群发
	 */
	public static void batchSend(CHttpPost cHttpPost)
	{
		try
		{
			Message message = new Message();
			
			// 将 userid转成大写,以防大小写不一致
			userid = userid.toUpperCase();
			// 对密码进行加密
			String encryptPwd = null;
			//判断密码是否加密。
			//密码加密，则对密码进行加密
			String timestamp=null;
			if(IS_ENCRYPT_PWD)
			{
				// 设置时间戳
			    timestamp = sdf.format(Calendar.getInstance().getTime());
				// 对密码进行加密
			    encryptPwd = cHttpPost.encryptPwd(userid,pwd, timestamp);
			}else
			{
				//不加密，不需要设置时间戳
				timestamp=null;
				encryptPwd=pwd;
			}
			
			//设置账号
			message.setUserid(userid);
			
			//设置密码
			message.setPwd(encryptPwd);
			
			//设置时间戳
			message.setTimestamp(timestamp);

			System.out.println("请输入手机号码（多个号码用英文逗号分隔，最多1000个号码）");
			System.out.print("手机号码：");
			boolean falgp = false;
			String phone;
			do
			{
				boolean flagg = true;
				phone = breader.readLine();
				if(phone != null && !"".equals(phone))
				{
					String[] phones = phone.split(",");
					if(phones.length > 0 && phones.length <= 1000)
					{
						for (int i = 0; i < phones.length; i++)
						{
							// 如果输入的对象号码不合法则要重新输入
							if("".equals(phones[i]) || !isUnSignDigit(phones[i]))
							{
								flagg = false;
								break;
							}
						}
						if(flagg)
						{
							falgp = true;
							// 设置手机号码
							message.setMobile(phone);
						}
						else
						{
							System.out.println("手机号码输入不合法，请重新输入");
							System.out.println("手机号码：");
						}
					}
					else
					{
						System.out.println("手机号码个数超过1000个，请重新输入");
						System.out.println("手机号码：");
					}
				}
				else
				{
					System.out.println("手机号码输入不合法，请重新输入");
					System.out.println("手机号码：");
				}
			} while(!falgp);

			System.out.println("请输入短信内容（内容长度不大于990个汉字(英文短信不大于1980)）");
			System.out.print("短信内容：");
			boolean flag3 = false;
			String strMessage;
			do
			{
				strMessage = breader.readLine();
				if(ValidateParamTool.validateMessage(strMessage))
				{
					flag3 = true;
					// 设置短信内容
					message.setContent(strMessage);
				}
				else if(strMessage == null || "".equals(strMessage.trim()))
				{
					System.out.println("短信长度为0，请重新输入");
					System.out.print("短信内容：");
				}
				else
				{
					System.out.println("短信长度超过990个汉字((英文短信超过1980))，请重新输入");
					System.out.print("短信内容：");
				}
			} while(!flag3);

			System.out.println("请输入扩展子号 （不带请不填，长度不大于6位）");
			System.out.print("扩展子号：");
			boolean flag4 = false;
			String strSubPort = null;
			do
			{
				strSubPort = breader.readLine();
				if(ValidateParamTool.validateSubPort(strSubPort))
				{
					flag4 = true;
					if(strSubPort!= null&&!"".equals(strSubPort.trim())){
						// 设置扩展号
						message.setExno(strSubPort);
					}
				}
				else
				{
					System.out.println("扩展子号输入不合法，请重新输入");
					System.out.print("扩展子号：");
				}
			} while(!flag4);

			System.out.println("请输入用户自定义流水号，不带请输入0");
			System.out.print("流水号：");
			boolean flag5 = false;
			String strUserMsgId = null;
			do
			{
				strUserMsgId = breader.readLine();
				if(ValidateParamTool.validateCustId(strUserMsgId))
				{
					flag5 = true;
					// 设置流水号
					if(!"0".equals(strUserMsgId))
					{
						message.setCustid(strUserMsgId);
					}
				}
				else
				{
					System.out.println("流水号输入不合法，请重新输入");
					System.out.print("流水号：");
				}
			} while(!flag5);

			System.out.println("请输入自定义扩展数据，不带请输入0");
			System.out.print("自定义扩展数据：");
			boolean flag6 = false;
			String exdata = null;
			do
			{
				exdata = breader.readLine();
				if(ValidateParamTool.validateExData(exdata))
				{
					flag6 = true;
					// 设置流水号
					if(!"0".equals(exdata))
					{
						message.setExdata(exdata);
					}
				}
				else
				{
					System.out.println("自定义扩展数据输入不合法，请重新输入");
					System.out.print("自定义扩展数据：");
				}
			} while(!flag6);
			
			System.out.println("请输入业务类型，不带无需填写");
			System.out.print("业务类型：");
			boolean flag7 = false;
			String svrtype = null;
			do
			{
				svrtype = breader.readLine();
				if(ValidateParamTool.validateSvrType(svrtype))
				{
					flag7 = true;
					// 设置业务类型
					if(svrtype != null&&!"".equals(svrtype.trim()))
					{
						message.setSvrtype(svrtype);
					}
				}
				else
				{
					System.out.println("业务类型输入不合法，请重新输入");
					System.out.print("业务类型：");
				}
			} while(!flag7);

			// 相同内容群发

			// 返回的流水号
			// 返回的流水号
			StringBuffer msgId=new StringBuffer();
			int result = cHttpPost.batchSend(message, msgId);
			if(result == 0)
			{
				System.out.println("相同内容发送成功。");
				System.out.println(msgId.toString());
			}
			else
			{
				System.out.println("相同内容发送失败，错误码：" + result);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 单条发送
	 */
	public static void singleSend(CHttpPost cHttpPost)
	{
		try
		{
			Message message = new Message();

			// 将 userid转成大写,以防大小写不一致
			userid = userid.toUpperCase();
			// 对密码进行加密
			String encryptPwd = null;
			//判断密码是否加密。
			//密码加密，则对密码进行加密
			String timestamp=null;
			if(IS_ENCRYPT_PWD)
			{
				// 设置时间戳
			    timestamp = sdf.format(Calendar.getInstance().getTime());
				// 对密码进行加密
			    encryptPwd = cHttpPost.encryptPwd(userid,pwd, timestamp);
			}else
			{
				//不加密，不需要设置时间戳
				timestamp=null;
				encryptPwd=pwd;
			}
			
			//设置账号
			message.setUserid(userid);
			
			//设置密码
			message.setPwd(encryptPwd);
			
			//设置时间戳
			message.setTimestamp(timestamp);
			
			System.out.println("请输入手机号码");
			System.out.print("手机号码：");
			boolean falgp = false;
			String phone;
			do
			{
				boolean flagg = true;
				phone = breader.readLine();
				if(phone != null && !"".equals(phone))
				{
					// 如果输入的对象号码不合法则要重新输入
					if("".equals(phone) || !isUnSignDigit(phone))
					{
						flagg = false;
					}
					if(flagg)
					{
						falgp = true;
						// 设置手机号码
						message.setMobile(phone);
					}
					else
					{
						System.out.println("手机号码输入不合法，请重新输入");
						System.out.println("手机号码：");
					}
				}
				else
				{
					System.out.println("手机号码输入不合法，请重新输入");
					System.out.println("手机号码：");
				}
			} while(!falgp);

			System.out.println("请输入短信内容（内容长度不大于990个汉字(英文短信不大于1980)）");
			System.out.print("短信内容：");
			boolean flag3 = false;
			String strMessage;
			do
			{
				strMessage = breader.readLine();
				if(ValidateParamTool.validateMessage(strMessage))
				{
					flag3 = true;
					// 设置短信内容
					message.setContent(strMessage);
				}
				else if(strMessage == null || "".equals(strMessage.trim()))
				{
					System.out.println("短信长度为0，请重新输入");
					System.out.print("短信内容：");
				}
				else
				{
					System.out.println("短信长度超过990个汉字((英文短信超过1980))，请重新输入");
					System.out.print("短信内容：");
				}
			} while(!flag3);

			System.out.println("请输入扩展子号 （不带无需填写，长度不大于6位）");
			System.out.print("扩展子号：");
			boolean flag4 = false;
			String strSubPort = null;
			do
			{
				strSubPort = breader.readLine();
				if(ValidateParamTool.validateSubPort(strSubPort))
				{
					flag4 = true;
					if(strSubPort != null&&!"".equals(strSubPort.trim())){
						// 设置扩展号
						message.setExno(strSubPort);
					}
				}
				else
				{
					System.out.println("扩展子号输入不合法，请重新输入");
					System.out.print("扩展子号：");
				}
			} while(!flag4);

			System.out.println("请输入用户自定义流水号，不带请输入0");
			System.out.print("流水号：");
			boolean flag5 = false;
			String strUserMsgId = null;
			do
			{
				strUserMsgId = breader.readLine();
				if(ValidateParamTool.validateCustId(strUserMsgId))
				{
					flag5 = true;
					// 设置流水号
					if(!"0".equals(strUserMsgId))
					{
						message.setCustid(strUserMsgId);
					}
				}
				else
				{
					System.out.println("流水号输入不合法，请重新输入");
					System.out.print("流水号：");
				}
			} while(!flag5);

			System.out.println("请输入自定义扩展数据，不带请输入0");
			System.out.print("自定义扩展数据：");
			boolean flag6 = false;
			String exdata = null;
			do
			{
				exdata = breader.readLine();
				if(ValidateParamTool.validateExData(exdata))
				{
					flag6 = true;
					// 设置流水号
					if(!"0".equals(exdata))
					{
						message.setExdata(exdata);
					}
				}
				else
				{
					System.out.println("自定义扩展数据输入不合法，请重新输入");
					System.out.print("自定义扩展数据：");
				}
			} while(!flag6);
			
			System.out.println("请输入业务类型，不带无需填写");
			System.out.print("业务类型：");
			boolean flag7 = false;
			String svrtype = null;
			do
			{
				svrtype = breader.readLine();
				if(ValidateParamTool.validateSvrType(svrtype))
				{
					flag7 = true;
					// 设置业务类型
					if(svrtype != null&&!"".equals(svrtype.trim()))
					{
						message.setSvrtype(svrtype);
					}
				}
				else
				{
					System.out.println("业务类型输入不合法，请重新输入");
					System.out.print("业务类型：");
				}
			} while(!flag7);

			// 单条发送
			// 返回的流水号
			StringBuffer msgId=new StringBuffer();
			int result = cHttpPost.singleSend(message,msgId);
			if(result == 0)
			{
				System.out.println("单条发送成功。");
				System.out.println(msgId.toString());
			}
			else
			{
				System.out.println("单条发送失败，错误码：" + result);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 个性化群发
	 */
	public static void multiSend(CHttpPost cHttpPost)
	{
		try
		{		
			
			// 将 userid转成大写,以防大小写不一致
			userid = userid.toUpperCase();
			// 对密码进行加密
			String encryptPwd = null;
			//判断密码是否加密。
			//密码加密，则对密码进行加密
			String timestamp=null;
			if(IS_ENCRYPT_PWD)
			{
				// 设置时间戳
			    timestamp = sdf.format(Calendar.getInstance().getTime());
				// 对密码进行加密
			    encryptPwd = cHttpPost.encryptPwd(userid,pwd, timestamp);
			}else
			{
				//不加密，不需要设置时间戳
				timestamp=null;
				encryptPwd=pwd;
			}
			
			List<MultiMt> multixMts = new ArrayList<MultiMt>();
			boolean flag = false;
			// 手机号码数量
			int mobileCount = 0;
			do
			{
				MultiMt multixMt = new MultiMt();
				System.out.println("请输入一个手机号码");
				System.out.print("手机号码：");
				boolean falgp = false;
				String strMobile = null;
				do
				{
					strMobile = breader.readLine();
					if(strMobile!=null&&!"".equals(strMobile) && isUnSignDigit(strMobile))
					{
						falgp = true;
						multixMt.setMobile(strMobile);
					}
					else
					{
						System.out.println("手机号码输入不合法，请重新输入");
						System.out.print("手机号码：");
					}
				} while(!falgp);

				System.out.println("请输入短信内容（内容长度不大于990个汉字(英文短信不大于1980)）");
				System.out.print("短信内容：");
				boolean flag3 = false;
				String strMessage;
				do
				{
					strMessage = breader.readLine();
					if(ValidateParamTool.validateMessage(strMessage))
					{
						flag3 = true;
						multixMt.setContent(strMessage);
					}
					else if(strMessage == null || "".equals(strMessage.trim()))
					{
						System.out.println("短信长度为0，请重新输入");
						System.out.print("短信内容：");
					}
					else
					{
						System.out.println("短信长度超过990个汉字((英文短信超过1980))，请重新输入");
						System.out.print("短信内容：");
					}
				} while(!flag3);

				System.out.println("请输入扩展子号 （不带请不填，长度不大于6位）");
				System.out.print("扩展子号：");
				boolean flag4 = false;
				String strSubPort = null;
				do
				{
					strSubPort = breader.readLine();
					if(ValidateParamTool.validateSubPort(strSubPort))
					{
						flag4 = true;
						if(strSubPort != null&&!"".equals(strSubPort.trim())){
							// 设置扩展号
							multixMt.setExno(strSubPort);
						}
					}
					else
					{
						System.out.println("扩展子号输入不合法，请重新输入");
						System.out.print("扩展子号：");
					}
				} while(!flag4);

				System.out.println("请输入用户自定义流水号，不带请输入0");
				System.out.print("流水号：");
				boolean flag5 = false;
				String strUserMsgId = null;
				do
				{
					strUserMsgId = breader.readLine();
					if(ValidateParamTool.validateCustId(strUserMsgId))
					{
						flag5 = true;
					}
					else
					{
						System.out.println("流水号输入不合法，请重新输入");
						System.out.print("流水号：");
					}
				} while(!flag5);
				if(!"0".equals(strUserMsgId))
				{
					multixMt.setCustid(strUserMsgId);
				}

				System.out.println("请输入自定义扩展数据，不带请输入0");
				System.out.print("自定义扩展数据：");
				boolean flag6 = false;
				String exdata = null;
				do
				{
					exdata = breader.readLine();
					if(ValidateParamTool.validateExData(exdata))
					{
						flag6 = true;
						// 设置流水号
						if(!"0".equals(exdata))
						{
							multixMt.setExdata(exdata);
						}
					}
					else
					{
						System.out.println("自定义扩展数据输入不合法，请重新输入");
						System.out.print("自定义扩展数据：");
					}
				} while(!flag6);
				
				System.out.println("请输入业务类型，不带无需填写");
				System.out.print("业务类型：");
				boolean flag7 = false;
				String svrtype = null;
				do
				{
					svrtype = breader.readLine();
					if(ValidateParamTool.validateSvrType(svrtype))
					{
						flag7 = true;
						// 设置业务类型
						if(svrtype != null&&!"".equals(svrtype.trim()))
						{
							multixMt.setSvrtype(svrtype);
						}
					}
					else
					{
						System.out.println("业务类型输入不合法，请重新输入");
						System.out.print("业务类型：");
					}
				} while(!flag7);

				multixMts.add(multixMt);
				// 手机号码数目加1
				mobileCount = mobileCount + 1;
				boolean flag2 = false;
				System.out.print("继续添加（Y/N）：");
				do
				{
					String yn = breader.readLine();
					if("y".equals(yn) || "Y".equals(yn))
					{
						if(mobileCount >= 1000)
						{
							System.out.println("手机号码个数已经有1000个，不能继续添加，请重新输入");
							System.out.print("继续添加（Y/N）：");
						}
						else
						{
							flag = true;
							flag2 = true;
						}
					}
					else if("n".equals(yn) || "N".equals(yn))
					{
						flag = false;
						flag2 = true;
					}
					else
					{
						System.out.println("继续添加（Y/N）输入不合法，请重新输入");
						System.out.print("继续添加（Y/N）：");
					}
				} while(!flag2);

			} while(flag);

			// 返回的流水号
			StringBuffer msgId=new StringBuffer();
			// 短信息发送接口
			int result = cHttpPost.multiSend(userid,encryptPwd,timestamp,multixMts, msgId);
			if(result == 0)
			{
				System.out.println("个性化群发发送成功。");
				
				System.out.println(msgId.toString());
				
			}
			else
			{
				System.out.println("个性化群发发送失败，错误码：" + result);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * 查询余额接口
	 */
	public static void getBalance(CHttpPost cHttpPost)
	{
		try
		{
			// 将 userid转成大写,以防大小写不一致
			userid = userid.toUpperCase();
			// 对密码进行加密
			String encryptPwd = null;
			//判断密码是否加密。
			//密码加密，则对密码进行加密
			String timestamp=null;
			if(IS_ENCRYPT_PWD)
			{
				// 设置时间戳
			    timestamp = sdf.format(Calendar.getInstance().getTime());
				// 对密码进行加密
			    encryptPwd = cHttpPost.encryptPwd(userid,pwd, timestamp);
			}else
			{
				//不加密，不需要设置时间戳
				timestamp=null;
				encryptPwd=pwd;
			}
			
			// 查询余额接口。
			int result = cHttpPost.getBalance(userid,encryptPwd,timestamp);

			if(result >= 0)
			{
				System.out.println("查询成功,余额为：" + result+"条");
			}
			else
			{
				System.out.println("查询失败，错误码为：" + result);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 获取上行接口
	 */
	public static void getMo(CHttpPost cHttpPost)
	{
		try
		{
			// 条数
			String retsizeStr = "";
			int retsize=0;
			// 获取最大条数
			System.out.print("请输入获取上行的最大条数：");
			boolean flagRetsize = false;
			do
			{
				retsizeStr = breader.readLine();
				if(retsizeStr!=null&&!"".equals(retsizeStr.trim())&&isUnSignDigit(retsizeStr.trim()))
				{
					flagRetsize = true;
					retsize=Integer.parseInt(retsizeStr.trim());
				}
				else
				{
					System.out.print("获取上行的最大条数输入不合法，请重新输入：");
				}
			} while(!flagRetsize);
			
			// 将 userid转成大写,以防大小写不一致
			userid = userid.toUpperCase();
			// 对密码进行加密
			String encryptPwd = null;
			//判断密码是否加密。
			//密码加密，则对密码进行加密
			String timestamp=null;
			if(IS_ENCRYPT_PWD)
			{
				// 设置时间戳
			    timestamp = sdf.format(Calendar.getInstance().getTime());
				// 对密码进行加密
			    encryptPwd = cHttpPost.encryptPwd(userid,pwd, timestamp);
			}else
			{
				//不加密，不需要设置时间戳
				timestamp=null;
				encryptPwd=pwd;
			}
			
			// 获取上行接口。
			// 上行对象
			List<MO> moList = new ArrayList<MO>();
			int result = cHttpPost.getMo(userid,encryptPwd,timestamp,retsize,moList);
			if(result == 0)
			{
				System.out.println("获取上行成功！获取到的上行有" + moList.size() + "条记录。");
				if(moList != null && moList.size() > 0)
				{
					// 打印上行信息
					printMoPack(moList);
				}
			}
			else
			{
				System.out.println("获取上行失败，错误码为:" + result);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 获取状态报告接口
	 */
	public static void getRpt(CHttpPost cHttpPost)
	{
		try
		{
			// 条数
			String retsizeStr = "";
			int retsize=0;
			// 获取最大条数
			System.out.print("请输入获取状态报告的最大条数：");
			boolean flagRetsize = false;
			do
			{
				retsizeStr = breader.readLine();
				if(retsizeStr!=null&&!"".equals(retsizeStr.trim())&&isUnSignDigit(retsizeStr.trim()))
				{
					flagRetsize = true;
					retsize=Integer.parseInt(retsizeStr.trim());
				}
				else
				{
					System.out.print("获取状态报告的最大条数输入不合法，请重新输入：");
				}
			} while(!flagRetsize);

			// 将 userid转成大写,以防大小写不一致
			userid = userid.toUpperCase();
			// 对密码进行加密
			String encryptPwd = null;
			//判断密码是否加密。
			//密码加密，则对密码进行加密
			String timestamp=null;
			if(IS_ENCRYPT_PWD)
			{
				// 设置时间戳
			    timestamp = sdf.format(Calendar.getInstance().getTime());
				// 对密码进行加密
			    encryptPwd = cHttpPost.encryptPwd(userid,pwd, timestamp);
			}else
			{
				//不加密，不需要设置时间戳
				timestamp=null;
				encryptPwd=pwd;
			}
			
			// 获取状态报告
			List<RPT> rptList = new ArrayList<RPT>();

			int result = cHttpPost.getRpt(userid,encryptPwd,timestamp,retsize,rptList);
			if(result == 0)
			{
				System.out.println("获取状态报告成功！获取到的状态报告有" + rptList.size() + "条记录。");
				if(rptList != null && rptList.size() > 0)
				{
					// 打印状态报告
					printRptPack(rptList);
				}
			}
			else
			{
				System.out.println("获取状态报告失败，错误码为:" + result);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 打印上行信息
	 * 
	 * @param moPackList
	 */
	public static void printMoPack(List<MO> moList)
	{
		if(moList != null && moList.size() > 0)
		{
			MO mo = null;
			// 循环上行信息
			for (int i = 0; i < moList.size(); i++)
			{
				mo = moList.get(i);
				System.out.println("msgid:"+mo.getMsgid() + ",mobile:" + mo.getMobile() + ",spno:" + mo.getSpno() 
						+ ",exno:" + mo.getExno() + ",rtime:" + mo.getRtime() + ",content:" + mo.getContent());
			}
		}
		else
		{
			System.out.println("无上行");
		}
	}

	/**
	 * 打印状态报告信息
	 * 
	 * @param rptPackList
	 */
	public static void printRptPack(List<RPT> rptList)
	{
		if(rptList != null && rptList.size() > 0)
		{
			RPT rpt = null;
			// 循环状态报告信息
			for (int i = 0; i < rptList.size(); i++)
			{
				rpt = rptList.get(i);
				System.out.println("msgid:"+rpt.getMsgid() + ",custid:" + rpt.getCustid() + ",pknum:" + rpt.getPknum()
						+ ",pktotal:" + rpt.getPktotal() + ",mobile:" + rpt.getMobile() + ",spno:" + rpt.getSpno() 
						+ ",exno:" + rpt.getExno() + ",stime:" + rpt.getStime() + ",rtime:" + rpt.getRtime() 
						+ ",status:" + rpt.getStatus() + ",errcode:" + rpt.getErrcode() + ",exdata:" + rpt.getExdata()
						+ ",errdesc:"+rpt.getErrdesc());
			}
		}
		else
		{
			System.out.println("无状态报告");
		}
	}
	
	/**
	 * 无符号数字
	 * @param str 要验证的数字
	 * @return
	 */
	private static boolean  isUnSignDigit(String str){
		char[] num=str.toCharArray();
		for (int i = 0; i < num.length; i++)
		{
			if(!Character.isDigit(num[i])){
				return false;
			}
		}
		return true;
	}

	
	/**
	 * 查询剩余金额或条数接口
	 */
	public static void getRemains(CHttpPost cHttpPost)
	{
		try
		{
			// 将 userid转成大写,以防大小写不一致
			userid = userid.toUpperCase();
			// 对密码进行加密
			String encryptPwd = null;
			//判断密码是否加密。
			//密码加密，则对密码进行加密
			String timestamp=null;
			if(IS_ENCRYPT_PWD)
			{
				// 设置时间戳
			    timestamp = sdf.format(Calendar.getInstance().getTime());
				// 对密码进行加密
			    encryptPwd = cHttpPost.encryptPwd(userid,pwd, timestamp);
			}else
			{
				//不加密，不需要设置时间戳
				timestamp=null;
				encryptPwd=pwd;
			}

			// 查询余额接口。
			Remains remains = cHttpPost.getRemains(userid,encryptPwd,timestamp);

			if(remains!=null)
			{
				if(remains.getResult()==0)
				{
					if(remains.getChargetype()==0)
					{
						System.out.println("查询成功,剩余条数为：" + remains.getBalance()+"条");
					}else if(remains.getChargetype()==1)
					{
						System.out.println("查询成功,剩余金额为：" + remains.getMoney()+"元");
					}else
					{
						System.out.println("未知的计费类型,计费类型:"+remains.getChargetype());
					}
				}
				else
				{
					System.out.println("查询失败，错误码为：" + remains.getResult());
				}
			}else
			{
				System.out.println("查询失败。");
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}

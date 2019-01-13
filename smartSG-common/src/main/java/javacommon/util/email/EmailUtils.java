package javacommon.util.email;

import com.ibm.icu.text.SimpleDateFormat;
import com.sun.mail.util.MailSSLSocketFactory;
import javacommon.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.*;

/**
 * 
 * @author duwufeng
 * @date 2015年12月8日 下午3:58:31
 */
public class EmailUtils {
	public static final String MAIL_CONTENT_CHARSET = "text/html;charset=utf-8";
	public static Session getSession(final String from, final String password) {
		Properties props = new Properties();
		String smtpHostName = "smtp." + from.split("@")[1];
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", smtpHostName);
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.auth", "true");
        
        MailSSLSocketFactory sf = null;
		try {
			sf = new MailSSLSocketFactory();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);
        
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
            	//验证发送邮箱用户名和密码
                return new PasswordAuthentication(from, password);
            }
              
        });
        return session;
	}
	/**
	 * 发送邮件
	 * @param email
	 * @return
	 */
	public static int sendToUser(GetEmail email) {
		int result = 0;
		Session session = getSession(email.getFrom(), email.getPassword());
		MimeMessage message = new MimeMessage(session);
		try {
			message.setSubject(email.getSubject());
			message.setSentDate(new Date());
			message.setFrom(new InternetAddress(email.getFrom()));
			message.setRecipient(RecipientType.TO, new InternetAddress(email.getToList().get(0)));
			message.setContent(email.getContent(), MAIL_CONTENT_CHARSET);
			Transport.send(message);// 发送邮件  
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 群发邮件
	 * @param email
	 * @return
	 */
	public static int sendToUsers(GetEmail email) {
		int result = 0;
		Session session = getSession(email.getFrom(), email.getPassword());
		MimeMessage message = new MimeMessage(session);
		List<String> list = email.getToList();
		try {
			InternetAddress[] toAddresses = new InternetAddress[list.size()];
			for (int i = 0; i < list.size(); i++) {
				toAddresses[i] = new InternetAddress(list.get(i));
			}
			message.setRecipients(RecipientType.TO, toAddresses);
			message.setSubject(email.getSubject());
			message.setSentDate(new Date());
			message.setFrom(new InternetAddress(email.getFrom()));
			message.setContent(email.getContent(), MAIL_CONTENT_CHARSET);
			Transport.send(message);// 发送邮件  
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 发送邮件附带附件
	 * @param email
	 * @return
	 */
	public static int sendToUserWidth(GetEmail email) {
		int result = 0;
		Session session = getSession(email.getFrom(), email.getPassword());
		MimeMessage message = new MimeMessage(session);
		try {
			message.setSubject(email.getSubject());
			message.setSentDate(new Date());
			message.setFrom(new InternetAddress(email.getFrom()));
			message.setRecipient(RecipientType.TO, new InternetAddress(email.getToList().get(0)));
			
			MimeMultipart mainMultipart = new MimeMultipart("mixed"); // 混合的组合关系
			message.setContent(mainMultipart);
			
			
			// 处理附件
			List<String> files = email.getFiles();
			for (String file : files) {
				MimeBodyPart attch = new MimeBodyPart();
				DataSource dataSource = new FileDataSource(new File(file));
				DataHandler dataHandler = new DataHandler(dataSource);
				attch.setDataHandler(dataHandler);
				attch.setFileName(file.substring(file.indexOf("/"), file.length()));
				mainMultipart.addBodyPart(attch);
			}
			
			// 正文内容
			MimeBodyPart contentPart = new MimeBodyPart();
			mainMultipart.addBodyPart(contentPart);
			contentPart.setContent(email.getContent(), MAIL_CONTENT_CHARSET);
			
			Transport.send(message);// 发送邮件
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 发送邮件附带附件
	 * @param email
	 * @return
	 */
	public static int sendToUserWidthNetFile(HttpServletRequest request, GetEmail email) {
		int result = 0;
		Session session = getSession(email.getFrom(), email.getPassword());
		MimeMessage message = new MimeMessage(session);
		try {
			message.setSubject(email.getSubject());
			message.setSentDate(new Date());
			message.setFrom(new InternetAddress(email.getFrom()));
			message.setRecipient(RecipientType.TO, new InternetAddress(email.getToList().get(0)));
			
			MimeMultipart mainMultipart = new MimeMultipart("mixed"); // 混合的组合关系
			message.setContent(mainMultipart);
			
			List<File> deleteFiles = new ArrayList<>();
			// 处理附件
			List<String> files = email.getFiles();
			File ss = null;
			for (String file : files) {
				MimeBodyPart attch = new MimeBodyPart();
				
				//String name = file;
				int i = file.lastIndexOf("/");
				String image = file.substring(i+1);
				
				ss = writeFileToUpload(request, file, image);
				deleteFiles.add(ss);
				message.setFileName(image);
				DataSource dataSource = new FileDataSource(ss);
				DataHandler dataHandler = new DataHandler(dataSource);
				attch.setDataHandler(dataHandler);
				attch.setFileName(file.substring(file.indexOf("/"), file.length()));
				mainMultipart.addBodyPart(attch);
			}
			
			// 正文内容
			MimeBodyPart contentPart = new MimeBodyPart();
			mainMultipart.addBodyPart(contentPart);
			contentPart.setContent(email.getContent(), MAIL_CONTENT_CHARSET);
			
			Transport.send(message);// 发送邮件
			ss.delete();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) throws MalformedURLException, FileNotFoundException {
		test1();
//		readEmail();
	}
	public static File writeFileToUpload(HttpServletRequest request, String netPath, String fileName) {
		File path = new File("/root/email");//linix系统
		//File path = new File("E:/test/email");//windows系统
		//String path = request.getSession().getServletContext().getRealPath("/")+"upload/";
		byte[] data = EmailUtils.getImageFromNetByUrl(netPath);
		if(fileName == null || fileName.trim().length() == 0) {
			String _fileName = UUID.getUUID();
			fileName = _fileName + ".jpg";
		}
		File file = new File(path + fileName);
		FileOutputStream outStream;
		try {
			outStream = new FileOutputStream(file);
			outStream.write(data);
			outStream.close();  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}
	public static byte[] getImageFromNetByUrl(String strUrl){  
        try {  
            URL url = new URL(strUrl);  
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
            conn.setRequestMethod("GET");  
            conn.setConnectTimeout(5 * 1000);  
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据  
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
            byte[] buffer = new byte[1024];  
            int len = 0;  
            while( (len=inStream.read(buffer)) != -1 ){  
                outStream.write(buffer, 0, len);  
            }  
            inStream.close();  
            return outStream.toByteArray();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    } 
	
	private static String from = "";
	private static String password = "";
	public static void initSendUser() {
		from = "wufeng_du@163.com";
		password = "a123456";
	}
	public static void test1() {
		initSendUser();
		GetEmail email = new GetEmail();
		email.setSubject("帐户激活邮件");
		email.setContent("<a href='http://www.baidu.com'>点击激活帐户</a>");
		email.setFrom(from);
		email.setPassword(password);
		email.addTo("635915376@qq.com");
		EmailUtils.sendToUser(email);
	}
	
	public static void test2() {
		initSendUser();
		GetEmail email = new GetEmail();
		email.setSubject("帐户激活邮件");
		email.setContent("<a href='http://www.baidu.com'>点击激活帐户</a>");
		email.setFrom(from);
		email.setPassword(password);
		email.addTo("455565973@qq.com");
		email.addFile("http://chun.blob.core.chinacloudapi.cn/public/9acd6eae91c84a67be89f23d4fb6318d.png");
		email.addFile("E:/test_2.txt");
		EmailUtils.sendToUserWidth(email);
	}
	
	public static void readEmail() {
		try {  
			initSendUser();
            Properties props = System.getProperties();  
            props.setProperty("mail.store.protocol", "pop3");  
            props.setProperty("mail.pop3.host", "pop3.163.com");
            Session session=Session.getInstance(props);
            Store store = session.getStore("pop3");  
            session.setDebug(false); 
			store.connect("pop3.163.com", from, password);  
			Folder folder = store.getFolder("INBOX"); 
			folder.open(Folder.READ_WRITE);  
//			Message[] messages = folder.getMessages();
			System.out.println(folder.getUnreadMessageCount());
			Message[] messages = folder.getMessages(folder.getMessageCount() - folder.getUnreadMessageCount()+1,folder.getMessageCount());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Map<String, Object>> emails = new ArrayList<>();
			for (Message message2 : messages) {
				MimeMessage message = (MimeMessage) message2; 
				Map<String, Object> email = new HashMap<>();
				InternetAddress fromAddress = getFrom(message);
				Date date = message.getSentDate();
				email.put("from", fromAddress.getAddress());
				email.put("sendDate", dateFormat.format(date));
				email.put("subject", message.getSubject());
				Object msgContent = message.getContent();
				if(msgContent instanceof MimeMultipart) {
					MimeMultipart content = (MimeMultipart) msgContent;
					int count = content.getCount();
					
					for (int i = 0; i < count; i++) {
						BodyPart bodyPart = content.getBodyPart(i);
						String contentType = bodyPart.getContentType();
						if(contentType.indexOf("text/plain") != -1) {
							email.put("content", bodyPart.getContent());
							
						} else if(contentType.indexOf("text/html") != -1) {
							email.put("html-content", bodyPart.getContent());
							
						} else if(bodyPart.isMimeType("multipart/*")) {
							saveAttachment(message, "ddddd", fromAddress.getAddress(), fromAddress.getPersonal());   
						} else if(bodyPart.isMimeType("application/*")) {
							saveAttachment(message, "E://test/", fromAddress.getAddress(), fromAddress.getPersonal());   
						}
					}
					
				} else if(msgContent instanceof String) {
					email.put("content", msgContent.toString().replace("\r\n", ""));
				}
				emails.add(email);
			}
			for (Map<String, Object> map : emails) {
				System.out.println(map);
			}
			folder.close(false);  
			store.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
	/**    
     * 保存附件    
     * @param part 邮件中多个组合体中的其中一个组合体    
     * @param destDir  附件保存目录    
     * @throws UnsupportedEncodingException    
     * @throws MessagingException    
     * @throws FileNotFoundException    
     * @throws IOException    
     */    
    public static void saveAttachment(Part part, String destDir,String email,String sendName) throws UnsupportedEncodingException, MessagingException,    
            FileNotFoundException, IOException {    
        if (part.isMimeType("multipart/*")) {    
            Multipart multipart = (Multipart) part.getContent();    //复杂体邮件    
            //复杂体邮件包含多个邮件体    
            int partCount = multipart.getCount();    
            for (int i = 0; i < partCount; i++) {    
                //获得复杂体邮件中其中一个邮件体    
                BodyPart bodyPart = multipart.getBodyPart(i);    
                //某一个邮件体也有可能是由多个邮件体组成的复杂体    
                String disp = bodyPart.getDisposition();    
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {    
                    InputStream is = bodyPart.getInputStream();    
                    saveFile(is, destDir, decodeText(bodyPart.getFileName()),email,sendName);    
                } else if (bodyPart.isMimeType("multipart/*")) {    
                    saveAttachment(bodyPart,destDir,email,sendName);    
                } else {    
                    String contentType = bodyPart.getContentType();    
                    if (contentType.indexOf("name") != -1 || contentType.indexOf("application") != -1) {    
                        saveFile(bodyPart.getInputStream(), destDir, decodeText(bodyPart.getFileName()),email,sendName);    
                    }    
                }    
            }    
        } else if (part.isMimeType("message/rfc822")) {    
            saveAttachment((Part) part.getContent(),destDir,email,sendName);    
        }    
    }
    /**    
     * 读取输入流中的数据保存至指定目录    
     * @param is 输入流  
     * @param fileName 文件名    
     * @param destDir 文件存储目录    
     * @throws FileNotFoundException    
     * @throws IOException    
     */    
    private static void saveFile(InputStream is, String destDir, String fileName,String email,String sendName)    
            throws FileNotFoundException, IOException {    
        //附件格式过滤  
       File file = new File(destDir + fileName);
       BufferedInputStream bis = new BufferedInputStream(is);    
       BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));    
       int len = -1;    
       while ((len = bis.read()) != -1) {    
           bos.write(len);  
           bos.flush();  
       }  
       bos.close();    
       bis.close();
    }
    public static String decodeText(String encodeText) throws UnsupportedEncodingException {    
        if (encodeText == null || "".equals(encodeText)) {    
            return "";    
        } else {  
            return MimeUtility.decodeText(encodeText);    
        }    
    }    
	/**  
     * 判断邮件中是否包含附件  
     * @param msg 邮件内容  
     * @return 邮件中存在附件返回true，不存在返回false  
     * @throws MessagingException  
     * @throws IOException  
     */    
    public static boolean isContainAttachment(Part part) throws MessagingException, IOException {    
        boolean flag = false;    
        if (part.isMimeType("multipart/*")) {    
            MimeMultipart multipart = (MimeMultipart) part.getContent();    
            int partCount = multipart.getCount();    
            for (int i = 0; i < partCount; i++) {    
                BodyPart bodyPart = multipart.getBodyPart(i);    
                String disp = bodyPart.getDisposition();    
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {    
                    flag = true;    
                } else if (bodyPart.isMimeType("multipart/*")) {    
                    flag = isContainAttachment(bodyPart);    
                } else {    
                    String contentType = bodyPart.getContentType();    
                    if (contentType.indexOf("application") != -1) {    
                        flag = true;    
                    }      
                        
                    if (contentType.indexOf("name") != -1) {    
                        flag = true;    
                    }     
                }    
                    
                if (flag) break;    
            }    
        } else if (part.isMimeType("message/rfc822")) {    
            flag = isContainAttachment((Part)part.getContent());    
        }    
        return flag;    
    }    
	/**  
     * 获得邮件发件人  
     * @param msg 邮件内容 
     * @return 地址  
     * @throws MessagingException  
     * @throws UnsupportedEncodingException   
     */  
    private static InternetAddress getFrom(Message msg) throws MessagingException, UnsupportedEncodingException {    
        Address[] froms = msg.getFrom();    
        if (froms.length < 1)    
            throw new MessagingException("没有发件人!");    
          
        return (InternetAddress) froms[0];    
    }    
}

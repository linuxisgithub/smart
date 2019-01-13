package javacommon.util.email;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author duwufeng
 * @date 2015年12月8日 下午3:58:36
 */
public class GetEmail {
	/**--邮件标题--**/
	private String subject;
	/**--邮件正文内容--**/
	private String content;
	/**--邮件发送者账号--**/
	private String from;
	/**--邮件发送者密码--**/
	private String password;
	/**--接收人--**/
	private List<String> toList = new ArrayList<String>();
	/**--附件：文件路径--**/
	private List<String> files = new ArrayList<>();
	
	public List<String> getFiles() {
		return files;
	}
	public void setFiles(List<String> files) {
		this.files = files;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<String> getToList() {
		return toList;
	}
	public void setToList(List<String> toList) {
		this.toList = toList;
	}
	public void addTo(String to) {
		this.toList.add(to);
	}
	
	public void addFile(String file) {
		this.files.add(file);
	}
	@Override
	public String toString() {
		return "GetEmail [subject=" + subject + ", content=" + content
				+ ", from=" + from + ", password=" + password + ", toList="
				+ toList + ", files=" + files + "]";
	}
}

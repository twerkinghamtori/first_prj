package model;
/*
CREATE TABLE member(
	emailaddress varchar(100) PRIMARY KEY,
	password varchar(100),
	nickname varchar(50) unique,
	picture varchar(100),
	regdate TIMESTAMP DEFAULT NOW(),
);
*/
import java.util.Date;

public class Member {
	private String emailaddress;
//	public String getEmail1() {
//		return email1;
//	}
//	public void setEmail1(String email1) {
//		this.email1 = email1;
//	}
//	public String getEmail2() {
//		return email2;
//	}
//	public void setEmail2(String email2) {
//		this.email2 = email2;
//	}
	private String password;
	private String nickname;
	private String picture;
//	private String email1;
//	private String email2;
	private Date regdate;
	
	public String getEmailaddress() {
		return emailaddress;
	}
	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	
}
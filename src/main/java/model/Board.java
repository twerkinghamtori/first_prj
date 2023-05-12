package model;
/*
CREATE TABLE board1 (
  no INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT null,
  nickname VARCHAR(255) not null,
  content TEXT NOT null,
  file1 VARCHAR(255),
  boardType INT,
  regdate DATETIME DEFAULT NOW(),
  hit INT DEFAULT 0,
  recommend INT DEFAULT 0,
  pub INT DEFAULT 1
);
	boardType : 1은 자유게시판글 2는 질문게시판글 3은 후기게시판글 4는 공지사항
	*/
import java.util.Date;

public class Board {
	private int no;
	private String nickname;
	private String title;
	private String content;
	private Date regdate;
	private String boardType;
	private int hit;
	private int recommend;
	private int pub;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public String getBoardType() {
		return boardType;
	}
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getRecommend() {
		return recommend;
	}
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	public int getPub() {
		return pub;
	}
	public void setPub(int pub) {
		this.pub = pub;
	}
	
}

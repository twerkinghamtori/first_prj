package model;
/*
CREATE TABLE comment (
	  no INT NOT NULL,
	  seq INT NOT NULL,
	  nickname VARCHAR(50) NOT NULL,
	  content TEXT,
	  regdate DATETIME DEFAULT NOW(),
	  recommend INT DEFAULT 0,
	  PRIMARY KEY (no,seq),
	  grp INT,
	  grpLevel INT,
	  grpStep INT,
	  FOREIGN KEY (NO) REFERENCES board(no) ON DELETE CASCADE,
	  FOREIGN KEY (nickname) REFERENCES member(nickname) ON DELETE CASCADE
);
*/

import java.util.Date;
public class Comment {
	private int no;
	private int seq;
	private String nickname;
	private String content;
	private Date regdate;
	private int recommend;
	private int grp;
	private int grpLevel;
	private int grpStep;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	public int getRecommend() {
		return recommend;
	}
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	public int getGrp() {
		return grp;
	}
	public void setGrp(int grp) {
		this.grp = grp;
	}
	public int getGrpLevel() {
		return grpLevel;
	}
	public void setGrpLevel(int grpLevel) {
		this.grpLevel = grpLevel;
	}
	public int getGrpStep() {
		return grpStep;
	}
	public void setGrpStep(int grpStep) {
		this.grpStep = grpStep;
	}
	@Override
	public String toString() {
		return "Comment [no=" + no + ", seq=" + seq + ", nickname=" + nickname + ", content=" + content + ", regdate="
				+ regdate + ", recommend=" + recommend + ", grp=" + grp + ", grpLevel=" + grpLevel + ", grpStep="
				+ grpStep + "]";
	}
	
	
}

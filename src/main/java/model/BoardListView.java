package model;

public class BoardListView extends Board {
	private String picture;
	private String commCnt;
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getCommCnt() {
		return commCnt;
	}
	public void setCommCnt(String commCnt) {
		this.commCnt = commCnt;
	}
	@Override
	public String toString() {
		return "BoardListView [picture=" + picture + ", commCnt=" + commCnt + "]";
	}
	
}

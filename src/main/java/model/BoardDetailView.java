package model;

public class BoardDetailView extends Board {
	private String picture;

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Override
	public String toString() {
		return "BoardDetailView [picture=" + picture + "]";
	}
	
}

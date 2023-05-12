package model;


public class ReleaseCard {
	private String agentsite;
	private String thumbnailUrl; 
	private String productName;
	private String releaseTime;
	private String additionalInfo;
	private String no;
	public String getAgentsite() {
		return agentsite;
	}
	public void setAgentsite(String agentsite) {
		this.agentsite = agentsite;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	@Override
	public String toString() {
		return "ReleaseCard [agentsite=" + agentsite + ", thumbnailUrl=" + thumbnailUrl + ", productName=" + productName
				+ ", releaseTime=" + releaseTime + ", additionalInfo=" + additionalInfo + ", no=" + no + "]";
	}
	
	
}

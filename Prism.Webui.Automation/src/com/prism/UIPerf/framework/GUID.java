package com.prism.UIPerf.framework;

public class GUID {
	private long GUID;
	private String BelongsTo;
	private String tags;
	public GUID() {
		this.GUID = System.currentTimeMillis();
	}
	public GUID(String teamName, String purpose) {
		this.GUID = System.currentTimeMillis();
		this.BelongsTo = teamName+"/"+purpose;
		this.tags="";
	}
	public void addTagToGUID(String tag) {
		this.tags+="/"+tag+"/";
	}
	public void removeTagFromGUID(String tag) {
		this.tags = this.tags.replace("/"+tag+"/", "");
	}
	public long getGUID() {
		return this.GUID;
	}
	public String getTags() {
		return this.tags;
	}
	public String getTeamNamePurpose() {
		return this.BelongsTo;
	}
}

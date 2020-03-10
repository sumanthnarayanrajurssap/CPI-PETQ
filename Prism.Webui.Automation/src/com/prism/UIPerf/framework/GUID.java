package com.prism.UIPerf.framework;

class GUID {
	private long GUID;
	private String BelongsTo;
	private String tags;
	public GUID() {
		this.GUID = System.currentTimeMillis();
	}
	GUID(String teamName, String purpose) {
		this.GUID = System.currentTimeMillis();
		this.BelongsTo = teamName+"/"+purpose;
		this.tags="";
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
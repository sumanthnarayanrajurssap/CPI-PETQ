package com.prism.xpath.constants;

public interface DesignOthersXpathConstants {
	public static final String DEPLOYSTATUS = "//*[contains(text(),'successfully deployed')]";
	public static final String YESPOPUP = "//bdi[text()='Yes']";
	public static final String OKPOPUP = "//bdi[contains(text(),'OK')]";
	public static final String SAVEDTOASTMESSAGE = "//*[contains(text(),'saved')]";
	public static final String SAVEBUTTON = "//button[@title = 'Save']";
	public static final String DEPLOYBUTTON = "//bdi[text()='Save']/../../../..//bdi[text()='Deploy']";
	public static final String OPENRESOURCE = "//span[contains(text(),'CompoundEmployeeEntityquerySync0.xsd')]";
	public static final String RESOURCELINK = "//*[text()='Resources']/..";
	public static final String ACECONTENT = "//*[@class='ace_content']";
}

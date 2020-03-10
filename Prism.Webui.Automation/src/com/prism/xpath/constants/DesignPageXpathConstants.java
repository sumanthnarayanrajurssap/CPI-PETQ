package com.prism.xpath.constants;

public interface DesignPageXpathConstants {
	
	public static final String DESIGNCONTENTPACKAGES = "/shell/design/contentpackage/";
	public static final String VMHEADERACTIONBUTTON = "//*[contains(@id,'valueMappingPageHeaderTitle-actions')]//child::button[@title='Edit']";
	public static final String IFLOWEDITBUTTON = "//*[contains(@id,'iflowObjectPageHeader-actions')]//child::button[@title='Edit']";
	public static final String ODPEDITBUTTON = "//*[contains(@id,'odataLandingPageHeader-actions')]//child::button[@title='Edit']";
	public static final String ARTIFACTHEADERBUTTON = "//*[contains(@id,'typeHeader-bdi')]";
	public static final String VALUEMAPPINGHEADER = "//*[contains(@id,'cvmHeading-bdi')]";
	public static final String DATASOURCE = "//*[text()='Data Source']";
	public static final String YESBUTTON = "//*[text()='Yes']/../..";
	public static final String CLOSEBUTTON = "//*[text()='Close']/../..";
	public static final String CANCELBUTTON = "//*[contains(text(),'Cancel')]";
	
	public static final String ARTIFACTSECTION = "?section=ARTIFACTS";
	public static final String FILTERARTIFACT = "//*[@placeholder='Filter Artifacts']";
}

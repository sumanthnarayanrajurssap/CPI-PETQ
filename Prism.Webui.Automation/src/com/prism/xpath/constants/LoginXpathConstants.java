package com.prism.xpath.constants;

public interface LoginXpathConstants {
	
	public static final String CHROMEINITIALIZECOMMAND = "webdriver.chrome.driver";
	public static final String CHROMEDRIVERLOCATION = "./chromedriver_win32/chromedriver.exe";
	public static final String CHROME = "chrome";
	public static final String DEFAULTPOPUPSETTING = "profile.default_content_settings.popups";
	public static final String SAFEBROWSING = "safebrowsing.enabled";
	public static final String PREFS = "prefs";
	public static final String TESTTYPE = "--test-type";
	public static final String DISABLEEXT = "--disable-extensions";
	public static final String DIABLEINFOBAR = "--disable-infobars";
	public static final String CHROMESWITCHKEY = "chrome.switches";
	public static final String CHROMESWITCHVALUE = "--disable-translate";
	public static final String INCOGNITO = "--incognito";
	public static final String HANACLOUDTITLE = "//*[@id='shell--toolHeader']//child::*[text()='SAP Cloud Platform Integration']";
	public static final String USERNAME = "//input[(@placeholder='Email') or contains(@placeholder,'E-Mail')]";
	public static final String PASSWORD = "//input[contains(@placeholder,'Password')]";
	public static final String LOGON = "//button[text()='Log On'] | //input[@value='Log On']";
}
package com.webui.Others.webui.tooling;

import java.util.Arrays;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import com.prism.UIPerf.framework.Prism_SUPAFramework;
import com.prism.xpath.constants.LoginXpathConstants;
import com.webui.Design.automation.Prism_Design_Others;
import com.webui.Design.automation.Prism_Design_Page;

public 
	class 
		Prism_Selenium_Loginlogout 
			extends 
				Prism_Master_Class
					implements
						LoginXpathConstants
							{
	
	public WebDriver driver;
	public Prism_Design_Others pOthers;
	private LoginPage login;
    public Prism_Design_Page pDesign;

	@AfterTest
	public void scenarioQuit() {
    	driver.quit();
    }
	
	@Parameters({
		"URL", 
		"USERNAME", 
		"PASSWORD",
		"driverOption"
		})
   	@BeforeTest
	public void scenarioStart(
			String URL, 
			String USERNAME, 
			String PASSWORD,
			String driverOption
			
			) throws InterruptedException {
		
		Prism_SUPAFramework.changeproxySettings();
		if(driverOption.equalsIgnoreCase(CHROME)){
			Proxy proxy = new Proxy();
			proxy.setProxyType(Proxy.ProxyType.AUTODETECT);
			java.util.HashMap<String, Object> chromePrefs = new java.util.HashMap<String, Object>();
			chromePrefs.put(DEFAULTPOPUPSETTING, 0);
			chromePrefs.put(SAFEBROWSING, "true");
			System.setProperty(CHROMEINITIALIZECOMMAND,CHROMEDRIVERLOCATION);
			org.openqa.selenium.chrome.ChromeOptions options = new org.openqa.selenium.chrome.ChromeOptions();
			options.setHeadless(true);
			options.setExperimentalOption(PREFS, chromePrefs);
			options.addArguments(TESTTYPE);
			options.addArguments(DISABLEEXT);
			options.addArguments(DIABLEINFOBAR);
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setBrowserName(org.openqa.selenium.remote.BrowserType.CHROME);
			cap.setPlatform(Platform.WINDOWS);
			cap.setCapability(CHROMESWITCHKEY, Arrays.asList(CHROMESWITCHVALUE));
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(CHROMESWITCHKEY, Arrays.asList(INCOGNITO));
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			options.merge(cap);
			driver = new ChromeDriver(options);
			Prism_SUPAFramework.changeproxySettings();
			driver.navigate().to(URL);
			driver.manage().window().setSize(new Dimension(3500,2500));
		}
		
		login = new LoginPage(driver);
		login.login(USERNAME, PASSWORD);
	}
}
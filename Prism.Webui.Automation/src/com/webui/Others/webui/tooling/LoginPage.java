package com.webui.Others.webui.tooling;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.prism.xpath.constants.LoginXpathConstants;

class 
		LoginPage 
			extends 
				Prism_Master_Class 
					implements 
						LoginXpathConstants
							{
	private WebDriver driver;
	
	LoginPage(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this);
		this.driver=driver;
	}
	
	long login(String Username,String Password){
		elementsizesendValues(USERNAME, Username);
		elementsizesendValues(PASSWORD, Password);
		elementsizeClick(LOGON);
		try{
			webdriver_wait(HANACLOUDTITLE, 100);
			String pageTitle = driver
					.findElement(
						By
						.xpath(HANACLOUDTITLE))
						.getText();
			System.out.println(pageTitle.toUpperCase());
		}catch(Exception e){
			throw e;
		}
		webdriver_wait(HANACLOUDTITLE, 70);
		try{
			List<WebElement> hanacloudTitle = driver.findElements(By.xpath(HANACLOUDTITLE));
			int hanacloudTitleSize = hanacloudTitle.size();
			if(hanacloudTitleSize>0){
				String titlepage = hanacloudTitle.get(hanacloudTitle.size()-hanacloudTitleSize).getText();
				if(titlepage.equals("SAP Cloud Platform Integration")){
					System.out.println("Logged into System SuccessFully");
				}
			}else{
				webdriver_wait(HANACLOUDTITLE, 60);
				String pageTitle = driver.findElement(By.xpath(HANACLOUDTITLE)).getText();
				if(pageTitle.equals("SAP Cloud Platform Integration")){
					System.out.println("Logged into System SuccessFully");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
}
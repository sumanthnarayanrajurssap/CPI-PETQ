package com.webui.Others.webui.tooling;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends Prism_Master_Class{
	public WebDriver driver;
	protected String hanaCloudTitle = "//*[@id='shell--toolHeader']//child::*[text()='SAP Cloud Platform Integration']";
	
	public LoginPage(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this);
		this.driver=driver;
	}
	
	public long login(String Username,String Password){
		elementsizesendValues("//input[(@placeholder='Email') or contains(@placeholder,'E-Mail')]", Username);
		elementsizesendValues("//input[contains(@placeholder,'Password')]", Password);
		elementsizeClick("//button[text()='Log On'] | //input[@value='Log On']");
		try{
			webdriver_wait(hanaCloudTitle, 100);
			String pageTitle = driver.findElement(By.xpath(hanaCloudTitle)).getText();
			System.out.println(pageTitle.toUpperCase());
		}catch(Exception e){
			e.printStackTrace();
		}
		webdriver_wait(hanaCloudTitle, 70);
		try{
			List<WebElement> hanacloudTitle = driver.findElements(By.xpath("//*[@id='shell--toolHeader']//child::*[text()='SAP Cloud Platform Integration']"));
			int hanacloudTitleSize = hanacloudTitle.size();
			if(hanacloudTitleSize>0){
				String titlepage = hanacloudTitle.get(hanacloudTitle.size()-hanacloudTitleSize).getText();
				if(titlepage.equals("SAP Cloud Platform Integration")){
					System.out.println("Logged into System SuccessFully");
				}
			}else{
				webdriver_wait(hanaCloudTitle, 60);
				String pageTitle = driver.findElement(By.xpath(hanaCloudTitle)).getText();
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
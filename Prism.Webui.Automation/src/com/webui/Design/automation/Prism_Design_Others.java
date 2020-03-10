package com.webui.Design.automation;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.prism.UIPerf.framework.UIPerfConstants;
import com.prism.xpath.constants.DesignOthersXpathConstants;
import com.webui.Others.webui.tooling.Prism_Master_Class;

public 
	class 
		Prism_Design_Others 
			extends 
				Prism_Master_Class 
					implements
						DesignOthersXpathConstants
							{
	
	private WebDriver driver;
	private long lStartTime;
	private long lEndTime;
	private long responseTime;
	
	@FindBy(xpath=DEPLOYSTATUS)
	private WebElement successfullDeplyment;
	
	@FindBy(xpath=YESPOPUP)
	private WebElement Deploy_YES_Confirmation_Button;
	
	public Prism_Design_Others(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this);
		this.driver = driver;
		new Actions(driver);
	}
	
public long save(Boolean ENABLEJVMPROFILING){
	lStartTime = System.currentTimeMillis();
	elementsizeClick(SAVEBUTTON);
	webdriver_wait(SAVEDTOASTMESSAGE, UIPerfConstants.timeout);
	try{
		List<WebElement> Save = driver.findElements(By.xpath(SAVEDTOASTMESSAGE));
		int saveconfirmatioSize = Save.size();
		if(saveconfirmatioSize>0){
			String saveConfirmation = Save.get(Save.size()-saveconfirmatioSize).getText();
			System.out.println("Iflow Saved SuccessFully "+saveConfirmation);
		}else{
			String saveConfirmation = driver.findElement(By.xpath(SAVEDTOASTMESSAGE)).getText();
			System.out.println("Iflow Saved SuccessFully "+saveConfirmation);
		}
		String saveConfirmation = driver.findElement(By.xpath(SAVEDTOASTMESSAGE)).getText();
		System.out.println("Iflow Saved SuccessFully "+saveConfirmation);
	}catch(Exception e){
		e.printStackTrace();
	}
	lEndTime = System.currentTimeMillis();
	responseTime = lEndTime - lStartTime;
	return responseTime;
}

	public long deploy(Boolean ENABLEJVMPROFILING){
		elementsizeClick(DEPLOYBUTTON);
		elementsizeClick(YESPOPUP);
		lStartTime = System.currentTimeMillis();
		elementsizeClick(OKPOPUP);
	try {
		webdriver_webelement_wait(successfullDeplyment, UIPerfConstants.timeout);
		successfullDeplyment.click();
		String deploymentStatus = successfullDeplyment.getText();
		System.out.println("Status of the Deployment : "+deploymentStatus.toUpperCase());
	} catch (Exception e2) {
		throw e2;
	}
    lEndTime = System.currentTimeMillis();
	responseTime = lEndTime - lStartTime;
	return responseTime;
}

public long deploy_VM(Boolean ENABLEJVMPROFILING){
	elementsizeClick(DEPLOYBUTTON);
	webElementsizeClick(Deploy_YES_Confirmation_Button);
	lStartTime = System.currentTimeMillis();
	try {
		webdriver_webelement_wait(successfullDeplyment, UIPerfConstants.timeout);
		successfullDeplyment.click();
		String deploymentStatus = successfullDeplyment.getText();
		System.out.println("Status of the Deployment : "+deploymentStatus.toUpperCase());
	} catch (Exception e2) {
		throw e2;
	}
    lEndTime = System.currentTimeMillis();
	responseTime = lEndTime - lStartTime;
	return responseTime;
}

	 public long prism_selenium_openResource(String resource, Boolean ENABLEJVMPROFILING){
			increaseparameterwindowSize();
			elementsizeClick(RESOURCELINK);
			lStartTime = System.currentTimeMillis();
			elementsizeClick("//a[text()='"+resource+"']");
			elementsizeClick(OPENRESOURCE);
			webdriver_wait(ACECONTENT, 100);
			lEndTime = System.currentTimeMillis();
			responseTime = lEndTime - lStartTime;
			return responseTime;
		}
}
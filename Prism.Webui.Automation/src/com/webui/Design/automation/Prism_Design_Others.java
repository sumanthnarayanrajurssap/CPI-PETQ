package com.webui.Design.automation;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.prism.UIPerf.framework.UIPerfConstants;
import com.webui.Others.webui.tooling.Prism_Master_Class;

public class Prism_Design_Others extends Prism_Master_Class {
	
	public WebDriver driver;
	public Actions action;
	private long lStartTime;
	private long lEndTime;
	private long responseTime;
	
	@FindBy(xpath="//*[contains(text(),'successfully deployed')]")
	private WebElement successfullDeplyment;
	
	@FindBy(xpath="//bdi[text()='Yes']")
	private WebElement Deploy_YES_Confirmation_Button;
	
	public Prism_Design_Others(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this);
		this.driver = driver;
		action=new Actions(driver);
	}
	
public void prism_design_saveClick() {
	elementsizeClick("//*[text()='Save as version']");
	waitforLoadingwebpage(2);
	elementsizesendValues("//input[contains(@id,input) and @maxlength='255']", "1.0.1");
	sleep(500);
	elementsizeClick("//bdi[contains(text(),'OK')]");
	try{ 
		webdriver_wait("//*[contains(text(),'saved')]", 90);
		String saveConfirmation = driver.findElement(By.xpath("//*[contains(text(),'saved')]")).getText();
		System.out.println("Iflow Saved SuccessFully "+saveConfirmation);
	}catch(Exception e){
		System.out.println("Saved Popup : \n ********"+e.getMessage()+"\n ***********");
	}
	sleep(1000);
	elementsizeClick("//*[text()='Deploy']/../../../..//*[text()='Edit']");
	try {
		webdriver_wait("//*[text()='Yes']/../../..", 2);
		waitforLoadingwebpage(20);
		driver.findElement(By.xpath("//*[text()='Yes']/../../..")).click();
	} catch (Exception e) {
		e.printStackTrace();
	}
}

public long save(Boolean ENABLEJVMPROFILING){
	
	System.out.println("Enetered into Save Project Step");
	
	String saveButton = "//button[@title = 'Save']";
	String saveConfirmationPopUp = "//*[contains(text(),'saved')]";
	
	lStartTime = System.currentTimeMillis();
	elementsizeClick(saveButton);
	webdriver_wait(saveConfirmationPopUp, UIPerfConstants.timeout);
	try{
		List<WebElement> Save = driver.findElements(By.xpath(saveConfirmationPopUp));
		int saveconfirmatioSize = Save.size();
		if(saveconfirmatioSize>0){
			String saveConfirmation = Save.get(Save.size()-saveconfirmatioSize).getText();
			System.out.println("Iflow Saved SuccessFully "+saveConfirmation);
		}else{
			String saveConfirmation = driver.findElement(By.xpath(saveConfirmationPopUp)).getText();
			System.out.println("Iflow Saved SuccessFully "+saveConfirmation);
		}
		String saveConfirmation = driver.findElement(By.xpath(saveConfirmationPopUp)).getText();
		System.out.println("Iflow Saved SuccessFully "+saveConfirmation);
	}catch(Exception e){
		e.printStackTrace();
	}
	lEndTime = System.currentTimeMillis();
	responseTime = lEndTime - lStartTime;
	return responseTime;
}

	public long deploy(Boolean ENABLEJVMPROFILING){
		elementsizeClick("//bdi[text()='Save']/../../../..//bdi[text()='Deploy']");
		elementsizeClick("//bdi[text()='Yes']");
		lStartTime = System.currentTimeMillis();
		elementsizeClick("//bdi[text()='OK']");
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
	elementsizeClick("//bdi[text()='Save']/../../../..//bdi[text()='Deploy']");
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

	public void sleepTime(long time){
		for (int i = 0; i < time; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	 public void clickOnCanvas() {
		 sleep(500);
	     WebElement galileiCanvasElement = driver.findElement(By.xpath("//*[@id='svgMainShapeLayer-1']"));
	     Point location = galileiCanvasElement.getLocation();	 
	     action.moveToElement(galileiCanvasElement, location.getX() + 50, location.getY() + 50).click().build().perform();	   
	     sleep(500);
	 }
	 
	 public long prism_selenium_openResource(String resource, Boolean ENABLEJVMPROFILING){
			String resourceTab = "//*[text()='Resources']/..";
			increaseparameterwindowSize();
			elementsizeClick(resourceTab);
			lStartTime = System.currentTimeMillis();
			elementsizeClick("//a[text()='"+resource+"']");
			elementsizeClick("//span[contains(text(),'CompoundEmployeeEntityquerySync0.xsd')]");
			try {
				webdriver_wait("//*[@class='ace_content']", 100);
			} catch (Exception e) {
				throw e;
			}
			lEndTime = System.currentTimeMillis();
			responseTime = lEndTime - lStartTime;
			return responseTime;
		}
	 
	 public long prism_selenium_openMessageMapping(String resource, Boolean ENABLEJVMPROFILING){
			action.moveToElement(driver.findElement(By.xpath("//*[contains(@sap-automation,'Message Mapping')]"))).click().build().perform();
			increaseparameterwindowSize();
			elementsizeClick("//*[contains(@id,'blTopArea')]//following::div[@role='tab']//child::div[text()='Processing']");
			lStartTime = System.currentTimeMillis();
			try {
				webdriver_wait("//*[text()='Resource:']//following::a[contains(@id,'mappingpath') and @role='link']", 60);
				List<WebElement> element = driver.findElements(By.xpath("//*[text()='Resource:']//following::a[contains(@id,'mappingpath') and @role='link']"));
				int elementSize = element.size();
				if (elementSize > 1) {
					element.get(elementSize - elementSize).click();
				} else {
					webdriver_wait("//*[text()='Resource:']//following::a[contains(@id,'mappingpath') and @role='link']", 15);
					driver.findElement(By.xpath("//*[text()='Resource:']//following::a[contains(@id,'mappingpath') and @role='link']")).click();
				}
			} catch (Exception e) {
				throw e;
			}
			waitforLoadingwebpage();
			waitforMessageMappingLoading();
			try {
				webdriver_wait("//*[contains(@id,'splitter1_firstPane')]", 100);
			} catch (Exception e) {
				throw e;
			}
			lEndTime = System.currentTimeMillis();
			responseTime = lEndTime - lStartTime;
			return responseTime;
		}
}
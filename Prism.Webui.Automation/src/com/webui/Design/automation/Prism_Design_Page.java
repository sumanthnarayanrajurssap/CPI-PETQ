package com.webui.Design.automation; 

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import com.prism.UIPerf.framework.Prism_SUPAFramework;
import com.prism.UIPerf.framework.UIPerfConstants;
import com.webui.Others.webui.tooling.Prism_Master_Class;

public class Prism_Design_Page extends Prism_Master_Class {
	public WebDriver driver;
	List<WebElement> status;
	Actions action;
	
	private long lStartTime;
	private long lEndTime;
	private long responseTime;
	
	public static String newArtifactName=null;
	
	public Prism_Design_Page(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this);
		this.driver = driver;
		action=new Actions(driver);
	}
	
	public void navigate2IntegrationPackage(String URL,String packagename,String iflowname){
			driver.navigate().to(URL+"/shell/design/contentpackage/"+packagename+"/integrationflows/"+iflowname);
			sleep(2000);
			waitforDesignPage();
			waitforLoadingwebpage();
			sleep(2000);
	}
	
	public long clickEditVm(){
		lStartTime = System.currentTimeMillis();
		elementsizeClick("//*[contains(@id,'valueMappingPageHeaderTitle-actions')]//child::button[@title='Edit']");
		waitforDesignPage();
		waitUntilBusyIndicatorIsInvisible();
		lEndTime = System.currentTimeMillis();
		responseTime = lEndTime - lStartTime;
		return responseTime;
	}
	
	public long clickEdit(){
		lStartTime = System.currentTimeMillis();
		elementsizeClick("//*[contains(@id,'iflowObjectPageHeader-actions')]//child::button[@title='Edit']");
		waitforDesignPage();
		waitUntilBusyIndicatorIsInvisible();
		lEndTime = System.currentTimeMillis();
		responseTime = lEndTime - lStartTime;
		return responseTime;
	}
	
	public void editODP() {
		elementsizeClick("//*[contains(@id,'odataLandingPageHeader-actions')]//child::button[@title='Edit']");
		waitforDesignPage();
		waitUntilBusyIndicatorIsInvisible();
		waitforUiLoad();
	}
	
	public void openArtificats(){
		elementsizeClick("//a[text()='"+newArtifactName+"']");
		try {
			webdriver_wait("//label[text()='Integration Flow']", 60);
			driver.findElement(By.xpath("//label[text()='Integration Flow']")).getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void openArtifacts(String artifact){
		elementsizeClick("//*[text()='"+artifact+"']");
		waitUntilBusyIndicatorIsInvisible();
		try {
			webdriver_wait("//*[contains(@id,'typeHeader-bdi')]", UIPerfConstants.timeout);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void openArtifacts_VM(String artifact){
		elementsizeClick("//*[text()='"+artifact+"']");
		waitUntilBusyIndicatorIsInvisible();
		waitforUiLoad();
		try {
			webdriver_wait("//*[contains(@id,'cvmHeading-bdi')]", UIPerfConstants.timeout);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void openArtifacts_ODP(String artifact){
		elementsizeClick("//*[text()='"+artifact+"']");
		waitforDesignPage();
		waitUntilBusyIndicatorIsInvisible();
		waitforUiLoad();
		webdriver_wait("//*[text()='Data Source']", UIPerfConstants.timeout);
	}
	
	public void prism_design_close_all_popups(String URL) {
		String yesButton = "//*[text()='Yes']/../..";
		String closeButton = "//*[text()='Close']/../..";
		String cancelButton = "//*[contains(text(),'Cancel')]";
			if(driver.getCurrentUrl().equalsIgnoreCase(URL+"/#/shell/manage")) {
			}
			else {
				while(true) {
					boolean i=false;
					boolean exists;
					sleep(2000);
						driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
						exists = (driver.findElements( By.xpath(closeButton) ).size() != 0) && (driver.findElement(By.xpath(closeButton)).isDisplayed());
						driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
						if(exists) {
							sleep(2000);
							driver.findElement(By.xpath(closeButton)).click();
							i=true;
							sleep(1000);
						}
						driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
						exists = (driver.findElements( By.xpath(cancelButton) ).size() != 0) && (driver.findElement(By.xpath(cancelButton)).isDisplayed());
						driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
						if(exists) {
							sleep(2000);
							driver.findElement(By.xpath(cancelButton)).click();
							i=true;
							sleep(1000);
						}
					if(!i) break;
				}
			}
		try {
			boolean exists = (driver.findElements( By.xpath(yesButton) ).size() != 0) && (driver.findElement(By.xpath( yesButton)).isDisplayed());
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			if(exists) {
				driver.findElement(By.xpath(yesButton)).click();
				sleep(1000);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public long prism_UIPerf_OpenIflow(
			String URL, 
			String packageName, 
			String iflowName, 
			Prism_SUPAFramework supa2,
			Boolean JVMProfiling
			) {
		driver.navigate().to(URL+"/#/shell/design/contentpackage/"+packageName+"?section=ARTIFACTS");
		waitforDesignPage();
		waitUntilBusyIndicatorIsInvisible();
		waitforUiLoad();
		sleep(1000);
		elementsizesendValues("//*[@placeholder='Filter Artifacts']", iflowName);
		return openIflow(iflowName,supa2,JVMProfiling);
	}
	
	public long prism_UIPerf_OpenIflow_VM(
			String URL, 
			String packageName, 
			String iflowName, 
			Prism_SUPAFramework supa2,
			Boolean JVMProfiling) {
		driver.navigate().to(URL+"/#/shell/design/contentpackage/"+packageName+"?section=ARTIFACTS");
		waitforDesignPage();
		waitUntilBusyIndicatorIsInvisible();
		return openIflow_VM(iflowName,supa2,JVMProfiling);
	}
	
	public long prism_UIPerf_OpenODP(
			String URL, 
			String packageName, 
			String iflowName, 
			Prism_SUPAFramework supa2,
			Boolean JVMProfiling
			) {
		driver.navigate().to(
				URL+"/#/shell/design/contentpackage/"+packageName+"?section=ARTIFACTS");
		waitforDesignPage();
		waitUntilBusyIndicatorIsInvisible();
		sleep(1000);
		return openIflow_ODP(iflowName,supa2,JVMProfiling);
	}
	
	public long openIflow_ODP(
			String iflowName, 
			Prism_SUPAFramework supa2,
			Boolean JVMProfiling
			) {
		if(supa2!=null) {
		supa2.startMeasurement("OpenODPProject");
		}
		long startTime= System.currentTimeMillis();
		openArtifacts_ODP(iflowName);
		long endTime = System.currentTimeMillis();
		if(supa2!=null) {
			try {
				supa2.stopMeasurement(1/*+(endTime-startTime)/1000*/);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return endTime-startTime;
	}
	
	public long openIflow_VM(
			String iflowName, 
			Prism_SUPAFramework supa2,
			Boolean JVMProfiling) {
		
		if(supa2!=null) {
		supa2.startMeasurement("OpenIflowTime"+iflowName);
		}
		long startTime= System.currentTimeMillis();
		openArtifacts_VM(iflowName);
		long endTime = System.currentTimeMillis();
		if(supa2!=null) {
			try {
				supa2.stopMeasurement(1/*10+(endTime-startTime)/1000*/);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return endTime-startTime;
	}
	
	public long openIflow(
			String iflowName, 
			Prism_SUPAFramework supa2,
			Boolean JVMProfiling) {
		if(supa2!=null) {
		supa2.startMeasurement("OpenIflowTime"+iflowName);
		}
		long startTime= System.currentTimeMillis();
		openArtifacts(iflowName);
		long endTime = System.currentTimeMillis();
		if(supa2!=null) {
			try {
				supa2.stopMeasurement(1/*10+(endTime-startTime)/1000*/);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return endTime-startTime;
	}
}
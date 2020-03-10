package com.webui.Design.automation; 

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.prism.UIPerf.framework.Prism_SUPAFramework;
import com.prism.UIPerf.framework.UIPerfConstants;
import com.prism.xpath.constants.DesignPageXpathConstants;
import com.webui.Others.webui.tooling.Prism_Master_Class;

public 
	class 
		Prism_Design_Page 
			extends 
				Prism_Master_Class 
					implements
						DesignPageXpathConstants
							{
	private WebDriver driver;
	private long lStartTime;
	private long lEndTime;
	private long responseTime;
	
	public Prism_Design_Page(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}
	
	public long clickEditVm(){
		lStartTime = System.currentTimeMillis();
		elementsizeClick(VMHEADERACTIONBUTTON);
		waitforDesignPage();
		waitUntilBusyIndicatorIsInvisible();
		lEndTime = System.currentTimeMillis();
		responseTime = lEndTime - lStartTime;
		return responseTime;
	}
	
	public long clickEdit(){
		lStartTime = System.currentTimeMillis();
		elementsizeClick(IFLOWEDITBUTTON);
		waitforDesignPage();
		waitUntilBusyIndicatorIsInvisible();
		lEndTime = System.currentTimeMillis();
		responseTime = lEndTime - lStartTime;
		return responseTime;
	}
	
	public void editODP() {
		elementsizeClick(ODPEDITBUTTON);
		waitforDesignPage();
		waitUntilBusyIndicatorIsInvisible();
		waitforUiLoad();
	}
	
	private void openArtifacts(String artifact){
		elementsizeClick("//*[text()='"+artifact+"']");
		waitUntilBusyIndicatorIsInvisible();
		waitforUiLoad();
		webdriver_wait(ARTIFACTHEADERBUTTON, UIPerfConstants.timeout);
	}
	
	private void openArtifacts_VM(String artifact){
		elementsizeClick("//*[text()='"+artifact+"']");
		waitUntilBusyIndicatorIsInvisible();
		waitforUiLoad();
		webdriver_wait(VALUEMAPPINGHEADER, UIPerfConstants.timeout);
	}
	
	private void openArtifacts_ODP(String artifact){
		elementsizeClick("//*[text()='"+artifact+"']");
		waitforDesignPage();
		waitUntilBusyIndicatorIsInvisible();
		waitforUiLoad();
		webdriver_wait(DATASOURCE, UIPerfConstants.timeout);
	}
	
	public void prism_design_close_all_popups(String URL) {
				while(true) {
					boolean i=false;
					boolean exists;
					sleep(2000);
						driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
						exists = (driver.findElements( By.xpath(CLOSEBUTTON) ).size() != 0) && (driver.findElement(By.xpath(CLOSEBUTTON)).isDisplayed());
						driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
						if(exists) {
							sleep(2000);
							driver.findElement(By.xpath(CLOSEBUTTON)).click();
							i=true;
							sleep(1000);
						}
						driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
						exists = (driver.findElements( By.xpath(CANCELBUTTON) ).size() != 0) && (driver.findElement(By.xpath(CANCELBUTTON)).isDisplayed());
						driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
						if(exists) {
							sleep(2000);
							driver.findElement(By.xpath(CANCELBUTTON)).click();
							i=true;
							sleep(1000);
						}
					if(!i) break;
				}
		try {
			boolean exists = (driver.findElements( By.xpath(YESBUTTON) ).size() != 0) && (driver.findElement(By.xpath(YESBUTTON)).isDisplayed());
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			if(exists) {
				driver.findElement(By.xpath(YESBUTTON)).click();
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
		driver.navigate().to(URL+DESIGNCONTENTPACKAGES+packageName+ARTIFACTSECTION);
		waitforDesignPage();
		waitUntilBusyIndicatorIsInvisible();
		waitforUiLoad();
		sleep(1000);
		elementsizesendValues(FILTERARTIFACT, iflowName);
		return openIflow(iflowName,supa2,JVMProfiling);
	}
	
	public long prism_UIPerf_OpenIflow_VM(
			String URL, 
			String packageName, 
			String iflowName, 
			Prism_SUPAFramework supa2,
			Boolean JVMProfiling) {
		driver.navigate().to(URL+DESIGNCONTENTPACKAGES+packageName+ARTIFACTSECTION);
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
				URL+DESIGNCONTENTPACKAGES+packageName+ARTIFACTSECTION);
		waitforDesignPage();
		waitUntilBusyIndicatorIsInvisible();
		sleep(1000);
		return openIflow_ODP(iflowName,supa2,JVMProfiling);
	}
	
	private long openIflow_ODP(
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
				supa2.stopMeasurement(1);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return endTime-startTime;
	}
	
	private long openIflow_VM(
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
				supa2.stopMeasurement(1);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return endTime-startTime;
	}
	
	private long openIflow(
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
				supa2.stopMeasurement(1);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return endTime-startTime;
	}
}
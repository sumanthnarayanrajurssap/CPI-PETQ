package com.webui.Others.webui.tooling;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import com.prism.UIPerf.framework.UIPerfConstants;
import com.prism.xpath.constants.PrismMasterClassXpathConstants;

public 
	class 
		Prism_Master_Class 
			implements
				PrismMasterClassXpathConstants{
	
	public WebDriver driver;
	public Actions action;
	
	@FindBy(xpath=WORKSPACEWINDOW)
	protected WebElement Workpsace_Window_Size;

	public Prism_Master_Class(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		action = new Actions(driver);
	}

	public Prism_Master_Class() {
	}
	
	public String prism_design_ReceiverElement(int xOffset,int yOffset){
		prism_design_panelClick("Participants");
		elementsizeClick(RECEIVER);
		elementmove(Workpsace_Window_Size, xOffset, yOffset);
	    WebElement generatedWebElement = null;
		String createdReceiver = null;
		String id= null;
		try {
			webdriver_wait(XPATHID, 1);
			createdReceiver =driver.findElement(By.xpath(XPATHID)).getAttribute("value");
			generatedWebElement = driver.findElement(By.xpath("//*[name()='g']//*[contains(@sap-automation,'"+createdReceiver+"')]"));
			id=generatedWebElement.getAttribute("id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public void webdriver_wait(String xpath_wait, int time_out) {
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(time_out))
					.pollingEvery(Duration.ofSeconds(1))
					.ignoring(Throwable.class);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath_wait)));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath_wait)));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath_wait)));
	}

	public void webdriver_webelement_wait(WebElement element, int time_out) {
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			.withTimeout(Duration.ofSeconds(time_out))
			.pollingEvery(Duration.ofSeconds(1))
			.ignoring(Throwable.class);
			wait.until(ExpectedConditions.visibilityOf(element));
			wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void webElementsizeClick(WebElement element) {
		try {
			webdriver_webelement_wait(element, 30);
			element.click();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void elementsizesendValues(String xpath, String values) {
		try {
			webdriver_wait(xpath, 60);
			List<WebElement> element = driver.findElements(By.xpath(xpath));
			int elementSize = element.size();
			if (elementSize > 1) {
				element.get(elementSize - elementSize).click();
				sleep(100);
				element.get(elementSize - elementSize).clear();
				sleep(100);
				element.get(elementSize - elementSize).sendKeys(values);
				sleep(500);
				element.get(elementSize - elementSize).click();
				sleep(100);
			} else {
				webdriver_wait(xpath, 30);
				driver.findElement(By.xpath(xpath)).click();
				sleep(100);
				driver.findElement(By.xpath(xpath)).clear();
				sleep(100);
				driver.findElement(By.xpath(xpath)).sendKeys(values);
				sleep(100);
				driver.findElement(By.xpath(xpath)).click();
				sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void elementsizeClick(String xpath) {
		try {
			webdriver_wait(xpath, 60);
			List<WebElement> element = driver.findElements(By.xpath(xpath));
			int elementSize = element.size();
			if (elementSize > 1) {
				element.get(elementSize - elementSize).click();
			} else {
				webdriver_wait(xpath, 100);
				driver.findElement(By.xpath(xpath)).click();
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void elementmove(WebElement element, int x, int y){
        try {
               webdriver_webelement_wait(element, 10);
               action.moveToElement(element).moveByOffset(-element.getSize().getWidth()/2 + x, -element.getSize().getHeight()/2 + y).clickAndHold().release().build().perform();
        } catch (Exception e) {
               throw e;
        }
	}

	public void prism_design_panelClick(String panel){
		String xpath = "//*[@class='modToolbar']//child::li[@title='"+panel+"']";
		waitforLoadingwebpage(2);
		decreaseparameterwindowSize();
		waitforLoadingwebpage(2);
		try {
			sleep(100);
			webdriver_wait(xpath, 10);
			List<WebElement> element = driver.findElements(By.xpath(xpath));
			int elementSize = element.size();
			if (elementSize > 1) {
				element.get(elementSize - elementSize).click();
			} else {
				webdriver_wait(xpath, 5);
				driver.findElement(By.xpath(xpath)).click();
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	 public void decreaseparameterwindowSize(){
		 elementsizeClick(PARAMETERMINIMIZE);
	 }
	 
	 public void increaseparameterwindowSize(){
		 elementsizeClick(PARAMETERMAXIMIZE);
	 }
	 
	 public void waitforMessageMappingLoading(){
				FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
						.withTimeout(Duration.ofSeconds(60))
						.pollingEvery(Duration.ofSeconds(1))
						.ignoring(Exception.class);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(WAITFORMAPPING)));
	 }
	 
	public void waitforLoadingwebpage(){
			FluentWait<WebDriver> wait = 
					new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(100))
					.pollingEvery(Duration.ofMillis(1000))
					.ignoring(Exception.class);
			wait.until(
					ExpectedConditions
					.invisibilityOfElementLocated(
							By.xpath(WAITFORPAGELOADING)));
	 	}
	 
		public void waitforLoadingwebpage(int timeout){
				FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
						.withTimeout(Duration.ofSeconds(timeout))
						.pollingEvery(Duration.ofMillis(1000))
						.ignoring(Exception.class);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(WAITFORPAGELOADING)));
		 	}
	 
	 public void waitforDesignPage(){
			 FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
						.withTimeout(Duration.ofSeconds(100))
						.pollingEvery(Duration.ofMillis(100))
						.ignoring(Exception.class);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(WAITFORDESIGNPAGE)));
	 }
	 
		 public void waitUntilBusyIndicatorIsInvisible() {
		        waitUntilElementIsInvisble(BUSYDIALOGUE);
		    }

		 public void waitforUiLoad(){
			 FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
						.withTimeout(Duration.ofMillis(UIPerfConstants.timeout*1000))
						.pollingEvery(Duration.ofMillis(100))
						.ignoring(Throwable.class);
			        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='operationBusyDialog-Dialog']")));
			        /*wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));*/
		 }
		 
		 public void waitUntilElementIsInvisble(String xpath) {
				 FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
							.withTimeout(Duration.ofMillis(UIPerfConstants.timeout*1000))
							.pollingEvery(Duration.ofMillis(100))
							.ignoring(Throwable.class);
				        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
		    }
}
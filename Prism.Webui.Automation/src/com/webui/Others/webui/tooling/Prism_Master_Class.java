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
	
	private WebDriver driver;
	@FindBy(xpath=WORKSPACEWINDOW)
	private WebElement Workpsace_Window_Size;

	public Prism_Master_Class(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		new Actions(driver);
	}

	public Prism_Master_Class() {
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
				element.get(0).click();
				sleep(100);
				element.get(0).clear();
				sleep(100);
				element.get(0).sendKeys(values);
				sleep(500);
				element.get(0).click();
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
			throw e;
		}
	}

	public void elementsizeClick(String xpath) {
		try {
			webdriver_wait(xpath, 60);
			List<WebElement> element = driver.findElements(By.xpath(xpath));
			int elementSize = element.size();
			if (elementSize > 1) {
				element.get(0).click();
			} else {
				webdriver_wait(xpath, 100);
				driver.findElement(By.xpath(xpath)).click();
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void increaseparameterwindowSize(){
		 elementsizeClick(PARAMETERMAXIMIZE);
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
						.withTimeout(Duration.ofSeconds(UIPerfConstants.timeout))
						.pollingEvery(Duration.ofMillis(100))
						.ignoring(Throwable.class);
			        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(BUSYDIALOGUE)));
		 }
		 
		 private void waitUntilElementIsInvisble(String xpath) {
				 FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
							.withTimeout(Duration.ofSeconds(UIPerfConstants.timeout))
							.pollingEvery(Duration.ofMillis(100))
							.ignoring(Throwable.class);
				        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
		    }
}
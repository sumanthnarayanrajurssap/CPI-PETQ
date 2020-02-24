package com.prism.xpath.constants;

public interface PrismMasterClassXpathConstants {
	
	final String WORKSPACEWINDOW = "//*[contains(@id, 'svgBackgroundPointerPanelLayer')]";
	final String XPATHID = "//input[contains(@id,'receiverSystemNameText')]";
	final String RECEIVER = "//li[@title='Receiver']";
	final String PARAMETERMINIMIZE = "//button[@title='Minimize']";
	final String PARAMETERMAXIMIZE = "//button[@title='Maximize']";
	final String WAITFORMAPPING = "//*[contains(@id,'busyIndicator') and @title='Please wait']";
	final String WAITFORPAGELOADING = "//*[contains(@id,'sap-ui-blocklayer-popup')][contains(@style,'visibility: visible')]";
	final String WAITFORDESIGNPAGE = "//*[@onload='onAppLoad()' and @aria-busy='true']";
	final String BUSYDIALOGUE = "//*[name()='div'][contains(@id,'operationBusyDialog')]";
}
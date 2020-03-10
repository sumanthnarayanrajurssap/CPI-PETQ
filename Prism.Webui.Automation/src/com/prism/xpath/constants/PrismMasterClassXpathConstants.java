package com.prism.xpath.constants;

public interface PrismMasterClassXpathConstants {
	
	final String WORKSPACEWINDOW = "//*[contains(@id, 'svgBackgroundPointerPanelLayer')]";
	final String PARAMETERMAXIMIZE = "//button[@title='Maximize']";
	final String WAITFORDESIGNPAGE = "//*[@onload='onAppLoad()' and @aria-busy='true']";
	final String BUSYDIALOGUE = "//*[name()='div'][contains(@id,'operationBusyDialog')]";
}
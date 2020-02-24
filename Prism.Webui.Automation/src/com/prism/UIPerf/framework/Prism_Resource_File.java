package com.prism.UIPerf.framework;

import java.io.IOException;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.webui.Others.webui.tooling.Prism_Selenium_Loginlogout;

public 
	class 
		Prism_Resource_File 
			extends 
				Prism_Selenium_Loginlogout
					{
	
	@Parameters({
		"compareTeamName",
		"comparePurpose",
		"teamName",
		"purpose",
		"tmnName",
		"providerSubAccountID",
		"ENABLEJVMPROFILING",
		"ENABLESUPA",
		"ListOfPackages",
		"ListOfIflows",
		"URL",
		"INVOCATIONCOUNT",
		"USERNAME",
		"PASSWORD",
		"Timeout",
		"IPAProject",
		"landscape",
		"grafanaURL"
		})
	@Test
	public void modelEverything(
			String compareTeamName, 
			String comparePurpose, 
			String teamName, 
			String purpose, 
			String tmnName, 
			String providerSubAccountID, 
			Boolean ENABLEJVMPROFILING,
			Boolean ENABLESUPA,
			String ListOfPackages,
			String ListOfIflows,
			String URL,
			int INVOCATIONCOUNT, 
			String USERNAME, 
			String PASSWORD, 
			String Timeout,
			String IPAProject,
			String landscape,
			String grafanaURL
			
			) {
		
		UIPerformanceBase perf = new UIPerformanceBase(
				driver, 
				UIPerformanceBase.ExecutionType.OpenResource,
				compareTeamName,
				comparePurpose,
				teamName,
				purpose,
				tmnName,
				providerSubAccountID,
				ENABLEJVMPROFILING,
				ENABLESUPA,
				ListOfPackages,
				ListOfIflows,
				URL,
				INVOCATIONCOUNT,
				USERNAME,
				PASSWORD,
				Timeout,
				IPAProject,
				landscape,
				grafanaURL
				);
		try {
			perf.initialProcess();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			perf.startSupaServer();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			try {
				perf.measurePerformance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				perf.collectData();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				perf.analyzeData();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		finally {
			perf.shutdownServer();
		}
	}
}
package com.prism.UIPerf.framework;

import java.io.IOException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.webui.Others.webui.tooling.Prism_Selenium_Loginlogout;



public class Prism_OpenIflowProject extends Prism_Selenium_Loginlogout{

	@Parameters({"compareTeamName","comparePurpose","teamName","purpose","tmnName","providerSubAccountID","ENABLEJVMPROFILING","ENABLESUPA","ListOfPackages","ListOfIflows","URL","INVOCATIONCOUNT","USERNAME","PASSWORD","Timeout","IPAProject","landscape","grafanaURL"})
	@Test
	public void modelEverything(String compareTeamName, String comparePurpose, String teamName, String purpose, String tmnName, String providerSubAccountID, Boolean ENABLEJVMPROFILING,Boolean ENABLESUPA,String ListOfPackages,String ListOfIflows,String URL,int INVOCATIONCOUNT, String USERNAME, String PASSWORD, String Timeout, String IPAProject,String landscape,String grafanaURL) throws IOException, Exception {
		UIPerformanceBase perf = new UIPerformanceBase(driver, UIPerformanceBase.ExecutionType.UIPerf,compareTeamName,comparePurpose,teamName,purpose,tmnName,providerSubAccountID,ENABLEJVMPROFILING,ENABLESUPA,ListOfPackages,ListOfIflows,URL,INVOCATIONCOUNT,USERNAME,PASSWORD,Timeout,IPAProject,landscape,grafanaURL);
		perf.initialProcess();
		perf.startSupaServer();
		try {
			perf.measurePerformance();
			perf.collectData();
			perf.analyzeData();
			perf.getErrorCount();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage()+"\nTerminating the job...\n");
		}
		finally {
			perf.shutdownServer();
		}
	}
}
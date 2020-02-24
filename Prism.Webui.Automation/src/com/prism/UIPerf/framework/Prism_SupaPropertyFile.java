package com.prism.UIPerf.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Prism_SupaPropertyFile {

	public void generatePropertyFile(
			String application,String subaccount,
			String username,String password) {
		if(username.contentEquals("P000003")) {
			username="P1941256996";
		}
		else if(username.contentEquals("P000008")) {
			username = "P1369096596";
		}
		else if(username.contentEquals("P000306")) {
			username = "P1369096596";
		}
		Properties property = new Properties();
		property.setProperty("branch.0.component.position","148,195");
		property.setProperty("branch.0.component.class","com.sap.nw.performance.supa.gui.base.config.landscape.model.components.LeanJavaServer");
		property.setProperty("branch.0.component.id","CP Neo");
		property.setProperty("branch.0.component.name","CP Neo");
		property.setProperty("branch.0.data.provider","com.sap.nw.performance.supa.core.dataprovider.jdsr.jpaas.JdsrDataProvider");
		property.setProperty("branch.0.cp.neo.monitoring.host","monitoring.int.sap.eu2.hana.ondemand.com");
		property.setProperty("branch.0.cp.subaccount",subaccount);
		property.setProperty("branch.0.cp.app",application);
		property.setProperty("branch.0.cp.neo.monitoring.port","443");
		property.setProperty("branch.0.cp.neo.monitoring.protocol","https");
		property.setProperty("branch.0.cp.neo.proxy.host","proxy");
		property.setProperty("branch.0.cp.neo.proxy.protocol","http");
		property.setProperty("branch.0.cp.neo.proxy.port","8080");
		property.setProperty("branch.0.j2ee.jmx.user",username);
		property.setProperty("branch.0.j2ee.jmx.password.cmid","SUPA__jmx_"+username);
		property.setProperty("branch.0.j2ee.jdsr.monitoredusers","*");
		property.setProperty("branch.0.name","CP Neo");
		property.setProperty("branch.1.component.position","292,126");
		property.setProperty("branch.1.component.class","com.sap.nw.performance.supa.gui.base.config.landscape.model.components.ChromeBrowser");
		property.setProperty("branch.1.component.id","");
		property.setProperty("branch.1.component.name","Chrome");
		property.setProperty("branch.1.supa.proxy.fiddler.simulate-network","false");
		property.setProperty("branch.1.get.ui.version","false");
		property.setProperty("branch.1.browser.process","chrome");
		property.setProperty("branch.1.supa.proxy.port","9090");
		property.setProperty("branch.1.name","");
		property.setProperty("scenario.name","timezone1");
		property.setProperty("data.provider","com.sap.nw.performance.supa.core.manager.BranchManager,com.sap.nw.performance.supa.core.dataprovider.ScreenshotDataProvider");
		property.setProperty("branch.1.data.provider","com.sap.nw.performance.supa.core.dataprovider.BrowserCpuTimeProvider,com.sap.nw.performance.supa.core.dataprovider.BrowserMemoryProvider");
		try {
			FileWriter writer = new FileWriter(UIPerfConstants.__SUPA_PATH);
			property.store(writer, "");
			writer.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readPropertyFile(String filepath) throws IOException{
		FileReader reader=new FileReader(filepath);  
		Properties property = new Properties();
		property.load(reader); 
	}
	
	public void generatereplicaofPropertyFile(String src_prop, String dest_prop) throws IOException{
		FileInputStream instream = null;
		FileOutputStream outstream = null;
		try{
    	    File infile =new File(src_prop);
    	    File outfile =new File(dest_prop);
    	    instream = new FileInputStream(infile);
    	    outstream = new FileOutputStream(outfile);
    	    byte[] buffer = new byte[1024];
    	    int length;
    	    while ((length = instream.read(buffer)) > 0){
    	    	outstream.write(buffer, 0, length);
    	    }
    	    instream.close();
    	    outstream.close();
    	}catch(IOException ioe){
    		ioe.printStackTrace();
    	 }
	}
	
	public void change_proxySettings_automationChangeScript(){
		try {
			Proxy proxy = new Proxy();
			 proxy.setProxyType(Proxy.ProxyType.PAC);
			 proxy.setProxyAutoconfigUrl("http://proxy:9081213/");
			 DesiredCapabilities cap = new DesiredCapabilities();
			 cap.setCapability(CapabilityType.PROXY, proxy);
			 Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
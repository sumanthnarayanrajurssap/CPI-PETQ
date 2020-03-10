package com.prism.UIPerf.framework;

import java.io.FileWriter;
import java.util.Properties;

class Prism_SupaPropertyFile implements VariableConstants{

	void generatePropertyFile(
			String application,String subaccount,
			String username,String password) {
		if(username.contentEquals("P000306")) {
			username = "P1369096596";
		}
		Properties property = new Properties();
		property.setProperty(COMPONENT,COMPONENTVALUE);
		property.setProperty(COMPONENTCLASS,COMPONENTCLASSVALUE);
		property.setProperty(COMPONENTID,COMPONENTIDVALUE);
		property.setProperty(COMPONENTNAME,COMPONENTIDVALUE);
		property.setProperty(DATAPROVIDER,DATAPROVIDERVALUE);
		property.setProperty(MONITORINGHOST,MONITORINGHOSTVALUE);
		property.setProperty(SUBACCOUNT,subaccount);
		property.setProperty(APPLICATION,application);
		property.setProperty(MONITORINGPORT,PORT);
		property.setProperty(MONITORINGPROTOCOL,"https");
		property.setProperty(PROXYHOST,"proxy");
		property.setProperty(PROXYPROTOCOL,"http");
		property.setProperty(PROXYPORT,"8080");
		property.setProperty(J2EEJMXUSER,username);
		property.setProperty(J2EEJMXPASS,"SUPA__jmx_"+username);
		property.setProperty(J2EEJDSRMONITOREDUSER,"*");
		property.setProperty(NAME,COMPONENTNAME);
		property.setProperty(POSITION,"292,126");
		property.setProperty(CLASS,CLASSVALUE);
		property.setProperty(ID,"");
		property.setProperty(BRANCH1NAME,CHROME);
		property.setProperty(FIDLERNETWORK,"false");
		property.setProperty(GETUIVERSION,"false");
		property.setProperty(BROWSERPROCESS,CHROME);
		property.setProperty(SUPAPROXY,"9090");
		property.setProperty(BRANCHNAME1,"");
		property.setProperty(SCENARIONAME,"timezone1");
		property.setProperty(DATAPROVIDER1,DATAPROVIDERVALUE1);
		property.setProperty(DATAPROVIDER2,DATAPROVIDER2VALUE);
		try {
			FileWriter writer = new FileWriter(UIPerfConstants.__SUPA_PATH);
			property.store(writer, "");
			writer.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
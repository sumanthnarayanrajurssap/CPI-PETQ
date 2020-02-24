package com.prism.UIPerf.framework;

import java.io.IOException;

public class Prism_UploadCredentials {
	private String TargetName;
	private String addCommand;
	private String removeCommand;
	public Prism_UploadCredentials(String UserName,String Password) {
		if(UserName.contentEquals("P000008")) {
			Password="Abcd1234";
			UserName = "P1369096596";
		}
		else if(UserName.contentEquals("P000003")) {
			Password="Abcd1234";
			UserName = "P1941256996";
		}
		
		else if(UserName.contentEquals("P000306")) {
			Password="Abcd1234";
			UserName = "P1369096596";
		}
		
		this.TargetName = "SUPA__jmx_"+UserName;
		this.addCommand = "cmd /c cmdkey /generic:"+TargetName+" /user:"+UserName+" /pass:"+Password;
		this.removeCommand = "cmd /c cmdkey /delete:"+TargetName;
	}

	public void setCredentials() {
		Runtime rt = Runtime.getRuntime();
		try {
			Process pr = rt.exec(addCommand);
			if(pr.waitFor()!=0)
				pr.destroy();
		}
		catch (InterruptedException e2) {
			e2.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeCredentials() {
		Runtime rt = Runtime.getRuntime();
		try {
			Process pr = rt.exec(removeCommand);
			if(pr.waitFor()!=0)
				pr.destroy();
		}
		catch (InterruptedException e2) {
			e2.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

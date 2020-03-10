package com.prism.UIPerf.framework;

class Prism_UploadCredentials {
	private String TargetName;
	private String addCommand;
	private String removeCommand;
	Prism_UploadCredentials(String UserName,String Password) {
		if(UserName.contentEquals("P000306")) {
			Password="Abcd1234";
			UserName = "P1369096596";
		}
		
		this.TargetName = "SUPA__jmx_"+UserName;
		this.addCommand = "cmd /c cmdkey /generic:"+TargetName+" /user:"+UserName+" /pass:"+Password;
		this.removeCommand = "cmd /c cmdkey /delete:"+TargetName;
	}

	void setCredentials() {
		Runtime rt = Runtime.getRuntime();
		try {
			Process pr = rt.exec(addCommand);
			if(pr.waitFor()!=0)
				pr.destroy();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	void removeCredentials() {
		Runtime rt = Runtime.getRuntime();
		try {
			Process pr = rt.exec(removeCommand);
			if(pr.waitFor()!=0)
				pr.destroy();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
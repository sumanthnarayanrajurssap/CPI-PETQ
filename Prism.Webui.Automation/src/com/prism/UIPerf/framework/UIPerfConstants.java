package com.prism.UIPerf.framework;

public class UIPerfConstants {
	static final String GUIDFileName = "GUIDFile.csv";
	static final String GUIDTagsFileName = "GUIDTagsFile.csv";
	static String __SUPA_PATH = "./Resources/UIPerformance/SUPA_ConfigFile/supaconfig.properties";
	private static float RegressionThresholdPercentage = 5;
	static String ListOfKPIs[] = {
			"EndToEndResponseTimesS",
			"ClientCPUTimeS",
			"EstimatedEndToEndResponseTimeinWANS",
			"RoundtripsClientServer",
			"SequentialRoundtripsClientServer",
			"DataTransferClientServerKB",
			"ResponseTimeClientServerS"
		};
	private static int NumberOfKPIs = ListOfKPIs.length;
	public static int timeout = 90;
	static float RegressionThreshold = 1+(RegressionThresholdPercentage/100);
	
	//UIPerf
	static String __COMMON_PATH = "D:\\DontDelete\\UIperformance\\";
	static String listOfActions[] ={"Open iflow","Edit iFlow","Save iflow","Deploy iflow"};
	static double baselineActions[] ={2.8,1.5,1.8,4.5};
	static double baselineActionsXL[] ={13.0,2.0,17.0,18.0};
	static int NumberOfActions = listOfActions.length;
	private int SizeOfExcel = (NumberOfKPIs*(NumberOfActions+1))+2;
	int computeSizeOfExcel(int NumberOfIflows) {
		SizeOfExcel=(NumberOfKPIs*((NumberOfActions*NumberOfIflows)+1))+2;
		return SizeOfExcel;
	}

	//ODP
	static String __COMMON_PATH_ODP = "D:\\DontDelete\\ODP\\";//"C:\\Users\\i334474\\";
	static String listOfActions_ODP[] ={"Open ODP", "Open Iflow", "Save Iflow", "Save Project", "Deploy Project"};//
	static double baselineActions_ODP[] ={6.2,3.6,1.5,3.7,12.49};
	static int NumberOfActions_ODP = listOfActions_ODP.length;
	private static int SizeOfExcel_ODP = (NumberOfKPIs*(NumberOfActions_ODP+1))+2;
	int computeSizeOfExcel_ODP(int NumberOfIflows) {
		SizeOfExcel_ODP=(NumberOfKPIs*((NumberOfActions_ODP*NumberOfIflows)+1))+2;
		return SizeOfExcel_ODP;
	}
	
	static String __COMMON_PATH_VM = "D:\\DontDelete\\ValueMapping\\";
	static String listOfActions_VM[] ={"Open Value Mapping","Save Value Mapping","Deploy Value Mapping"};
	static double baselineActions_VM[] ={2.8,1.5,3.0};
	static int NumberOfActions_VM = listOfActions_VM.length;
	private int SizeOfExcel_VM = (NumberOfKPIs*(NumberOfActions_VM+1))+2;
	int computeSizeOfExcel_VM(int NumberOfIflows) {
		SizeOfExcel_VM=(NumberOfKPIs*((NumberOfActions_VM*NumberOfIflows)+1))+2;
		return SizeOfExcel_VM;
	}
	
	static String __COMMON_PATH_RS = "D:\\DontDelete\\ResourceOpen\\";
	static String listOfActions_RS[] ={"Open iflow","Open Resource"};
	static double baselineActions_RS[] ={3.0,14.1};
	static int NumberOfActions_RS = listOfActions_RS.length;
	private int SizeOfExcel_RS = (NumberOfKPIs*(NumberOfActions_RS+1))+2;
	int computeSizeOfExcel_RS(int NumberOfIflows) {
		SizeOfExcel_RS=(NumberOfKPIs*((NumberOfActions_RS*NumberOfIflows)+1))+2;
		return SizeOfExcel_RS;
	}
	
	static String __COMMON_PATH_MM = "D:\\DontDelete\\ResourceOpenMessageMapping\\";
	static String listOfActions_MM[] ={"Open iflow","Open MessageMapping"};
	static int NumberOfActions_MM = listOfActions_MM.length;
	static double baselineActions_MM[] ={3.0,14.1};
	private int SizeOfExcel_MM = (NumberOfKPIs*(NumberOfActions_MM+1))+2;
	int computeSizeOfExcel_MM(int NumberOfIflows) {
		SizeOfExcel_MM=(NumberOfKPIs*((NumberOfActions_MM*NumberOfIflows)+1))+2;
		return SizeOfExcel_MM;
	}
	
	static String __COMMON_PATH_ODP2 = "D:\\DontDelete\\ODP2\\";
	static String listOfActions_ODP2[] ={"Open ODP Content", "Open ODP Iflow", "Save ODP Iflow","Load_ODPvariantsProjects", "Save ODP Content", "Deploy ODP Content"};
	static int NumberOfActions_ODP2 = listOfActions_ODP2.length;
	static double baselineActions_ODP2[] ={3.4,3.3,1.1,2.0,1.8,4.7};
	private int SizeOfExcel_ODP2 = (NumberOfKPIs*(NumberOfActions_ODP2+1))+2;
	int computeSizeOfExcel_ODP2(int NumberOfIflows) {
		SizeOfExcel_ODP2=(NumberOfKPIs*((NumberOfActions_ODP2*NumberOfIflows)+1))+2;
		return SizeOfExcel_ODP2;
	}
	
	static String __COMMON_PATH_LContnModifier = "D:\\DontDelete\\LargeContentModifier\\";
	static String listOfActions_LContnModifier[] ={"Open LCM iflow","Edit LCM iFlow","Save LCM iflow","Deploy LCM iflow"};
	static int NumberOfActions_LContnModifier = listOfActions_LContnModifier.length;
	static double baselineActions_LContnModifier[] ={2.5,1.0,1.0,3.0};
	private int SizeOfExcel_LContnModifier = (NumberOfKPIs*(NumberOfActions_LContnModifier+1))+2;
	int computeSizeOfExcel_LContnModifier(int NumberOfIflows) {
		SizeOfExcel_LContnModifier=(NumberOfKPIs*((NumberOfActions_LContnModifier*NumberOfIflows)+1))+2;
		return SizeOfExcel_LContnModifier;
	}
}
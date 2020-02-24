package com.prism.UIPerf.framework;

public class UIPerfConstants {
	public static final String GUIDFileName = "GUIDFile.csv";
	public static final String GUIDTagsFileName = "GUIDTagsFile.csv";
	public static String __SUPA_PATH = "./Resources/UIPerformance/SUPA_ConfigFile/supaconfig.properties";
	public static float RegressionThresholdPercentage = 5;
	public static String ListOfKPIs[] = {
			"EndToEndResponseTimesS",//Perf-02
			"ClientCPUTimeS",//Perf-08
			"EstimatedEndToEndResponseTimeinWANS",
			"RoundtripsClientServer",//Perf-09
			"SequentialRoundtripsClientServer",//Perf-09
			"DataTransferClientServerKB",//Perf-10
			"ResponseTimeClientServerS"
			//"ServerCPUTimeS",//Perf-08
			//"ServerMemoryMB"//Perf-07
		};
	public static int NumberOfKPIs = ListOfKPIs.length;
	public static int timeout = 90;
	public static float RegressionThreshold = 1+(RegressionThresholdPercentage/100);
	
	//UIPerf
	public static String __COMMON_PATH = "D:\\DontDelete\\UIperformance\\";
	public static String listOfActions[] ={"Open iflow","Edit iFlow","Save iflow","Deploy iflow"};
	public static double baselineActions[] ={2.8,1.5,1.8,4.5};
	public static double baselineActionsXL[] ={14.0,6.5,28.2,18.3};
	public static int NumberOfActions = listOfActions.length;
	public int SizeOfExcel = (NumberOfKPIs*(NumberOfActions+1))+2;
	public int computeSizeOfExcel(int NumberOfIflows) {
		SizeOfExcel=(NumberOfKPIs*((NumberOfActions*NumberOfIflows)+1))+2;
		return SizeOfExcel;
	}

	//ODP
	public static String __COMMON_PATH_ODP = "D:\\DontDelete\\ODP\\";//"C:\\Users\\i334474\\";
	public static String listOfActions_ODP[] ={"Open ODP", "Open Iflow", "Save Iflow", "Save Project", "Deploy Project"};//
	public static double baselineActions_ODP[] ={6.2,3.6,1.5,3.7,12.49};
	public static int NumberOfActions_ODP = listOfActions_ODP.length;
	public static int SizeOfExcel_ODP = (NumberOfKPIs*(NumberOfActions_ODP+1))+2;
	public int computeSizeOfExcel_ODP(int NumberOfIflows) {
		SizeOfExcel_ODP=(NumberOfKPIs*((NumberOfActions_ODP*NumberOfIflows)+1))+2;
		return SizeOfExcel_ODP;
	}
	
	public static String __COMMON_PATH_VM = "D:\\DontDelete\\ValueMapping\\";
	public static String listOfActions_VM[] ={"Open Value Mapping","Save Value Mapping","Deploy Value Mapping"};
	public static double baselineActions_VM[] ={2.8,1.5,3.0};
	public static int NumberOfActions_VM = listOfActions_VM.length;
	public int SizeOfExcel_VM = (NumberOfKPIs*(NumberOfActions_VM+1))+2;
	public int computeSizeOfExcel_VM(int NumberOfIflows) {
		SizeOfExcel_VM=(NumberOfKPIs*((NumberOfActions_VM*NumberOfIflows)+1))+2;
		return SizeOfExcel_VM;
	}
	
	public static String __COMMON_PATH_RS = "D:\\DontDelete\\ResourceOpen\\";
	public static String listOfActions_RS[] ={"Open iflow","Open Resource"};
	public static double baselineActions_RS[] ={3.0,14.1};
	public static int NumberOfActions_RS = listOfActions_RS.length;
	public int SizeOfExcel_RS = (NumberOfKPIs*(NumberOfActions_RS+1))+2;
	public int computeSizeOfExcel_RS(int NumberOfIflows) {
		SizeOfExcel_RS=(NumberOfKPIs*((NumberOfActions_RS*NumberOfIflows)+1))+2;
		return SizeOfExcel_RS;
	}
	
	public static String __COMMON_PATH_MM = "D:\\DontDelete\\ResourceOpenMessageMapping\\";
	public static String listOfActions_MM[] ={"Open iflow","Open MessageMapping"};
	public static int NumberOfActions_MM = listOfActions_MM.length;
	public static double baselineActions_MM[] ={3.0,14.1};
	public int SizeOfExcel_MM = (NumberOfKPIs*(NumberOfActions_MM+1))+2;
	public int computeSizeOfExcel_MM(int NumberOfIflows) {
		SizeOfExcel_MM=(NumberOfKPIs*((NumberOfActions_MM*NumberOfIflows)+1))+2;
		return SizeOfExcel_MM;
	}
	
	public static String __COMMON_PATH_ODP2 = "D:\\DontDelete\\ODP2\\";
	public static String listOfActions_ODP2[] ={"Open ODP Content", "Open ODP Iflow", "Save ODP Iflow","Load_ODPvariantsProjects", "Save ODP Content", "Deploy ODP Content"};
	public static int NumberOfActions_ODP2 = listOfActions_ODP2.length;
	public static double baselineActions_ODP2[] ={3.4,3.3,1.1,2.0,1.8,4.7};
	public int SizeOfExcel_ODP2 = (NumberOfKPIs*(NumberOfActions_ODP2+1))+2;
	public int computeSizeOfExcel_ODP2(int NumberOfIflows) {
		SizeOfExcel_ODP2=(NumberOfKPIs*((NumberOfActions_ODP2*NumberOfIflows)+1))+2;
		return SizeOfExcel_ODP2;
	}
	
	public static String __COMMON_PATH_LContnModifier = "D:\\DontDelete\\LargeContentModifier\\";
	public static String listOfActions_LContnModifier[] ={"Open LCM iflow","Edit LCM iFlow","Save LCM iflow","Deploy LCM iflow"};
	public static int NumberOfActions_LContnModifier = listOfActions_LContnModifier.length;
	public static double baselineActions_LContnModifier[] ={2.6,1.3,1.4,4.3};
	public int SizeOfExcel_LContnModifier = (NumberOfKPIs*(NumberOfActions_LContnModifier+1))+2;
	public int computeSizeOfExcel_LContnModifier(int NumberOfIflows) {
		SizeOfExcel_LContnModifier=(NumberOfKPIs*((NumberOfActions_LContnModifier*NumberOfIflows)+1))+2;
		return SizeOfExcel_LContnModifier;
	}
}

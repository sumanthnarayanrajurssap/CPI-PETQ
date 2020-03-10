package com.prism.UIPerf.framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import com.sap.nw.performance.supa.automation.Supa;
import au.com.bytecode.opencsv.CSVReader;

public class Prism_SUPAFramework extends Supa{
	private FileUtils csvWrite;
	
	private String allInOneSummaryLevel0FileRelativePath = 
			"latestResult\\allInOneLevel0_Summary.csv";
	private String csvExtn = ".csv";
	private String grafanaURL;
	private String landscape;
	private String comparePurpose;
	private String nodeAssemblyVersion;
	private double[] baselineActions;
	
	Prism_SUPAFramework(String[] configFile) throws Exception {
		super(configFile);
	}
	boolean writeToDirectory() {
		try {
				System.out.println("SUPA result dir = "+this.getResultDirectory());
				this.writeExcelReport();
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void startMeasurement(String stepNameOverride) {
		try {
			super.startMeasurement(stepNameOverride);
		}
		catch (Exception e) {
			e.printStackTrace();
			try {
				stopMeasurement(30);
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Override
	public Map<String,String> stopMeasurement(
			double timeInSecondsOverride) {
		try {
			return super.stopMeasurement(timeInSecondsOverride);
		} catch (Exception e) {
			try {
				resetDataProviders();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return null;
		}
	}
	
	void copySUPAResultsToSharedFolder(String commonPath) {
		try {
			Process p = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", "%CD%\\Resources\\UIPerformance\\BatchFiles\\moveLatestSupaFile.bat "+commonPath});
			p.waitFor();
			BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
            String line; 
            while((line = reader.readLine()) != null) { 
                System.out.println(line);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void storeResults(
			int noA,
			int NumberOfIflows, 
			int SizeOfExcel, 
			String commonPathGUID, 
			String commonPath,
			String grafanaURL,
			String landscape,
			String comparePurpose,
			String variant,
			String nodeAssemblyVersion,
			String []listOfActions,
			double[]baselineActions) {
		
		csvWrite = new FileUtils();
		this.grafanaURL = grafanaURL;
		this.landscape = landscape;
		this.comparePurpose = comparePurpose;
		this.nodeAssemblyVersion = nodeAssemblyVersion;
		this.baselineActions = baselineActions;
		File results = new File(commonPathGUID+allInOneSummaryLevel0FileRelativePath);
		try {
			CSVReader resultsReader = new au.com.bytecode.opencsv.CSVReader(
					new FileReader(results),',');
			List<String[]> resultsList = resultsReader.readAll();
			resultsReader.close();
			writeIntoFile(
					noA,
					NumberOfIflows, 
					SizeOfExcel,
					resultsList,
					commonPath,
					variant,
					listOfActions);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private void writeIntoFile(
			int noA,
			int NumberOfIflows,
			int SizeOfExcel, List<String[]> resultsList,
			String commonPath,
			String variant,
			String[] listOfActions) {
		FileWriter writer = null;
		int noI;
		noI = NumberOfIflows;
		int actions = 0;
		List<String> values;
		for(int i=3,j=0;i<(SizeOfExcel);i+=(noI*noA+1),j++) {
			try {
				writer = new FileWriter(
						commonPath+UIPerfConstants.ListOfKPIs[j]+csvExtn,
						true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			values = new ArrayList<String>();
			for(int k=0;k<noA;k++) {
				double sum = 0;
				double avg=0;
				noI = NumberOfIflows;
				for(int l=0;l<noI*noA;l+=noA) {
					if(resultsList.get(1)[l+i+k].isEmpty()) {
						noI--;
					}
					else {
						sum+=Float.parseFloat(resultsList.get(1)[l+i+k]);
					}
				}
				avg = sum/noI;
				values.add(""+avg);
				GrafanaReport pushintoInfluxDB = new GrafanaReport();
				pushintoInfluxDB.pushAggregatedSUPAResultsToGrafana(
						avg,
						UIPerfConstants.ListOfKPIs[j],
						variant,
						listOfActions[actions],
						baselineActions[actions],
						landscape,
						nodeAssemblyVersion,
						comparePurpose,
						grafanaURL
								);
				actions++;
			}
			try {
				csvWrite.writeLine(writer, values);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			actions = 0;
		}
	}
	
	boolean compareResults(
			String [] ActionList,
			String teamName, 
			String purpose, 
			String commonPath,
			String targetTeamName,
			String targetPurpose
			) {
		if(targetTeamName
				.contentEquals(teamName)
				&&targetPurpose
				.contentEquals(purpose)) {
			return compareWithItself(
					ActionList ,
					teamName, 
					purpose, 
					commonPath);
		}
		else {
			return compareWithRT(
					ActionList, 
					teamName, 
					purpose, 
					commonPath, 
					targetTeamName, 
					targetPurpose);
		}
	}
	
	boolean compareResultsWithBaseline(
			String [] ActionList,
			String teamName, 
			String purpose, 
			String commonPath, 
			double[] baseline
			) throws IOException {
		if(baseline!=null) {
			boolean KPIsMet= true;
			String rtResults = commonPath+"\\"+teamName+"\\"+purpose+"\\";
			System.out.println("comparing with itself= "+rtResults);
			File rtFile = new File(rtResults+UIPerfConstants.ListOfKPIs[0]+csvExtn);
			CSVReader rtReader = new au.com.bytecode.opencsv.CSVReader(
					new FileReader(rtFile),',');
			List<String[]> rtList = rtReader.readAll();
			rtReader.close();
			int rtReading = rtList.size()-1;
			double[] current= new double[ActionList.length];
			for(int i=0;i<ActionList.length;i++) {
				current[i] = Float.parseFloat(rtList.get(rtReading)[i]);
				if(compareResult(baseline[i],current[i])) {
					regression(baseline[i],current[i],ActionList[i]);
					KPIsMet= false;
				}
				else if (current[i]<baseline[i]) {
					improvement(ActionList[i]);
				}
			}
			displayResultsBaseline(baseline,current,ActionList);
			return KPIsMet;
		}
		return true;
	}
	
	
	
	private boolean compareWithItself(
			String [] ActionList, 
			String teamName, 
			String purpose, 
			String commonPath
			) {
		String rtResults = commonPath+"\\"+teamName+"\\"+purpose+"\\";
		File rtFile = new File(rtResults+UIPerfConstants.ListOfKPIs[0]+csvExtn);
		CSVReader rtReader = null;
		try {
			rtReader = new au.com.bytecode.opencsv.CSVReader(new FileReader(rtFile),',');
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		List<String[]> rtList = null;
		try {
			rtList = rtReader.readAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			rtReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int rtReading = rtList.size()-2;
		int snapshotReading = rtList.size()-1;
		return comparePreviousAndCurrent(
				ActionList, 
				rtList, 
				rtList, 
				rtReading, 
				snapshotReading
				);
	}
	
	private boolean compareWithRT(
			String [] ActionList, 
			String teamName, 
			String purpose, 
			String commonPath,
			String targetTeamName, 
			String targetPurpose) {
		
		String rtResults;
		rtResults = commonPath+"\\"+targetTeamName+"\\"+targetPurpose+"\\";
		String resultsToCompare = commonPath+"\\"+teamName+"\\"+purpose+"\\";
		File snapshotFile = new File(
				resultsToCompare+UIPerfConstants.ListOfKPIs[0]+csvExtn);
		File rtFile = new File(
				rtResults+UIPerfConstants.ListOfKPIs[0]+csvExtn);
		CSVReader rtReader = null;
		try {
			rtReader = new au.com.bytecode.opencsv.CSVReader(
					new FileReader(rtFile),',');
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		CSVReader snapshotReader = null;
		try {
			snapshotReader = new au.com.bytecode.opencsv.CSVReader(
					new FileReader(snapshotFile),',');
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		List<String[]> rtList = null;
		try {
			rtList = rtReader.readAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String[]> snapshotList = null;
		try {
			snapshotList = snapshotReader.readAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			rtReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			snapshotReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int rtReading = rtList.size()-1;
		int snapshotReading = snapshotList.size()-1;
		return comparePreviousAndCurrent(
				ActionList, 
				rtList,
				snapshotList, 
				rtReading, 
				snapshotReading
				);
	}

	private boolean comparePreviousAndCurrent(
			String [] ActionList, 
			List<String[]> rtList, 
			List<String[]> snapshotList, 
			int rtReading,
			int snapshotReading) {
		
		boolean KPIsMet= true;
		double[] previous= new double[ActionList.length];
		double[] current= new double[ActionList.length];
		for(int i=0;i<ActionList.length;i++) {
			previous[i] = Float.parseFloat(rtList.get(rtReading)[i]);
			current[i] = Float.parseFloat(snapshotList.get(snapshotReading)[i]);
			if(compareResult(previous[i],current[i])) {
				regression(previous[i],current[i],ActionList[i]);
				KPIsMet= false;
			}
			else if (current[i]<previous[i]) {
				improvement(ActionList[i]);
			}
		}
		displayResults(previous,current,ActionList);
		return KPIsMet;
	}
	
	private void regression(double f, double g, String string) {
		System.out.println("\n\n");
		System.out.println("*****            "
				+ "                   "+(g-f)*100/f+"% regression         "
						+ "          "
						+ "                  "
						+ "          *****\n".toUpperCase());
		System.out.println("                    "
				+ "                Regression in "+string+"           "
						+ "           "
						+ "                    \n".toUpperCase());
		System.out.println("***************"
				+ "**************************"
				+ "***********************************"
				+ "*******\n\n");
	}
	
	private void displayResults(
			double[] previous, 
			double[] current, 
			String[] ActionList) {
		System.out.println("\n\n**                   "
				+ "                      "
				+ "Previous Readings                       "
				+ "                          "
				+ "**\n\n");
		displayResults(previous,ActionList);
		System.out.println("\n\n**                        "
				+ "                 "
				+ "Current Readings                        "
				+ "                    "
				+ "**\n\n");
		displayResults(current,ActionList);
	}
	
	private void displayResultsBaseline(
			double[] previous, 
			double[] current, 
			String[] ActionList) {
		System.out.println("\n\n**                  "
				+ "                       "
				+ "Baseline Readings                      "
				+ "                      "
				+ "**\n\n");
		displayResults(previous,ActionList);
		System.out.println("\n\n**                        "
				+ "                 "
				+ "Current Readings                      "
				+ "                      "
				+ "**\n\n");
		displayResults(current,ActionList);
	}
	
	private void displayResults(double[] previous,String[] ActionList) {
		for(int i=0; i<previous.length;i++) {
			System.out.println(" "
					+ "**                                     "
					+ ""+ActionList[i]+" = "+previous[i]+"                                       "
							+ "**  ");
		}
	}
	
	private boolean compareResult(double initial, double current) {
		return (current<=initial*UIPerfConstants.RegressionThreshold)?false:true; 
	}
	
	public static void changeproxySettings() {
		try {
			Process p = Runtime
					.getRuntime()
					.exec(
							new String[]{
									"cmd.exe", 
									"/c", 
									"%CD%\\Resources\\UIPerformance\\BatchFiles\\ResetProxySettings.bat"});
			p.waitFor(3, TimeUnit.SECONDS);
			BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
            String line; 
            while((line = reader.readLine()) != null) { 
                System.out.println(line);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	void regression() {
		System.out.println(""
				+ "-------------------------------------"
				+ "---------------------------------------"
				+ "-----------------------------------");
		System.out.println("-----                                                                                                     -----");
		System.out.println(""
				+ "---------------------------------"
				+ "Regression exists. Need to run JVM profiling."
				+ "---------------------------------");
		System.out.println("-----                                                                                                     -----");
		System.out.println("---------------------------"
				+ "--------------------------------------"
				+ "----------------------------------------------");
	}
	
	void noRegression() {
		System.out.println("-----------------------"
				+ "-----------------------------------"
				+ "---------------------------------------"
				+ "--------------");
		System.out.println("-----                                                                                                     -----");
		System.out.println("-------------------------"
				+ "------------------No Regression, KPIs met.------------"
				+ "--------------------------------");
		System.out.println("-----                                                                                                     -----");
		System.out.println("---------------------------"
				+ "---------------------------------------------"
				+ "---------------------------------------");
	}
	
	private void improvement(String KPI) {
		System.out.println("-------------------------"
				+ "-------------------------------------"
				+ "-------------------------------------------------");
		System.out.println("-----                                                                                                     -----");
		System.out.println("-------------------------------"
				+ "-----Improvement found in "+KPI+"----------------"
						+ "-----------------------");
		System.out.println("-----                                                                                                     -----");
		System.out.println("--------------------------------"
				+ "-----------------------------------------------------"
				+ "--------------------------");
	}
	
	void analyzeClientLogs(String path, String[] listOfActions) {
		int noA = listOfActions.length;
		List<String> proxyTrcEntries = new ArrayList<String>();
		proxyTrcEntries= getDataFromProxyTrcFile(path);
		Map<String,Integer> roundtrips = new HashMap<String,Integer>();
		Map<String,Integer> roundtripsDetailed = new HashMap<String,Integer>();
		String httpCallType=null;
		int i=-1;
		int j=0;
		int p=0;
		boolean newAction = true;
		for(String proxyEntry: proxyTrcEntries) {
			if(proxyEntry.contains("#####") && newAction) {
				newAction=false;
				p++;
				if(p>noA*2) break;
				j=(p>noA)?1:0;
				i=(i+1)%noA;
			}
			else if(!proxyEntry.contains("#####")) {
				newAction=true;
				String httpCall = proxyEntry.split("\"")[1];
				String returnType = proxyEntry.split("\"")[2].split(" ")[1];
				if(Integer.parseInt(returnType)>399) {
					System.out.println("Http response error in "+httpCall+returnType);
				}
				String httpMethod = httpCall.split(" ")[0];
				String httpAddress = httpCall.split(" ")[1];
				httpCallType = returnhttpCallType(httpAddress);
				searchAndAddEntries(listOfActions,roundtrips, roundtripsDetailed,httpCallType,i,j);
				addEntriesToFile(
						listOfActions[i].replaceAll(" ", "")+((j==0)?"WithoutCache":"WithCache"),
						path, 
						httpMethod, 
						httpAddress,
						httpCallType);
			}
		}
		for (Map.Entry<String,Integer> entry : roundtrips.entrySet()) {
			  String key = entry.getKey();
			  int value = entry.getValue();
			  System.out.println(key+" : "+value);
		}
		System.out.println();
		for (Map.Entry<String,Integer> entry : roundtripsDetailed.entrySet()) {
			  String key = entry.getKey();
			  int value = entry.getValue();
			  System.out.println(key+" : "+value);
		}
	}

	private void searchAndAddEntries(
			String [] listOfActions, 
			Map<String, Integer> roundtrips, 
			Map<String, Integer> roundtripsDetailed, 
			String httpCallType, 
			int i, 
			int j) {
		String key = listOfActions[i]+ ((j==0)?" Without Cache ":" With Cache ")+ httpCallType;
		searchAndAddEntry(roundtripsDetailed, key);
		key = listOfActions[i]+ ((j==0)?" Without Cache ":" With Cache ");
		searchAndAddEntry(roundtrips, key);
	}
	
	private void searchAndAddEntry(Map<String, Integer> roundtrips, String key) {
		if(roundtrips.containsKey(key)) {
			roundtrips.put(key, roundtrips.get(key)+1);
		}
		else {
			roundtrips.put(key, 1);
		}	
	}
	
	private void addEntriesToFile(
			String action, 
			String path, 
			String httpMethod, 
			String httpAddress,
			String httpCallType) {
		
		try {
			File currentCall = new File(path+"\\"+httpCallType+action+".csv");
			FileWriter currentCallWriter = new FileWriter(currentCall, true);
			List<String> inputLine = new ArrayList<String>();
			inputLine.add(httpMethod);
			inputLine.add(httpAddress);
			new FileUtils().writeLine(currentCallWriter,inputLine);
			currentCallWriter.flush();
			currentCallWriter.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private String returnhttpCallType(String httpAddress) {
		if(httpAddress.contains("/api/")) {
			return "api";
		}
		else if(httpAddress.contains("/odata/")) {
			return "odata";
		}
		else if(httpAddress.contains("/appresources/")) {
			return "appResources";
		}
		else if(httpAddress.contains("//sapui5")) {
			return "sapUI5";
		}
		return "otherCalls";
	}
	
	private List<String> getDataFromProxyTrcFile(String path) {
		List<String> proxyTrcEntries = new ArrayList<String>();
		try {
			File[] zipFileList = FileUtils.listFilesMatching(new File(path),".*.zip");
			ZipFile zipFile = null;
			zipFile = new ZipFile(zipFileList[0].getAbsolutePath());
			ZipEntry proxyFile = zipFile.getEntry("proxy.trc");
			InputStream stream = zipFile.getInputStream(proxyFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
	        StringBuilder out = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	proxyTrcEntries.add(line);
	        	out.append(line);
	            out.append("\n");
	        }
	        reader.close();
			zipFile.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return proxyTrcEntries;
	}
}
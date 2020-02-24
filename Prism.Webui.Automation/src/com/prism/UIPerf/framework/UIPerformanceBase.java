package com.prism.UIPerf.framework;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.webui.Design.automation.Prism_Design_Others;
import com.webui.Design.automation.Prism_Design_Page;
import com.webui.Others.webui.tooling.Prism_Master_Class;
import com.webui.Others.webui.tooling.Prism_Selenium_Loginlogout;

public class UIPerformanceBase extends Prism_Selenium_Loginlogout{
	
	
	private int errorCount=0;
	private int errorCount2=0;
	private String compareTeamName;
	private String comparePurpose;
	private String teamName; 
	private String purpose; 
	private String tmnName; 
	private String providerSubAccountID; 
	private Boolean ENABLEJVMPROFILING;
	private Boolean ENABLESUPA;
	private String URL;
	private int INVOCATIONCOUNT;
	private String USERNAME; 
	private String PASSWORD;
	private String IPAProject;
	private String branch;
	private ExecutionType exec;
	private int numberOfActions;
	private List<Long> uiResponseTimes;
	private final String fileextension = ".csv";
	private final String openIflowPathName = "OPENIFLOWFILE";
	private FileUtils csvwrite;
	private Prism_SUPAFramework supa;
	private Prism_UploadCredentials uploadCred;
	private Prism_API_Framework apifwk;
	private Prism_SupaPropertyFile propertyFile;
	private String pathForUse;
	private String pathForUseDir;
	private GUID guID;
	private GUID_Fwk gFwk;
	private String pathForUseDirGUID;
	private String pathForUseGUID;
	private UIPerfConstants ExcelCompute;
	public String[] PackageList;
	public String[] IflowList;
	public int numberOfIflows;
	public int SizeOfExcel;
	private String homePath;
	private List<String> problemIflows;
	private String nodeAssemblyVersion;
	private String grafanaURL;
	
	public static enum ExecutionType {
	    UIPerf, ODP,  ValueMapping, OpenResource, OPenMessageMapping , ODP2, LargeContentModifier;
	}
	private void setTestVariables(
			ExecutionType exec, 
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
			String PASSWORD, String IPAProject,String branch, String grafanaURL) 
	{
		
		this.compareTeamName = compareTeamName;
		this.comparePurpose = comparePurpose;
		this.teamName = teamName;
		this.purpose = purpose;
		this.tmnName = tmnName;
		this.providerSubAccountID = providerSubAccountID;
		this.ENABLEJVMPROFILING = ENABLEJVMPROFILING;
		this.ENABLESUPA = ENABLESUPA;
		this.URL = URL;
		this.INVOCATIONCOUNT = INVOCATIONCOUNT;
		this.USERNAME = USERNAME;
		this.PASSWORD = PASSWORD;
		this.exec = exec;
		this.IPAProject = IPAProject;
		this.branch = branch;
		this.grafanaURL = grafanaURL;
	}
	private void initializeCustomObjects() {
		ExcelCompute = new UIPerfConstants();
		propertyFile = new Prism_SupaPropertyFile();
		guID = new GUID(teamName,purpose);
		apifwk = new Prism_API_Framework(URL,USERNAME,PASSWORD);
	}
	private void setPathVariables() {
		homePath=(
				exec.name().contentEquals("UIPerf"))?UIPerfConstants.__COMMON_PATH:
					(
							exec.name().contentEquals("ODP")?UIPerfConstants.__COMMON_PATH_ODP:
								exec.name().contentEquals("ValueMapping")?UIPerfConstants.__COMMON_PATH_VM:
									exec.name().contentEquals("OpenResource")?UIPerfConstants.__COMMON_PATH_RS:
										exec.name().contentEquals("OPenMessageMapping")?UIPerfConstants.__COMMON_PATH_MM:
											exec.name().contentEquals("ODP2")?UIPerfConstants.__COMMON_PATH_ODP2:
												UIPerfConstants.__COMMON_PATH_LContnModifier);
		pathForUseDir =homePath +teamName+"\\"+purpose;
		pathForUse=pathForUseDir+"\\";
		pathForUseDirGUID = pathForUse+"\\"+guID.getGUID();
		pathForUseGUID = pathForUseDirGUID+"\\";
		numberOfActions = (
				exec.name().contentEquals("UIPerf"))?UIPerfConstants.NumberOfActions:
					(
							exec.name().contentEquals("ODP")?UIPerfConstants.NumberOfActions_ODP:
								exec.name().contentEquals("ValueMapping")?UIPerfConstants.NumberOfActions_VM:
									exec.name().contentEquals("OpenResource")?UIPerfConstants.NumberOfActions_RS:
										exec.name().contentEquals("OPenMessageMapping")?UIPerfConstants.NumberOfActions_MM:
											exec.name().contentEquals("ODP2")?UIPerfConstants.NumberOfActions_ODP2:
												UIPerfConstants.NumberOfActions_LContnModifier);
	}
	public UIPerformanceBase
	(
			WebDriver driver, 
			ExecutionType exec, 
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
			String branch,
			String grafanaURL
			) 
	{
		
		this.driver = driver;
		setTestVariables(
				exec, 
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
				IPAProject,
				branch,
				grafanaURL
				);

		initializeCustomObjects();
		setPathVariables();
		System.out.println("Prerequisites starting\n");
		setTimeout(Timeout);
		PackageList = ListOfPackages.split(", ");
		IflowList = ListOfIflows.split(", ");
		numberOfIflows= PackageList.length;
		SizeOfExcel = (
				exec.name().contentEquals("UIPerf"))?(ExcelCompute.computeSizeOfExcel(numberOfIflows)):
					(
							exec.name().contentEquals("ODP")?(ExcelCompute.computeSizeOfExcel_ODP(numberOfIflows)):
								exec.name().contentEquals("ValueMapping")?ExcelCompute.computeSizeOfExcel_VM(numberOfIflows):
									exec.name().contentEquals("OpenResource")?ExcelCompute.computeSizeOfExcel_RS(numberOfIflows):
										exec.name().contentEquals("OPenMessageMapping")?ExcelCompute.computeSizeOfExcel_MM(numberOfIflows):
											exec.name().contentEquals("ODP2")?ExcelCompute.computeSizeOfExcel_ODP2(numberOfIflows):
												ExcelCompute.computeSizeOfExcel_LContnModifier(numberOfIflows));

	}
	
	public void initialProcess() {
		try {
            apifwk.createTeamPurposeDirectories(pathForUseGUID);
            if(ENABLESUPA) {
                propertyFile.generatePropertyFile(tmnName,providerSubAccountID, USERNAME,PASSWORD);
                uploadCred = new Prism_UploadCredentials(USERNAME, PASSWORD);
                uploadCred.setCredentials();
                nodeAssemblyVersion = apifwk.getNodeAssemblyVersion(URL,USERNAME, PASSWORD);
            }
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
	public void startSupaServer() {
		if(ENABLESUPA) {
            for (int i = 0; i < 10; i++) {
                try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
                try{
                    supa = new Prism_SUPAFramework(getSupaConfig(UIPerfConstants.__SUPA_PATH));
                    supa.setLogLevel(Level.ALL);
                    break;
                }catch(Exception e){
                    String exception = e.getMessage();
                    if(exception.contains("Connection") || exception.contains("failed to respond")){
                        i++;
                    }
                }
            }
        }
        else supa= null;
	}
	
	public void measurePerformance() {
		int i=0;
		problemIflows= new ArrayList<String>();
		do {
			try {
				mainflowForOneIflow(PackageList[i],IflowList[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}while(i<numberOfIflows);
	}
	
	public void collectData() throws Exception {
		try {
			computeResponseTimeStats(INVOCATIONCOUNT);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		if(ENABLESUPA) {
			supa.setEnabled(true);
			try {
				supa.finish();
				supa.uploadResultNew(IPAProject,exec.name()+"-"+comparePurpose, branch, nodeAssemblyVersion, "SingleUserTests", null, "i045359","Pgw5");		//Upload Results to IPA
				supa.writeToDirectory();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			finally {
				supa.copySUPAResultsToSharedFolder(pathForUseDirGUID);			
				supa.storeResults(numberOfActions,numberOfIflows, SizeOfExcel,pathForUseGUID,pathForUse,grafanaURL,branch,comparePurpose,exec.name(),nodeAssemblyVersion,
						exec.name().contentEquals("UIPerf")?UIPerfConstants.listOfActions:
							(exec.name().contentEquals("ODP"))?UIPerfConstants.listOfActions_ODP:
								exec.name().contentEquals("ValueMapping")?UIPerfConstants.listOfActions_VM:
									exec.name().contentEquals("OpenResource")?UIPerfConstants.listOfActions_RS:
										exec.name().contentEquals("OPenMessageMapping")?UIPerfConstants.listOfActions_MM:
											exec.name().contentEquals("ODP2")?UIPerfConstants.listOfActions_ODP2:
												UIPerfConstants.listOfActions_LContnModifier,
											
											exec.name().contentEquals("UIPerf")&& comparePurpose.contentEquals("RT")?UIPerfConstants.baselineActions:
												(exec.name().contentEquals("ODP"))?UIPerfConstants.baselineActions_ODP:
													exec.name().contentEquals("UIPerf")&& comparePurpose.contentEquals("RTXL")?UIPerfConstants.baselineActionsXL:
													exec.name().contentEquals("ValueMapping")?UIPerfConstants.baselineActions_VM:
														exec.name().contentEquals("OpenResource")?UIPerfConstants.baselineActions_RS:
															exec.name().contentEquals("ODP2")?UIPerfConstants.baselineActions_ODP2:
															exec.name().contentEquals("OPenMessageMapping")?UIPerfConstants.baselineActions_MM:
																exec.name().contentEquals("ODP2")?UIPerfConstants.baselineActions_ODP2:
																	UIPerfConstants.baselineActions_LContnModifier
																	);
			}
		}
		try {
			gFwk= new GUID_Fwk(homePath);
			gFwk.addGUIDEntry(guID);
			gFwk.putBackAllEntries();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void analyzeData() throws IOException {
		if(ENABLESUPA) {
			if(compareTeamName.contentEquals("") && comparePurpose.contentEquals("")) {
				compareTeamName = "prism";
				comparePurpose="RT";
			}
			boolean KPImet = supa
					.compareResults(((
							exec.name().contentEquals("UIPerf"))?UIPerfConstants.listOfActions:
								(exec.name().contentEquals("ODP"))?UIPerfConstants.listOfActions_ODP:
									exec.name().contentEquals("ValueMapping")?UIPerfConstants.listOfActions_VM:
										exec.name().contentEquals("OpenResource")?UIPerfConstants.listOfActions_RS:
											exec.name().contentEquals("OPenMessageMapping")?UIPerfConstants.listOfActions_MM:
												exec.name().contentEquals("ODP2")?UIPerfConstants.listOfActions_ODP2:
													UIPerfConstants.listOfActions_LContnModifier),
													teamName, 
														purpose, 
							(
									exec.name().contentEquals("UIPerf"))?UIPerfConstants.__COMMON_PATH:
										(
												exec.name().contentEquals("ODP"))?UIPerfConstants.__COMMON_PATH_ODP:
													exec.name().contentEquals("ValueMapping")?UIPerfConstants.__COMMON_PATH_VM:
														exec.name().contentEquals("OpenResource")?UIPerfConstants.__COMMON_PATH_RS:
															exec.name().contentEquals("OPenMessageMapping")?UIPerfConstants.__COMMON_PATH_MM:
																exec.name().contentEquals("ODP2")?UIPerfConstants.__COMMON_PATH_ODP2:
																	UIPerfConstants.__COMMON_PATH_LContnModifier,
																	compareTeamName,
																		comparePurpose);
											if(!KPImet) {
														supa.regression();
														}
											else {
														supa.noRegression();
													}
			if(comparePurpose.endsWith("XL")){
				boolean KPImetWithBaseline = supa
						.compareResultsWithBaseline(
								(
										(
												exec.name().contentEquals("UIPerf"))?UIPerfConstants.listOfActions:
													(
															exec.name().contentEquals("ODP"))?UIPerfConstants.listOfActions_ODP:
																exec.name().contentEquals("ValueMapping")?UIPerfConstants.listOfActions_VM:
																	exec.name().contentEquals("OpenResource")?UIPerfConstants.listOfActions_RS:
																		exec.name().contentEquals("OPenMessageMapping")?UIPerfConstants.listOfActions_MM:
																			exec.name().contentEquals("ODP2")?UIPerfConstants.listOfActions_ODP2:
																				UIPerfConstants.listOfActions_LContnModifier),
																				teamName, 
																					purpose, 
																						(
																								exec.name().contentEquals("UIPerf")
																									)?UIPerfConstants.__COMMON_PATH:
																										(exec.name().contentEquals("ODP"))?UIPerfConstants.__COMMON_PATH_ODP:
																											exec.name().contentEquals("ValueMapping")?UIPerfConstants.__COMMON_PATH_VM:
																												exec.name().contentEquals("OpenResource")?UIPerfConstants.__COMMON_PATH_RS:
																													exec.name().contentEquals("OPenMessageMapping")?UIPerfConstants.__COMMON_PATH_MM:
																														exec.name().contentEquals("ODP2")?UIPerfConstants.__COMMON_PATH_ODP2:
																															UIPerfConstants.__COMMON_PATH_LContnModifier,
																														(
																																(
																																		exec.name().contentEquals("UIPerf"))?UIPerfConstants.baselineActionsXL:
																																			(
																																					exec.name().contentEquals("ODP"))?UIPerfConstants.baselineActions_ODP:
																																						(
																																								exec.name().contentEquals("ValueMapping"))?UIPerfConstants.baselineActions_VM:
																																									(
																																											exec.name().contentEquals("OpenResource"))?UIPerfConstants.baselineActions_RS:
																																												exec.name().contentEquals("OPenMessageMapping")?UIPerfConstants.baselineActions_MM:
																																													exec.name().contentEquals("ODP2")?UIPerfConstants.baselineActions_ODP2:
																																														exec.name().contentEquals("LargeContentModifier")?UIPerfConstants.baselineActions_LContnModifier:null
																																										)
																																											);
				if(!KPImetWithBaseline) {
					supa.regression();
				}
				else {
					supa.noRegression();
				}
			}else{
				boolean KPImetWithBaseline = supa
						.compareResultsWithBaseline(
								(
										(
												exec.name().contentEquals("UIPerf")
												)
										?UIPerfConstants.listOfActions:
											(
													exec.name().contentEquals("ODP"))?UIPerfConstants.listOfActions_ODP:
														exec.name().contentEquals("ValueMapping")?UIPerfConstants.listOfActions_VM:
															exec.name().contentEquals("OpenResource")?UIPerfConstants.listOfActions_RS:
																exec.name().contentEquals("OPenMessageMapping")?UIPerfConstants.listOfActions_MM:
																	exec.name().contentEquals("ODP2")?UIPerfConstants.listOfActions_ODP2:
																		UIPerfConstants.listOfActions_LContnModifier),
																		teamName, 
																			purpose, 
								(
										exec.name().contentEquals("UIPerf"))?UIPerfConstants.__COMMON_PATH:
											(
													exec.name().contentEquals("ODP"))?UIPerfConstants.__COMMON_PATH_ODP:
														exec.name().contentEquals("ValueMapping")?UIPerfConstants.__COMMON_PATH_VM:
															exec.name().contentEquals("OpenResource")?UIPerfConstants.__COMMON_PATH_RS:
																exec.name().contentEquals("OPenMessageMapping")?UIPerfConstants.__COMMON_PATH_MM:
																	exec.name().contentEquals("ODP2")?UIPerfConstants.__COMMON_PATH_ODP2:
																		UIPerfConstants.__COMMON_PATH_LContnModifier,
																	(
																			(
																					exec.name().contentEquals("UIPerf"))?UIPerfConstants.baselineActions:
																						(
																								exec.name().contentEquals("ODP"))?UIPerfConstants.baselineActions_ODP:
																									(
																											exec.name().contentEquals("ValueMapping"))?UIPerfConstants.baselineActions_VM:
																												(
																														exec.name().contentEquals("OpenResource"))?UIPerfConstants.baselineActions_RS:
																															exec.name().contentEquals("OPenMessageMapping")?UIPerfConstants.baselineActions_MM:
																																exec.name().contentEquals("ODP2")?UIPerfConstants.baselineActions_ODP2:
																																	exec.name().contentEquals("LargeContentModifier")?UIPerfConstants.baselineActions_LContnModifier:null
																															)
																	);
				if(!KPImetWithBaseline) {
					supa.regression();
				}
				else {
					supa.noRegression();
				}
			}
			supa
			.analyzeClientLogs(
					pathForUseGUID+"\\latestResult",
					(
							exec.name().contentEquals("UIPerf"))?UIPerfConstants.listOfActions:
								(
										exec.name().contentEquals("ODP"))?UIPerfConstants.listOfActions_ODP:
											exec.name().contentEquals("ValueMapping")?UIPerfConstants.listOfActions_VM:
												exec.name().contentEquals("OpenResource")?UIPerfConstants.listOfActions_RS:
													exec.name().contentEquals("OPenMessageMapping")?UIPerfConstants.listOfActions_MM:
														exec.name().contentEquals("ODP2")?UIPerfConstants.listOfActions_ODP2:
															UIPerfConstants.listOfActions_LContnModifier);
			
		}
		else {
			System.out.println("Check UI based results in VM");
		}
	}
	
	public void shutdownServer() {
		if(ENABLESUPA) {
			try{
				supa.shutdownServer();
			}catch(Exception e) {
				System.out.println("--------------------------------------------------------------------------------------------");
				System.out.println("--------------------Failed to shutdown the supa server. Change proxy in VM.-----------------");
				System.out.println("--------------------------------------------------------------------------------------------");
				e.printStackTrace();
			}
			uploadCred.removeCredentials();
		}
	}
	
	private void mainflowForOneIflow(String PACKAGENAME, String IFLOWNAME) { 
			if(exec.name().contentEquals("UIPerf"))
				measureUIPerf(PACKAGENAME,IFLOWNAME);
			else if(exec.name().contentEquals("ODP")) 
				measureODP(PACKAGENAME,IFLOWNAME);
			else if(exec.name().contentEquals("ValueMapping"))
				measureVM(PACKAGENAME,IFLOWNAME);
			else if(exec.name().contentEquals("OpenResource"))
				measureResource(PACKAGENAME, IFLOWNAME);
			else if(exec.name().contentEquals("OPenMessageMapping"))
				measureMessageMappng(PACKAGENAME, IFLOWNAME);
			else if(exec.name().contentEquals("ODP2"))
				measureODP2(PACKAGENAME,IFLOWNAME);
			else
				measureLargeContentModifier(PACKAGENAME, IFLOWNAME);
	}
	
	private void computeResponseTimeStats(int INVOCATIONCOUNT)  {
		try {
			csvwrite = new FileUtils();
			csvwrite.frequencyDistributionTest(pathForUseGUID,INVOCATIONCOUNT,numberOfActions);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void measureUIPerf(String PACKAGENAME, String IFLOWNAME) {
		boolean errorFlag = false;
		boolean iflowWorking = false;
		for(int i=0;i<INVOCATIONCOUNT;i++) {
			try {
				uiResponseTimes = new ArrayList<>();
				openIFLOW(PACKAGENAME,IFLOWNAME);
				editDirtyBitChange(IFLOWNAME);
				saveIflow(IFLOWNAME);
				deployIflow(IFLOWNAME);
				writeResponses(PACKAGENAME, IFLOWNAME,i);
				iflowWorking=true;
			}
			catch(Exception e) {
				e.printStackTrace();
				errorFlag=true;
				System.out.println(e.getMessage()+"\nProblem in Iflow: \n"+IFLOWNAME+"\nSkipping this iflow...");
				try {
					supa.resetDataProviders();
				} catch (Exception e3) {
					e3.printStackTrace();
					
				}
			}
			try {
				cleanTenant(IFLOWNAME);
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("Couldn't close popups properly");
			}
		}
		if(!iflowWorking) {
			errorCount2++;
			try {
				throw new Exception("Problem in iflow "+ IFLOWNAME);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(errorFlag) {
			errorCount++;
			problemIflows.add(IFLOWNAME);
			int index = problemIflows.indexOf(IFLOWNAME);
			System.out.println(index);
		}
	}

	private void measureVM(String PACKAGENAME, String IFLOWNAME) {
		for(int i=0;i<INVOCATIONCOUNT;i++) {
			try {
				uiResponseTimes = new ArrayList<>();
				openIFLOW_VM(PACKAGENAME,IFLOWNAME);
				editDirtyBitChange_VM();
				saveIflow_VM(IFLOWNAME);
				deployIflow_VM(IFLOWNAME);
				writeResponses(PACKAGENAME, IFLOWNAME,i);
				navigateBack_VM();
			}
			catch(Exception e) {
				e.printStackTrace();
				try {
					supa.resetDataProviders();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
			try {
				cleanTenant(IFLOWNAME);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void measureResource(String PACKAGENAME, String IFLOWNAME) {
		for(int i=0;i<INVOCATIONCOUNT;i++) {
			try {
				uiResponseTimes = new ArrayList<>();
				openIFLOW(PACKAGENAME,IFLOWNAME);
				OpenresourceFile("CompoundEmployeeEntityquerySync0");
				writeResponses(PACKAGENAME, IFLOWNAME,i);
			}catch(Exception e) {
				e.printStackTrace();
				try {
					supa.resetDataProviders();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
			try {
				cleanTenant(IFLOWNAME);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void measureMessageMappng(String PACKAGENAME, String IFLOWNAME) {
		for(int i=0;i<INVOCATIONCOUNT;i++) {
			try {
				uiResponseTimes = new ArrayList<>();
				openIFLOW(PACKAGENAME,IFLOWNAME);
				OpenresourceMessageMapping("CompoundEmployeeEntityquerySync0");
				writeResponses(PACKAGENAME, IFLOWNAME,i);
			}catch(Exception e) {
				e.printStackTrace();
				try {
					supa.resetDataProviders();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
			try {
				cleanTenant(IFLOWNAME);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void measureODP(String PACKAGENAME, String IFLOWNAME) {
		for(int i=0;i<INVOCATIONCOUNT;i++) {
			try {
				uiResponseTimes = new ArrayList<>();
				openODPProject(PACKAGENAME,IFLOWNAME);
				editODP();
				openODPIflow();
			//	dirtyBitChange();
				saveODPIflow();
				saveODP();
				deployODP();
				writeResponses(PACKAGENAME, IFLOWNAME,i);
			}
			catch (Exception e) {
				e.printStackTrace();
				try {
					supa.resetDataProviders();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
			try {
				cleanTenant(URL);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void measureODP2(String PACKAGENAME, String IFLOWNAME) {
		for(int i=0;i<INVOCATIONCOUNT;i++) {
			try {
				uiResponseTimes = new ArrayList<>();
				openODPProject2(PACKAGENAME,IFLOWNAME);
				editODP2();
				openODPIflow2();
			//	dirtyBitChange();
				saveODPIflow2();
				cancelODPiFlow();
				saveODP2();
				deployODP2();
				writeResponses(PACKAGENAME, IFLOWNAME,i);
			}
			catch (Exception e) {
				e.printStackTrace();
				try {
					supa.resetDataProviders();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
			try {
				cleanTenant(URL);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void measureLargeContentModifier(String PACKAGENAME, String IFLOWNAME) {
		for(int i=0;i<INVOCATIONCOUNT;i++) {
			try {
				uiResponseTimes = new ArrayList<>();
				openIFLOW(PACKAGENAME,IFLOWNAME);
				editDirtyBitChange(IFLOWNAME);
				saveIflow(IFLOWNAME);
				deployIflow(IFLOWNAME);
				writeResponses(PACKAGENAME, IFLOWNAME,i);
				navigateBack_VM();
			}
			catch(Exception e) {
				e.printStackTrace();
				try {
					supa.resetDataProviders();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
			try {
				cleanTenant(IFLOWNAME);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void editODP2() {
		pDesign = new Prism_Design_Page(driver);
		pDesign.editODP();
	}
	
	private void deployODP() throws Exception {
		pDesign = new Prism_Design_Page(driver);
		pOthers = new Prism_Design_Others(driver);
		if(supa!=null) {
			supa.startMeasurement("deployODPProject");
		}		
		uiResponseTimes.add(4,pOthers.deploy(false));
		if(supa!=null) {
			try {
				supa.stopMeasurement(1/*uiResponseTimes.get(4)/1000+10*/);
			}
			catch (Exception e) {
				throw new Exception(e.getMessage()+"\nProblem in saving ODP project\n");
			}
		}
	}
	
	private void deployODP2() throws Exception {
		pDesign = new Prism_Design_Page(driver);
		pOthers = new Prism_Design_Others(driver);
		if(supa!=null) {
			supa.startMeasurement("deployODPProject");
		}		
		uiResponseTimes.add(5,pOthers.deploy(false));
		if(supa!=null) {
			try {
				supa.stopMeasurement(1/*uiResponseTimes.get(5)/1000+10*/);
			}
			catch (Exception e) {
				throw new Exception(e.getMessage()+"\nProblem in saving ODP project\n");
			}
		}
	}
	
	private void openODPProject(String PACKAGENAME, String IFLOWNAME) {
		pDesign = new Prism_Design_Page(driver);
		uiResponseTimes.add(0,pDesign.prism_UIPerf_OpenODP(URL,PACKAGENAME,IFLOWNAME,supa,ENABLEJVMPROFILING));
	}
	
	private void openODPProject2(String PACKAGENAME, String IFLOWNAME) throws Exception{
		pDesign = new Prism_Design_Page(driver);
		uiResponseTimes.add(0,pDesign.prism_UIPerf_OpenODP(URL,PACKAGENAME,IFLOWNAME,supa,ENABLEJVMPROFILING));
	}
	
	private void saveODP() throws Exception {
		pOthers = new Prism_Design_Others(driver);
		if(supa!=null) {
			supa.startMeasurement("saveODPProject");
		}		
		uiResponseTimes.add(3,pOthers.save(false));
		if(supa!=null) {
			try {
				supa.stopMeasurement(1/*uiResponseTimes.get(3)/1000+10*/);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void saveODP2() throws Exception {
		pDesign = new Prism_Design_Page(driver);
		pOthers = new Prism_Design_Others(driver);
		if(supa!=null) {
			supa.startMeasurement("saveODPProject");
		}		
		uiResponseTimes.add(4,pOthers.save(false));
		if(supa!=null) {
			try {
				supa.stopMeasurement(1/*uiResponseTimes.get(4)/1000+10*/);
			}
			catch (Exception e) {
				throw new Exception(e.getMessage()+"\nProblem in saving ODP project\n");
			}
		}
	}
	
	private void saveODPIflow() throws Exception {
		Prism_Master_Class masterClass = new Prism_Master_Class(driver);
		pDesign = new Prism_Design_Page(driver);
		if(supa!=null) {
			supa.startMeasurement("SaveODPIflow");
		}
		long start = System.currentTimeMillis();
		pDesign.elementsizeClick("//bdi[text() ='OK']/../../..");
		waitUntilLoadingCompletes(UIPerfConstants.timeout);
		long stop = System.currentTimeMillis();
		uiResponseTimes.add(2,stop-start);
		if(supa!=null) {
			try {
				supa.stopMeasurement(1/*uiResponseTimes.get(2)/1000+10*/);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		pDesign.elementsizeClick("//*[@title='OK']//following::button[@title='Cancel'][position()=1]");
		masterClass.waitforDesignPage();
		waitUntilLoadingCompletes(UIPerfConstants.timeout);
	}
	
	private void saveODPIflow2() throws Exception {
		pDesign = new Prism_Design_Page(driver);
		if(supa!=null) {
			supa.startMeasurement("SaveODPIflow");
		}
		long start = System.currentTimeMillis();
		pDesign.elementsizeClick("//bdi[text() ='OK']/../../..");
		waitUntilLoadingCompletes(UIPerfConstants.timeout);
		long stop = System.currentTimeMillis();
		uiResponseTimes.add(2,stop-start);
		if(supa!=null) {
			try {
				supa.stopMeasurement(1/*uiResponseTimes.get(2)/1000+10*/);
			}
			catch (Exception e) {
				throw new Exception(e.getMessage()+"\nProblem in saving iflow:\nTask set Update\n");
			}
		}
	}
	
	private void cancelODPiFlow() throws Exception{
		pDesign = new Prism_Design_Page(driver);
		if(supa!=null) {
			supa.startMeasurement("cancelODPIflow");
		}
		long start = System.currentTimeMillis();
		pDesign.elementsizeClick("//bdi[text() ='OK']/../../../..//bdi[text() ='Cancel']/../../..");
		waitUntilLoadingCompletes(UIPerfConstants.timeout);
		long stop = System.currentTimeMillis();
		uiResponseTimes.add(3,stop-start);
		if(supa!=null) {
			try {
				supa.stopMeasurement(1/*uiResponseTimes.get(3)/1000+10*/);
			}
			catch (Exception e) {
				throw new Exception(e.getMessage()+"\nProblem in saving iflow:\nTask set Update\n");
			}
		}
	}
	
	private void openODPIflow(){
		Prism_Master_Class masterClass = new Prism_Master_Class(driver);
		if(supa!=null) {
			supa.startMeasurement("OpenODPIflow");
		}
		long start = System.currentTimeMillis();
		masterClass.elementsizeClick("//span[text() = 'Update']/../../../../..//span[@title='Navigate to Integration Flow Editor' and @style]");
		waitUntilLoadingCompletes(UIPerfConstants.timeout);
		long stop = System.currentTimeMillis();
		uiResponseTimes.add(1,stop-start);
		if(supa!=null) {
			try {
				supa.stopMeasurement(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void openODPIflow2() throws Exception{
		pDesign = new Prism_Design_Page(driver);
		if(supa!=null) {
			supa.startMeasurement("OpenODPIflow");
		}
		long start = System.currentTimeMillis();
		pDesign.elementsizeClick("//span[text() = 'Update']/../../../../..//span[@title='Navigate to Integration Flow Editor' and contains(@style,'font-family')]");
		waitUntilLoadingCompletes(UIPerfConstants.timeout);
		long stop = System.currentTimeMillis();
		uiResponseTimes.add(1,stop-start);
		if(supa!=null) {
			try {
				supa.stopMeasurement(1/*uiResponseTimes.get(1)/1000+10*/);
			}
			catch (Exception e) {
				throw new Exception(e.getMessage()+"\nProblem in opening iflow:\nTask set Update\n");
			}
		}
	}
	
	public void waitUntilLoadingCompletes(int timeout) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofMillis(100))
				.ignoring(Exception.class);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(
	                By.xpath("//*[contains(@id,'sap-ui-blocklayer-popup')][contains(@style,'visibility: visible')]")));
	}
	
	private void cleanTenant(String URL) {
		pDesign = new Prism_Design_Page(driver);
		pDesign.prism_design_close_all_popups(URL);
	}
	
	private void deployIflow(String IFLOWNAME) throws Exception {
		pOthers = new Prism_Design_Others(driver);
		if(supa!=null) {
			supa.startMeasurement("DeployIflowTime"+IFLOWNAME);
		}
		uiResponseTimes.add(3,pOthers.deploy(ENABLEJVMPROFILING));
		if(supa!=null) {
			try {
				supa.stopMeasurement(1/*uiResponseTimes.get(3)/1000+10*/);
			}
			catch (Exception e) {
				throw new Exception(e.getMessage()+"\nProblem in deploying iflow:\n"+IFLOWNAME);
			}
		}
	}
	
	private void deployIflow_VM(String IFLOWNAME) throws Exception {
		pOthers = new Prism_Design_Others(driver);
		if(supa!=null) {
			supa.startMeasurement("DeployIflowTime"+IFLOWNAME);
		}
		uiResponseTimes.add(2,pOthers.deploy_VM(ENABLEJVMPROFILING));
		if(supa!=null) {
			try {
				supa.stopMeasurement(1/*uiResponseTimes.get(2)/1000+10*/);
			}
			catch (Exception e) {
				throw new Exception(e.getMessage()+"\nProblem in deploying iflow:\n"+IFLOWNAME);
			}
		}
	}
	
	private void navigateBack_VM() throws Exception {
		Prism_Master_Class masterClass = new Prism_Master_Class(driver);
		try {
			masterClass.sleep(2000);
			masterClass.elementsizeClick("//*[text()='Save']/../../../../..//*[text()='UI_Performance_Value_Mapping']");
			waitUntilBusyIndicatorIsInvisible();
			masterClass.elementsizeClick("//*[text()='UI_Perf_Value_Mapping']/../../../../../..//button");
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void saveIflow(String IFLOWNAME) throws Exception {
		pOthers = new Prism_Design_Others(driver);
		if(supa!=null) {
			supa.startMeasurement("SaveIflowTime"+IFLOWNAME);
		}
		uiResponseTimes.add(2,pOthers.save(ENABLEJVMPROFILING));
		if(supa!=null) {
			try {
				supa.stopMeasurement(1/*(uiResponseTimes.get(2))/1000+10*/);
			}
			catch (Exception e) {
				throw new Exception(e.getMessage()+"\nProblem in saving iflow"+IFLOWNAME);
			}
		}
	}
	
	private void saveIflow_VM(String IFLOWNAME) throws Exception {
		pOthers = new Prism_Design_Others(driver);
		if(supa!=null) {
			supa.startMeasurement("SaveIflowTime"+IFLOWNAME);
		}
		uiResponseTimes.add(1,pOthers.save(ENABLEJVMPROFILING));
		if(supa!=null) {
			try {
				supa.stopMeasurement(1/*(uiResponseTimes.get(1))/1000+10*/);
			}
			catch (Exception e) {
				throw new Exception(e.getMessage()+"\nProblem in saving iflow"+IFLOWNAME);
			}
		}
	}
	
	
	private void openIFLOW(String PACKAGENAME, String IFLOWNAME) throws Exception {
		pDesign = new Prism_Design_Page(driver);
		uiResponseTimes.add(0,pDesign.prism_UIPerf_OpenIflow(URL,PACKAGENAME,IFLOWNAME,supa,ENABLEJVMPROFILING));
	}
	
	private void openIFLOW_VM(String PACKAGENAME, String IFLOWNAME) throws Exception {
		pDesign = new Prism_Design_Page(driver);
		uiResponseTimes.add(0,pDesign.prism_UIPerf_OpenIflow_VM(URL,PACKAGENAME,IFLOWNAME,supa,ENABLEJVMPROFILING));
	}
	
	private void editDirtyBitChange(String IFLOWNAME) throws Exception {
		clickEditOpenIflow(IFLOWNAME);
	 //	dirtyBitChange();
	}
	
	private void editDirtyBitChange_VM() {
		pDesign = new Prism_Design_Page(driver);
		pDesign.clickEditVm();
		//dirtyBitChange_VM();
	}
	
	private void editODP() {
		pDesign = new Prism_Design_Page(driver);
		pDesign.editODP();
		//dirtyBitChange_VM();
	}
	
	/*private void dirtyBitChange() {
		Prism_Master_Class masterClass = new Prism_Master_Class(driver);
		prism_design_ReceiverElement(30, 30);
		masterClass.elementsizeClick("//*[@id='Delete']");
	}
	
	private void dirtyBitChange_VM() {
		Prism_Master_Class masterClass = new Prism_Master_Class(driver);
		String valuemappingDeleteStatus = "//*[contains(text(),'Value Mapping deleted')]";
		String addvaluemapping = "//button[contains(@id,'addCVM')]";
		String deletevaluemapping = "//*[contains(@id,'CVMTable-rows-row0-col3')]//span";
		String yesButton = "//*[text()='Yes']";

		masterClass.elementsizeClick(addvaluemapping);
		masterClass.sleep(1000);
		masterClass.elementsizeClick(deletevaluemapping);
		masterClass.sleep(1000);
		masterClass.elementsizeClick(yesButton);
		masterClass.elementsizeClick(valuemappingDeleteStatus);
		String valuemappingDeletestatus = driver.findElement(By.xpath(valuemappingDeleteStatus)).getText();
		System.out.println("Status of the VM Delete : "+valuemappingDeletestatus.toUpperCase());
	}*/
	
	private void clickEditOpenIflow(String IFLOWNAME) throws Exception {
		pDesign = new Prism_Design_Page(driver);
		if(supa!=null) {
			supa.startMeasurement("EditIflowTime"+IFLOWNAME);
		}
		uiResponseTimes.add(1,pDesign.clickEdit());
		if(supa!=null) {
			try {
				supa.stopMeasurement(1/*(uiResponseTimes.get(1))/1000+10*/);
			}
			catch (Exception e) {
				throw new Exception(e.getMessage()+"\nProblem in Edit iflow"+IFLOWNAME);
			}
		}
	}

	private void writeResponses(String PACKAGENAME, String IFLOWNAME,int i) {
		try {
			String invocationType = ((i>0)?"WithCache":"WithoutCache");
			csvwrite = new FileUtils();
			FileWriter writer;
			FileWriter writer2;
			writer = new FileWriter(pathForUseGUID+IFLOWNAME+invocationType+fileextension,true);
			writer2 = new FileWriter(pathForUseGUID+openIflowPathName+invocationType+fileextension,true);
			List<String> values = new ArrayList<String>();
			for(int j=0;j<numberOfActions;j++) {
				values.add(""+uiResponseTimes.get(j));
			}
			csvwrite.writeLine(writer, values);
			csvwrite.writeLine(writer2, values);
            writer2.flush(); 
            writer2.close();
            writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	
	private String[] getSupaConfig(String filePath) throws IOException {
		File file;
		try {
			file = new File(filePath);
		}catch(Exception e ) { e.printStackTrace();throw e;}
		List<String> config1= null;
		String[] config2 = null;
		try {
			config1= Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
			config2 = config1.toArray(new String[0]);
			System.out.println("Printing from getSupaConfig Method : ");
			System.out.println(config2.length);
		}
		catch(Exception e) {e.printStackTrace();throw e;}
		return config2;
	}
	
	private void setTimeout(String Timeout) {
		if(!Timeout.contentEquals("")){
			UIPerfConstants.timeout = Integer.parseInt(Timeout);
			System.out.println("Time Setting from Jenkin : "+UIPerfConstants.timeout);
		}
	}
	
	public void OpenresourceFile(String resource) throws Exception{
		pOthers = new Prism_Design_Others(driver);
		if(supa!=null) {
			supa.startMeasurement("OpenResource"+resource);
		}
		uiResponseTimes.add(1,pOthers.prism_selenium_openResource(resource,ENABLEJVMPROFILING));
		if(supa!=null) {
			try {
				supa.stopMeasurement(1/*(uiResponseTimes.get(1))/1000+10*/);
			}
			catch (Exception e) {
				throw new Exception(e.getMessage()+"\nProblem in Opening resource"+resource);
			}
		}
	}
	
	public void OpenresourceMessageMapping(String resource) throws Exception{
		pOthers = new Prism_Design_Others(driver);
		if(supa!=null) {
			supa.startMeasurement("OPenMessageMapping"+resource);
		}
		uiResponseTimes.add(1,pOthers.prism_selenium_openMessageMapping(resource,ENABLEJVMPROFILING));
		if(supa!=null) {
			try {
				supa.stopMeasurement((uiResponseTimes.get(1))/1000+10);
			}
			catch (Exception e) {
				throw new Exception(e.getMessage()+"\nProblem in Opening Message Mapping"+resource);
			}
		}
	}
	
	public void getErrorCount() {
		System.out.println("Number of Iflows failed = "+errorCount+" \nNumberof Iflows failed completely = "+errorCount2);
		System.out.println("Following Iflows had problems atleast once in either open, save or deploy\n");
		for(String iflow: problemIflows) {
			System.out.println(iflow);
		}
	}
}
package com.prism.UIPerf.framework;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import au.com.bytecode.opencsv.CSVReader;

class GUID_Fwk {
	private String GUIDFilePath;
	private String GUIDDataFile;
	private String GUIDTagsFile;
	private final String GUIDTagsFileName = UIPerfConstants.GUIDTagsFileName;
	private final String GUIDFileName = UIPerfConstants.GUIDFileName;
	private Map<Long, String> GUIDData = new HashMap<Long, String>();
	private Map<Long, String> GUIDTagsData = new HashMap<Long, String>();
	
	GUID_Fwk(String filePath) throws IOException {
		this.GUIDFilePath = filePath;
		this.GUIDDataFile = this.GUIDFilePath+this.GUIDFileName;
		this.GUIDTagsFile = this.GUIDFilePath+this.GUIDTagsFileName;
		getGUIDDataFromFile(GUIDTagsData,GUIDTagsFile);
		getGUIDDataFromFile(GUIDData,GUIDDataFile);
	}
	
	private void getGUIDDataFromFile(Map<Long,String> map, String fileName) throws IOException {
		File GUIDFileObject = new File(fileName);
		CSVReader GUIDcsvReader = new au.com.bytecode.opencsv.CSVReader(new FileReader(GUIDFileObject),',');
		List<String[]> GUIDEntryList = GUIDcsvReader.readAll();
		GUIDcsvReader.close();
		for(int i=0;i<GUIDEntryList.size();i++) {
			map.put(Long.parseLong((GUIDEntryList.get(i))[0]), (GUIDEntryList.get(i))[1]);
		}
	}
	
	
	
	
	
	
	
	void addGUIDEntry(GUID guID) {
		GUIDTagsData.put(guID.getGUID(), guID.getTags());
		GUIDData.put(guID.getGUID(), guID.getTeamNamePurpose());
	}
	
	void putBackAllEntries(){
		putBackEntries(this.GUIDTagsData,GUIDTagsFile);
		putBackEntries(this.GUIDData,GUIDDataFile);
	}
	
	private void putBackEntries(Map<Long,String> map, String FilePath) {
		File fileObject = new File(FilePath);
		FileWriter writer;
		try {
			writer = new FileWriter(fileObject,false);
			for (Map.Entry<Long, String> entry : map.entrySet()) {
			    Long key = entry.getKey();
			    String value = entry.getValue();
			    writer.write(""+key+","+value+"\n");
			    writer.flush();
			}
			writer.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
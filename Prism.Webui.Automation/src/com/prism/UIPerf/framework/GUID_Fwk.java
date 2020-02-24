package com.prism.UIPerf.framework;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;

public class GUID_Fwk {
	private String GUIDFilePath;
	private String GUIDDataFile;
	private String GUIDTagsFile;
	private final String GUIDTagsFileName = UIPerfConstants.GUIDTagsFileName;
	private final String GUIDFileName = UIPerfConstants.GUIDFileName;
	private Map<Long, String> GUIDData = new HashMap<Long, String>();
	private Map<Long, String> GUIDTagsData = new HashMap<Long, String>();
	
	public GUID_Fwk(String filePath) throws IOException {
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
	
	public void addTagToGUID(GUID guID, String tag) {
		addTagToGUID(guID.getGUID(),tag);
	}
	
	public void addTagToGUID(long guID, String tag) {
		if(GUIDTagsData.containsKey(guID)) {
			if(tagIsNew(guID,tag)) {
				GUIDTagsData.put(guID, (GUIDTagsData.get(guID)==null)?tag:GUIDTagsData.get(guID)+"/"+tag+"/");
			}
			else {
				System.out.println("This tag already exists for the given GUID");
			}
		}
		else {
			System.out.println("Invalid GUID");
		}
	}
	
	private boolean tagIsNew(long key, String tag) {
		if(GUIDTagsData.get(key)==null) {
			return true;
		}
		else return GUIDTagsData.get(key).contains("/"+tag+"/");
	}
	
	public void removeTagFromGUID(GUID guID, String tag) {
		removeTagFromGUID(guID.getGUID(),tag);
	}
	
	public void removeTagFromGUID(long guID, String tag) {
		if(GUIDTagsData.containsKey(guID)) {
			if(GUIDTagsData.get(guID)!=null) {
				String newTagList = GUIDTagsData.get(guID).replace("/"+tag+"/", "");
				if(newTagList.contentEquals(GUIDTagsData.get(guID)))
					System.out.println("Tag does not exist for the GUID");
				else
					GUIDTagsData.put(guID, newTagList);
			}
			else
				System.out.println("Tag does not exist for the GUID");
		}
		else
			System.out.println("Invalid GUID");
	} 
	
	public Set<Long> returnGUIDS(String[] tags) {
		Set<Long> GUIDS = new HashSet<Long>();
        for(Map.Entry<Long,String> entry: GUIDTagsData.entrySet()){
            for(int i = 0;i<tags.length;i++) {
	        	if(entry.getValue().contains("/"+tags[i]+"/")){
	            	GUIDS.add(entry.getKey());
	            }
            }
        }
        System.out.println("keys : " + GUIDS);
        return GUIDS;
	}
	
	public void addGUIDEntry(GUID guID) {
		GUIDTagsData.put(guID.getGUID(), guID.getTags());
		GUIDData.put(guID.getGUID(), guID.getTeamNamePurpose());
	}
	
	public void putBackAllEntries(){
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
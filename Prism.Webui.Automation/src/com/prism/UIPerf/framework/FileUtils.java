package com.prism.UIPerf.framework;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileUtils {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.ms");
	private char DEFAULT_SEPARATOR = ',';
	private static String openIflowresponseTimesWithCache = "OPENIFLOWFILEWithCache";
	public void frequencyDistributionTest(String responseFilePath,int INVOCATIONCOUNT, int numberOfActions) throws IOException {
		new FileUtils().doCalculations(responseFilePath+openIflowresponseTimesWithCache+".csv",responseFilePath+openIflowresponseTimesWithCache,numberOfActions);
	}
	
	public static File[] listFilesMatching(File root, String regex) {
	    if(!root.isDirectory()) {
	        throw new IllegalArgumentException(root+" is no directory.");
	    }
	    final Pattern p = Pattern.compile(regex); 
	    return root.listFiles(new FileFilter(){
	        @Override
	        public boolean accept(File file) {
	            return p.matcher(file.getName()).matches();
	        }
	    });
	}

	public void writeLine(Writer w, List<String> values) throws IOException {
		writeLine(w, values, DEFAULT_SEPARATOR, ' ');
	}
	
	public void writeLine(Writer w, List<String> values, char separators) throws IOException {
		writeLine(w, values, separators, ' ');
	}
	private String followCVSformat(String value) {
		String result = value;
		if (result.contains("\"")) {
			result = result.replace("\"", "\"\"");
		}
		return result;
	}
	public int findSum(List<Integer> m) {
		int i;
		int sum = 0;
		for(i = 0; i < m.size(); i++)
		    sum += m.get(i);
		return sum;
	}
	public void fillBuckets(int[] bucket,List<Integer> values) {
		for(int i=0;i<values.size();i++) {
			double a=((values.get(i))/1000.0)*2;
			int b= (int)a;
			bucket[b>=200?200:b]++;
		}
	}
	
	private void doCalculations(String filename, String nameWithoutExtension, int numberOfActions) throws IOException{
		FileWriter writeFile = new FileWriter(nameWithoutExtension+"Distribution.csv",false);
	    FileWriter writeFile2 = new FileWriter(nameWithoutExtension+"Average.csv",true);
	    File file = new File(filename);
		List <Integer>	action = new ArrayList<Integer>();
		int actionBucket[];
		List<String> avgAction = new ArrayList<String>();
	    List<String> lines = Files.readAllLines(file.toPath(), 
	            StandardCharsets.UTF_8);
	    for(int i=0;i<numberOfActions;i++) {
	    	action = new ArrayList<Integer>();
	    	int avg=0;
	    	for (String line : lines) {
	    		String[] array = line.split(",");
	    		action.add(Integer.parseInt(array[i]));
	    	}
	    	actionBucket = new int[200];
	    	avg = findSum(action)/(action.size());
	    	avgAction.add(""+avg);
	    	fillBuckets(actionBucket,action);
	    	writeLine(writeFile,actionBucket);
	    }
	    writeLine(writeFile2,avgAction);
	    writeLineSpecial(writeFile);
	    writeFile.flush();
	    writeFile.close();
		writeFile2.flush();
	    writeFile2.close();
	}
	
	public void writeLineSpecial(Writer w) throws IOException {
		boolean first = true;
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<200;i++) {
			if (!first) {
				sb.append(DEFAULT_SEPARATOR);
			}
				sb.append(i/(2.0));
			first = false;
		}
		sb.append("\n");
		w.append(sb.toString());
	}
	
	public void writeLine(Writer w, int[] values) throws IOException {
		boolean first = true;
		StringBuilder sb = new StringBuilder();
		for (int value : values) {
			if (!first) {
				sb.append(DEFAULT_SEPARATOR);
			}
			sb.append(value);
			first = false;
		}
		sb.append("\n");
		w.append(sb.toString());
	}
	
	public void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {
		boolean first = true;
		if (separators == ' ') {
			separators = DEFAULT_SEPARATOR;
		}
		StringBuilder sb = new StringBuilder();
		for (String value : values) {
			if (!first) {
				sb.append(separators);
			}
			if (customQuote == ' ') {
				sb.append(followCVSformat(value));
			} else {
				sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
			}
			first = false;
		}
		sb.append("\n");
		w.append(sb.toString());
	}
	    
	public String timestamp(){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return sdf.format(timestamp);
	}
}
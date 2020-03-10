package com.prism.UIPerf.framework;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.regex.Pattern;

class FileUtils {
	
	/** Comma Separator **/
	private final char DEFAULT_SEPARATOR = ',';
	static File[] listFilesMatching(File root, String regex) {
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

	void writeLine(Writer w, List<String> values) throws IOException {
		writeLine(w, values, DEFAULT_SEPARATOR, ' ');
	}
	
	private String followCVSformat(String value) {
		String result = value;
		if (result.contains("\"")) {
			result = result.replace("\"", "\"\"");
		}
		return result;
	}
	
	private void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {
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
	    
	/*public String timestamp(){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return sdf.format(timestamp);
	}*/
}
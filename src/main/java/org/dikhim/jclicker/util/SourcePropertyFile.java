package org.dikhim.jclicker.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class SourcePropertyFile {
	private Map<String, String> properties = new HashMap<>();
 	private String source;
	public SourcePropertyFile(File file) {
		try {
			this.source = FileUtils.readFileToString(file, "UTF-8");
			parse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Cannot read source prop file");
		}
	}

	private void parse() {
		//for String like 
		//$item1:
		//text1
		//
		//$item2:
		//text2
		String p = "\\$(\\w+):\n((.\n?)+)\n?";//group1 = item1, group2 = item2
		
		Pattern pattern = Pattern.compile(p);
		Matcher m = pattern.matcher(source);
		while (m.find()) {
			if(m.groupCount()>1) {
				properties.put(m.group(1), m.group(2));
			}
		}
		for(Map.Entry<String, String> entry:properties.entrySet()) {
			System.out.println(entry.getKey()+" : "+entry.getValue());
		}
	}
	public String get(String propertyName) {
		String out = properties.get(propertyName);
		if(out==null)return "";
		return out;
	}

}

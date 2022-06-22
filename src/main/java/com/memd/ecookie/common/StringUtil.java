package com.memd.ecookie.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
	public static String splitAndGet(String input, String delimiter, int index) {
		if (input == null || index < 0) {
			return null;
		}
		
		if (delimiter == null) {
			delimiter = Constants.COMMA;
		}
		
		String[] splits = input.split(delimiter);
		if (index < splits.length) {
			return splits[index];
		}
		
		return null;
	}
	
	public static String getFirstNonNull(String...args) {
		for (String arg : args) {
			if (arg != null) {
				return arg;
			}
		}
		
		return null;
	}

	public static List<String> splitAndTrim(String input, String delimiter) {
		if (StringUtils.isBlank(input)) {
			return null;
		}
		
		if (delimiter == null) {
			delimiter = Constants.COMMA;
		}
		
		String[] splits = input.split(delimiter);
		List<String> result = new ArrayList<>(splits.length);
		for (String split : splits) {
			if (StringUtils.isNotBlank(split)) {
				result.add(split.trim());
			}
		}
		
		return result;
	}
}

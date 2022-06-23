package com.memd.ecookie.common;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(NumberUtil.class);
	
	public static Double parseDoubleNullZero(String valueStr) {
		Double value = parseDouble(valueStr);
		
		if(value == null) {
			return 0.0;
		}
		
		return value;
	}
	
	public static Double parseDouble(String valueStr) {
		if (StringUtils.isBlank(valueStr)) {
			return null;
		}
		
		Double value = null;
		try {
			value = Double.parseDouble(valueStr);
			if(value == null || Double.isNaN(value)) {
				return null;
			}
		} catch(Exception e) {
			LOGGER.warn(e.getMessage(), e);
		}
		
		return value;
	}

	public static Long parseLong(String valueStr) {
		if (valueStr == null) {
			return null;
		}
		Long value = null;
		try {
			value = Long.parseLong(valueStr);
		} catch(Exception e) {
			LOGGER.warn(e.getMessage(), e);
		}
		
		return value;
	}

	public static Integer parseInteger(String valueStr) {
		Integer value = null;
		try {
			value = Integer.parseInt(valueStr);
		} catch(Exception e) {
			LOGGER.warn(e.getMessage(), e);
		}
		
		return value;
	}
	
	public static Integer parseIntegerWithDefaultValue(String valueStr, int defaultValue) {
		Integer value = null;
		try {
			value = Integer.parseInt(valueStr);
		} catch(Exception e) {
			value = defaultValue;
		}
		
		return value;
	}
	
	public static String formatNumber(String valueStr, String format) {
		if(CommonUtil.isEmpty(valueStr) || CommonUtil.isEmpty(format)) {
			return valueStr;
		}
		if(valueStr.trim().length() == 0) {
			return "";
		}
		
		Double value = null;
		try {
			value = Double.parseDouble(valueStr);
			if(value == null || Double.isNaN(value)) {
				return valueStr;
			}
			NumberFormat formatter = new DecimalFormat(format); 
			return formatter.format(value);
			
		} catch(Exception e) {
		}
		return valueStr;
	}
	
	
	public static String toRawNumber(String formatedValue, String format) {
		if(CommonUtil.isEmpty(formatedValue) || CommonUtil.isEmpty(format)) {
			return format;
		}
		if(formatedValue.trim().length() == 0) {
			return "";
		}
		formatedValue = formatedValue.replaceAll(",", "");
		return formatedValue;
	}
	
	public static Double[] parseDoubleArray(String valuesStr) {
		if (StringUtils.isBlank(valuesStr)) {
			return null;
		}
		
		String[] splits = valuesStr.split(Constants.COMMA);
		if (splits == null || splits.length <= 0) {
			return null;
		}
		
		Double[] results = new Double[splits.length];
		for (int i=0; i<splits.length; i++) {
			results[i] = parseDouble(splits[i]);
		}
		
		return results;
	}

	public static Long getLong(Object value) {
		if (value == null) {
			return null;
		}
		
		if (value instanceof Number) {
			return ((Number)value).longValue();
		}
		
		return parseLong(value.toString());
	}

	public static Number getNumber(Object value) {
		if (value == null) {
			return null;
		}
		
		if (value instanceof Number) {
			return ((Number)value);
		}
		
		return parseDouble(value.toString());
	}

	public static Double getDouble(Object value) {
		if (value == null) {
			return null;
		}
		
		if (value instanceof Number) {
			return ((Number)value).doubleValue();
		}
		
		return parseDouble(value.toString());
	}

	public static Double multiply(Object...objects) {
		if (objects == null) {
			return null;
		}
		
		Double result = null;
		for (Object obj : objects) {
			Double doubleValue = getDouble(obj);
			if (doubleValue == null) {
				return null;
			}
			
			if (result == null) {
				result = doubleValue;
			} else {
				result *= doubleValue;
			}
		}
		
		return result;
	}
	
	public static Double getFirstDouble(Object... objects) {
		if (objects == null) {
			return null;
		}
		
		for (Object obj : objects) {
			Double doubleValue = getDouble(obj);
			if (doubleValue != null) {
				return doubleValue;
			}
		}
		
		return null;
	}
}

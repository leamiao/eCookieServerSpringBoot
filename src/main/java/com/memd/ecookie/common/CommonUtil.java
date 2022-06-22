package com.memd.ecookie.common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

	public static Double normalizePercent(Double percent) {
		if(percent != null) {
			return percent * 0.01;
		}
		
		return null;
	}

	public static Double normalizePercentNullZero(Double percent) {
		if(percent == null) {
			return 0.0;
		} else {
			return percent * 0.01;
		}
	}

	public static Double normalizePercentNullZeroWithBothBounds(Double percent) {
		if(percent == null) {
			return 0.0;
		} else if(percent < 0.0){
			return 0.0;
		} else if(percent > 100.0){
			return 1.0;
		}
		
		return percent * 0.01;
	}

	public static Double normalizePercentNullOneWithBothBounds(Double percent) {
		if(percent == null) {
			return 1.0;
		} else if(percent < 0.0){
			return 0.0;
		} else if(percent > 100.0){
			return 1.0;
		}
		
		return percent * 0.01;
	}

	public static Double normalizePercentNullOne(Double percent) {
		if(percent == null) {
			return 1.0;
		} else {
			return percent * 0.01;
		}
	}

	public static Double normalizePercentPlusOne(Double percent) {
		if(percent != null) {
			return 1.0 + percent * 0.01;
		}
		
		return null;
	}

	public static String trim(String input) {
		if(input != null) {
			input = input.trim();
		}		
		
		return input;
	}
	
	public static boolean isEmpty(String input) {
		if(input == null) {
			return true;
		}
		
		if(input.isEmpty()) {
			return true;
		}
		
		return false;
	}

	public static List<String> getFirstValueListFromStringPairList(Collection<StringPair> spList) {
		if(spList == null || spList.isEmpty()) {
			return null;
		}
		
		List<String> results = new ArrayList<String>();
		for(StringPair sp : spList) {
			if(sp.getValue1() != null) {
				results.add(sp.getValue1());
			}
		}
		
		return results;
	}

	public static double sum(double[] values, int fromIndex, int toIndex) {
		if(values == null) {
			return 0.0;
		}
		
		int size = values.length;
		double total = 0.0;
		for(int i=fromIndex; i<=toIndex; i++) {
			if(i >= 0 && i < size) {
				total += values[i];
			}
		}
		
		return total;
	}
	
    public static double round(double input, int precision) {    	
    	if(Double.isNaN(input)) {
    		return input;
    	}
        BigDecimal bd = new BigDecimal(Double.toString(input));
        if (input >= 0)
            bd = bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
        else
            bd = bd.setScale(precision, BigDecimal.ROUND_HALF_DOWN);

        return bd.doubleValue();
    }

    public static double floor(double input, int precision) {    	
    	if(Double.isNaN(input)) {
    		return input;
    	}
        BigDecimal bd = new BigDecimal(Double.toString(input));
        bd = bd.setScale(precision, BigDecimal.ROUND_FLOOR);

        return bd.doubleValue();
    }

    public static double ceil(double input, int precision) {    	
    	if(Double.isNaN(input)) {
    		return input;
    	}
        BigDecimal bd = new BigDecimal(Double.toString(input));
        bd = bd.setScale(precision, BigDecimal.ROUND_CEILING);

        return bd.doubleValue();
    }

	public static boolean getBooleanValue(Boolean nonReturnable) {		
		return nonReturnable != null ? nonReturnable.booleanValue() : false;
	}

	public static boolean isTrue(Object object) {
		if(object instanceof Boolean) {
			return ((Boolean)object).booleanValue();
		} else if(object instanceof Number) {
			return ((Number)object).intValue() != 0;
		} else if(object instanceof String) {
			return !((String)object).equals("");
		} else if(object != null) {
			return true;
		}
		
		return false;
	}
	

	public static String[] getUnitPair(String unit) {
		String[] results = new String[2];
		
		if(unit != null) {
			String[] splits = unit.split("/");
			if(splits.length > 0) {
				results[0] = splits[0];
			}
			if(splits.length > 1) {
				results[1] = splits[1];
			}
		}
		
		return results;
	}	
	
	public static String toStringFromObjectArray(Object[] values) {
		if(values == null) {
			return null;
		}
		
		return JsonHelper.getInstance().convertToJson(values);
	}
	
	public static String toStringFromDoubleArray(Double[] values) {
		if(values == null) {
			return null;
		}

		return JsonHelper.getInstance().convertToJson(values);
	}
	
	public static Double[] toDoubleArrayFromString(String valueStr) {
		if(valueStr == null) {
			return null;
		}
		
//		return JsonHelper.getInstance().convertFromJson(valueStr, Double[].class);
		valueStr = valueStr.replace("[", "");
		valueStr = valueStr.replace("]", "");
		String[] tmpValues = valueStr.split(",");
		if(tmpValues != null) {
			Double[] values = new Double[tmpValues.length];
			for(int i=0; i<tmpValues.length; i++) {
				String b = CommonUtil.trim(tmpValues[i]);
				if(b.contains("\"")) {
					b = b.replace("\"", "");
				}
				if(isNumber(b)) {
					values[i] = Double.parseDouble(b);
				}
			}
			
			return values;
		}
		return null; 
	}	
	
	public static String toStringFromStringArray(String[] values) {
		if(values == null) {
			return null;
		}
		/* 
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<values.length; i++) {
			if(i > 0) {
				sb.append(Constants.COMMA);
			}
			Double value = values[i];
			if(value != null) {
				sb.append(value);
			}
		}
		
		return sb.toString(); */
		return JsonHelper.getInstance().convertToJson(values);
	}
	
	public static String[] toStringArrayFromString(String valueStr) {
		if(valueStr == null) {
			return null;
		}
		
		valueStr = valueStr.replace("[", "");
		valueStr = valueStr.replace("]", "");
		String[] tmpValues = valueStr.split(",");
		if(tmpValues != null) {
			String[] values = new String[tmpValues.length];
			for(int i=0; i<tmpValues.length; i++) {
				String b = CommonUtil.trim(tmpValues[i]);
				if(b.length() > 0) {
					values[i] = b;
				}
			}
			
			return values;
		}
		return null; 
	}
	
	private static boolean isNumber(final String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        final char[] chars = str.toCharArray();
        int sz = chars.length;
        boolean hasExp = false;
        boolean hasDecPoint = false;
        boolean allowSigns = false;
        boolean foundDigit = false;
        // deal with any possible sign up front
        final int start = (chars[0] == '-') ? 1 : 0;
        if (sz > start + 1 && chars[start] == '0') { // leading 0
            if(chars[start + 1] != '.') {
            	if (
                        (chars[start + 1] == 'x') || 
                        (chars[start + 1] == 'X') 
                   ) { // leading 0x/0X
                       int i = start + 2;
                       if (i == sz) {
                           return false; // str == "0x"
                       }
                       // checking hex (it can't be anything else)
                       for (; i < chars.length; i++) {
                           if ((chars[i] < '0' || chars[i] > '9')
                               && (chars[i] < 'a' || chars[i] > 'f')
                               && (chars[i] < 'A' || chars[i] > 'F')) {
                               return false;
                           }
                       }
                       return true;
                  } else { // leading 0, but not hex, must be octal
                      int i = start + 1;
                      for (; i < chars.length; i++) {
                          if (chars[i] < '0' || chars[i] > '7') {
                              return false;
                          }
                      }
                      return true;               
                  }
            }        	
        }
        sz--; // don't want to loop to the last char, check it afterwords
              // for type qualifiers
        int i = start;
        // loop to the next to last char or to the last char if we need another digit to
        // make a valid number (e.g. chars[0..5] = "1234E")
        while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                foundDigit = true;
                allowSigns = false;

            } else if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent   
                    return false;
                }
                hasDecPoint = true;
            } else if (chars[i] == 'e' || chars[i] == 'E') {
                // we've already taken care of hex.
                if (hasExp) {
                    // two E's
                    return false;
                }
                if (!foundDigit) {
                    return false;
                }
                hasExp = true;
                allowSigns = true;
            } else if (chars[i] == '+' || chars[i] == '-') {
                if (!allowSigns) {
                    return false;
                }
                allowSigns = false;
                foundDigit = false; // we need a digit after the E
            } else {
                return false;
            }
            i++;
        }
        if (i < chars.length) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                // no type qualifier, OK
                return true;
            }
            if (chars[i] == 'e' || chars[i] == 'E') {
                // can't have an E at the last byte
                return false;
            }
            if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent
                    return false;
                }
                // single trailing decimal point after non-exponent is ok
                return foundDigit;
            }
            if (!allowSigns
                && (chars[i] == 'd'
                    || chars[i] == 'D'
                    || chars[i] == 'f'
                    || chars[i] == 'F')) {
                return foundDigit;
            }
            if (chars[i] == 'l'
                || chars[i] == 'L') {
                // not allowing L with an exponent or decimal point
                return foundDigit && !hasExp && !hasDecPoint;
            }
            // last character is illegal
            return false;
        }
        // allowSigns is true iff the val ends in 'E'
        // found digit it to make sure weird stuff like '.' and '1E-' doesn't pass
        return !allowSigns && foundDigit;
    }
	
	public static String removePrefix(String input, String prefix) {
		if(input == null || prefix == null) {
			return null;
		}
		
		if(prefix.length() >= input.length()) {
			return null;
		}
		
		int index = input.indexOf(prefix);
		if(index >= 0) {
			return input.substring(index + prefix.length());
		}
		
		return null;
	}
	
	public static boolean hasValue(Double[] values) {
		if(values == null) {
			return false;
		}
		
		if(values.length > 0) {
			for(int i=0; i<values.length; i++) {
				if(values[i] != null) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static String formatDate(DateFormat format, Date date) {
		if(format == null || date == null) {
			return null;
		}
		
		return format.format(date);
	}

	public static Double parseDefaultDecimalFormatToDouble(Object value) {
		if(value == null) {
			return null;
		}
		
		if(value instanceof Number) {
			return ((Number) value).doubleValue();
		}

		try {
			String valueStr = trim(value.toString());
			if(!CommonUtil.isEmpty(valueStr)) {
				Number number = Constants.DEFAULT_DECIMAL_FORMAT.get().parse(valueStr);
				if(number != null) {
					return number.doubleValue();
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static Double[] getDoubleArrayFromIndex(Double[] values, int startIndex) {
		if(values == null) {
			return null;
		}
		
		if(startIndex <= 0) {
			return values;
		}
		
		if(startIndex >= values.length) {
			return null;
		}
		
		return Arrays.copyOfRange(values, startIndex, values.length);
	}

	public static String getFormatedValue(String format, String value) {
		if(format == null || value == null) {
			return value;
		}
		
		if(!CommonUtil.isEmpty(format) && !CommonUtil.isEmpty(value) && !format.equals(Constants.DATE_PATTERN)) {
			Date date = DateUtil.parseDate(value);
			if(date != null) {
				SimpleDateFormat dt1 = new SimpleDateFormat(format);
				return DateUtil.formatDate(dt1, date);
			} 
	
			// not date, try double.
			Double doubleValue = NumberUtil.parseDouble(value);
			if(doubleValue != null) {
				DecimalFormat formatter = new DecimalFormat (format);
				return formatter.format(doubleValue);
			}
		}
		
		// could not format, return original value;
		return value;
	}
	
	public static InputStream toInputStream(byte[] bytes) {
		if(bytes == null) {
			return null;
		}
		
		return new ByteArrayInputStream(bytes);
	}
	
	public static Double parseDoubleFromStr(String valueStr) {
		Double value = null;
		try {
			value = Double.valueOf(valueStr);
		} catch(Exception e) {
		}
		
		return value;
	}
	
	public static Integer parseIntegerFromStr(String valueStr) {
		Integer value = null;
		try {
			value = Integer.valueOf(valueStr);
		} catch(Exception e) {
		}
		
		return value;
	}
	
	
	public static Long parseLongFromStr(String valueStr) {
		Long value = null;
		try {
			value = Long.valueOf(valueStr);
		} catch(Exception e) {
		}
		
		return value;
	}
	
	public static Double calculateValueByPct(Double value1, Double value2, Double pct) {
		if(pct == null) {
			return value2;
		}
		
		if(value1 == null) {
			if(value2 == null) {
				return null;
			}
			
			return value2 * (1.0 - pct);
		}
		
		if(value2 == null) {
			return value1 * pct;
		}
		
		return value1 * pct + value2 * (1.0 - pct);
	}
	
	public static <T extends Comparable<T>> int compare(T from, T to) {
		if (from == null) {
			if (to == null) {
				return 0;
			}
			
			return -1;
		} else if (to == null) {
			return 1;
		}
		
		return from.compareTo(to);
	}
}

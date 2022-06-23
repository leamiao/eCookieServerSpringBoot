package com.memd.ecookie.common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class CommonUtil {

    /**
     * Trim input.
     * 
     * @param input
     * @return trimmed string.
     */
    public static String trim(String input) {
        if (input != null) {
            input = input.trim();
        }

        return input;
    }

    public static boolean isEmpty(String input) {
        if (input == null) {
            return true;
        }

        if (input.isEmpty()) {
            return true;
        }

        return false;
    }

    public static List<String> getFirstValueListFromStringPairList(Collection<StringPair> spList) {
        if (spList == null || spList.isEmpty()) {
            return null;
        }

        List<String> results = new ArrayList<String>();
        for (StringPair sp : spList) {
            if (sp.getValue1() != null) {
                results.add(sp.getValue1());
            }
        }

        return results;
    }

    public static double sum(double[] values, int fromIndex, int toIndex) {
        if (values == null) {
            return 0.0;
        }

        int size = values.length;
        double total = 0.0;
        for (int i = fromIndex; i <= toIndex; i++) {
            if (i >= 0 && i < size) {
                total += values[i];
            }
        }

        return total;
    }

    public static double round(double input, int precision) {
        if (Double.isNaN(input)) {
            return input;
        }
        BigDecimal bd = new BigDecimal(Double.toString(input));
        if (input >= 0)
            bd = bd.setScale(precision, RoundingMode.HALF_UP);
        else
            bd = bd.setScale(precision, RoundingMode.HALF_DOWN);

        return bd.doubleValue();
    }

    public static double floor(double input, int precision) {
        if (Double.isNaN(input)) {
            return input;
        }
        BigDecimal bd = new BigDecimal(Double.toString(input));
        bd = bd.setScale(precision, RoundingMode.FLOOR);

        return bd.doubleValue();
    }

    public static double ceil(double input, int precision) {
        if (Double.isNaN(input)) {
            return input;
        }
        BigDecimal bd = new BigDecimal(Double.toString(input));
        bd = bd.setScale(precision, RoundingMode.CEILING);

        return bd.doubleValue();
    }

    public static boolean getBooleanValue(Boolean nonReturnable) {
        return nonReturnable != null ? nonReturnable.booleanValue() : false;
    }

    public static boolean isTrue(Object object) {
        if (object instanceof Boolean) {
            return ((Boolean) object).booleanValue();
        } else if (object instanceof Number) {
            return ((Number) object).intValue() != 0;
        } else if (object instanceof String) {
            return !((String) object).equals("");
        } else if (object != null) {
            return true;
        }

        return false;
    }

    public static String toStringFromObjectArray(Object[] values) {
        if (values == null) {
            return null;
        }

        return JsonHelper.getInstance().convertToJson(values);
    }

    public static String toStringFromDoubleArray(Double[] values) {
        if (values == null) {
            return null;
        }

        return JsonHelper.getInstance().convertToJson(values);
    }

    public static String removePrefix(String input, String prefix) {
        if (input == null || prefix == null) {
            return null;
        }

        if (prefix.length() >= input.length()) {
            return null;
        }

        int index = input.indexOf(prefix);
        if (index >= 0) {
            return input.substring(index + prefix.length());
        }

        return null;
    }

    public static boolean hasValue(Double[] values) {
        if (values == null) {
            return false;
        }

        if (values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                if (values[i] != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String formatDate(DateFormat format, Date date) {
        if (format == null || date == null) {
            return null;
        }

        return format.format(date);
    }

    public static Double parseDefaultDecimalFormatToDouble(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }

        try {
            String valueStr = trim(value.toString());
            if (!CommonUtil.isEmpty(valueStr)) {
                Number number = Constants.DEFAULT_DECIMAL_FORMAT.get().parse(valueStr);
                if (number != null) {
                    return number.doubleValue();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Double[] getDoubleArrayFromIndex(Double[] values, int startIndex) {
        if (values == null) {
            return null;
        }

        if (startIndex <= 0) {
            return values;
        }

        if (startIndex >= values.length) {
            return null;
        }

        return Arrays.copyOfRange(values, startIndex, values.length);
    }

    public static String getFormatedValue(String format, String value) {
        if (format == null || value == null) {
            return value;
        }

        if (!CommonUtil.isEmpty(format) && !CommonUtil.isEmpty(value) && !format.equals(Constants.DATE_PATTERN)) {
            Date date = DateUtil.parseDate(value);
            if (date != null) {
                SimpleDateFormat dt1 = new SimpleDateFormat(format);
                return DateUtil.formatDate(dt1, date);
            }

            // not date, try double.
            Double doubleValue = NumberUtil.parseDouble(value);
            if (doubleValue != null) {
                DecimalFormat formatter = new DecimalFormat(format);
                return formatter.format(doubleValue);
            }
        }

        // could not format, return original value;
        return value;
    }

    public static InputStream toInputStream(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        return new ByteArrayInputStream(bytes);
    }

    public static Double parseDoubleFromStr(String valueStr) {
        Double value = null;
        try {
            value = Double.valueOf(valueStr);
        } catch (Exception e) {
        }

        return value;
    }

    public static Integer parseIntegerFromStr(String valueStr) {
        Integer value = null;
        try {
            value = Integer.valueOf(valueStr);
        } catch (Exception e) {
        }

        return value;
    }

    public static Long parseLongFromStr(String valueStr) {
        Long value = null;
        try {
            value = Long.valueOf(valueStr);
        } catch (Exception e) {
        }

        return value;
    }

    public static Double calculateValueByPct(Double value1, Double value2, Double pct) {
        if (pct == null) {
            return value2;
        }

        if (value1 == null) {
            if (value2 == null) {
                return null;
            }

            return value2 * (1.0 - pct);
        }

        if (value2 == null) {
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

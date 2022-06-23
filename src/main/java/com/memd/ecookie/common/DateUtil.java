package com.memd.ecookie.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    public static final int DIFF_DAY = 1;
    public static final int DIFF_HOUR = 2;
    public static final int DIFF_SECOND = 3;
    public static final int MILLSECOND_PER_DAY = 24 * 60 * 60 * 1000;
    public static final int MILLSECOND_PER_HOUR = 60 * 60 * 1000;
    public static final int MILLSECOND_PER_SECOND = 1000;

    public static final TimeZone UTC = TimeZone.getTimeZone(Constants.UTC);

    public static final ThreadLocal<Calendar> UTC_CALENDAR = new ThreadLocal<Calendar>() {
        @Override
        protected Calendar initialValue() {
            return new GregorianCalendar(UTC);
        }
    };

    private static final ThreadLocal<SimpleDateFormat> UTC_DATE_FORMAT_SEARCH = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_PATTERN_SEARCH);
            sdf.setTimeZone(UTC);
            return sdf;
        }
    };

    private static final ThreadLocal<SimpleDateFormat> UTC_DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_PATTERN);
            sdf.setTimeZone(UTC);
            return sdf;
        }
    };

    private static final ThreadLocal<SimpleDateFormat> UTC_TIME_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.TIME_PATTERN);
            sdf.setTimeZone(UTC);
            return sdf;
        }
    };

    private static final ThreadLocal<SimpleDateFormat> UTC_TIMESTAMP_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_PATTERN);
            sdf.setTimeZone(UTC);
            return sdf;
        }
    };

    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }

        return UTC_DATE_FORMAT.get().format(date);
    }

    public static String formatTime(Date date) {
        if (date == null) {
            return null;
        }

        return UTC_TIME_FORMAT.get().format(date);
    }

    public static String formatTimestamp(Date date) {
        if (date == null) {
            return null;
        }

        return UTC_TIMESTAMP_FORMAT.get().format(date);
    }

    public static String formatDate(DateFormat format, Date date) {
        if (format == null || date == null) {
            return null;
        }

        return format.format(date);
    }

    public static Date parseDate(String input) {
        return parseDate(UTC_DATE_FORMAT.get(), input);
    }

    public static Date parseDateTime(String input) {
        return parseDate(UTC_TIMESTAMP_FORMAT.get(), input);
    }

    public static Date parseDate(DateFormat format, String input) {
        if (format == null || input == null) {
            return null;
        }

        try {
            return format.parse(input);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return null;
    }

    public static long getDiff(Date date1, Date date2, int type) {
        long result = 0;
        switch (type) {
        case DIFF_DAY:
            result = (date2.getTime() - date1.getTime()) / MILLSECOND_PER_DAY;
            break;
        case DIFF_HOUR:
            result = (date2.getTime() - date1.getTime()) / MILLSECOND_PER_HOUR;
            break;
        case DIFF_SECOND:
            result = (date2.getTime() - date1.getTime()) / MILLSECOND_PER_SECOND;
        default:
            break;
        }

        return result;
    }

    public static int diffInYear(Date date1, Date date2) {
        return 0;
    }

    public static Date getFirstNonNullDate(Object... objects) {
        if (objects == null) {
            return null;
        }

        for (Object object : objects) {
            Date date = getDate(object);
            if (date != null) {
                return date;
            }
        }

        return null;
    }

    public static Date getDate(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Date) {
            return (Date) value;
        }

        if (value instanceof Number) {
            return new Date(((Number) value).longValue());
        }

        return parseDate(value.toString());
    }

    public static Date getDateBySearchFormat(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Date) {
            return (Date) value;
        }

        Date date = parseDate(UTC_DATE_FORMAT_SEARCH.get(), value.toString());
        if (date == null) {
            date = parseDate(value.toString());
        }

        return date;
    }

    public static Date getDateByDateTimeFormat(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Date) {
            return (Date) value;
        }

        return parseDate(UTC_TIMESTAMP_FORMAT.get(), value.toString());
    }

    public static Date[] getDateRange(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Date[] dates = new Date[2];
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        dates[0] = calendar.getTime();

        calendar.add(Calendar.DATE, 1);
        dates[1] = calendar.getTime();

        return dates;
    }

    public static Date truncate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date truncateToNextDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);

        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
}

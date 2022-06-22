package com.memd.ecookie.common;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class DateHelper {
	//private static final Logger LOGGER = LoggerFactory.getLogger(DateHelper.class);
	private static DateHelper instance_ = new DateHelper();
	
	public static DateHelper getInstance() {
		return instance_;
	}

	private GregorianCalendar gregorianCalendar = new GregorianCalendar();
	//private int[] daysInMonth =     { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	//private int[] daysInMonthLeapYr = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private int[] daysInQuarter =  { 90, 91, 92, 92 };
	private int[] daysInQuarterLeapYr =  { 91, 91, 92, 92 };
	private int[] daysInSemiYear =  { 181, 184 };
	private int[] daysInSemiYearLeapYr =  { 182, 184 };
	
	//private Map<Integer, Integer> yearDaysMap = new HashMap<Integer, Integer>();
	
	private DateHelper () {
	}

	/* public Integer getDaysInYear(Integer yearInt) {
		if(yearInt == null) {
			return null;
		}
		Integer daysInYear = this.yearDaysMap.get(yearInt);
		if(daysInYear == null) {
			synchronized(this) {
				daysInYear = this.yearDaysMap.get(yearInt);
				if(daysInYear == null) {
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.YEAR, yearInt);
					daysInYear = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
					this.yearDaysMap.put(yearInt, daysInYear);
				}
			}
		}
		
		return daysInYear;
	} */
	
	public Integer getDaysInYear(Date date) {
		if(date == null) {
			return null;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
	    
	    if (this.isLeapYear(calendar.get(Calendar.YEAR))) {
	    	return 366;
	    }
	    
	    return 365;

		//return this.getDaysInYear(calendar.get(Calendar.YEAR));
		//return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
	}	
	
	public Integer getDaysInYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
	}

	public Integer getDaysInMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public Integer getDaysInMonth(Date date) {
		if(date == null) {
			return null;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
		
	public Integer getDaysInQuarter() {
	    return this.getDaysInQuarter(Calendar.getInstance());
	}

	public Integer getDaysInQuarter(Date date) {
		if(date == null) {
			return null;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return this.getDaysInQuarter(calendar);
	}

	public Integer getDaysInQuarter(Calendar calendar) {
		if(calendar == null) {
			return null;
		}
			    
	    int currMth = calendar.get(Calendar.MONTH) + 1;
	    int currQtr = currMth / 3;
	    
	    if (this.isLeapYear(calendar.get(Calendar.YEAR))) {
        	return daysInQuarterLeapYr[currQtr];
        } else {
        	return daysInQuarter[currQtr];
        }
	}	

	public Integer getDaysInSemiYear() {
	    return this.getDaysInSemiYear(Calendar.getInstance());
	}

	public Integer getDaysInSemiYear(Date date) {
		if(date == null) {
			return null;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return this.getDaysInSemiYear(calendar);
	}

	public Integer getDaysInSemiYear(Calendar calendar) {
		if(calendar == null) {
			return null;
		}
		
	    
	    int currMth = calendar.get(Calendar.MONTH);
	    int currSemiYear = currMth / 6;
	    
	    if (this.isLeapYear(calendar.get(Calendar.YEAR))) {
        	return daysInSemiYearLeapYr[currSemiYear];
        } else {
        	return daysInSemiYear[currSemiYear];
        }
	}
	
	public boolean isLeapYear(int year) {
		return gregorianCalendar.isLeapYear(year);
	}
	
	public Date moveToStartOfTheYear(Date date) {
		if(date == null) {
			return null;
		}
		
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date);
		calendar1.set(Calendar.MONTH, Calendar.JANUARY);
		calendar1.set(Calendar.DAY_OF_MONTH, 5);
		
		return calendar1.getTime();
	}
}



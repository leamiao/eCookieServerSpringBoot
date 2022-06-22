package com.memd.ecookie.common;

import java.util.TimeZone;

public class TimeZoneUtil {
	public static void setDefaultTimeZone() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-06:00"));
	}
}

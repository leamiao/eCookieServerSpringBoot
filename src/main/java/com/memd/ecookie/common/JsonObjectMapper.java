package com.memd.ecookie.common;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonObjectMapper extends ObjectMapper {
    private static final long serialVersionUID = 1L;

    public JsonObjectMapper() {
        super(null, null, null);

        TimeZoneUtil.setDefaultTimeZone();

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_PATTERN);
        this.setDateFormat(sdf);
    }

    public JsonObjectMapper(JsonFactory jf) {
        super(jf, null, null);

        TimeZoneUtil.setDefaultTimeZone();

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_PATTERN);
        this.setDateFormat(sdf);
    }
}

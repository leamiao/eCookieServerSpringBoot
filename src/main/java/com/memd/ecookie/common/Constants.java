package com.memd.ecookie.common;

import java.text.DecimalFormat;

public class Constants {
    public static final String UTC = "UTC";

    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_PATTERN_SEARCH = "MM/dd/yyyy";

    public static final String PRODUCT_ENTITY = "Product";
    public static final String DUPLICATE_ENTITY_NAME = "DUPLICATE_ENTITY_NAME";
    public static final String ENTITY_NOT_EXIST_FOR_PATCH = "ENTITY_NOT_EXIST_FOR_PATCH";
    public static final String PRODUCT_PRICE_SHOULD_NOT_BE_NEGATIVE = "PRODUCT_PRICE_SHOULD_NOT_BE_NEGATIVE";
    public static final String DEFAULT_IMG_URL = "1.gif";

    public static final String NULL = "null";

    public static final String START = "start";

    public static final String LIMIT = "limit";

    public static final String SEARCH_TERM = "searchTerm";

    public static final String SORT = "sort";

    public static final String FILTER = "filter";

    public static final String LIKE = "like";

    public static final String EQ = "eq";

    public static final String LT = "lt";

    public static final String GT = "gt";

    public static final ThreadLocal<DecimalFormat> DEFAULT_DECIMAL_FORMAT = ThreadLocal
            .withInitial(() -> new DecimalFormat("#,###.00"));

    public static final String COMMA = ",";

    public static final String IN = "in";

    public static final String CATEGORY_NOT_EXIST = "CATEGORY_NOT_EXIST";
}

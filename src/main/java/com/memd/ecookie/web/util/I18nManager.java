package com.memd.ecookie.web.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContextUtils;

@Component
public class I18nManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(I18nManager.class);

    @Autowired
    private MessageSource messageSource;

    public Locale getLocale(HttpServletRequest request) {
        Locale locale = RequestContextUtils.getLocale(request);

        if (locale == null) {
            locale = request.getLocale();
        }

        return locale;
    }

    public String getString(HttpServletRequest request, String key) {
        return getString(this.getLocale(request), key, key);
    }

    public String getString(HttpServletRequest request, String key, String defaultValue) {
        return getString(this.getLocale(request), key, defaultValue, null);
    }

    public String getString(HttpServletRequest request, String key, Object[] params) {
        return getString(this.getLocale(request), key, key, params);
    }

    public String getString(HttpServletRequest request, String key, String defaultValue, Object[] params) {
        return getString(this.getLocale(request), key, defaultValue, params);
    }

    public String getString(Locale locale, String key) {
        return getString(locale, key, key);
    }

    public String getString(Locale locale, String key, String defaultValue) {
        return getString(locale, key, defaultValue, null);
    }

    public String getString(Locale locale, String key, Object[] params) {
        return getString(locale, key, key, params);
    }

    public String getString(Locale locale, String key, String defaultValue, Object[] params) {
        if (messageSource != null) {
            try {
                return messageSource.getMessage(key, params, locale);
            } catch (Exception e) {
                LOGGER.warn(e.getMessage(), e);
            }
        }

        return defaultValue;
    }
}

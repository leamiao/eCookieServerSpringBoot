package com.memd.ecookie.web.controller;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.memd.ecookie.common.JsonHelper;
import com.memd.ecookie.web.util.I18nManager;

public class BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	protected I18nManager i18nManager;
	
	protected void printRequestParams(HttpServletRequest request) {
		Enumeration<String> params = request.getParameterNames();
		while(params.hasMoreElements()) {
			String paramName = params.nextElement();
	  
			String value = request.getParameter(paramName);
	  
			logger.info("======== " + paramName + " --> " + value);
		}
	}
	
	protected String getJsonString(HttpServletRequest request, Object model) {
		String jsonCallback = request.getParameter("callback");
		String jsonStr = JsonHelper.getInstance().convertToJson(model);
		if(jsonCallback != null) return jsonCallback + "(" + jsonStr + ")";
		
		return  jsonStr;
	}
}

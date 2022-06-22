package com.memd.ecookie.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
public class BeanLoader implements ApplicationContextAware, InitializingBean{

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext1) throws BeansException {
		applicationContext = applicationContext1;
	}	
	
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
}

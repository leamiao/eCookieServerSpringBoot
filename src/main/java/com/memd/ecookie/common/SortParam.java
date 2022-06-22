package com.memd.ecookie.common;

import java.io.Serializable;

public class SortParam implements Serializable {
	private static final long serialVersionUID = 2114041940200422500L;

	private String property;
	
	private String direction;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	
}

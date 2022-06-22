package com.memd.ecookie.common;

import java.util.Objects;

public class StringPair {
	private String value1;
	private String value2;
	
	public StringPair() {
	}
	
	public StringPair(String value1, String value2) {
		this.setValue1(value1);
		this.setValue2(value2);
	}


	@Override  
	public boolean equals(Object obj) {  
		if (obj == null) return false;  		
		if(this == obj) return true;
		if (getClass() != obj.getClass())  return false;   
	      
		final StringPair other = (StringPair) obj;		
		return Objects.equals(getValue1(), other.getValue1())
			&& Objects.equals(this.getValue2(), other.getValue2());
	}  	
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getValue1(), this.getValue2());
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}
	
	@Override
	public String toString() {
		return value1 + ":" + value2;
	}
}

package com.memd.ecookie.common;

import java.io.Serializable;

public class SearchParam implements Serializable {
    private static final long serialVersionUID = 3599983867747591292L;

    private String operator;
    private Object value;
    private String property;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}

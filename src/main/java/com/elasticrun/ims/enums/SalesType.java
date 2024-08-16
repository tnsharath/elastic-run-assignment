package com.elasticrun.ims.enums;

public enum SalesType {

    SALES_ORDER("SALES_ORDER"), SALES_RETURN("SALES_RETURN");
    String value;
    SalesType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

package com.vendorvalley_api.vendorvalley_api.enums;

public enum SignUpVendorSQLQueryEnum {
    COMPANY_CITY(1),
    COMPANY_COUNTRY(2),
    COMPANY_EMAIL(3),
    COMPANY_MOBILE(4),
    COMPANY_NAME(5),
    COMPANY_PROVINCE(6),
    COMPANY_REGISTRATION_ID(7),
    COMPANY_STREET(8);

    public int queryIndex;

    SignUpVendorSQLQueryEnum(int queryIndex) {
        this.queryIndex = queryIndex;
    }
}

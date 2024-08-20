package com.motta.insurance_association_service.util;

public class AssociationConstants {
    public static final String LOG_MESSAGE_ASSOCIATION_NOT_FOUND = "Association id not found. Please enter different id: {}";
    public static final String LOG_MESSAGE_ASSOCIATION_PERSISTED = "Association id persisted: {}";
    public static final String LOG_MESSAGE_ASSOCIATION_UPDATED = "Association updated: {}";
    public static final String LOG_MESSAGE_ASSOCIATION_DELETE_FAILED = "Association delete failed: {}";
    public static final String LOG_MESSAGE_ASSOCIATION_UPDATE_FAILED = "Updating scheme id = {} has failed.";
    public static final String LOG_MESSAGE_FETCHING_ASSOCIATIONS_FAILED = "Failed fetching associations for scheme Id.";
    public static final String LOG_MESSAGE_FETCHED_ASSOCIATION = "Association id = {} has been fetched.";
    public static final String LOG_MESSAGE_FETCHED_ALL_ASSOCIATIONS = "All Associations fetched.";


    public static final String EXCEPTION_MESSAGE_ASSOCIATION_NOT_FOUND = "Association id not found!";
    public static final String EXCEPTION_MESSAGE_INVALID_FROM_DATE = "From Date should be less than To Date";
    public static final String EXCEPTION_MESSAGE_ASSOCIATION_ID_IS_MANDATORY = "Association Id is mandatory";
    public static final String EXCEPTION_MESSAGE_EMPLOYEE_ID_IS_MANDATORY = "Employee Id is mandatory";
    public static final String EXCEPTION_MESSAGE_ASSOCIATION_NAME_IS_MANDATORY = "Association Name is mandatory";
    public static final String EXCEPTION_MESSAGE_ASSOCIATION_ID_LESS_THAN_INITIAL_VALUE = "Association Id must not be less than the initial value of: ";
    public static final String EXCEPTION_MESSAGE_TO_DATE_IS_MANDATORY = "Valid To Date is mandatory";
    public static final String EXCEPTION_MESSAGE_ASSOCIATION_TYPE_IS_MANDATORY = "Association Type is mandatory";
    public static final String EXCEPTION_MESSAGE_ASSOCIATION_AMOUNT_IS_MANDATORY = "Association Amount is mandatory";
    public static final String EXCEPTION_MESSAGE_SHARE_IS_MANDATORY = "Share  is mandatory";
    public static final String EXCEPTION_MESSAGE_COMMISSION_IS_MANDATORY = "Commission  is mandatory";
    public static final String EXCEPTION_MESSAGE_ASSOCIATIONS_NOT_FOUND = "Associations not found";

    public static final String URL_GET_SCHEME_BY_SCHEME_ID = "http://localhost:8800/schemes/";


}

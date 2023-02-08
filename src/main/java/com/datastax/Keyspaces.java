package com.datastax;

public enum Keyspaces {

    ACCOUNT_KS("account"),
    ACCOUNT_CONTACT_KS("account_contact"),
    ASSOC_ACCOUNT_KS("assoc_account"),
    APPLY_DISCOUNT_KS("apply_discount"),
    LINE_OF_BUSINESS_KS("line_of_business"),
    COMMENT_KS("comment"),
    GROUP_KS("group"),
    AUDIT_HISTORY_KS("audit_history"),
    TIME_EVENT_KS("time_event"),
    CENTRALIZED_VIEW_KS("centralized_view"),
    PAYMENT_INFO_KS("payment_info"),
    DYNAMIC_PROFILE_KS("dynamic_profile"),
    SEARCH_KS("search"),
    ARCHIVE_KS("archive"),
	SEQUENCE_KS("sequence"),
    CAM_GROUP_L1_KS("cam_group_l1_ks");

    private String keyspaceName;
    Keyspaces(String ksName) { this.keyspaceName = ksName;}
    public String keyspaceName(String level, String app) { 
    	return app + "_" + keyspaceName + "_" + level + "_ks";}
}

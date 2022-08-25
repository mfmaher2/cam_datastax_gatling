package com.datastax;


import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString(includeFieldNames=true)
@Entity
@CqlName("cust_acct_v1")
public class CustomerAccount {

    @PartitionKey @CqlName("account_number") private String accountNumber;
    @ClusteringColumn(0)  private String opco;

    @CqlName("last_update_tmstp") private Instant lastUpdated;
//    @CqlName("last_update_timestamp") private Instant lastUpdated;
//
    // enterpriseProfile
    @CqlName("profile__customer_type") private String customerType;
    @CqlName("profile__account_type")  private String accountType;
    @CqlName("profile__account_service_level") private String accountServiceLevel;
    @CqlName("profile__account_status__status_code") private String statusCode;
    @CqlName("profile__account_status__status_date") private LocalDate statusDate;
    @CqlName("profile__account_status__reason_code") private String reasonCode;

    @CqlName("profile__fdx_ok_to_call_flag") private Boolean fdxOkToCallFlag;
    @CqlName("profile__enterprise_source") private String enterpriseSource;
    @CqlName("profile__nasa_id") private String nasaId;
    @CqlName("profile__nasa_key") private String nasaKey;
    @CqlName("profile__creation_date") private LocalDate creationDate;
    @CqlName("profile__origin_source") private String originSource;
    @CqlName("profile__account_linkage_flag") private Boolean accountLinkageFlag;
    @CqlName("profile__welcome_kit__welcome_kit_flag") private Boolean welcomeKitFlag;
    @CqlName("profile__welcome_kit__welcome_kit_promo_code") private String welcomeKitPromoCode;
    
    @CqlName("profile_service_restrictions") private Set<String> serviceRestrictions;
    @CqlName("profile_view_restrictions") private Set<String> viewRestrictions;
    @CqlName("profile_tax_exempt") private Boolean enterpriseTaxExempt;
    
    // enterpriseEligibility
    @CqlName("eligibility__ground") private Boolean ground;
    @CqlName("eligibility__express") private Boolean express;
    @CqlName("eligibility__freight") private Boolean freight;
    @CqlName("eligibility__office") private Boolean office;
   
    // accountProfile && accountCreationProfile

    @CqlName("profile__customer_requester_name")  private String customerRequesterName;
    @CqlName("profile__employee_requester__opco")  private String employeeRequesterOpco ;
    @CqlName("profile__employee_requester__number")  private String employeeRequesterNumber;
    @CqlName("profile__source_group")  private String sourceGroup;
    @CqlName("profile__source_dept")  private String sourceDept;
    @CqlName("profile__source_system")  private String sourceSystem;
    @CqlName("profile__employee_creator_opco")  private String employeeCreatorOpco;
    @CqlName("profile__employee_creator_number")  private String employeeCreatorNumber ;
    @CqlName("profile__account_sub_type")  private String accountSubType;
    @CqlName("profile__customer_account_status")  private String customerAccountStatus;
    @CqlName("profile__duplicate_account_flag")  private Boolean duplicateAccountFlag;
    @CqlName("profile__archive_date") private LocalDate archiveDate;
    @CqlName("profile__archive_reason_code")  private String archiveReasonCode ;
    @CqlName("profile__archive_options")  private String archiveOptions ;
    @CqlName("profile__cargo_ind")  private String cargoInd;
    @CqlName("profile__pref_cust_flag")  private Boolean prefCustFlag ;
    @CqlName("profile__sales_rep__opco")  private String salesRepOpco;
    @CqlName("profile__sales_rep__number")  private String  salesRepNumber;
    @CqlName("profile__service_level")  private String serviceLevel;
    @CqlName("profile__scac_code")  private String scacCode ;
    @CqlName("profile_special_instructions")  private String specialInstructions ;
    
    // cargoAccountReceivables
    // express_account_receivables
    @CqlName("account_receivables__coll_zone_desc")  private String collZoneDesc;
    @CqlName("account_receivables__gsp_write_off")  private String gspWriteOff;
    @CqlName("account_receivables__online_eligibility")  private Boolean onlineEligibility;
    @CqlName("account_receivables__partial_pay_letter_flag")  private Boolean partialPayLetterFlag;
    @CqlName("account_receivables__payment_type")  private String paymentType;
    @CqlName("account_receivables__payment_method_code")  private String paymentMethodCode;
    @CqlName("account_receivables__payor_type")  private String payorType;
    @CqlName("account_receivables__arrow_customer_flag_cd")  private String arrowCustomerFlag;
    @CqlName("account_receivables__international___ar_preference") private Integer internationalARPreference;
    @CqlName("account_receivables__international___ar_date") private LocalDate internationalARDate;
    @CqlName("account_receivables__no_refund_flag")  private Boolean noRefundFlag;
    @CqlName("account_receivables__debut_company_code")  private String debutCompanyCode;
    @CqlName("account_receivables__credit_note_flag")  private Boolean creditNoteFlag;
    @CqlName("account_receivables__credit_note_exception_flag")  private Boolean creditNoteExceptionFlag;
    @CqlName("account_receivables__fifo_eligibility_code")  private String fifoEligibilityCode;

    // express_aggregations
    @CqlName("aggregations__ed_aggr_code")  private String edAggrCode;
    @CqlName("aggregations__geo_acct_number")  private String geoAcctNumber;
    @CqlName("aggregations__global_account_number")  private String globalAccountNumber;
    @CqlName("aggregations__global_subgroup")  private String globalSubgroup;
    @CqlName("aggregations__ss_account_number")  private String ssAccountNumber;
    @CqlName("aggregations__bill_to_number")  private String billToNumber ;
    @CqlName("aggregations__edi_number")  private String ediNumber;
    @CqlName("aggregations__copy_master_address")  private Boolean copyMasterAddress;

    // express_claims_preference
    @CqlName("claims_preference")  private String claimsPreference;

    // express_credit_detail
    // freightCreditDetail
    // officeCreditDetail
    // ukDomesticCreditCardDetail
    @CqlName("credit_detail__credit_status")  private String creditStatus;
    @CqlName("credit_detail__credit_status_reason_code")  private String creditStatusReasonCode;
    @CqlName("credit_detail__denied_flag")  private Boolean deniedFlag;
    @CqlName("credit_detail__bankruptcy_date") private LocalDate bankruptcyDate;
    @CqlName("credit_detail__cash_only_flag")  private Boolean cashOnlyFlag;
    @CqlName("credit_detail__cash_only_date") private LocalDate cashOnlyDate;
    @CqlName("credit_detail__cash_only_reason")  private String cashOnlyReason;
    @CqlName("credit_detail__credit_alert_detail")  private String creditAlertDetail;
    @CqlName("credit_detail__credit_alert_account_number")  private String creditAlertAccountNumber;
    @CqlName("credit_detail__credit_alert_parent_type")  private String creditAlertParentType;
    @CqlName("credit_detail__credit_limit")  private String creditLimit;
    @CqlName("credit_detail__credit_limit_tolerance_pct") private Integer creditLimitTolerancePct;
    @CqlName("credit_detail__credit_override_date") private LocalDate creditOverrideDate;
    @CqlName("credit_detail__credit_rating")  private String creditRating;
    @CqlName("credit_detail__receivership_account_number")  private String receivershipAccountNumber;
    @CqlName("credit_detail__receivership_date") private LocalDate receivershipDate;
    @CqlName("credit_detail__rev_auth_id")  private String revAuthId;
    
    // express_direct_debit
    /*
    @CqlName("direct_debit_detail__person__first_name") private String directDebitDetailPersonFirstname;
    @CqlName("direct_debit_detail__person__last_name") private String directDebitDetailPersonLastname;
    @CqlName("direct_debit_detail__bank_account_holder_name") private String directDebitDetailBankAccountHolderName; 
    @CqlName("direct_debit_detail__bank_name") private String directDebitDetailBankName;
    @CqlName("direct_debit_detail__address__street_line") private String directDebitDetailAddressStreetLine;
    @CqlName("direct_debit_detail__address__additional_line1") private String directDebitDetailAddressAdditionalLine1;
    @CqlName("direct_debit_detail__address__additional_line2") private String directDebitDetailAddressAdditionalLine2;
    @CqlName("direct_debit_detail__address__secondary__unit1") private String directDebitDetailAddressSecondaryUnit1;
    @CqlName("direct_debit_detail__address__secondary__value1") private String directDebitDetailAddressSecondaryValue1;
    @CqlName("direct_debit_detail__address__secondary__unit2") private String directDebitDetailAddressSecondaryUnit2;
    @CqlName("direct_debit_detail__address__secondary__value2") private String directDebitDetailAddressSecondaryValue2;
    @CqlName("direct_debit_detail__address__secondary__unit3") private String directDebitDetailAddressSecondaryUnit3;
    @CqlName("direct_debit_detail__address__secondary__value3") private String directDebitDetailAddressSecondaryValue3;
    @CqlName("direct_debit_detail__address__secondary__unit4") private String directDebitDetailAddressSecondaryUnit4;
    @CqlName("direct_debit_detail__address__secondary__value4") private String directDebitDetailAddressSecondaryValue4;
    @CqlName("direct_debit_detail__address__geo_political_subdivision1") private String directDebitDetailAddressGeoPoliticalSubdivision1;
    @CqlName("direct_debit_detail__address__geo_political_subdivision2") private String directDebitDetailAddressGeoPoliticalSubdivision2;
    @CqlName("direct_debit_detail__address__geo_political_subdivision3") private String directDebitDetailAddressGeoPoliticalSubdivision3;
    @CqlName("direct_debit_detail__address__postal_code") private String directDebitDetailAddressPostalCode;
    @CqlName("direct_debit_detail__address__country_code") private String directDebitDetailAddressCountryCode;
    @CqlName("direct_debit_detail__iban__swift_code") private String directDebitDetailIbanSwiftCode;
    @CqlName("direct_debit_detail__iban_iban") private String directDebitDetailIbanIban;
    @CqlName("direct_debit_detail__noiban__bank_code") private String directDebitDetailNoibanBankCode;
    @CqlName("direct_debit_detail__noiban__branch_code") private String directDebitDetailNoibanBranchCode;
    @CqlName("direct_debit_detail__account_number") private String directDebitDetailAccountNumber;
    @CqlName("direct_debit_detail__sort_code") private String directDebitDetailSortCode;
    @CqlName("direct_debit_detail__mandate_id") private String directDebitDetailMandateId;
    @CqlName("direct_debit_detail__mandate_start_date") private LocalDate directDebitDetailMandateStartDate;
    @CqlName("direct_debit_detail__legal_entity") private String directDebitDetailLegalEntity;
    */
    
    // express_edi
    @CqlName("edi__cust_inv_rept_flag")  private Boolean custInvReptFlag;
    @CqlName("edi__dom_data_frmt")  private String domDataFrmt;
    @CqlName("edi__dom_frmt_ver")  private String domFrmtVer;
    @CqlName("edi__dom_inv_print_until_date") private LocalDate domInvPrintUntilDate;
    @CqlName("edi__intl_data_frmt")  private String intlDataFrmt;
    @CqlName("edi__intl_inv_frmt_ver")  private String intlInvFrmtVer;
    @CqlName("edi__intl_inv_print_until_date") private LocalDate intlInvPrintUntilDate;
    @CqlName("edi__mm_bill_3rd_party")  private String mmBill3rdParty; /// WHAT TO DO WITH DIGITS ??>?????
    @CqlName("edi__mm_bill_recip")  private String mmBillRecip;
    @CqlName("edi__mm_bill_ship")  private String mmBillShip;
    @CqlName("edi__mm_bill_pwr_ship")  private String mmBillPwrShip;
    @CqlName("edi__past_due_medium")  private String pastDueMedium;
    @CqlName("edi__past_due_send_to")  private String pastDueSendTo;
    @CqlName("remit_frmt_vers")  private String remitFrmtVers;
    @CqlName("sep_exp_grnd_file")  private Boolean sepExpGrndFile;

    // express_invoice_preference
    // freightInvoicePreference
    // officeInoivcePreference
    // techConnectInvoicePreference
    // ukDomesticInvoicePreference
    @CqlName("invoice_preference__additional_invoice_copy_flag_cd")  private String additionalInvoiceCopyFlag;
    @CqlName("invoice_preference__audit_firm_exp_year_month")  private String auditFirmExpYearMonth;
    @CqlName("invoice_preference__audit_firm_number")  private String auditFirmNumber;
    @CqlName("invoice_preference__billing_closing_day")  private String billingClosingDay;
    @CqlName("invoice_preference__billing_cycle")  private String billingCycle;
    @CqlName("invoice_preference__billing_medium")  private String billingMedium;
    @CqlName("invoice_preference__billing_payment_day") private Integer billingPaymentDay;
    @CqlName("invoice_preference__billing_payment_month") private Integer billingPaymentMonth;
    @CqlName("invoice_preference__billing_restriction_indicator")  private String billingRestrictionIndicator;
    @CqlName("invoice_preference__billing_type") private Integer billingType;
    @CqlName("invoice_preference__combine_option")  private String combineOption;
    @CqlName("invoice_preference__consolidated_invoicing_flag_cd")  private String consolidatedInvoicingFlag;
    @CqlName("invoice_preference__consolidated_refund_flag")  private Boolean consolidatedRefundFlag;
    @CqlName("invoice_preference__cost_center_number")  private String costCenterNumber;
    @CqlName("invoice_preference__currency_code")  private String currencyCode;
    @CqlName("invoice_preference__customer_reference_information")  private String customerReferenceInformation;
    @CqlName("invoice_preference__daysto_credit") private Integer daystoCredit;
    @CqlName("invoice_preference__daysto_pay") private Integer daystoPay;
    @CqlName("invoice_preference__document_exception_indicator") private Integer documentExceptionIndicator;
    @CqlName("invoice_preference__duty_tax_daysto_pay") private Integer dutyTaxDaystoPay;
    @CqlName("invoice_preference__duty_tax_billing_cycle")  private String dutyTaxBillingCycle;
    @CqlName("invoice_preference__electronic_bill_payment_plan_flag_cd")  private String electronicBillPaymentPlanFlag;
    @CqlName("invoice_preference__electronic_data_record_proof_of_delivery")  private Boolean electronicDataRecordProofOfDelivery;
    @CqlName("invoice_preference__fax_flag")  private Boolean faxFlag;
    @CqlName("invoice_preference__fec_discount_card_flag_cd")  private String fecDiscountCardFlag;
    @CqlName("invoice_preference__ground_auto___pod")  private Boolean groundAutoPOD;
    @CqlName("invoice_preference__ground_duty_tax_billing_cycle")  private String groundDutyTaxBillingCycle;
    @CqlName("invoice_preference__ground_print_weight_indicator")  private String groundPrintWeightIndicator;
    @CqlName("invoice_preference__international_billing_cycle")  private String internationalBillingCycle;
    @CqlName("invoice_preference__international_billing_medium")  private String internationalBillingMedium;
    @CqlName("invoice_preference__international_invoice_bypass")  private String internationalInvoiceBypass;
    @CqlName("invoice_preference__international_invoice_program_override_flag")  private Boolean internationalInvoiceProgramOverrideFlag;
    @CqlName("invoice_preference__international_parent_child_flag")  private Boolean internationalParentChildFlag;
    @CqlName("invoice_preference__international_duty_tax_invoice_bypass")  private String internationalDutyTaxInvoiceBypass;
    @CqlName("invoice_preference__invoice__detail_level") private Integer invoiceDetailLevel;
    @CqlName("invoice_preference__invoice__level_discount_eff_date") private LocalDate invoiceLevelDiscountEffDate;
    @CqlName("invoice_preference__invoice__level_discount_exp_date") private LocalDate invoiceLevelDiscountExpDate;
    @CqlName("invoice_preference__invoice__level_discount_flag_cd")  private String invoiceLevelDiscountFlag;
    @CqlName("invoice_preference__invoice__minimum_override_flag")  private Boolean invoiceMinimumOverrideFlag;
    @CqlName("invoice_preference__invoice__option_flag_cd")  private String invoiceOptionFlag;
    @CqlName("invoice_preference__invoice__page_layout_indicator") private Integer invoicePageLayoutIndicator;
    @CqlName("invoice_preference__invoice__transaction_breakup_type") private Integer invoiceTransactionBreakupType;
    @CqlName("invoice_preference__invoice__wait_days") private Integer invoiceWaitDays;
    @CqlName("invoice_preference__manage_my_account_at_fed_ex_flag_cd")  private String manageMyAccountAtFedExFlag;
    @CqlName("invoice_preference__master_account_invoice_summary_flag_cd")  private String masterAccountInvoiceSummaryFlag;
    @CqlName("invoice_preference__monthly_billing_indicator")  private String monthlyBillingIndicator;
    @CqlName("invoice_preference__past_due_detail_level") private Integer pastDueDetailLevel;
    @CqlName("invoice_preference__past_due_flag_cd")  private String pastDueFlag;
    @CqlName("invoice_preference__pod_wait_days") private Integer podWaitDays;
    @CqlName("invoice_preference__primary_sort_option") private Integer primarySortOption;
    @CqlName("invoice_preference__print_summary_page_flag")  private Boolean printSummaryPageFlag;
    @CqlName("invoice_preference__print_weight_indicator")  private String printWeightIndicator;
    @CqlName("invoice_preference__reference_append") private Integer referenceAppend;
    @CqlName("invoice_preference__return_envelope_indicator")  private String returnEnvelopeIndicator;
    @CqlName("invoice_preference__single_invoice_option")  private String singleInvoiceOption;
    @CqlName("invoice_preference__sort_field_length") private Integer sortFieldLength;
    @CqlName("invoice_preference__split_bill_duty_tax")  private Boolean splitBillDutyTax;
    @CqlName("invoice_preference__statement_of_account__billing_cycle")  private String statementOfAccountBillingCycle;
    @CqlName("invoice_preference__statement_of_account__layout_indicator") private Integer statementOfAccountLayoutIndicator;
    @CqlName("invoice_preference__statement_of_account__receipt_flag_cd")  private String statementOfAccountReceiptFlag;
    @CqlName("invoice_preference__statement_type")  private String statementType;
    @CqlName("invoice_preference__statement_type_date") private LocalDate statementTypeDate;
    @CqlName("invoice_preference__viewed_statement_type")  private String viewedStatementType;
    @CqlName("invoice_preference__direct_link_flag")  private Boolean directLinkFlag;
    @CqlName("invoice_preference__no___pod_flag_cd")  private String noPODFlag;
    @CqlName("invoice_preference__settlement_level_indicator")  private String settlementLevelIndicator;
    @CqlName("invoice_preference__direct_debit_indicator")  private String directDebitIndicator;
    @CqlName("invoice_preference__fbo_eft_flag")  private Boolean fboEftFlag;
    @CqlName("invoice_preference__balance_forward_code")  private String balanceForwardCode;
    @CqlName("invoice_preference__late_fee_enterprise_waiver")  private String lateFeeEnterpriseWaiver;

    // expressMMA
    @CqlName("mma_stats__last_cancel_code")  private String lastCancelCode;
    @CqlName("mma_stats__last_cancel_date") private LocalDate lastCancelDate;
    @CqlName("mma_stats__last_deactivation_date") private LocalDate lastDeactivationDate;
    @CqlName("mma_stats__last_registration_date") private LocalDate lastRegistrationDate;
    @CqlName("mma_stats__last_reject_code")  private String lastRejectCode;
    @CqlName("mma_stats__last_reject_date") private LocalDate lastRejectDate;
    @CqlName("mma_stats__last_update_date_time") private Instant lastUpdateDateTime;
    @CqlName("mma_stats__last_update_user")  private String lastUpdateUser;
    @CqlName("swipe__cc_eligibility_flag")  private Boolean ccEligibilityFlag;
    @CqlName("swipe__decline_count") private Integer declineCount;
    @CqlName("swipe__swipe_lockout_date_time") private Instant swipeLockoutDateTime;
    
    // Duty Tax Info
    @CqlName("duty_tax_info")  private Map<String,String> dutyTaxInfoMap;
    
    //supplyChainTaxInfo
    //freightTaxInfo
    //techConnectTaxInfo
    
    // Need to test the map part
    @CqlName("tax_info__tax_exempt_code")  private Map<String,String> tax_info__tax_exempt_code;
    @CqlName("tax_info__codice_fiscale")  private String taxInfoCodiceFiscale;
    @CqlName("tax_info__mdb_eff_date") private LocalDate taxInfoMdbEffDate;
    @CqlName("tax_info__mdb_exp_date") private LocalDate taxInfoMdbExpDate;
    @CqlName("tax_info__tax_exempt_number") private Integer taxInfoTaxExemptNumber;
    @CqlName("tax_info__vat__type")  private String taxInfoVatType;
    @CqlName("tax_info__vat__number")  private String taxInfoVatNumber;
    @CqlName("tax_info__vat__exemption_code")  private String taxInfoVatExemptionCode;
    //@CqlName("tax_info__vat__exception_ref")  private String taxInfoVatExceptionRef;
    @CqlName("tax_info__vat__eff_date") private LocalDate taxInfoVatEffDate;
    @CqlName("tax_info__vat__exp_date") private LocalDate taxInfoVatExpDate;
    @CqlName("tax_info__vat__response_code") private Integer taxInfoVatResponseCode;
    @CqlName("tax_info__vat__category_code") private Integer taxInfoVatCategoryCode;
    @CqlName("tax_info__vat__threshold_amount") private Float taxInfoVatThresholdAmount;
    
    
    // ***** OPERATIONS STREAM  *****
    // smart_post_operations_profile
    @CqlName("profile__distribution_id") private String distributionId;
    @CqlName("profile__mailer_id") private String mailerId;
    @CqlName("profile__pickup_carrier")  private String pickupCarrier;
    @CqlName("profile__return_eligibility_flag")  private Boolean returnEligibilityFlag;
    @CqlName("profile__return_svc_flag")  private String returnSvcFlag;
    @CqlName("profile__hub_id") private Set<String> hubId;
    @CqlName("profile__usps_bound_printed_matter_flag") private Integer uspsBoundPrintedMatterFlag;
    @CqlName("profile__usps_media_mail_flag") private Integer uspsMediaMailFlag;
    @CqlName("profile__usps_parcel_select_flag") private Integer uspsParcelSelectFlag;
    @CqlName("profile__usps_standard_mail_flag") private Integer uspsStandardMailFlag;
    @CqlName("profile__smartpost_enabled_flag")  private Boolean smartpostEnabledFlag;
    @CqlName("profile__delivery_confirmation")  private Boolean deliveryConfirmation;
    @CqlName("profile__zone_indicator")  private String zoneIndicator;

    // express_operations_profile
    @CqlName("profile__multiplier_ref_exp")  private String multiplierRefExp;
    @CqlName("profile__mulitiplier_ref_grnd")  private String mulitiplierRefGrnd;
    @CqlName("profile__agent_flag")  private String agentFlag;
    @CqlName("profile__alcohol_flag")  private Boolean alcoholFlag;
    @CqlName("profile__cut_flowers_flag")  private Boolean cutFlowersFlag;
    @CqlName("profile__declared_value_exception")  private Boolean declaredValueException;
    @CqlName("profile__derived_station") private String derivedStation;
    @CqlName("profile__drop_ship_flag")  private Boolean dropShipFlag;
    @CqlName("profile__emerge_flag")  private Boolean emergeFlag;
    @CqlName("profile__doc_prep_service_flag")  private Boolean docPrepServiceFlag;
    @CqlName("profile__ftbd_flag")  private String ftbdFlag;
    @CqlName("profile__ftbd_svc")  private Map<String,String> ftbdSvc;
    @CqlName("profile__hazardous_shipper_flag")  private String hazardousShipperFlag;  
    @CqlName("profile__high_value_accept_cd")  private String highValueAcceptCd;
    @CqlName("profile__interline_cd")  private String interlineCd;
    @CqlName("profile__idf_elig_flag")  private String idfEligFlag;
    @CqlName("profile__ifs_flag")  private Boolean ifsFlag;
    @CqlName("profile__ipd_flag")  private String ipdFlag;
    @CqlName("profile__money_back_guarantee")  private String moneyBackGuarantee;
    @CqlName("profile__notify_ship_delay_cd")  private Boolean notifyShipDelayCd;
    @CqlName("profile__overnight_frgt_ship_flag")  private Boolean overnightFrgtShipFlag;
    @CqlName("profile__pak_isrt_flag")  private String pakIsrtFlag;
    @CqlName("profile__power_of_attorney_date") private LocalDate powerOfAttorneyDate;
    @CqlName("profile__power_of_attorney_flag")  private Boolean powerOfAttorneyFlag;
    @CqlName("profile__regular_stop_flag")  private Boolean regularStopFlag;
    @CqlName("profile__reroutes_allowed_flag")  private Boolean reroutesAllowedFlag;
    @CqlName("profile__signature_on_file")  private Boolean signatureOnFile;
    @CqlName("profile__signature_required")  private Boolean signatureRequired;
    @CqlName("profile__tpc_flag")  private Boolean tpcFlag;
    @CqlName("profile__emp_ship_emp_number")  private String empShipEmpNumber;
    @CqlName("profile__supply_no_cut_flag")  private String supplyNoCutFlag;
    @CqlName("profile__starter_kit") private String starterKit;
    @CqlName("profile__starter_kit_quantity") private String starterKitQuantity;
    @CqlName("profile__exception_flag")  private Boolean exceptionFlag;
    @CqlName("profile__international_shipper")  private String internationalShipper;
    @CqlName("profile__special_dist_flag")  private String specialDistFlag;
    @CqlName("profile__transmart_flag")  private Boolean transmartFlag;
    @CqlName("profile__special_comment_cd")  private String specialCommentCd;
    @CqlName("profile__contact_flag")  private Boolean contactFlag;

    // express_geographic_info
    @CqlName("geographic_info__alpha_id")  private String alphaId;
    @CqlName("geographic_info__station_number")  private String stationNumber;

    // tnt_operations_profile
    @CqlName("profile__source_name")  private String sourceName;
    @CqlName("profile__tnt_customer_number") private String tntCustomerNumber;
    @CqlName("profile__migration_date") private LocalDate migrationDate;
    @CqlName("profile__deactivation_date") private LocalDate deactivationDate;

    // ground_operations_profile
    @CqlName("profile__grnd_barcode_type")  private String grndBarcodeType;
    @CqlName("profile__grnd_hazmat_flag")  private Boolean grndHazmatFlag;
    @CqlName("profile__grnd_pickup_type")  private String grndPickupType;
    @CqlName("profile__grnd_collect_flag")  private Boolean grndCollectFlag;
    @CqlName("profile__national_account_number")  private String nationalAccountNumber;
    @CqlName("profile__grnd_lbl_hazmat_flag")  private Boolean grndLblHazmatFlag;
    @CqlName("profile__grnd_lbl_p_r_pFlag")  private Boolean grndLblPRPFlag;
    @CqlName("profile__grnd_lbl_univ_waste_flag")  private Boolean grndLblUnivWasteFlag;

    // freight_operations_profile
    @CqlName("profile__svc_center_code")  private String svcCenterCode;

    // cargo_operations_profile
    @CqlName("profile__airport_code")  private String airportCode;
    @CqlName("profile__business_mode")  private String businessMode;
    @CqlName("profile__coding_instructions")  private String codingInstructions;
    @CqlName("profile__synonym_name_1")  private String synonymName1;
    @CqlName("profile__synonym_name_2")  private String synonymName2;

    
    // ***** AUTOMATION STREAM  *****
    // expressAutomationInfo
    @CqlName("automation_info__insight_flag")  private Boolean insightFlag;
    @CqlName("automation_info__meter_zone_flag")  private Boolean meterZoneFlag;
    @CqlName("automation_info__device_type_code")  private String deviceTypeCode;

    
    
    // ***** CLEARANCE STREAM  *****
    // cargoRegulatory
    @CqlName("account_regulatory__fdc_broker_nbr")  private String fdcBrokerNbr;
    @CqlName("account_regulatory__fdc_broker_type_cd")  private String fdcBrokerTypeCd;

    // expressCustomerId
    @CqlName("customer_id__iata_number")  private String iataNumber;
    @CqlName("customer_id__custom_importer_id")  private String customImporterId;
    @CqlName("customer_id__customer_id_doc_nbr")  private String customerIdDocNbr;

    // expressRegulatory AND freightRegulatory
    @CqlName("account_regulatory__regulated_agentRegimeEffYearMonth") private String regulatedAgentRegimeEffYearMonth;  
    @CqlName("account_regulatory__regulated_agentRegimeExpYearMonth") private String regulatedAgentRegimeExpYearMonth;
    @CqlName("account_regulatory__bus_registration_id")  private String busRegistrationId;
    @CqlName("account_regulatory__broker_date") private LocalDate brokerDate;
    @CqlName("account_regulatory__canadian_broker_id")  private String canadianBrokerId;
    @CqlName("account_regulatory__employer_id")  private String  employerId;  
    @CqlName("account_regulatory__employer_id_type")  private String employerIdType;
    @CqlName("account_regulatory__forwd_brkr_cd")  private String forwdBrkrCd;
    @CqlName("account_regulatory__gaa_flag")  private Boolean gaaFlag;
    @CqlName("account_regulatory__import_declaration_cd") private Set<String> importDeclarationCd;
    @CqlName("account_regulatory__nri_cd")  private String nriCd;
    @CqlName("account_regulatory__shipper_export_declaration_flag")  private Boolean shipperExportDeclarationFlag;

    // ***** PRICING STREAM  *****
    // expressPricingProfile
    @CqlName("profile__spot_rate_ind")  private Boolean spotRateInd;
    @CqlName("profile__express_plan_flag")  private String expressPlanFlag;
    @CqlName("profile__express_plan_activity_date") private LocalDate expressPlanActivityDate;
    @CqlName("profile__catalog_remail_service_cd")  private String catalogRemailServiceCd;
    @CqlName("profile__middle_man_cd")  private String middleManCd;
    @CqlName("profile__gratuity_flag")  private Boolean gratuityFlag;

    // expressPricingPreference
    @CqlName("profile__bonus_weight_envelope_flag")  private String bonusWeightEnvelopeFlag;
    @CqlName("profile__priority_alert_flag")  private String priorityAlertFlag;
    @CqlName("profile__domestic_max_declared_value_flag")  private String domesticMaxDeclaredValueFlag;
    @CqlName("profile__international_max_declared_value_flag")  private String internationalMaxDeclaredValueFlag;
    @CqlName("profile__linehaul_charge_flag")  private String linehaulChargeFlag;

    // freightPricingProfile
    @CqlName("profile__pricing_flag")  private Boolean pricingFlag;
    @CqlName("profile__blind_shipper_flag")  private Boolean blindShipperFlag;
    @CqlName("profile__pricing_code")  private String pricingCode;
    
    // ***** SALES STREAM  *****
    // expressSalesProfile 
    @CqlName("profile__geo_terr")  private String geoTerr;
    @CqlName("profile__marketing_cd")  private String marketingCd;
    @CqlName("profile__correspondenceCd")  private Boolean correspondenceCd;
    
    // expressPotentialRevenue ,  expressPotentialRevenue ,  expressPotentialRevenue
    @CqlName("potential_revenue_detail__opening_acct_reason")  private String openingAcctReason;
    @CqlName("potential_revenue_detail__opening_acct_comment")  private String openingAcctComment;
    @CqlName("potential_revenue_detail__lead_employee_opco")  private String leadEmployeeOpco;
    @CqlName("potential_revenue_detail__lead_employee_number")  private String leadEmployeeNumber;
    @CqlName("potential_revenue_detail__revenue_source_system")  private String revenueSourceSystem;
    @CqlName("potential_revenue_detail__potential_revenue_account_type")  private String potentialRevenueAccountType;
    
    // SETS and UDTS
//    @CqlName("tax_info__tax_data") Set<CustomerTaxInfoTaxDataDetails> taxData;
    @CqlName("tax_info__tax_data") Map<String,String> taxData;
    @CqlName("tax_info__tax_exempt_detail") Set<CustomerTaxInfoTaxExemptDetails> taxExemptDetail;
    @CqlName("potential_revenue_detail__potential_revenue") Set<CustomerPotentialRevenue> potentialRevenue;
    @CqlName("potential_revenue_detail__other_potential_info") Set<CustomerOtherPotentialInfo> otherPotentialInfo;
}

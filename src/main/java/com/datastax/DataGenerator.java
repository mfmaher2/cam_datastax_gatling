package com.datastax;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.springframework.beans.factory.annotation.Value;
import com.datastax.oss.driver.api.core.CqlSession;
//import org.apache.commons.io.IOUtils;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.Row;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@Getter @Setter @NoArgsConstructor @ToString(includeFieldNames=true)
public class DataGenerator {

	private static Logger LOG = LoggerFactory.getLogger(DataGenerator.class);
//	private CustomerContactDao customerContactDao;
	private CustomerAccountDao customerAccountDao;
//	private CustomerAssocAccountDao customerAssocAccountDao;
//	private CustomerApplyDiscountDao customerApplyDiscountDao;
//	private CustomerCommentDao customerCommentDao;
	private PodamFactory podamFactory = new PodamFactoryImpl();

	/*
	public DataGenerator(CustomerAccountDao customerAccountDao, CustomerCommentDao customerCommentDao) {
		this.customerCommentDao = customerCommentDao;
		this.customerAccountDao = customerAccountDao;
	}

	public DataGenerator(CustomerAccountDao customerAccountDao, CustomerAssocAccountDao customerAssocAccountDao) {
		this.customerAssocAccountDao = customerAssocAccountDao;
		this.customerAccountDao = customerAccountDao;
	}

	public DataGenerator(CustomerAccountDao customerAccountDao, CustomerContactDao customerContactDao,
			CustomerApplyDiscountDao customerApplyDiscountDao) {
		this.customerContactDao = customerContactDao;
		this.customerAccountDao = customerAccountDao;
		this.customerApplyDiscountDao = customerApplyDiscountDao;
	}*/

//	public DataGenerator(CqlSession cqlSession, String datastax_app_level, String datastax_app_name) {
	public DataGenerator(CqlSession cqlSession, String keyspace) {

		CustomerMapper customerMapper = new CustomerMapperBuilder(cqlSession).build();
		
//		this.customerContactDao = customerMapper.customerContactDao(Keyspaces.ACCOUNT_CONTACT_KS.keyspaceName(datastax_app_level, datastax_app_name));
		this.customerAccountDao = customerMapper.customerAccountDao(keyspace);
//		this.customerApplyDiscountDao = customerApplyDiscountDao;
//		this.customerCommentDao = customerMapper.customerCommentDao(Keyspaces.COMMENT_KS.keyspaceName(datastax_app_level, datastax_app_name));
	}


	public GeneratedDataExchange<CustomerAccount> getGeneratedAccount(String accountNum, String opco) throws Exception {
		CustomerAccount custAccount = generateAccountData(accountNum, opco);
		BoundStatement saveStmt = customerAccountDao.batchSave(custAccount);
		GeneratedDataExchange<CustomerAccount> exchange = new GeneratedDataExchange<CustomerAccount>(custAccount, saveStmt);

		return exchange;
	}

	public CustomerAccount getCustomerAccountEntity(Row acctRow){
		return customerAccountDao.asCustomerAccount(acctRow);
	}

	// =================================================================
	// TIME HELPER FUNCTIONs FOR APPLYSDISCOUNT, COMMENT, GROUPS ETC.
	// =================================================================

	public Instant generateExpTime() {
		Calendar c1 = Calendar.getInstance();
		TimeZone tz = TimeZone.getTimeZone("GMT");
		c1.setTimeZone(tz);
		c1.set(Calendar.MONTH, 11);
		c1.set(Calendar.DATE, 31);
		c1.set(Calendar.YEAR, 2099);
		c1.set(Calendar.HOUR_OF_DAY, 5);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		Date dateOne = c1.getTime();
		Instant expTime = dateOne.toInstant();
		return expTime;
	}

	public static Date between(Date startInclusive, Date endExclusive) {
		long startMillis = startInclusive.getTime();
		long endMillis = endExclusive.getTime();
		long randomMillisSinceEpoch = ThreadLocalRandom.current().nextLong(startMillis, endMillis);

		return new Date(randomMillisSinceEpoch);
	}

	public Instant generateRandomTime() {

		long aDay = TimeUnit.DAYS.toMillis(1);
		long now = new Date().getTime();
		Date twoYearsAgo = new Date(now - aDay * 365 * 5);
		Date tenDaysAgo = new Date(now - aDay * 10);
		Date randomDate = DataGenerator.between(twoYearsAgo, tenDaysAgo);

		return randomDate.toInstant();

		/*
		 * 
		 * Calendar c1 = Calendar.getInstance(); TimeZone tz =
		 * TimeZone.getTimeZone("GMT"); c1.setTimeZone(tz); c1.set(Calendar.MONTH, (int)
		 * ((Math.random() * (12 - 1)) + 1)); c1.set(Calendar.DATE, (int)
		 * ((Math.random() * (30 - 1)) + 1)); c1.set(Calendar.YEAR, (int)
		 * ((Math.random() * (1980 - 2021)) + 2021)); c1.set(Calendar.HOUR_OF_DAY, (int)
		 * ((Math.random() * (24 - 0)) + 0)); c1.set(Calendar.MINUTE, 0);
		 * c1.set(Calendar.SECOND, 0); c1.set(Calendar.MILLISECOND, 0); Date dateOne =
		 * c1.getTime(); Instant effTime = dateOne.toInstant(); return effTime;
		 */
	}

	// =================================================================
	// HELPER FUNCTION TO GENERATE DUMMY COMMENT DATA FOR TESTING
	// =================================================================
//	public void generateAssociatedAccountData() throws Exception {
//
//		String[] accounts = IOUtils.toString(getClass().getResourceAsStream("/cql/accountList.txt")).split("\n");
//		for (String account : accounts) {
//			// Comment accountFX = podamFactory.manufacturePojo(Comment.class);
//			int assocAccountCounter = 0;
//			for (assocAccountCounter = 0; assocAccountCounter < 3; assocAccountCounter++) {
//				AssocAccount assocAccount = new AssocAccount();
//				assocAccount.setAccountNumber(account);
//
//				if (assocAccountCounter == 0)
//					assocAccount.setAssociatedAccountNumber(account);
//					assocAccount.setAssociatedAccountOpco("FDEG");
//				if (assocAccountCounter == 1)
//					assocAccount.setAssociatedAccountNumber("US" + account);
//					assocAccount.setAssociatedAccountOpco("TNT");
//				if (assocAccountCounter == 2)
//					assocAccount.setAssociatedAccountNumber("F12312312" + assocAccountCounter);
//					assocAccount.setAssociatedAccountOpco("FSAI");
//					customerAssocAccountDao.save(assocAccount);
//			}
//		}
//
//		LOG.info("associatedAccount DATA GENERATION COMPLETE");
//	}

	// =================================================================
	// HELPER FUNCTION TO GENERATE DUMMY COMMENT DATA FOR TESTING
	// =================================================================
//	public void generateCommentData(String opco) throws Exception {
//
//		String[] accounts = IOUtils.toString(getClass().getResourceAsStream("/cql/accountList.txt")).split("\n");
//		for (String account : accounts) {
//			// Comment accountFX = podamFactory.manufacturePojo(Comment.class);
//			int commentCounter = 0;
//			for (commentCounter = 0; commentCounter < 4; commentCounter++) {
//				Comment comment1 = new Comment();
//				Instant randomTime = generateRandomTime();
//				comment1.setAccountNumber(account);
//				comment1.setOpco(opco);
//				comment1.setCommentDateTime(randomTime);
//
//				if (commentCounter == 0)
//					comment1.setCommentType("GI");
//				if (commentCounter == 1)
//					comment1.setCommentType("BI");
//				if (commentCounter == 2)
//					comment1.setCommentType("DT");
//				if (commentCounter == 3)
//					comment1.setCommentType("CO");
//
//				comment1.setCommentId("" + opco + commentCounter);
//
//				comment1.setCommentDesc("THIS IS COMMENT : " + commentCounter);
//				comment1.setEmployeeNumber("TEMP");
//				comment1.setEmployeeOpco("FDX");
//				comment1.setRequester("DSTAX");
//				comment1.setDepartmentNumber(123);
//
//				customerCommentDao.save(comment1);
//			}
//		}

//		LOG.info("customerCommentDao DATA GENERATION COMPLETE");
//	}

	// =================================================================
	// HELPER FUNCTION TO GENERATE DUMMY APPLYDISCOUNT DATA FOR TESTING
	// =================================================================

//	public void generateApplyDiscountData(String opco) throws Exception {
//
//		String[] accounts = IOUtils.toString(getClass().getResourceAsStream("/cql/applyDiscountLitmus.txt"))
//				.split("\n");
//
//		int accountCounter = 0;
//
//		for (String account : accounts) {
//			ApplyDiscount accountFX = podamFactory.manufacturePojo(ApplyDiscount.class);
//			Instant randomTime = generateRandomTime();
//			accountFX.setAccountNumber(account);
//			accountFX.setOpco(opco);
//			accountFX.setApplyDiscountEffectiveDateTime(randomTime);
//			accountFX.setApplyDiscountExpirationDateTime(generateExpTime());
//			accountFX.setLastUpdated(randomTime);
//			customerApplyDiscountDao.save(accountFX);
//		}
//
//		LOG.info("CustomerApplyDiscountDao DATA GENERATION COMPLETE");
//	}

	// =================================================================
	// HELPER FUNCTION TO GENERATE DUMMY ACCOUNT DATA FOR TESTING
	// =================================================================

	public CustomerAccount generateAccountData(String account, String opco) throws Exception {

//		String[] accounts = IOUtils.toString(getClass().getResourceAsStream("/cql/accountList.txt")).split("\n");

		int accountCounter = 0;

//		for (String account : accounts) {

		CustomerAccount accountFX = podamFactory.manufacturePojo(CustomerAccount.class);
		//CustomerAccount accountFX = new CustomerAccount();
			accountFX.setAccountNumber(account);

			if (opco.equals("ENT")) {

				if ((accountCounter % 2) == 0) {

					accountFX.setOpco(opco);
					accountFX.setCustomerType("BUS");
					accountFX.setAccountType("BUS");
					accountFX.setStatusCode("ACTIVE");
					accountFX.setReasonCode("OP");
					accountFX.setAccountServiceLevel("GOL");
				} else {

					accountFX.setOpco(opco);
					accountFX.setCustomerType("IND");
					accountFX.setAccountType("IND");
					accountFX.setStatusCode("ACTIVE");
					accountFX.setReasonCode("OP");
					accountFX.setAccountServiceLevel("GOL");
				}

			} else {

				// CREDIT DETAIL ENUMERATIONS
				accountFX.setCreditStatus("CR");
				accountFX.setCashOnlyReason("00");
				accountFX.setCreditAlertDetail("AW");
				accountFX.setCreditAlertParentType("B");

				// CREATION ENUMERATIONS
				accountFX.setEmployeeCreatorNumber("398787");
				accountFX.setEmployeeCreatorOpco("FX");
				accountFX.setEmployeeRequesterOpco("FX");
				accountFX.setEmployeeRequesterNumber("123123");
				accountFX.setSourceGroup("ALL");

				if (opco.equals("FX")) {
					// INVOICE PREFERENCE ENUMERATIONS
					accountFX.setBillingClosingDay("01");
					accountFX.setBillingCycle("DY");
					accountFX.setBillingPaymentDay(3);
					accountFX.setBillingPaymentMonth(3);
					accountFX.setBillingRestrictionIndicator("N");
					accountFX.setBillingType(2);
					accountFX.setCombineOption("02");
					accountFX.setConsolidatedInvoicingFlag("N");

					accountFX.setDaystoCredit(999);
					accountFX.setDaystoPay(999);
					accountFX.setDocumentExceptionIndicator(1);
					accountFX.setDutyTaxDaystoPay(999);
					accountFX.setDutyTaxBillingCycle("03");
					accountFX.setElectronicBillPaymentPlanFlag("N");
					accountFX.setFecDiscountCardFlag("N");
					accountFX.setGroundDutyTaxBillingCycle("03");
					accountFX.setGroundPrintWeightIndicator("K");
					accountFX.setInternationalBillingCycle("03");
					accountFX.setInternationalInvoiceBypass("N");
					accountFX.setInternationalDutyTaxInvoiceBypass("N");
					accountFX.setInvoiceDetailLevel(2);
					accountFX.setInvoiceLevelDiscountFlag("N");
					accountFX.setInvoicePageLayoutIndicator(1);
					accountFX.setInvoiceTransactionBreakupType(1);
					accountFX.setInvoiceWaitDays(1);
					accountFX.setManageMyAccountAtFedExFlag("N");
					accountFX.setMasterAccountInvoiceSummaryFlag("52");
					accountFX.setMonthlyBillingIndicator("N");
					accountFX.setPastDueDetailLevel(2);
					accountFX.setPastDueFlag("N");
					accountFX.setPodWaitDays(99);
					accountFX.setPrimarySortOption(2);
					accountFX.setPrintWeightIndicator("B");
					accountFX.setReferenceAppend(2);
					accountFX.setReturnEnvelopeIndicator("B");
					accountFX.setSingleInvoiceOption("05");
					accountFX.setSortFieldLength(2);
					accountFX.setStatementOfAccountBillingCycle("M");
					accountFX.setStatementOfAccountLayoutIndicator(2);
					accountFX.setStatementOfAccountReceiptFlag("N");
					accountFX.setStatementType("P");
					accountFX.setViewedStatementType("P");
					accountFX.setNoPODFlag("N");
					accountFX.setSettlementLevelIndicator("N");
					accountFX.setDirectDebitIndicator("N");
					accountFX.setBalanceForwardCode("N");
					accountFX.setLateFeeEnterpriseWaiver("N");

					// EXPRESS OPERATIONS ENUMERATIONS
					accountFX.setMultiplierRefExp("INV");
					accountFX.setMulitiplierRefGrnd("INV");
					accountFX.setAgentFlag("N");
					accountFX.setFtbdFlag("N");

					accountFX.setIdfEligFlag("N");
					accountFX.setIpdFlag("N");
					accountFX.setMoneyBackGuarantee("B");
					accountFX.setPakIsrtFlag("N");
					accountFX.setSupplyNoCutFlag("N");
					accountFX.setInternationalShipper("N");
					accountFX.setSpecialDistFlag("N");
					accountFX.setSpecialCommentCd("N");
					accountFX.setHazardousShipperFlag("N");

					// GROUND OPERATIONS ENUMERATIONS
					accountFX.setGrndBarcodeType("N");
					accountFX.setGrndPickupType("N");

					// CARGO OPERATIONS ENUMERATIONS
					accountFX.setBusinessMode("03");

					// SMARTPOST OPERATIONS ENUMERATIONS
					accountFX.setPickupCarrier("G");
					accountFX.setZoneIndicator("C");

					// EXPRESS AUTOMATION ENUMERATIONS
					accountFX.setDeviceTypeCode("A");

					// EXPRESS PRICING ENUMERATIONS
					accountFX.setExpressPlanFlag("U");
					accountFX.setCatalogRemailServiceCd("A");
					accountFX.setMiddleManCd("Y");

				} else if (opco.equals("FDFR")) {
					// FREIGHT PRICING ENUMERATIONS
					accountFX.setPricingCode("E");
				}

				if ((accountCounter % 2) == 0) {

					accountFX.setOpco(opco);
					accountFX.setCustomerType("BUS");
					accountFX.setAccountType("B");
					accountFX.setCargoInd("E");
					accountFX.setStatusCode("ACTIVE");
					accountFX.setReasonCode("OP");
					accountFX.setAccountServiceLevel("GOL");

				} else {

					accountFX.setOpco(opco);
					accountFX.setCustomerType("IND");
					accountFX.setAccountType("I");
					accountFX.setCargoInd("E");
					accountFX.setStatusCode("ACTIVE");
					accountFX.setReasonCode("OP");
					accountFX.setAccountServiceLevel("GOL");
				}

				accountFX.setSalesRepOpco("FX");
				accountFX.setScacCode("FXFE");
				accountFX.setCurrencyCode("USD");
			}
			accountCounter++;
//			customerAccountDao.save(accountFX);
			// LOG.info(accountFX.toString());
			// LOG.info("CustomerAccountDao ACCOUNTS COMPLETED:" + accountCounter + "|LAST
			// ACCOUNT COMPLETED:" + account);

//		}

//		LOG.info("CustomerAccountDao DATA GENERATION COMPLETE");

		return accountFX;
	}

	// =================================================================
	// HELPER FUNCTION TO GENERATE DUMMY CONTACT DATA FOR TESTING
	// =================================================================
//	public void generateContactData() throws Exception {
//
//		String[] accounts = IOUtils.toString(getClass().getResourceAsStream("/cql/contactAccountList.txt")).split("\n");
//
//		int accountCounter = 0;
//
//		for (String account : accounts) {
//
//			String[] accountSplit = account.split("\\|");
//			String accountNumber = accountSplit[0];
//			String opco = accountSplit[1];
//			String contactType = accountSplit[2];
//
//			CustomerContact contact = podamFactory.manufacturePojo(CustomerContact.class);
//
//			contact.setAccountNumber(accountNumber);
//			contact.setOpco(opco);
//			contact.setContactTypeCode(contactType);
//			contact.setContactBusinessId("01");
//
//			// SET THE ENUMS
//			contact.setGender("U");
//			contact.setPagerUse("N");
//			contact.setHtmlUse("N");
//			contact.setEmailMarketingFlag("N");
//
//			contact.setAdditionalEmailInfoHtmlUse("N");
//			contact.setAdditionalEmailInfoEmailMarketingFlag("N");
//			contact.setAdditionalEmailInfo2EmailMarketingFlag("N");
//			contact.setAdditionalEmailInfo2HtmlUse("N");
//
//			SecureRandom sr = new SecureRandom();
//			byte[] rndBytes = new byte[10];
//
//			int i = 0;
//			for (CustomerContactTelecomDetails customerContactTelecomDetails : contact.getTeleCom()) {
//				if (i == 0)
//					customerContactTelecomDetails.setTeleComMethod("PV");
//				if (i == 1)
//					customerContactTelecomDetails.setTeleComMethod("SV");
//				if (i == 2)
//					customerContactTelecomDetails.setTeleComMethod("MB");
//				if (i == 3)
//					customerContactTelecomDetails.setTeleComMethod("PG");
//				if (i == 4)
//					customerContactTelecomDetails.setTeleComMethod("FX");
//				sr.nextBytes(rndBytes);
//				i++;
//				customerContactTelecomDetails.setPhoneNumber("" + ByteBuffer.wrap(rndBytes).getInt());
//			}
//
//			i = 0;
//			for (CustomerContactSocialMediaDetails customerContactSocialMediaDetails : contact.getSocialMedia()) {
//				if (i % 2 == 0)
//					customerContactSocialMediaDetails.setTypeCode("OTHER");
//				else
//					customerContactSocialMediaDetails.setTypeCode("GOOGLE");
//				i++;
//			}
//
//			// System.out.println(contact.toString());
//			customerContactDao.save(contact);
//			accountCounter++;

			// LOG.info("CustomerContactDao ACCOUNTS COMPLETED:" + accountCounter + "|LAST
			// ACCOUNT COMPLETED:" + account);

//		}

//		LOG.info("CustomerContactDao DATA GENERATION COMPLETE");
//	}
}

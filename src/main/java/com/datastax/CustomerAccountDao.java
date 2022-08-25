package com.datastax;

import java.util.Map;
import com.datastax.oss.driver.api.core.MappedAsyncPagingIterable;
import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.CustomerAccount;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Dao
public interface CustomerAccountDao {

    //=========================================================================================
	// Direct calls
    //=========================================================================================
    @Select
	PagingIterable<CustomerAccount> findAllByAccountNumber(String accountNumber);
	
	@Select
	PagingIterable<CustomerAccount> findAllByAccountNumberOpco(String accountNumber, String opco);
	
	 // Query by Acct / opco 
	@Query("select * from ${keyspaceId}.cust_acct_v1 where account_number in (:number)")
    PagingIterable<CustomerAccount> queryByAccountNumber(String number);
	
    @Query("select * from ${keyspaceId}.cust_acct_v1 where account_number in (:number) and opco in (:opco)")
    CustomerAccount queryByAccountNumberOpco(String number , String opco);

    @GetEntity
    CustomerAccount asCustomerAccount(Row row);

    //=========================================================================================
	// Async calls
    //=========================================================================================
    @Select
    CompletableFuture<MappedAsyncPagingIterable<CustomerAccount>> findByAccountNumberAsync(String accountNumber);
    
    @Select
    CompletableFuture<CustomerAccount> findByAccountNumberOpcoAsync(String accountNumber, String opco);
    
    @Query("select * from ${keyspaceId}.cust_acct_v1 where account_number in (:number)" )
    CompletableFuture<MappedAsyncPagingIterable<CustomerAccount>> queryByAccountAsync(String number);

    @Query("select * from ${keyspaceId}.cust_acct_v1 where account_number in (:number) and opco in (:opco)" )
    CompletableFuture<CustomerAccount> queryByAccountNumberOpcoAsync(String number , String opco);

    @Insert
    void save(CustomerAccount account);
    
    @Insert
    BoundStatement batchSave(CustomerAccount account);

//    @Update
//    void update(CustomerAccount account);  //todo - determine why causing runtime exception

    @Delete
    void delete(CustomerAccount account);
    
    @Delete
    BoundStatement batchDelete(CustomerAccount account);
    
    @Query("UPDATE ${keyspaceId}.cust_acct_v1 SET duty_tax_info = duty_tax_info + :mapEnt WHERE account_number = :acctNum AND opco = :opco") 
    //TODO use common table ID instead of hard coded value
    void upsertDutyTaxInfoMapEntries(String acctNum, String opco, Map<String, String> mapEnt);
    
    @Query("UPDATE ${keyspaceId}.cust_acct_v1 SET profile__customer_type = :custType WHERE account_number = :acctNum AND opco = :opco") //TODO use common table ID instead of hard coded value
    void updateCustomerType(String acctNum, String opco, String custType);

    @Query("DELETE FROM ${keyspaceId}.cust_acct_v1 WHERE account_number = :accountNum")
    void deleteAllByAccountNumber(String accountNum);

    @Query("DELETE FROM ${keyspaceId}.cust_acct_v1 WHERE account_number = :accountNum")
    BoundStatement batchDeleteAllByAccountNumber(String accountNum);
}


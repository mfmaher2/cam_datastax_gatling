package com.datastax;

import com.datastax.oss.driver.api.core.MappedAsyncPagingIterable;
import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.annotations.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Dao
public interface GroupInfoDao {

    //=========================================================================================
	// Direct calls
    //=========================================================================================
    @Select
	PagingIterable<GroupInfo> findAllByAccountNumber(String accountNumber);
	
	@Select
	PagingIterable<GroupInfo> findAllByAccountNumberOpco(String accountNumber, String opco);
	
	 // Query by Acct / opco 
	@Query("select * from ${keyspaceId}.group_info_v1 where account_number in (:number)")
    PagingIterable<GroupInfo> queryByAccountNumber(String number);
	
    @Query("select * from ${keyspaceId}.group_info_v1 where account_number in (:number) and opco in (:opco)")
    GroupInfo queryByAccountNumberOpco(String number , String opco);

    @GetEntity
    GroupInfo asCustomerAccount(Row row);

    //=========================================================================================
	// Async calls
    //=========================================================================================
    @Select
    CompletableFuture<MappedAsyncPagingIterable<GroupInfo>> findByAccountNumberAsync(String accountNumber);
    
    @Select
    CompletableFuture<GroupInfo> findByAccountNumberOpcoAsync(String accountNumber, String opco);
    
    @Query("select * from ${keyspaceId}.group_info_v1 where account_number in (:number)" )
    CompletableFuture<MappedAsyncPagingIterable<GroupInfo>> queryByAccountAsync(String number);

    @Query("select * from ${keyspaceId}.group_info_v1 where account_number in (:number) and opco in (:opco)" )
    CompletableFuture<GroupInfo> queryByAccountNumberOpcoAsync(String number , String opco);

    @Insert
    void save(GroupInfo account);
    
    @Insert
    BoundStatement batchSave(GroupInfo account);

//    @Update
//    void update(CustomerAccount account);  //todo - determine why causing runtime exception

    @Delete
    void delete(GroupInfo account);
    
    @Delete
    BoundStatement batchDelete(GroupInfo account);
    
//    @Query("UPDATE ${keyspaceId}.group_info_v1 SET duty_tax_info = duty_tax_info + :mapEnt WHERE account_number = :acctNum AND opco = :opco")
//    //TODO use common table ID instead of hard coded value
//    void upsertDutyTaxInfoMapEntries(String acctNum, String opco, Map<String, String> mapEnt);
    
    @Query("UPDATE ${keyspaceId}.group_info_v1 SET group_id__type = :group_id__type WHERE account_number = :acctNum AND opco = :opco") //TODO use common table ID instead of hard coded value
    void updateCustomerType(String acctNum, String opco, String custType);

    @Query("DELETE FROM ${keyspaceId}.group_info_v1 WHERE account_number = :accountNum")
    void deleteAllByAccountNumber(String accountNum);

    @Query("DELETE FROM ${keyspaceId}.group_info_v1 WHERE account_number = :accountNum")
    BoundStatement batchDeleteAllByAccountNumber(String accountNum);
}


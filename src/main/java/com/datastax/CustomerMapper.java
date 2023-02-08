package com.datastax;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
//import com.fedex.dstax.dao.CustomerAccountDao;
//import com.fedex.dstax.dao.CustomerAssocAccountDao;
//import com.fedex.dstax.dao.CustomerAuditHistoryDao;
//import com.fedex.dstax.dao.CustomerCommentDao;
//import com.fedex.dstax.dao.CustomerContactDao;
//import com.fedex.dstax.dao.CustomerPaymentInfoDao;
//import com.fedex.dstax.dao.CustomerApplyDiscountDao;
//import com.fedex.dstax.dao.CustomerNationalAccountDao;
//import com.fedex.dstax.dao.CustomerTimeEventDao;

@Mapper
public interface CustomerMapper {

    @DaoFactory
    GroupInfoDao groupInfoDao(@DaoKeyspace String keyspace);

//    @DaoFactory
//    CustomerAccountDao customerAccountDao(@DaoKeyspace String keyspace);
    
//    @DaoFactory
//    CustomerPaymentInfoDao customerPaymentInfoDao(@DaoKeyspace String keyspace);
//
//    @DaoFactory
//    CustomerAssocAccountDao customerAssocAccountDao(@DaoKeyspace String keyspace);
//
//    @DaoFactory
//    CustomerNationalAccountDao customerNationalAccountDao(@DaoKeyspace String keyspace);
//
//    @DaoFactory
//    CustomerCommentDao customerCommentDao(@DaoKeyspace String keyspace);
//
//    @DaoFactory
//    CustomerAuditHistoryDao customerAuditHistoryDao(@DaoKeyspace String keyspace);
//
//    @DaoFactory
//    CustomerContactDao customerContactDao(@DaoKeyspace String keyspace);
//
//    @DaoFactory
//    CustomerApplyDiscountDao customerApplyDiscountDao(@DaoKeyspace String keyspace);
//
//    @DaoFactory
//    CustomerTimeEventDao timeEventDao(@DaoKeyspace String keyspace);
//

//  static CustomerMainMapper builder(CqlSession session) {
//  return new CustomerMapperBuilder(session).build();
//}
    
}

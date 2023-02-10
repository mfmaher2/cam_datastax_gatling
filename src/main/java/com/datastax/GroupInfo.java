package com.datastax;


import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @ToString(includeFieldNames=true)
@Entity
@CqlName("group_info_v1")
public class GroupInfo {

    @PartitionKey @CqlName("account_number") private String accountNumber;
    @ClusteringColumn(0)  private String opco;
    @CqlName("group_id__code") private String groupCode;
    @CqlName("group_id__number")  private String groupNumber;
    @CqlName("group_id__type") private String groupType;
    @CqlName("effective_date_time") private Instant effectiveDate;
    @CqlName("expiration_date_time") private Instant expirationDate;
    @CqlName("group_id_detail__master_account") private String detailMasterAccount;
    @CqlName("group_id_detail__name")  private String detailName;
    @CqlName("group_id_detail__requester") private String detailRequester;
    @CqlName("last_update_tmstp") private Instant lastUpdate;

}

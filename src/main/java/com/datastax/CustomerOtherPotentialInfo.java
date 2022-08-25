package com.datastax;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.SchemaHint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString(includeFieldNames=true)

@Entity
@CqlName("other_potential_info_type")
@SchemaHint(targetElement = SchemaHint.TargetElement.UDT)
public class CustomerOtherPotentialInfo {
	@CqlName("revenue_event_source") private String revenueEventSource;
	@CqlName("revenue_comments") private String revenueComments;
	@CqlName("comment_sequence_nbr") private int commentSequenceNbr;
	@CqlName("revenue_lead_type") private String revenueLeadType;
	@CqlName("program") private String program;
}
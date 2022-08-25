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
@CqlName("tax_exempt_data_type")
@SchemaHint(targetElement = SchemaHint.TargetElement.UDT)
public class CustomerTaxInfoTaxExemptDetails {
    private String tax_exempt_id;
    private String tax_exempt_id_desc;
    private boolean tax_exempt_flag;
}

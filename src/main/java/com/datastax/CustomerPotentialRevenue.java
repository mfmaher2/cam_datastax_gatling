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
@CqlName("potential_revenue_type")
@SchemaHint(targetElement = SchemaHint.TargetElement.UDT)
public class CustomerPotentialRevenue {
	
	@CqlName("shipping_revenue_type") private String shippingRevenueType;
	@CqlName("time_period") private String timePeriod;
	@CqlName("shipping_package_quantity") private String shippingPackageQuantity;
	@CqlName("shipping_weight_quantity") private String shippingWeightQuantity;
	@CqlName("shipping_carrier") private String shippingCarrier;
	@CqlName("shipping_unit_code") private String shippingUnitCode;
	@CqlName("express_freight_oversize") private Boolean expressFreightOversize;
	@CqlName("supplies_services_amt") private Integer suppliesServicesAmt;
	@CqlName("shipping_package_percent") private String shippingPackagePercent;

}
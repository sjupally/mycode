package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "panic_summary_view")
public class PanicSummaryView implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	@Column(name = "isClosed")
	private Boolean isClosed;

	@Column(name = "count")
	private Long count;

	@Column(name = "event_type")
	private String eventSource;

	@Column(name = "state_id")
	private Long stateId;
	
	@Column(name = "district_id")
	private Long districtId;
	
	@Column(name = "city_id")
	private Long cityId;

	@Column(name = "packet_date")
	private String date;

}

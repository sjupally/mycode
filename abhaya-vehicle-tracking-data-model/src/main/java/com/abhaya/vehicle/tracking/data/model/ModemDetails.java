package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
@Table(name = "modem_details")
@SequenceGenerator(name = "modem_details_seq", sequenceName = "modem_details_seq",  allocationSize = 1)
public class ModemDetails implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "modem_details_seq")
	private Long id;

	@Column(name = "imei_number" ,unique = true)
	private String imeiNumber;

	@Column(name = "sim_number")
	private String simNumber;

	@Column(name = "imsi_number")
	private String imsiNumber;

	@Column(name = "id_address")
	private String ipAddress;

	@Column(name = "signal_strength")
	private String signalStrength;
	
	@Column(name = "version")
	private String version;
	
	@Column(name = "serial_number")
	private String serialNumber;
	
	@Column(name = "created_date" ,updatable = false)
	private Timestamp createdDate;
	
	@Column(name = "mobile_number")
	private String mobileNumber;
	@Column(name = "updated_date")
	private Timestamp updatedDate;
}

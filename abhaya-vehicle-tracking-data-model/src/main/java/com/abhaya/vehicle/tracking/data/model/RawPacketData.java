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

@Setter
@Getter
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(name = "raw_packet_data_seq", sequenceName = "raw_packet_data_seq", allocationSize = 1)
@Table(name = "raw_packet_data")
public class RawPacketData implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "raw_packet_data_seq")
	private Long id;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "serial_number")
	private String serialNumber;
	
	@Column(name = "raw_data" ,length = 10485760)
	private String rawData;
	
	@Column(name = "packet_date")
	private String packetDate;
	
	@Column(name = "packet_time")
	private String packetTime;
	
	@Column(name = "imei_number")
	private String imeiNumber;
}
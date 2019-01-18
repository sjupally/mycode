package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "alerts")
@SequenceGenerator(name = "alerts_seq", sequenceName = "alerts_seq", allocationSize = 1)
public class Alerts implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alerts_seq")
	private Long id;
	
	@Column(name = "command_type")
	private String alertType;
	
	@Column(name = "command_name")
	private String alertName;
	
	@Column(name = "serial_number")
	private String serialNumber;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "citizen_id" ,nullable = false)
	private CitizenDetails citizenDetails;
	
	@Column(name = "alert_time")
	private Timestamp alertTime;
	
	private String latitude;
	private String langitude;
	private String comments;
	
	@Column(name = "is_closed")
	private Boolean isClosed;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "attended_by")
	private UserDetails attendedBy;
}

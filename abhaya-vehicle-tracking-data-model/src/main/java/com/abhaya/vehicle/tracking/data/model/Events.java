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
@Table(name = "events_details")
@SequenceGenerator(name = "events_details_seq", sequenceName = "events_details_seq", allocationSize = 1)
public class Events implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "events_details_seq")
	private Long id;

	@Column(name = "event_type")
	private String eventType;

	@Column(name = "event_name")
	private String eventName;

	private String value;

	@Column(name = "created_date")
	private Timestamp createdDate;
}

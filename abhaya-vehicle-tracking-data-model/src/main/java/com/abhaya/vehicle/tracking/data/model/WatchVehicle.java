package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.abhaya.vehicle.tracking.data.audit.model.AbstractAuditEntity;

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
@Table(name = "watch_vehicle")
@SequenceGenerator(name = "watch_vehicle_seq", sequenceName = "watch_vehicle_seq", initialValue = 1)
public class WatchVehicle extends AbstractAuditEntity  implements Serializable
{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "watch_vehicle_seq")
	private Long id;

	@Column(name = "created_date")
	private Timestamp createdDate;
	
	private String location;
	
	@ManyToOne
	@JoinColumn(name = "vehicle_id" ,nullable = false)
	private VehicleDetails vehicleDetails;
}

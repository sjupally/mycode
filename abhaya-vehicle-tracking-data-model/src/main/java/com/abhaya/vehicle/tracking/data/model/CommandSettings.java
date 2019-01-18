package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;

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
@Table(name = "command_settings")
@SequenceGenerator(name = "command_settings_seq", sequenceName = "command_settings_seq", allocationSize = 1)
public class CommandSettings implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "command_settings_seq")
	private Long id;

	@Column(name = "command_type")
	private String commandType;

	@Column(name = "command_name")
	private String commandName;

	private String value;
}

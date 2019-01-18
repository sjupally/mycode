package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "roles")
@SequenceGenerator(name = "roles_seq", sequenceName = "roles_seq", allocationSize = 1)
public class Roles implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq")
	private Long id;

	@Column(length = 50, unique = true, nullable = false)
	private String name;

	private String description;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "roles_privileges", joinColumns = {
	@JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = 	{@JoinColumn(name = "privilege_id", referencedColumnName = "id") })
	private List<Privilege> privileges;
}
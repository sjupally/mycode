package com.abhaya.vehicle.tracking.vos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
@JsonInclude(Include.NON_DEFAULT)
public class PrivilagesVO implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
}
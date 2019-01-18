package com.abhaya.vehicle.tracking.vos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboDataVO implements Serializable 
{

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String code;
}

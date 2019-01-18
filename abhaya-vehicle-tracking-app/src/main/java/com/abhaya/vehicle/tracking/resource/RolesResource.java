package com.abhaya.vehicle.tracking.resource;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.abhaya.vehicle.tracking.vos.PrivilagesVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonInclude(Include.NON_NULL)
public class RolesResource extends ResourceSupport
{
	private Long roleId;
	private String name;
	private long[] privilege;
	private List<PrivilagesVO> privileges;
}

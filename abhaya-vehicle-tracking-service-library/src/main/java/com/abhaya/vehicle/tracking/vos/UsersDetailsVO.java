package com.abhaya.vehicle.tracking.vos;

import java.io.Serializable;
import java.util.List;

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
public class UsersDetailsVO implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long roleId;
	private Object[] roleName;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private Long mobileNumber;
	private Boolean isEnabled;
	private String status;
	private long[] roles;
	private String emailId;
	private List<RolesVO> userRoles;
	private String createDate;
	private Long departmentId;
	private String departmentName;
	private Long stateId;
	private String stateName;
	private Long districtId;
	private String districtName;
	private Long cityId;
	private String cityName;
	private int userLevel;
}

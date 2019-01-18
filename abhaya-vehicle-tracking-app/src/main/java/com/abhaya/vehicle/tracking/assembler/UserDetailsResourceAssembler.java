package com.abhaya.vehicle.tracking.assembler;

import javax.validation.Valid;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.command.controller.UserRegistrationController;
import com.abhaya.vehicle.tracking.resource.UserDetailsResource;
import com.abhaya.vehicle.tracking.vos.UsersDetailsVO;

@Component
public class UserDetailsResourceAssembler extends ResourceAssemblerSupport<UsersDetailsVO, UserDetailsResource> 
{
    public UserDetailsResourceAssembler() 
    {
		super(UserRegistrationController.class, UserDetailsResource.class);
	}

    @Override
    public UserDetailsResource toResource(UsersDetailsVO entity) 
    {
    	return UserDetailsResource.builder()
    			.username(entity.getUsername())
    			.firstName(entity.getFirstName())
    			.lastName(entity.getLastName())
    			.userId(entity.getId())
    			.mobileNumber(entity.getMobileNumber())
    			.isEnabled(entity.getIsEnabled())
    			.emailId(entity.getEmailId())
    			.createdDate(entity.getCreateDate())
    			.rolesVO(entity.getUserRoles())
    			.userId(entity.getId())
    			.departmentId(entity.getDepartmentId())
    			.departmentName(entity.getDepartmentName())
    			.stateId(entity.getStateId())
    			.stateName(entity.getStateName())
    			.districtId(entity.getDistrictId())
    			.districtName(entity.getDistrictName())
    			.cityId(entity.getCityId())
    			.cityName(entity.getCityName())
    			.userLevel(entity.getUserLevel())
    			.build();
    }

	public UsersDetailsVO fromResource(@Valid UserDetailsResource resource) 
	{
		return UsersDetailsVO.builder()
				.firstName(resource.getFirstName())
				.lastName(resource.getLastName())
				.mobileNumber(resource.getMobileNumber())
				.password(resource.getPassword())
				.roles(resource.getRoles())
				.username(resource.getUsername())
				.emailId(resource.getEmailId())
				.id(resource.getUserId())
				.departmentId(resource.getDepartmentId())
				.stateId(resource.getStateId())
    			.districtId(resource.getDistrictId())
    			.cityId(resource.getCityId())
				.userLevel(resource.getUserLevel())
				.build();
	}
}
package com.abhaya.vehicle.tracking.query.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.assembler.UserDetailsResourceAssembler;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadUsersDataSetEvent;
import com.abhaya.vehicle.tracking.resource.UserDetailsResource;
import com.abhaya.vehicle.tracking.services.UserService;
import com.abhaya.vehicle.tracking.utils.QueryParameterConstants;
import com.abhaya.vehicle.tracking.vos.UsersDetailsVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/userDetails")
public class UserQueryController 
{
	@Autowired private UserDetailsResourceAssembler assembler;
	@Autowired private UserService service;
	
	@ApiOperation(value = "View a list of Users", response = ResponseEntity.class)
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<UserDetailsResource>> readUsersData(
			@RequestParam(value = QueryParameterConstants.SORT_DIRECTION, required = false) String sortDirection,
			@RequestParam(value = QueryParameterConstants.SORT_COLUMN_NAME, required = false) String sortColumnName,
			@RequestParam(value = QueryParameterConstants.MOBILE_NUMBER, required = false) Long mobileNumber,
			@RequestParam(value = QueryParameterConstants.USERNAME, required = false) String userName,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "districtId", required = false) Long districtId,
			@RequestParam(value = "cityId", required = false) Long cityId,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,
			PagedResourcesAssembler<UsersDetailsVO> pagedAssembler,HttpServletRequest httpServletRequest) 
	{
		ReadUsersDataSetEvent request = new ReadUsersDataSetEvent().setPageable(pageable);
		request.setSortDirection(sortDirection);
		request.setSortColumnName(sortColumnName);
		request.setMobileNumber(mobileNumber);
		request.setUserName(userName);
		request.setSearchValue(searchValue);
		request.setDistrictId(districtId);
		request.setCityId(cityId);

		PageReadEvent<UsersDetailsVO> event = service.readUsersData(request);
		Page<UsersDetailsVO> page=event.getPage();
		PagedResources<UserDetailsResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
}

package com.abhaya.vehicle.tracking.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.data.model.Privilege;
import com.abhaya.vehicle.tracking.data.model.Roles;
import com.abhaya.vehicle.tracking.data.model.UserDetails;
import com.abhaya.vehicle.tracking.data.repos.CityRepository;
import com.abhaya.vehicle.tracking.data.repos.DepartmentsRepository;
import com.abhaya.vehicle.tracking.data.repos.DistrictsRepository;
import com.abhaya.vehicle.tracking.data.repos.RolesRepositary;
import com.abhaya.vehicle.tracking.data.repos.StateRepository;
import com.abhaya.vehicle.tracking.data.repos.UserDetailsRepository;
import com.abhaya.vehicle.tracking.data.repos.UserDetailsSpecifications;
import com.abhaya.vehicle.tracking.events.CreateUserEvent;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadUsersDataSetEvent;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.util.NepheleValidationUtils;
import com.abhaya.vehicle.tracking.vos.PrivilagesVO;
import com.abhaya.vehicle.tracking.vos.ResponseVO;
import com.abhaya.vehicle.tracking.vos.RolesVO;
import com.abhaya.vehicle.tracking.vos.UsersDetailsVO;

import lombok.extern.slf4j.Slf4j;

public interface UserService extends UserDetailsService
{
	public ResponseVO save(CreateUserEvent createUserEvent);
	public PageReadEvent<UsersDetailsVO> readUsersData(ReadUsersDataSetEvent request);
	public UsersDetailsVO readUserData(String username);
	
	@Slf4j
	@Service
	public class impl implements UserService
	{
		@Autowired private UserDetailsRepository repository;
		@Autowired private RolesRepositary rolesRepositary;
		@Autowired private DepartmentsRepository departmentsRepository;
		@Autowired private StateRepository stateRepository;
		@Autowired private DistrictsRepository districtsRepository;
		@Autowired private CityRepository cityRepository;
		
		@Override
		public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
		{
			log.info("---- START ---- REQUEST:: UserName - "+ username);
			UserDetails user = repository.findByUsername(username);
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user.getRoles()));

		
		}
		private List<GrantedAuthority> getAuthority(List<Roles> authorities) 
		{
			 return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
		}
		@Override
		public ResponseVO save(CreateUserEvent createUserEvent) 
		{
			ResponseVO responseVO = new ResponseVO();
			UserDetails users = new UserDetails();
			List<Roles> authorities = new ArrayList<>();
			UsersDetailsVO usersDetailsVO = createUserEvent.getUsersDetailsVO();
			if (usersDetailsVO.getId() == null)
			{
				users.setPassword(usersDetailsVO.getPassword());
				users.setCreatedDate(DateUitls.getCurrentSystemTimestamp());
				users.setLastPasswordResetDate(new Date(DateUitls.getSqlTimeStamp().getTime()));
				
				UserDetails user = repository.findByUsername(usersDetailsVO.getUsername());
				if (user != null)
				{
					responseVO.setMessage("User with user name " + usersDetailsVO.getUsername() + " already exist." );
					responseVO.setCode(402L);
					return responseVO;
				}
				user = repository.findByMobileNumber(usersDetailsVO.getMobileNumber());
				if (user != null)
				{
					responseVO.setMessage("User with Mobile Number " + usersDetailsVO.getUsername() + " already exist." );
					responseVO.setCode(402L);
					return responseVO;
				}
			}
			if (usersDetailsVO.getRoles() != null && usersDetailsVO.getRoles().length > 0)
			{
				for (Long roleId : usersDetailsVO.getRoles())
				{
					Roles  authority  = rolesRepositary.getOne(roleId);
					authorities.add(authority);
				}
				users.setRoles(authorities);
			}
			users.setUsername(usersDetailsVO.getUsername());
			users.setRoles(authorities);
			users.setFirstName(usersDetailsVO.getFirstName());
			users.setLastName(usersDetailsVO.getLastName());
			users.setIsEnabled(true);
			users.setEmailId(usersDetailsVO.getEmailId());
			users.setMobileNumber(usersDetailsVO.getMobileNumber());
			users.setId(usersDetailsVO.getId());
			users.setDepartments(departmentsRepository.getOne(usersDetailsVO.getDepartmentId()));
			users.setUserLevel(usersDetailsVO.getUserLevel());
			users.setState(!StringUtils.isEmpty(usersDetailsVO.getStateId()) ? stateRepository.getOne(usersDetailsVO.getStateId()) : null);
			users.setDistricts(!StringUtils.isEmpty(usersDetailsVO.getDistrictId()) ? districtsRepository.getOne(usersDetailsVO.getDistrictId()) : null);
			users.setCity(!StringUtils.isEmpty(usersDetailsVO.getCityId()) ? cityRepository.getOne(usersDetailsVO.getCityId()) : null);
			repository.save(users);
			responseVO.setMessage("Success");
			responseVO.setCode(200L);
			return responseVO;
		}
		
		@Override
		public PageReadEvent<UsersDetailsVO> readUsersData(ReadUsersDataSetEvent request)
		{
			Page<UserDetails> dbContent = repository.findAll(new UserDetailsSpecifications(request.getUserName(),request.getMobileNumber(),request.getSearchValue(),request.getDistrictId(),request.getCityId()),UserDetailsSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize()));
			List<UsersDetailsVO> content = new ArrayList<>();
			for (UserDetails record : NepheleValidationUtils.nullSafe(dbContent)) 
			{
				List<Roles> roles = record.getRoles();
				List<RolesVO> rolesVos = new ArrayList<>();
				for (Roles role : roles)
				{
					List<PrivilagesVO> privilagesVOs = new ArrayList<>();
					for (Privilege privilege : role.getPrivileges())
					{
						PrivilagesVO privilagesVO = new PrivilagesVO();
						privilagesVO.setId(privilege.getId());
						privilagesVO.setName(privilege.getName());
						privilagesVOs.add(privilagesVO);
					}
					RolesVO rolesVO = new RolesVO();
					rolesVO.setId(role.getId());
					rolesVO.setName(role.getName());
					rolesVO.setPrivileges(privilagesVOs);
					rolesVos.add(rolesVO);
				}
				UsersDetailsVO details = UsersDetailsVO.builder()
					.firstName(record.getFirstName())
					.lastName(record.getLastName())
					.username(record.getUsername())
					.id(record.getId())
					.mobileNumber(record.getMobileNumber())
					.isEnabled(record.getIsEnabled())
					.emailId(record.getEmailId())
					.userRoles(rolesVos)
					.userLevel(record.getUserLevel())
					.departmentId(record.getDepartments() != null ? record.getDepartments().getId() : null)
					.departmentName(record.getDepartments() != null ? record.getDepartments().getName() : null)
					.stateId(record.getState() != null ? record.getState().getId() : null)
					.stateName(record.getState() != null ? record.getState().getName() : null)
					.districtId(record.getDistricts() != null ? record.getDistricts().getId() : null)
					.districtName(record.getDistricts() != null ? record.getDistricts().getName() : null)
					.cityId(record.getCity() != null ? record.getCity().getId() : null)
					.cityName(record.getCity() != null ? record.getCity().getName() : null)
					.createDate(record.getCreatedDate() != null ? DateUitls.getStringFromTimestamp(record.getCreatedDate()) : null)
					.build();
				content.add(details); 
			}
			Page<UsersDetailsVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}
		@Override
		public UsersDetailsVO readUserData(String username) 
		{
			UserDetails record = repository.findByUsername(username);
			List<Roles> roles = record.getRoles();
			List<RolesVO> rolesVos = new ArrayList<>();
			for (Roles role : roles)
			{
				List<PrivilagesVO> privilagesVOs = new ArrayList<>();
				for (Privilege privilege : role.getPrivileges())
				{
					PrivilagesVO privilagesVO = new PrivilagesVO();
					privilagesVO.setId(privilege.getId());
					privilagesVO.setName(privilege.getName());
					privilagesVOs.add(privilagesVO);
				}
				RolesVO rolesVO = new RolesVO();
				rolesVO.setId(role.getId());
				rolesVO.setName(role.getName());
				rolesVO.setPrivileges(privilagesVOs);
				rolesVos.add(rolesVO);
			}
			UsersDetailsVO details = UsersDetailsVO.builder()
				.firstName(record.getFirstName())
				.lastName(record.getLastName())
				.username(record.getUsername())
				.id(record.getId())
				.mobileNumber(record.getMobileNumber())
				.isEnabled(record.getIsEnabled())
				.emailId(record.getEmailId())
				.userRoles(rolesVos)
				.departmentId(record.getDepartments() != null ? record.getDepartments().getId() : null)
				.departmentName(record.getDepartments() != null ? record.getDepartments().getName() : null)
				.stateId(record.getState() != null ? record.getState().getId() : null)
				.stateName(record.getState() != null ? record.getState().getName() : null)
				.districtId(record.getDistricts() != null ? record.getDistricts().getId() : null)
				.districtName(record.getDistricts() != null ? record.getDistricts().getName() : null)
				.cityId(record.getCity() != null ? record.getCity().getId() : null)
				.userLevel(record.getUserLevel())
				.cityName(record.getCity() != null ? record.getCity().getName() : null)
				.createDate(record.getCreatedDate() != null ? DateUitls.getStringFromTimestamp(record.getCreatedDate()) : null)
				.build();
			return details;
		}
	}
}

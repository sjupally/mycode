package com.abhaya.vehicle.tracking.data.repos;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.data.model.EmergencyContactNumbers;

public class EmergencyContactNumbersSpecifications implements Specification<EmergencyContactNumbers> 
{
	private static final long serialVersionUID = 1L;
	private Long emergencyContactNumber;
	private String citizenMobileNumber;

	public EmergencyContactNumbersSpecifications(Long emergencyContactNumber, String citizenMobileNumber) 
	{
		super();
		this.emergencyContactNumber = emergencyContactNumber;
		this.citizenMobileNumber = citizenMobileNumber;
	}

	public static Sort sortByIdAsc() 
	{
		return new Sort(Sort.Direction.DESC, "id");
	}

	public static Pageable constructPageSpecification(int pageIndex, int pageSize) 
	{
		return PageRequest.of(pageIndex, pageSize,sortByIdAsc());
	}
	
	public static Pageable constructPageSpecification(int pageIndex, int pageSize,Sort sort) 
	{
		return PageRequest.of(pageIndex, pageSize,sort);
	}

	@Override
	public Predicate toPredicate(Root<EmergencyContactNumbers> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		if (!StringUtils.isEmpty(emergencyContactNumber)) 
		{
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("emergencyContactNumber"), emergencyContactNumber));
		}
		if (!StringUtils.isEmpty(citizenMobileNumber)) 
		{
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("citizenDetails").get("mobileNumber"), citizenMobileNumber));
		}
		return predicate;
	}
}
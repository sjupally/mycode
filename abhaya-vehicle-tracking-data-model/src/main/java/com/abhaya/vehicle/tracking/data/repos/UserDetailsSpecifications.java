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

import com.abhaya.vehicle.tracking.data.model.UserDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserDetailsSpecifications implements Specification<UserDetails> 
{
	private static final long serialVersionUID = 1L;
	private String userName;
	private Long mobileNumber;	
	private String searchValue;
	private Long districtId;
	private Long cityId;

	public UserDetailsSpecifications(String userName, Long mobileNumber,String searchValue,Long districtId,Long cityId) 
	{
		super();
		this.userName = userName;
		this.mobileNumber = mobileNumber;
		this.searchValue = searchValue;
		this.districtId = districtId;
		this.cityId = cityId;
	}
	public static Sort sortByIdAsc() 
	{
		return new Sort(Sort.Direction.ASC, "id");
	}
	public static Pageable constructPageSpecification(int pageIndex, int pageSize) 
	{
		return PageRequest.of(pageIndex, pageSize);
	}

	@Override
	public Predicate toPredicate(Root<UserDetails> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		log.info("mobileNUmber : "+ userName + " "+mobileNumber);
		Predicate predicate = criteriaBuilder.conjunction();
		if (userName != null) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("username"),userName));
		}
		if (mobileNumber != null) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("mobileNumber"),mobileNumber));
		}
		if (!StringUtils.isEmpty(searchValue))
		{
			predicate = criteriaBuilder.or(criteriaBuilder.equal(root.get("username"),searchValue));
		}
		if (!StringUtils.isEmpty(districtId))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("districts").get("id"),districtId));
		}
		if (!StringUtils.isEmpty(cityId))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("city").get("id"),cityId));
		}
		return predicate;
	}
}
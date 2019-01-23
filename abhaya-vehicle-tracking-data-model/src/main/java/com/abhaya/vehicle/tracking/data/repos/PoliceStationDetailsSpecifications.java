package com.abhaya.vehicle.tracking.data.repos;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.abhaya.vehicle.tracking.data.model.PoliceStationDetails;

public class PoliceStationDetailsSpecifications implements Specification<PoliceStationDetails> 
{
	private static final long serialVersionUID = 1L;
	private String stationName;
	private String mobileNumber;

	public PoliceStationDetailsSpecifications(String stationName, String mobileNumber) 
	{
		super();
		this.stationName = stationName;
		this.mobileNumber = mobileNumber;
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
	public Predicate toPredicate(Root<PoliceStationDetails> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		if (stationName != null) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("stationName"),stationName));
		}
		if (mobileNumber != null) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("mobileNumber"),mobileNumber));
		}
		return predicate;
	}
}
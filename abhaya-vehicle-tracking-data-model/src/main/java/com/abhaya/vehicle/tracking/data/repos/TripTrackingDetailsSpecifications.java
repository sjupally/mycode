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

import com.abhaya.vehicle.tracking.data.model.TravelTracking;

public class TripTrackingDetailsSpecifications implements Specification<TravelTracking> 
{
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long tripId;
	private String startDate;
	private String serialNumber;
	private String searchValue;
	private String searchDate;

	public TripTrackingDetailsSpecifications(String serialNumber,String startDate,Long tripId,Long id,String searchValue,String searchDate) 
	{
		super();
		this.serialNumber = serialNumber;
		this.startDate = startDate;
		this.tripId = tripId;
		this.id = id;
		this.searchValue = searchValue;
		this.searchDate = searchDate;
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
	public Predicate toPredicate(Root<TravelTracking> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		if (!StringUtils.isEmpty(serialNumber)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("serialNumber"),serialNumber));
		}
		if (!StringUtils.isEmpty(startDate)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("packetDate"),startDate));
		}
		if (!StringUtils.isEmpty(tripId)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("tripDetails").get("id"),tripId));
		}
		if (!StringUtils.isEmpty(id)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.greaterThan(root.get("id"),id));
		}
		if (!StringUtils.isEmpty(searchValue)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("serialNumber"),searchValue));
		}
		if (!StringUtils.isEmpty(searchDate))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("packetDate"),searchDate));
		}
		return predicate;
	}
}
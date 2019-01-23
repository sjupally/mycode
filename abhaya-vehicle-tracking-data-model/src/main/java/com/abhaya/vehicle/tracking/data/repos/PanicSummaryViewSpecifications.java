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

import com.abhaya.vehicle.tracking.data.model.PanicSummaryView;

public class PanicSummaryViewSpecifications implements Specification<PanicSummaryView> 
{
	private static final long serialVersionUID = 1L;
	private String eventSource;
	private Long stateId;
	private Long districtId;
	private Long cityId;
	private String searchDate;
	
	public PanicSummaryViewSpecifications(String eventSource,Long stateId, Long districtId,Long cityId,String searchDate) 
	{
		super();
		this.eventSource = eventSource;
		this.stateId = stateId;
		this.districtId = districtId;
		this.cityId = cityId;
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

	@Override
	public Predicate toPredicate(Root<PanicSummaryView> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		if (!StringUtils.isEmpty(eventSource))
		{
			predicate = criteriaBuilder.or(criteriaBuilder.equal(root.get("eventSource"),eventSource));
		}
		if (!StringUtils.isEmpty(stateId)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("stateId"),stateId));
		}
		if (!StringUtils.isEmpty(districtId)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("districtId"),districtId));
		}
		if (!StringUtils.isEmpty(cityId)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("cityId"),cityId));
		}
		if (!StringUtils.isEmpty(searchDate))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("packetDate"),searchDate));
		}
		return predicate;
	}
}
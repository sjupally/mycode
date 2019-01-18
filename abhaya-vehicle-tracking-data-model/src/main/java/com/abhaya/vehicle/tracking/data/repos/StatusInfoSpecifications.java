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

import com.abhaya.vehicle.tracking.data.model.StatusInfo;

public class StatusInfoSpecifications implements Specification<StatusInfo> 
{
	private static final long serialVersionUID = 1L;
	private String dlNumber;
	private String rcNumber;
	private String serialNumber;
	private String rfId;
	private Long vehicleId;
	private Long trackId;

	public StatusInfoSpecifications(String dlNumber, String rcNumber,String serialNumber,String rfId,Long vehicleId,Long trackId) 
	{
		super();
		this.dlNumber = dlNumber;
		this.rcNumber = rcNumber;
		this.serialNumber = serialNumber;
		this.rfId = rfId;
		this.vehicleId = vehicleId;
		this.trackId = trackId;
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
	public Predicate toPredicate(Root<StatusInfo> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		if (!StringUtils.isEmpty(dlNumber)) 
		{
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("driverDetails").get("dlNumber"), dlNumber));
		}
		if (!StringUtils.isEmpty(rcNumber)) 
		{
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("vehicleDetails").get("rcNumber"), rcNumber));
		}
		if (!StringUtils.isEmpty(vehicleId)) 
		{
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("vehicleDetails").get("id"), vehicleId));
		}
		if (!StringUtils.isEmpty(serialNumber)) 
		{
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("serialNumber"), serialNumber));
		}
		if (!StringUtils.isEmpty(rfId)) 
		{
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("rfId"), rfId));
		}
		if (!StringUtils.isEmpty(trackId)) 
		{
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("trackId"), trackId));
		}
		return predicate;
	}
}

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

import com.abhaya.vehicle.tracking.data.model.VehicleDriverDetailsView;

public class VehicleDriverDetailsSpecifications implements Specification<VehicleDriverDetailsView> 
{
	private static final long serialVersionUID = 1L;
	private String searchValue;
	private String rcNumber;
	private String packetDate;
	private Long districtId;
	private Long cityId;
	private String date;
	
	public VehicleDriverDetailsSpecifications(String searchValue,String rcNumber,String packetDate, Long districtId,Long cityId,String date) 
	{
		super();
		this.searchValue = searchValue;
		this.rcNumber = rcNumber;
		this.packetDate = packetDate;
		this.districtId = districtId;
		this.cityId = cityId;
		this.date = date;
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
	public Predicate toPredicate(Root<VehicleDriverDetailsView> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		if (!StringUtils.isEmpty(searchValue))
		{
			predicate = criteriaBuilder.or(criteriaBuilder.equal(root.get("rcNumber"),searchValue),
					criteriaBuilder.equal(root.get("serialNumber"),searchValue),
					criteriaBuilder.equal(root.get("rfId"),searchValue),
					criteriaBuilder.equal(root.get("dlNumber"),searchValue),
					criteriaBuilder.equal(root.get("ownerContactNumber"),searchValue));
		}
		if (!StringUtils.isEmpty(rcNumber))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("rcNumber"),rcNumber));
		}
		if (!StringUtils.isEmpty(packetDate))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("packetDate"),packetDate));
		}
		if (!StringUtils.isEmpty(districtId)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("districtId"),districtId));
		}
		if (!StringUtils.isEmpty(cityId)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("cityId"),cityId));
		}
		return predicate;
	}
}
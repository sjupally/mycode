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

import com.abhaya.vehicle.tracking.data.model.DriverDetails;

public class DriverDetailsSpecifications implements Specification<DriverDetails> 
{
	private static final long serialVersionUID = 1L;
	private String driverName;
	private String dlNumber;
	private String rfId;
	private String searchValue;
	private Long districtId;
	private Long cityId;
	private String date;
	private String driverContactNumber;

	public DriverDetailsSpecifications(String driverName, String dlNumber,String searchValue,String rfId,Long districtId,
			Long cityId,String date, String driverContactNumber)
	{
		super();
		this.driverName = driverName;
		this.dlNumber = dlNumber;
		this.rfId = rfId;
		this.searchValue = searchValue;
		this.districtId = districtId;
		this.cityId = cityId;
		this.date = date;
		this.driverContactNumber = driverContactNumber;
	}

	public static Sort sortByIdAsc() 
	{
		return new Sort(Sort.Direction.DESC, "id");
	}

	public static Pageable constructPageSpecification(int pageIndex, int pageSize,Sort sort) 
	{
		return PageRequest.of(pageIndex, pageSize,sort);
	}

	@Override
	public Predicate toPredicate(Root<DriverDetails> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		if (!StringUtils.isEmpty(driverName)) {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("driverName"), driverName));
		}
		if (!StringUtils.isEmpty(rfId)) {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("rfId"), rfId));
		}
		if (!StringUtils.isEmpty(dlNumber)) {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("dlNumber"), dlNumber));
		}
		if (!StringUtils.isEmpty(searchValue))
		{
			predicate = criteriaBuilder.or(criteriaBuilder.equal(root.get("dlNumber"),searchValue),
						criteriaBuilder.equal(root.get("rfId"),searchValue),
						criteriaBuilder.equal(root.get("contactNumber"),searchValue));
		}
		if (!StringUtils.isEmpty(districtId))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("districts").get("id"),districtId));
		}
		if (!StringUtils.isEmpty(cityId))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("city").get("id"),cityId));
		}
		if (!StringUtils.isEmpty(driverContactNumber))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("contactNumber"), driverContactNumber));
		}
		/*if (!StringUtils.isEmpty(date))
		{
			try {
				predicate = criteriaBuilder.and(predicate,criteriaBuilder.between(root.get("deviceMappedDate"),DateUtils.appendStartTime(date),DateUtils.appendEndTime(date)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		return predicate;
	}
}
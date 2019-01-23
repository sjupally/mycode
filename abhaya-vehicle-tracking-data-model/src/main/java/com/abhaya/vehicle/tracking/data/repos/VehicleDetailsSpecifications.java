package com.abhaya.vehicle.tracking.data.repos;

import java.sql.Timestamp;
import java.text.ParseException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.data.model.VehicleDetails;
import com.abhaya.vehicle.tracking.utils.DateUtils;

public class VehicleDetailsSpecifications implements Specification<VehicleDetails> 
{
	private static final long serialVersionUID = 1L;
	private String mobileNumber;
	private String rcNumber;
	private String serialNumber;
	private String searchValue;
	private Boolean isDeviceMapped;
	private Long districtId;
	private Long cityId;
	private String date;

	public VehicleDetailsSpecifications(String mobileNumber,String rcNumber,String serialNumber,String searchValue,Boolean isDeviceMapped,Long districtId,
			Long cityId,String date) 
	{
		super();
		this.mobileNumber = mobileNumber;
		this.rcNumber = rcNumber;
		this.serialNumber = serialNumber;
		this.searchValue = searchValue;
		this.isDeviceMapped = isDeviceMapped;
		this.districtId = districtId;
		this.cityId = cityId;
		this.date = date;
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
	public Predicate toPredicate(Root<VehicleDetails> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		if (!StringUtils.isEmpty(mobileNumber)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("ownerDetails").get("ownerContactNumber"),mobileNumber));
		}
		if (!StringUtils.isEmpty(rcNumber))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("rcNumber"),rcNumber));
		}
		if (!StringUtils.isEmpty(serialNumber))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("serialNumber"),serialNumber));
		}
		if (!StringUtils.isEmpty(searchValue))
		{
			predicate = criteriaBuilder.or(criteriaBuilder.equal(root.get("rcNumber"),searchValue),
					criteriaBuilder.equal(root.get("serialNumber"),searchValue),
					criteriaBuilder.equal(root.get("ownerDetails").get("ownerContactNumber"),searchValue));
		}
		if (!StringUtils.isEmpty(isDeviceMapped))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("isDeviceMapped"),isDeviceMapped));
		}
		if (!StringUtils.isEmpty(districtId))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("districts").get("id"),districtId));
		}
		if (!StringUtils.isEmpty(cityId))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("city").get("id"),cityId));
		}
		if (!StringUtils.isEmpty(date)) 
		{
			try 
			{
				Timestamp sDate = DateUtils.appendStartTime(date);
				Timestamp eDate = DateUtils.appendEndTime(date);
				predicate = criteriaBuilder.and(predicate,criteriaBuilder.between(root.get("deviceMappedDate"),sDate,eDate));
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}
		}
		return predicate;
	}
}
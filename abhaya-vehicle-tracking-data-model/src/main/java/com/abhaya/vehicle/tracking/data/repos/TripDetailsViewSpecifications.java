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

import com.abhaya.vehicle.tracking.data.model.TripDetailsView;
import com.abhaya.vehicle.tracking.utils.DateUtils;

public class TripDetailsViewSpecifications implements Specification<TripDetailsView> 
{
	private static final long serialVersionUID = 1L;
	private String dlNumber;
	private String rcNumber;
	private Boolean isTripClosed;
	private String citizenMobileNumber;
	private String startDate;
	private Long stateId;
	private Long cityId;
	private Long districtId;
	private String searchValue;
	private String driverContactNumber;

	public TripDetailsViewSpecifications(String dlNumber,String rcNumber,Boolean isTripClosed,String citizenMobileNumber,String startDate,Long stateId, Long cityId,Long districtId,String searchValue, String driverContactNumber)
	{
		super();
		this.dlNumber = dlNumber;
		this.rcNumber = rcNumber;
		this.isTripClosed = isTripClosed;
		this.citizenMobileNumber = citizenMobileNumber;
		this.startDate =  startDate;
		this.stateId =  stateId;
		this.cityId =  cityId;
		this.districtId =  districtId;
		this.searchValue =  searchValue;
		this.driverContactNumber =  driverContactNumber;
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
	public Predicate toPredicate(Root<TripDetailsView> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		if (!StringUtils.isEmpty(dlNumber)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("dlNumber"),dlNumber));
		}
		if (!StringUtils.isEmpty(isTripClosed)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("isTripClosed"),isTripClosed));
		}
		if (!StringUtils.isEmpty(rcNumber)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("rcNumber"),rcNumber));
		}
		if (!StringUtils.isEmpty(citizenMobileNumber)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("citizenMobileNumber"),citizenMobileNumber));
		}
		if (!StringUtils.isEmpty(startDate)) 
		{
			try 
			{
				Timestamp sDate = DateUtils.appendStartTime(startDate);
				Timestamp eDate = DateUtils.appendEndTime(startDate);
				predicate = criteriaBuilder.and(predicate,criteriaBuilder.between(root.get("requestTime"),sDate,eDate));
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}
		}
		if (!StringUtils.isEmpty(searchValue))
		{
			predicate = criteriaBuilder.or(criteriaBuilder.equal(root.get("rcNumber"),searchValue),criteriaBuilder.equal(root.get("serialNumber"),searchValue),criteriaBuilder.equal(root.get("citizenMobileNumber"),searchValue));
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
		if (!StringUtils.isEmpty(driverContactNumber))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("driverContactNumber"),driverContactNumber));
		}
		
		return predicate;
	}
}
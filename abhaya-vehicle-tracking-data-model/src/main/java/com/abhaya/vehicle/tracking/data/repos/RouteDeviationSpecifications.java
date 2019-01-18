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

import com.abhaya.vehicle.tracking.data.model.RouteDeviationDetailsView;
import com.abhaya.vehicle.tracking.utils.DateUtils;

public class RouteDeviationSpecifications implements Specification<RouteDeviationDetailsView> 
{
	private static final long serialVersionUID = 1L;
	private String dlNumber;
	private String rcNumber;
	private String citizenMobileNumber;
	private String serialNumber;
	private String rfId;
	private String searchValue;
	private String searchDate;
	private Long tripId;

	public RouteDeviationSpecifications(String dlNumber, String rcNumber,String citizenMobileNumber,String serialNumber,String rfId, String searchValue, String searchDate, Long tripId) 
	{
		super();
		this.dlNumber = dlNumber;
		this.rcNumber = rcNumber;
		this.serialNumber = serialNumber;
		this.citizenMobileNumber = citizenMobileNumber;
		this.rfId = rfId;
		this.searchValue = searchValue;
		this.searchDate = searchDate;
		this.tripId = tripId;
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
	public Predicate toPredicate(Root<RouteDeviationDetailsView> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		if (!StringUtils.isEmpty(dlNumber)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("dlNumber"),dlNumber));
		}
		if (!StringUtils.isEmpty(rcNumber)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("rcNumber"),rcNumber));  
		}
		if (!StringUtils.isEmpty(citizenMobileNumber)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("citizenContactNumber"),citizenMobileNumber));
		}
		if (!StringUtils.isEmpty(serialNumber)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("serialNumber"),serialNumber)); 
		}
		if (!StringUtils.isEmpty(rfId)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("rfId"),rfId)); 
		}
		if (!StringUtils.isEmpty(tripId)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("tripId"),tripId)); 
		}
		if (!StringUtils.isEmpty(searchValue))
		{
			predicate = criteriaBuilder.or(criteriaBuilder.equal(root.get("rcNumber"),searchValue),
										    criteriaBuilder.equal(root.get("serialNumber"),searchValue),
										    criteriaBuilder.equal(root.get("rfId"),searchValue),
										    criteriaBuilder.equal(root.get("dlNumber"),searchValue),
										    criteriaBuilder.equal(root.get("citizenContactNumber"),searchValue)
					    );
			
		}
		if (!StringUtils.isEmpty(searchDate)) 
		{
			try 
			{
				Timestamp sDate = DateUtils.appendStartTime(searchDate);
				Timestamp eDate = DateUtils.appendEndTime(searchDate);
				predicate = criteriaBuilder.and(predicate,criteriaBuilder.between(root.get("deviationTime"),sDate,eDate));
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}
		}
		return predicate;
	}
}
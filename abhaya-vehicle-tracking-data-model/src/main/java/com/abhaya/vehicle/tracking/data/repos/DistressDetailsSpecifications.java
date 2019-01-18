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

import com.abhaya.vehicle.tracking.data.model.DistressDetailsView;
import com.abhaya.vehicle.tracking.utils.DateUtils;

public class DistressDetailsSpecifications implements Specification<DistressDetailsView> 
{
	private static final long serialVersionUID = 1L;
	private String serialNumber;
	private String rcNumber;
	private String dlNumber;
	private String citizenMobileNumber;
	private Boolean isClosed;
	private String eventType;
	private Long stateId;
	private Long districtId;
	private Long cityId;
	private String searchValue;
	private String searchDate;
	private Long tripId;

	public DistressDetailsSpecifications(String serialNumber, String rcNumber, String dlNumber,String citizenMobileNumber,Boolean isClosed,
			String eventType,Long stateId,Long districtId,Long cityId, String searchValue, String searchDate, Long tripId) 
	{
		super();
		this.serialNumber = serialNumber;
		this.rcNumber = rcNumber;
		this.dlNumber = dlNumber;
		this.citizenMobileNumber = citizenMobileNumber;
		this.isClosed = isClosed;
		this.eventType = eventType;
		this.stateId = stateId;
		this.districtId = districtId;
		this.cityId = cityId;
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

	@Override
	public Predicate toPredicate(Root<DistressDetailsView> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		if (!StringUtils.isEmpty(citizenMobileNumber)) {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("citizenMobileNumber"), citizenMobileNumber));
		}
		if (!StringUtils.isEmpty(serialNumber)) {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("serialNumber"), serialNumber));
		}
		if (!StringUtils.isEmpty(dlNumber)) {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("dlNumber"), dlNumber));
		}
		if (!StringUtils.isEmpty(rcNumber)) {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("rcNumber"), rcNumber));
		}
		if (!StringUtils.isEmpty(isClosed)) {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("isClosed"), isClosed));
		}
		if (!StringUtils.isEmpty(eventType)) {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("eventType"), eventType));
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
		if (!StringUtils.isEmpty(tripId)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("tripId"),tripId));
		}
		if (!StringUtils.isEmpty(searchValue))
		{
			predicate = criteriaBuilder.or(criteriaBuilder.equal(root.get("dlNumber"),searchValue),criteriaBuilder.equal(root.get("rcNumber"),searchValue),
										   criteriaBuilder.equal(root.get("citizenMobileNumber"),searchValue) );
		}
		if (!StringUtils.isEmpty(searchDate)) 
		{
			try 
			{
				Timestamp sDate = DateUtils.appendStartTime(searchDate);
				Timestamp eDate = DateUtils.appendEndTime(searchDate);
				predicate = criteriaBuilder.and(predicate,criteriaBuilder.between(root.get("createdDate"),sDate,eDate));
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}
		}
		return predicate;
	}
}
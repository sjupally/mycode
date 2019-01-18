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

import com.abhaya.vehicle.tracking.data.model.RawPacketData;
import com.abhaya.vehicle.tracking.utils.DateUtils;

public class RawDataSpecifications implements Specification<RawPacketData> 
{
	private static final long serialVersionUID = 1L;
	private String serialNumber;
	private String imeiNumber;
	private String fromDate;
	private String toDate;
	private String packetDate;
	private String searchValue;

	public RawDataSpecifications(String serialNumber, String imeiNumber,String fromDate,String toDate,String packetDate,String searchValue) 
	{
		super();
		this.serialNumber = serialNumber;
		this.imeiNumber = imeiNumber;
		this.toDate = toDate;
		this.fromDate = fromDate;
		this.packetDate = packetDate;
		this.searchValue = searchValue;
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
	public Predicate toPredicate(Root<RawPacketData> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		if (!StringUtils.isEmpty(serialNumber)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("serialNumber"),serialNumber));
		}
		if (!StringUtils.isEmpty(imeiNumber)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("imeiNumber"),imeiNumber));
		}
		if (!StringUtils.isEmpty(searchValue))
		{
			predicate = criteriaBuilder.or(criteriaBuilder.equal(root.get("serialNumber"),searchValue),criteriaBuilder.equal(root.get("imeiNumber"),searchValue));
		}
		if (!StringUtils.isEmpty(packetDate))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("packetDate"),packetDate));
		}
		if (!StringUtils.isEmpty(fromDate) && !StringUtils.isEmpty(toDate)) 
		{
			try 
			{
				Timestamp sDate = DateUtils.appendStartTime(fromDate);
				Timestamp eDate = DateUtils.appendEndTime(toDate);
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
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

import com.abhaya.vehicle.tracking.data.model.ModemDetails;
import com.abhaya.vehicle.tracking.utils.DateUtils;

public class ModemDetailsSpecifications implements Specification<ModemDetails> 
{
	private static final long serialVersionUID = 1L;
	private String serialNumber;
	private String imeiNumber;
	private String searchValue;
	private String searchDate;

	public ModemDetailsSpecifications(String serialNumber, String imeiNumber,String searchValue,String searchDate) 
	{
		super();
		this.serialNumber = serialNumber;
		this.imeiNumber = imeiNumber;
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

	@Override
	public Predicate toPredicate(Root<ModemDetails> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
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
			predicate = criteriaBuilder.or(criteriaBuilder.equal(root.get("serialNumber"),searchValue),criteriaBuilder.equal(root.get("imeiNumber"),searchValue),criteriaBuilder.equal(root.get("simNumber"),searchValue));
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
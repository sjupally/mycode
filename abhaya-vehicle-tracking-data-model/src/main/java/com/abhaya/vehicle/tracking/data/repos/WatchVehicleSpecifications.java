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

import com.abhaya.vehicle.tracking.data.model.WatchVehicleView;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class WatchVehicleSpecifications implements Specification<WatchVehicleView> 
{
	private static final long serialVersionUID = 1L;
	private String rcNumber;
	private String serialNumber;
	private Long stateId;
	private Long districtId;
	private Long cityId;

	public static Sort sortByIdAsc() 
	{
		return new Sort(Sort.Direction.DESC, "id");
	}
	public static Pageable constructPageSpecification(int pageIndex, int pageSize,Sort sort) 
	{
		return PageRequest.of(pageIndex, pageSize,sortByIdAsc());
	}

	@Override
	public Predicate toPredicate(Root<WatchVehicleView> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		if (!StringUtils.isEmpty(serialNumber))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("vehicleDetails").get("serialNumber"),serialNumber));
		}
		if (!StringUtils.isEmpty(rcNumber))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("vehicleDetails").get("rcNumber"),rcNumber));
		}
		if (!StringUtils.isEmpty(stateId))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("vehicleDetails").get("state").get("id"),stateId));
		}
		if (!StringUtils.isEmpty(districtId))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("vehicleDetails").get("districts").get("id"),districtId));
		}
		if (!StringUtils.isEmpty(cityId))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("vehicleDetails").get("city").get("id"),cityId));
		}
		return predicate;
	}
}
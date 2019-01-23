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

import com.abhaya.vehicle.tracking.data.model.DeviceCommunicationView;

public class DeviceCommunicationSpecifications implements Specification<DeviceCommunicationView> {
    private static final long serialVersionUID = 1L;

    private String status;
    private Long stateId;
    private Long districtId;
    private Long cityId;
    private String searchValue;
    private String searchDate;
    private String movement;
	private String batteryStatus;
	private String ignitionStatus;
	private String engineStatus;
	private String tamperStatus;
	private String panicButtonStatus;

    public DeviceCommunicationSpecifications(String status, Long stateId, Long districtId, Long cityId ,String searchValue ,String searchDate,
    		String movement, String batteryStatus, String ignitionStatus, String engineStatus, String tamperStatus, String panicButtonStatus) {
        this.status = status;
        this.stateId = stateId;
        this.districtId = districtId;
        this.cityId = cityId;
        this.searchValue = searchValue;
        this.searchDate = searchDate;
        this.movement = movement;
        this.batteryStatus = batteryStatus;
        this.ignitionStatus = ignitionStatus;
        this.engineStatus = engineStatus;
        this.tamperStatus = tamperStatus;
        this.panicButtonStatus = panicButtonStatus;
    }

    public static Sort sortByIdAsc() {
        return new Sort(Sort.Direction.DESC, "packetDate" , "packetTime");
    }

    public static Pageable constructPageSpecification(int pageIndex, int pageSize) {
        return PageRequest.of(pageIndex, pageSize, sortByIdAsc());
    }

    @Override
    public Predicate toPredicate(Root<DeviceCommunicationView> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();
        if (!StringUtils.isEmpty(status)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), status));
        }
        if (!StringUtils.isEmpty(stateId)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("stateId"), stateId));
        }
        if (!StringUtils.isEmpty(districtId)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("districtId"), districtId));
        }
        if (!StringUtils.isEmpty(cityId)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("cityId"), cityId));
        }
        if (!StringUtils.isEmpty(movement)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("movement"), movement));
        }
        if (!StringUtils.isEmpty(batteryStatus)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("batteryStatus"), batteryStatus));
        }
        if (!StringUtils.isEmpty(ignitionStatus)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("ignitionStatus"), ignitionStatus));
        }
        if (!StringUtils.isEmpty(engineStatus)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("engineStatus"), engineStatus));
        }
        if (!StringUtils.isEmpty(tamperStatus)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("tamperStatus"), tamperStatus));
        }
        if (!StringUtils.isEmpty(panicButtonStatus)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("panicButtonStatus"), panicButtonStatus));
        }
        if (!StringUtils.isEmpty(searchValue))
		{
			predicate = criteriaBuilder.or(criteriaBuilder.equal(root.get("serialNumber"),searchValue), criteriaBuilder.equal(root.get("rcNumber"),searchValue));
		}
        if (!StringUtils.isEmpty(searchDate)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("packetDate"), searchDate));
        }
        return predicate;
    }
}
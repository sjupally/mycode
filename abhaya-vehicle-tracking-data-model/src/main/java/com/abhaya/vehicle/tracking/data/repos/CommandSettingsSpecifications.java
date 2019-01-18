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

import com.abhaya.vehicle.tracking.data.model.CommandSettings;

public class CommandSettingsSpecifications implements Specification<CommandSettings> 
{
	private static final long serialVersionUID = 1L;
	private String commandName;

	public CommandSettingsSpecifications(String commandName) 
	{
		super();
		this.commandName = commandName;
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
	public Predicate toPredicate(Root<CommandSettings> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		if (!StringUtils.isEmpty(commandName)) 
		{
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("commandName"), commandName));
		}
		return predicate;
	}
}
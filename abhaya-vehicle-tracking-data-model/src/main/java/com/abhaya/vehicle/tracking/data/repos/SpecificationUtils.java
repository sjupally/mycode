package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public final class SpecificationUtils 
{

  /**
   * Get in ASc order
   *
   * @return
   */
  public static Sort sortByIdAsc() 
  {
    return new Sort(Sort.Direction.ASC, "id");
  }

  /**
   * Returns a new object which specifies the the wanted result page.
   *
   * @param pageIndex The index of the wanted result page
   * @return
   */
  public static Pageable constructPageSpecification(int pageIndex, int pageSize, Sort sortingOrderSpec) 
  {
	  Pageable pageSpecification = PageRequest.of(pageIndex, pageSize, sortingOrderSpec);
   	  return pageSpecification;
  }


  /**
   * This is a common method for sorting implementation among all the entities, Default sort is  by Id
   *
   * @param sortColumnName , Name of the target column
   * @param sortDirection, Sort direction either ASC or DESC
   * @return Sort object
   */
  public static Sort sortBySortKey(String sortColumnName,String sortDirection) 
  {
		if (!StringUtils.isEmpty(sortDirection) && !StringUtils.isEmpty(sortColumnName)) 
		{
			return new Sort(Sort.Direction.valueOf(sortDirection.toUpperCase()), sortColumnName);
		}
		return new Sort(Sort.Direction.DESC, "id");
  }
}

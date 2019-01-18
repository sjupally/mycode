package com.abhaya.vehicle.tracking.data.repos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.utils.DriverAuthVO;

@SuppressWarnings("deprecation")
@Repository
public class DriverAuthReportSQLRepository 
{
	
	@PersistenceContext
    private EntityManager  manager;
	
    //Until Hibernate 6 release ,you have to use these deprecated methods only,no other alternative to these deprecated methods in 5.3	
	@SuppressWarnings({ "unchecked","rawtypes"})
	public List<DriverAuthVO> getDriverAuthenticationDetails(DriverAuthVO request)
	{
		StringBuilder builder = new StringBuilder();
		if (!StringUtils.isEmpty(request.getStateId()))
		{
			builder.append("where state_id=" + request.getStateId());
		}
		if (!StringUtils.isEmpty(request.getDistrictId()))
		{
			builder.append("and district_id=" + request.getDistrictId());
		}
		if (!StringUtils.isEmpty(request.getCityId()))
		{
			builder.append("and city_id=" + request.getCityId());
		}
		if (!StringUtils.isEmpty(request.getSearchDate()))
		{
			builder.append("and packet_date=" +'\''+request.getSearchDate()+'\'');
		}
		
		String sql =  "select A.case AS \"authType\", A.count FROM ( select CASE WHEN du.id IS NULL THEN 'UnAuth' "
			    + " WHEN du.id IS NOT NULL THEN 'Auth' "
			    + " ELSE 'other' "
			    + " END "
			    + " from driver_duty_details du "
			    + " FULL OUTER JOIN vehicle_details v ON (du.vehicle_id = v.id) "
			    +   builder.toString()
			    + " ) A "
			    + " Group by A.case " ;
		Session session = (Session) manager.getDelegate();
		Query query =  session.createSQLQuery(sql)
				   	  .addScalar("authType",StringType.INSTANCE)
				      .addScalar("count",LongType.INSTANCE);
		query.setResultTransformer(Transformers.aliasToBean(DriverAuthVO.class));
		List<DriverAuthVO> list = query.list();
		List<DriverAuthVO> newList = new ArrayList<>();

		if (list.isEmpty()) {
			DriverAuthVO driverAuthVO = new DriverAuthVO();
			driverAuthVO.setAuthType("Auth");
			driverAuthVO.setCount(0L);

			newList.add(driverAuthVO);
			driverAuthVO = new DriverAuthVO();
			driverAuthVO.setAuthType("UnAuth");
			driverAuthVO.setCount(0L);
			newList.add(driverAuthVO);
			return newList;
		}
		return list;
	}
}

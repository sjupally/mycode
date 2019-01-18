package com.abhaya.vehicle.tracking.data.repos;

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

import com.abhaya.vehicle.tracking.utils.VehicleDashboardVO;

@SuppressWarnings("deprecation")
@Repository
public class SQLRepository 
{
	
	@PersistenceContext
    private EntityManager  manager;
	
    //Until Hibernate 6 release ,you have to use these deprecated methods only,no other alternative to these deprecated methods in 5.3	
	@SuppressWarnings({ "unchecked","rawtypes"})
	public List<VehicleDashboardVO> getLiveVehicle(VehicleDashboardVO request)
	{
		StringBuilder builder = new StringBuilder();
		if (!StringUtils.isEmpty(request.getStateId()))
		{
			builder.append("and state_id=" + request.getStateId());
		}
		if (!StringUtils.isEmpty(request.getDistrictId()))
		{
			builder.append("and district_id=" + request.getDistrictId());
		}
		if (!StringUtils.isEmpty(request.getCityId()))
		{
			builder.append("and city_id=" + request.getCityId());
		}
		
		if (!StringUtils.isEmpty(request.getDistrictName()))
		{
			builder.append("and district_name=" + request.getDistrictName());
		}
		if (!StringUtils.isEmpty(request.getCityName()))
		{
			builder.append("and city_name=" + request.getCityName());
		}

		String sql =  "select DISTINCT ON (serial_number) id AS \"id\",latitude AS \"latitude\",langitude AS \"langitude\",serial_number AS \"serialNumber\",rc_number AS \"rcNumber\" "
				+ " from vehicle_live_status_view where 1=1 " + builder.toString() + " ORDER BY serial_number,id DESC";

		Session session = (Session) manager.getDelegate();
		Query query =  session.createSQLQuery(sql)
				   	  .addScalar("id",LongType.INSTANCE)
				      .addScalar("latitude",StringType.INSTANCE)
				      .addScalar("langitude",StringType.INSTANCE)
				      .addScalar("rcNumber",StringType.INSTANCE)
		 			  .addScalar("serialNumber",StringType.INSTANCE);
		query.setResultTransformer(Transformers.aliasToBean(VehicleDashboardVO.class));
		return query.list();
	}
}

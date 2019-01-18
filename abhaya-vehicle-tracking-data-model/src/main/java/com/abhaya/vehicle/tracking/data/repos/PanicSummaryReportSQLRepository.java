package com.abhaya.vehicle.tracking.data.repos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.utils.PanicSummaryVO;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("deprecation")
@Slf4j
@Repository
public class PanicSummaryReportSQLRepository 
{
	
	@PersistenceContext
    private EntityManager  manager;
	
    //Until Hibernate 6 release ,you have to use these deprecated methods only,no other alternative to these deprecated methods in 5.3	
	@SuppressWarnings({ "unchecked","rawtypes"})
	public List<PanicSummaryVO> getPanicSummary(PanicSummaryVO request)
	{
		StringBuilder having = new StringBuilder();
		if(StringUtils.isEmpty(request.getEventSource())) {
			request.setEventSource("WEB");
		}
		having.append(" having ds.event_type= " + "\'"+request.getEventSource()+"\'");
		
		if (!StringUtils.isEmpty(request.getStateId()))
		{
			having.append(" and v.state_id= " + request.getStateId());
		}
		if (!StringUtils.isEmpty(request.getDistrictId()))
		{
			having.append(" and v.district_id= " + request.getDistrictId());
		}
		if (!StringUtils.isEmpty(request.getCityId()))
		{
			having.append(" and v.city_id=" + request.getCityId());
		}
		
		StringBuilder select = new StringBuilder();
		
		select.append("select ds.is_closed as \"isClosed\" ,  count(ds.is_closed) as \"count\" , ds.packet_date as \"packetDate\"");
		
		select.append(", ds.event_type as \"eventSource\"");
		
		if (!StringUtils.isEmpty(request.getStateId())) {
			select.append(", v.state_id as \"stateId\"");
		}
		if (!StringUtils.isEmpty(request.getDistrictId())) {
			select.append(", v.district_id as \"districtId\"");
		}
		if (!StringUtils.isEmpty(request.getCityId())){
			select.append(", v.city_id as \"cityId\"");
		}

		StringBuilder group = new StringBuilder();
		group.append("group by ds.is_closed , ds.event_type, ds.packet_date");
		if (!StringUtils.isEmpty(request.getStateId())) {
			group.append(", v.state_id");
		}
		if (!StringUtils.isEmpty(request.getDistrictId())) {
			group.append(", v.district_id ");
		}
		if (!StringUtils.isEmpty(request.getCityId())){
			group.append(", v.city_id");
		}
		
		String sql =  select.toString()
				 + " from distress_details ds "
				 + " JOIN trip_details t on t.id = ds.trip_id "
				 + " JOIN vehicle_details v ON t.vehicle_id = v.id "
				 + " JOIN driver_details d ON t.driver_id = d.id "
				 + " JOIN citizen_details c ON t.citizen_id = c.id ";
				 if (!StringUtils.isEmpty(request.getStateId())) {
					 sql = sql+ " JOIN state s ON v.state_id = s.id ";
				 }
				 if (!StringUtils.isEmpty(request.getDistrictId())) {
					 sql = sql+ " JOIN districts dis ON v.district_id = dis.id ";
				 }
				 if (!StringUtils.isEmpty(request.getCityId())){
					 sql = sql+ " JOIN city city ON v.city_id = city.id ";
				 }
				 sql = sql+ group.toString();
				 sql = sql+ having.toString();
		log.info(" SQL - "+ sql);

		Session session = (Session) manager.getDelegate();
		
		SQLQuery query = session.createSQLQuery(sql);
		List<Object[]> rows = query.list(); // Changes needed
//		Query query =  session.createSQLQuery(sql)
//				   	  .addScalar("isClosed",BooleanType.INSTANCE)
//				      .addScalar("count",LongType.INSTANCE)
//					  .addScalar("eventSource",StringType.INSTANCE)
//					  .addScalar("packetDate",StringType.INSTANCE)
//					  .addScalar("stateId",LongType.INSTANCE)
//					  .addScalar("districtId",LongType.INSTANCE)
//					  .addScalar("cityId",LongType.INSTANCE)
//					  ;
//		query.setResultTransformer(new AliasToBeanResultTransformer(PanicSummaryVO.class));
		
		log.info(" Result - "+ rows); // changes need
		List<PanicSummaryVO> returnList = new ArrayList<>();
		for(Object[] row : rows){
			PanicSummaryVO panicSummaryVO = new PanicSummaryVO();
			panicSummaryVO.setCount(Long.parseLong(row[1].toString()));
			panicSummaryVO.setIsClosed(Boolean.parseBoolean(row[0].toString()));
			panicSummaryVO.setPacketDate((String) row[2]);
			panicSummaryVO.setEventSource((String) row[3]);
			if (!StringUtils.isEmpty(request.getStateId())) {
				panicSummaryVO.setStateId(Long.parseLong(row[4].toString()));
			}
			if (!StringUtils.isEmpty(request.getCityId())) {
				panicSummaryVO.setCityId(Long.parseLong(row[6].toString()));
			}
			if (!StringUtils.isEmpty(request.getDistrictId())) {
				panicSummaryVO.setDistrictId(Long.parseLong(row[5].toString()));
			}
			returnList.add(panicSummaryVO);
		}
		
		return returnList;
	}
}

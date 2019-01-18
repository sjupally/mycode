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

import com.abhaya.vehicle.tracking.utils.VehicleStatusVO;

@SuppressWarnings("deprecation")
@Repository
public class VehicleStatusInfoReportSQLRepository
{
	
	@PersistenceContext
    private EntityManager  manager;
	
    //Until Hibernate 6 release ,you have to use these deprecated methods only,no other alternative to these deprecated methods in 5.3	
	@SuppressWarnings({ "unchecked","rawtypes"})
	public List<VehicleStatusVO> getVehicleStatusInfo(VehicleStatusVO request)
	{
		List<VehicleStatusVO> result = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		StringBuilder parkingSqlBuilder = new StringBuilder();
		if (!StringUtils.isEmpty(request.getDate()))
		{
			builder.append(" and s.packet_date='" + request.getDate()+"'");
		}
		if (!StringUtils.isEmpty(request.getStateId()))
		{
			builder.append(" and state_id=" + request.getStateId());
		}
		if (!StringUtils.isEmpty(request.getDistrictId()))
		{
			builder.append(" and district_id=" + request.getDistrictId());
		}
		if (!StringUtils.isEmpty(request.getCityId()))
		{
			builder.append(" and city_id=" + request.getCityId());
		}
		
		parkingSqlBuilder.append("select count(s.vehicle_parking_status) As count, s.vehicle_parking_status As statusType");
		parkingSqlBuilder.append(" from status_info s");
		parkingSqlBuilder.append(" JOIN travel_tracking tt ON tt.id = s.track_id");
		parkingSqlBuilder.append(" JOIN vehicle_details v ON s.vehicle_id = v.id");
		parkingSqlBuilder.append(" JOIN trip_details t ON t.vehicle_id = v.id");
		parkingSqlBuilder.append(" where 1=1");
		parkingSqlBuilder.append(builder.toString());
		parkingSqlBuilder.append(" GROUP BY s.vehicle_parking_status");

		Session session = (Session) manager.getDelegate();
		Query query =  session.createSQLQuery(parkingSqlBuilder.toString())
				   	  .addScalar("statusType",StringType.INSTANCE)
				      .addScalar("count",LongType.INSTANCE);
		query.setResultTransformer(Transformers.aliasToBean(VehicleStatusVO.class));
		VehicleStatusVO parkingResult = (VehicleStatusVO) query.uniqueResult();
		if (parkingResult != null) {
			parkingResult.setStatusType("Parking Status");
			parkingResult.setCityId(request.getCityId());
			parkingResult.setStateId(request.getStateId());
			parkingResult.setDistrictId(request.getDistrictId());
			result.add(parkingResult);
		}else {
			VehicleStatusVO parkingResultEmpty = new VehicleStatusVO();
			parkingResultEmpty.setStatusType("Parking Status");
			parkingResultEmpty.setCount(0L);
			result.add(parkingResultEmpty);
		}

		StringBuilder overSpeedSqlBuilder = new StringBuilder();
		overSpeedSqlBuilder.append("select count(s.over_speed) As count, s.over_speed As statusType");
		overSpeedSqlBuilder.append(" from status_info s");
		overSpeedSqlBuilder.append(" JOIN travel_tracking tt ON tt.id = s.track_id");
		overSpeedSqlBuilder.append(" JOIN vehicle_details v ON s.vehicle_id = v.id");
		overSpeedSqlBuilder.append(" JOIN trip_details t ON t.vehicle_id = v.id");
		overSpeedSqlBuilder.append(" where 1=1");
		overSpeedSqlBuilder.append(builder.toString());
		overSpeedSqlBuilder.append("AND s.over_speed='Over Speed'");
		overSpeedSqlBuilder.append(" GROUP BY s.over_speed");

		session = (Session) manager.getDelegate();
		query =  session.createSQLQuery(overSpeedSqlBuilder.toString())
				.addScalar("statusType",StringType.INSTANCE)
				.addScalar("count",LongType.INSTANCE);
		query.setResultTransformer(Transformers.aliasToBean(VehicleStatusVO.class));
		VehicleStatusVO overSpeed = (VehicleStatusVO) query.uniqueResult();
		if(overSpeed != null) {
			overSpeed.setCityId(request.getCityId());
			overSpeed.setStateId(request.getStateId());
			overSpeed.setDistrictId(request.getDistrictId());
			overSpeed.setStatusType("Over Speed");
			result.add(overSpeed);
		}else {
			VehicleStatusVO overSpeedEmpty = new VehicleStatusVO();
			overSpeedEmpty.setStatusType("Over Speed");
			overSpeedEmpty.setCount(0L);
			result.add(overSpeedEmpty);
		}

		//Vehicle Ideal
		StringBuilder idealStatusSqlBuilder = new StringBuilder();
		idealStatusSqlBuilder.append("select count(s.vehicle_ideal_status) As count, s.vehicle_ideal_status As statusType");
		idealStatusSqlBuilder.append(" from status_info s");
		idealStatusSqlBuilder.append(" JOIN travel_tracking tt ON tt.id = s.track_id");
		idealStatusSqlBuilder.append(" JOIN vehicle_details v ON s.vehicle_id = v.id");
		idealStatusSqlBuilder.append(" JOIN trip_details t ON t.vehicle_id = v.id");
		idealStatusSqlBuilder.append(" where 1=1");
		idealStatusSqlBuilder.append(builder.toString());
		idealStatusSqlBuilder.append("AND s.vehicle_ideal_status='Vehicle Ideal'");
		idealStatusSqlBuilder.append(" GROUP BY s.vehicle_ideal_status");
		session = (Session) manager.getDelegate();
		query =  session.createSQLQuery(idealStatusSqlBuilder.toString())
				.addScalar("statusType",StringType.INSTANCE)
				.addScalar("count",LongType.INSTANCE);
		query.setResultTransformer(Transformers.aliasToBean(VehicleStatusVO.class));
		VehicleStatusVO vehicleIdeal = (VehicleStatusVO) query.uniqueResult();
		if (vehicleIdeal != null) {
			vehicleIdeal.setStatusType("Vehicle Ideal");
			vehicleIdeal.setCityId(request.getCityId());
			vehicleIdeal.setStateId(request.getStateId());
			vehicleIdeal.setDistrictId(request.getDistrictId());
			result.add(vehicleIdeal);
		}else {
			VehicleStatusVO vehicleIdealEmpty = new VehicleStatusVO();
			vehicleIdealEmpty.setStatusType("Vehicle Ideal");
			vehicleIdealEmpty.setCount(0L);
			result.add(vehicleIdealEmpty);
		}

		//Route Deviation
		String routeDeviationSql =   "select count(t.id) As count\n" +
				" from route_deviation s\n" +
				" JOIN trip_details t ON t.id = s.trip_id \n" +
				" JOIN vehicle_details v ON v.id = t.vehicle_id  \n";

		session = (Session) manager.getDelegate();
		query =  session.createSQLQuery(routeDeviationSql)
				.addScalar("count",LongType.INSTANCE);
		query.setResultTransformer(Transformers.aliasToBean(VehicleStatusVO.class));
		VehicleStatusVO routeDeviation = (VehicleStatusVO) query.uniqueResult();
		if (routeDeviation != null) {
			routeDeviation.setStatusType("Route Deviation");
			routeDeviation.setCityId(request.getCityId());
			routeDeviation.setStateId(request.getStateId());
			routeDeviation.setDistrictId(request.getDistrictId());
			result.add(routeDeviation);
		}else {
			VehicleStatusVO routeDeviationEmpty = new VehicleStatusVO();
			routeDeviationEmpty.setStatusType("Route Deviation");
			routeDeviationEmpty.setCount(0L);
			result.add(routeDeviationEmpty);
		}

		return result;
	}
}

package com.abhaya.vehicle.tracking.utils;

public class QueryBuilderTest {

	public static void main(String[] args) {

		
		
		String sql =  " select ds.is_closed AS \"isClosed\" , count(ds.is_closed) AS \"count\", ds.event_type, "
				 + "v.state_id AS \"stateId\", v.district_id AS \"districtId\", v.city_id AS \"cityId\" ,ds.packet_date AS \"date\" " 
				 + " from distress_details ds "
				 + " JOIN trip_details t on t.id = ds.trip_id "
				 + " JOIN vehicle_details v ON t.vehicle_id = v.id "
				 + " JOIN driver_details d ON t.driver_id = d.id "
				 + " JOIN citizen_details c ON t.citizen_id = c.id "
				 + " JOIN state s ON v.state_id = s.id "
				 + " JOIN districts dis ON v.district_id = dis.id "
				 + " JOIN city city ON v.city_id = city.id "
				 + " group by ds.is_closed , ds.event_type, v.state_id, v.district_id, v.city_id, ds.packet_date "
					 + " having ds.event_type='WEB' AND v.state_id=1 AND v.district_id=2 AND v.city_id=2; " ;
		
		System.out.println("SQL:" + sql);

	}

}

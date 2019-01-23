INSERT INTO roles (ID, NAME,DESCRIPTION) VALUES (1, 'SUPER_ADMIN','Super Admin');
INSERT INTO roles (ID, NAME,DESCRIPTION) VALUES (2, 'ROLE_ADMIN', 'Admin');
INSERT INTO roles (ID, NAME,DESCRIPTION) VALUES (3, 'ROLE_USER', 'User');

INSERT INTO departments (ID, NAME,DESCRIPTION) VALUES (1, 'Transport', 'Transport');
INSERT INTO departments (ID, NAME,DESCRIPTION) VALUES (2, 'Police','Police');

INSERT INTO state (ID, CODE,NAME) VALUES (1,'AP', 'Andhra Pradesh');
    
INSERT INTO public.user_details(id,user_name, email_id, mobile_number, first_name,last_name,is_enabled,password,last_password_reset_date,created_date,department_id,state_id)
	VALUES (1,'superadmin', 'superadmin', '9985996541', 'superadmin', 'superadmin',true,'$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi',now(),now(),1,1);

INSERT INTO USER_AUTHORITY (USER_ID, ROLE_ID) VALUES (1, 1);

INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 1);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 2);


 create or replace view vehicle_driver_details_view as
 select du.id,
  		v.vehicle_name,
		v.rc_number,
		v.registration_date,
		o.owner_name,
		o.owner_contact_number,
		v.rc_expiry_date,
		v.is_owner,
		v.is_device_mapped,
		v.serial_number,
		v.created_date,
		d.driver_name,
		d.driving_licence_number,
		d.dl_expiry_date,
		d.contact_number as driver_contact_number,
		d.gender,
        d.rf_id,
        d.image,
        du.packet_date,
        du.packet_time,
		v.state_id,
     v.city_id,
     v.district_id
  from driver_duty_details du
  JOIN vehicle_details v ON du.vehicle_id = v.id
  LEFT JOIN owner_details o ON o.id = v.owner_id
  JOIN driver_details d ON du.driver_id = d.id
  JOIN state s ON s.id = v.state_id
JOIN districts di ON di.id = v.district_id
JOIN city ci ON ci.id = v.city_id;
# =========================================================================================

create or replace view trip_details_view as
  select 
  		 t.id, 
		 close_time, 
		 desti_lat_lang, 
		 is_trip_closed, 
		 remarks, 
		 request_id, 
		 request_time, 
		 source_lat_lang, 
		 travel_mode, 
		 d.driver_name,
		 d.driving_licence_number,
		 d.contact_number as driver_contact_number,
		 d.rf_id,
		 v.rc_number,
		 c.mobile_number as citizen_mobile_number,
		 v.serial_number,
		 v.state_id,
	     v.city_id,
	     v.district_id,
	     t.source_location,
	     t.desti_location,
	     COALESCE(ci.name, ' ') || ' '||COALESCE(di.name, '') || ' '||COALESCE(s.name, '') as vehicle_address,
	     o.owner_name,
	     o.owner_contact_number,
	     COALESCE(ci1.name, ' ') || ' '||COALESCE(di1.name, '') || ' '||COALESCE(s1.name, '') as driver_address
 from trip_details t
 JOIN vehicle_details v ON v.id = t.vehicle_id 
  JOIN owner_details o ON o.id = v.owner_id
 JOIN driver_details d  ON d.id = t.driver_id
 JOIN citizen_details c ON c.id = t.citizen_id
 JOIN state s ON s.id = v.state_id
 JOIN districts di ON di.id = v.district_id
 JOIN city ci ON ci.id = v.city_id
 JOIN state s1 ON s1.id = d.state_id
 JOIN districts di1 ON di1.id = d.district_id
 JOIN city ci1 ON ci1.id = d.city_id


# =========================================================================================
 create or replace view distress_details_view as
 select ds.id,
       ds.created_date,
       ds.distress_location,
       ds.packet_time,
       ds.packet_date,
       ds.is_closed,
       t.request_id as trip_request_id,
       v.serial_number,
       v.rc_number,
       d.driving_licence_number as dl_number,
       d.driver_name,
       d.contact_number as driver_mobile_number,
       d.rf_id,
       c.mobile_number as citizen_mobile_number,
       t.request_time as trip_request_time,
       t.id as trip_id,
       v.state_id,
     v.city_id,
     v.district_id,
       ds.event_type
 from distress_details ds
 JOIN trip_details t on t.id = ds.trip_id
 JOIN vehicle_details v ON t.vehicle_id = v.id
 JOIN driver_details d ON t.driver_id = d.id
 JOIN citizen_details c ON t.citizen_id = c.id
JOIN state s ON s.id = v.state_id
JOIN districts di ON di.id = v.district_id
JOIN city ci ON ci.id = v.city_id;


  
# =========================================================================================

create or replace view vehicle_live_status_view as
	select t.id,
       t.serial_number,
       t.latitude,
       t.langitude,
       v.rc_number,
       v.state_id,
       v.city_id,
       v.district_id,
       d.name as district_name,
       c.name as city_name
 from vehicle_details v 
 JOIN travel_tracking t ON t.serial_number = v.serial_number
 JOIN state s ON s.id = v.state_id
 JOIN districts d ON d.id = v.district_id
 JOIN city c ON c.id = v.city_id;
 
 # =========================================================================================

create or replace view panic_summary_view as
select max(ds.id) AS "id", ds.is_closed , count(ds.is_closed) , ds.event_type , 
v.state_id , v.district_id , 
v.city_id  ,ds.packet_date   
from distress_details ds  
JOIN trip_details t on t.id = ds.trip_id  
JOIN vehicle_details v ON t.vehicle_id = v.id  
JOIN driver_details d ON t.driver_id = d.id  
JOIN citizen_details c ON t.citizen_id = c.id  
JOIN state s ON v.state_id = s.id  
JOIN districts dis ON v.district_id = dis.id  
JOIN city city ON v.city_id = city.id  
group by ds.is_closed , ds.event_type, v.state_id, v.district_id, v.city_id, ds.packet_date;


 # =========================================================================================
 
create or replace view route_deviation_details_view as
select rd.id,
 		rd.deviation_time,
 		rd.location,
 		rd.created_date,
 		rd.geo_location,
 		t.id as trip_id,
		t.request_id,
		t.source_lat_lang,
        t.desti_lat_lang,
        t.source_location,
        t.desti_location,
		v.rc_number,
		v.serial_number,
		v.state_id,
		v.city_id,
		v.district_id,
		d.driver_name,
		d.driving_licence_number as dl_num,
		d.contact_number as driver_contact_number,
		d.rf_id,
		od.owner_name,
		od.owner_contact_number,
		cd.mobile_number as citizen_contact_number
  from route_deviation rd
  JOIN trip_details t ON t.id = rd.trip_id
  JOIN vehicle_details v ON t.vehicle_id = v.id
  JOIN driver_details d ON t.driver_id = d.id
  JOIN owner_details od ON v.owner_id = od.id
  JOIN citizen_details cd ON cd.id = t.citizen_id
  JOIN state s ON s.id = v.state_id
  JOIN districts di ON di.id = v.district_id
  JOIN city ci ON ci.id = v.city_id;

 # =========================================================================================

create or replace view device_communication_view as
 select dc.id,
       dc.created_date,
       dc.serial_number,
       v.rc_number,
       dc.imei_number,
	   dc.latitude,
	   dc.langitude,
	   dc.location,
	   dc.packet_date,
       dc.packet_time,
       dc.prev_latitude,
	   dc.prev_langitude,
	   dc.prev_packet_date,
	   dc.prev_packet_time,
	   dc.status,
	   dc.movement,
	   dc.battery_status,
	   dc.ignition_status,
	   dc.engine_status,
	   dc.tamper_status,
	   dc.panic_button_status,
	   s.name As state_name,
	   di.name As district_name,
	   ci.name As city_name,
	   s.id As state_id,
	   di.id As district_id,
	   ci.id As city_id
 from device_communication dc
 JOIN vehicle_details v ON v.serial_number = dc.serial_number
 JOIN state s ON s.id = v.state_id
 JOIN districts di ON di.id = v.district_id
 JOIN city ci ON ci.id = v.city_id;
 
 # =========================================================================================

create or replace view watch_vehicle_view as
 select wv.id,
       wv.created_date,
	   wv.vehicle_id,
	   v.rc_number,
	   dc.serial_number,
	   dc.imei_number,
	   dc.latitude,
	   dc.langitude,
	   dc.location,
	   dc.packet_date,
       dc.packet_time,
       dc.prev_latitude,
	   dc.prev_langitude,
	   dc.prev_packet_date,
	   dc.prev_packet_time,
	   dc.status,
	   dc.movement,
	   dc.battery_status,
	   dc.ignition_status,
	   dc.engine_status,
	   dc.tamper_status,
	   dc.panic_button_status,
	   s.name As state_name,
	   di.name As district_name,
	   ci.name As city_name,
	   s.id As state_id,
	   di.id As district_id,
	   ci.id As city_id
 from watch_vehicle wv
 JOIN vehicle_details v ON v.id = wv.vehicle_id
 JOIN device_communication dc ON dc.serial_number = v.serial_number
 JOIN state s ON s.id = v.state_id
 JOIN districts di ON di.id = v.district_id
 JOIN city ci ON ci.id = v.city_id;
 
 # =========================================================================================

create or replace view modem_details_view as
 select md.id,
       md.created_date,
       md.imei_number,
	   md.imsi_number,
	   md.id_address,
	   v.rc_number,
	   md.serial_number,
	   md.signal_strength,
	   md.sim_number,
       md.updated_date,
       md.version,
	   md.mobile_number,
	   s.name As state_name,
	   di.name As district_name,
	   ci.name As city_name,
	   s.id As state_id,
	   di.id As district_id,
	   ci.id As city_id
 from modem_details md
 JOIN vehicle_details v ON v.serial_number = md.serial_number
 JOIN state s ON s.id = v.state_id
 JOIN districts di ON di.id = v.district_id
 JOIN city ci ON ci.id = v.city_id;
  
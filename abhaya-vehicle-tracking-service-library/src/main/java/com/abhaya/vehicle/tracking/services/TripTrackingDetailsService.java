package com.abhaya.vehicle.tracking.services;

import java.util.List;

import com.abhaya.vehicle.tracking.data.repos.*;
import com.abhaya.vehicle.tracking.events.ReadTripDetailsSetEvent;
import com.abhaya.vehicle.tracking.utils.TripSummaryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.abhaya.vehicle.tracking.data.model.TravelTracking;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadTravelTrackingSetEvent;
import com.abhaya.vehicle.tracking.util.ObjectMapperUtils;
import com.abhaya.vehicle.tracking.utils.VehicleDashboardVO;
import com.abhaya.vehicle.tracking.utils.VehicleLiveLocByDistrictVO;
import com.abhaya.vehicle.tracking.vos.TravelTrackingVO;

public interface TripTrackingDetailsService 
{
	public PageReadEvent<VehicleDashboardVO> getLiveVehicles(ReadTravelTrackingSetEvent request);
	public PageReadEvent<TravelTrackingVO> readTripTrackingData(ReadTravelTrackingSetEvent request);
	public PageReadEvent<TravelTrackingVO> readLiveTrackingData(ReadTravelTrackingSetEvent request);
	public TravelTrackingVO readLiveVehicleLocation(ReadTravelTrackingSetEvent request);

	public List<VehicleLiveLocByDistrictVO> getLatestLiveLocCountByDist(ReadTravelTrackingSetEvent request);

    List<TripSummaryVO> getTripsSummary(ReadTripDetailsSetEvent request);

	List<TripSummaryVO> getRouteDeviatedAndPanicSummary(ReadTripDetailsSetEvent request);

	@Service
	public class impl implements TripTrackingDetailsService
	{
		@Autowired private TravelTrackingRepository repository;
		@Autowired private SQLRepository sqlRepository;
		@Autowired private VehicleLiveLocationReportSQLRepository vehicleLiveLocationReportSQLRepository;
		@Autowired private TripSummaryReportSQLRepository tripSummaryReportSQLRepository;

		@Override
		public PageReadEvent<TravelTrackingVO> readTripTrackingData(ReadTravelTrackingSetEvent request) 
		{
			TripTrackingDetailsSpecifications detailsSpecifications = new TripTrackingDetailsSpecifications(request.getSerialNumber(),request.getStartDate(),request.getTripId(),request.getId(),request.getSearchValue(),request.getSearchDate());
			@SuppressWarnings("deprecation")
			Page<TravelTracking> dbContent = repository.findAll(detailsSpecifications,TripTrackingDetailsSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), 
					request.getPageable().getPageSize(),new Sort(new Order(Direction.DESC, "packetDate"),new Order(Direction.DESC, "packetTime"))));

			List<TravelTrackingVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), TravelTrackingVO.class);
			Page<TravelTrackingVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}
		
		@Override
		public PageReadEvent<TravelTrackingVO> readLiveTrackingData(ReadTravelTrackingSetEvent request) 
		{
			TripTrackingDetailsSpecifications detailsSpecifications = new TripTrackingDetailsSpecifications(request.getSerialNumber(),request.getStartDate(),request.getTripId(),request.getId(),request.getSearchValue(),request.getSearchDate());
			Page<TravelTracking> dbContent = repository.findAll(detailsSpecifications,TripTrackingDetailsSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), 
					request.getPageable().getPageSize()));

			List<TravelTrackingVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), TravelTrackingVO.class);
			Page<TravelTrackingVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}

		@Override
		public TravelTrackingVO readLiveVehicleLocation(ReadTravelTrackingSetEvent request) {
			List<TravelTracking> travelTrackings = repository.getBySerialNumber(request.getSerialNumber());
			if (!travelTrackings.isEmpty()) {
				TravelTracking tracking = travelTrackings.stream().findFirst().get();
				TravelTrackingVO travelTrackingVO = ObjectMapperUtils.map(tracking, TravelTrackingVO.class);
				return travelTrackingVO;
			}
			return null;
		}

		@Override
		public List<VehicleLiveLocByDistrictVO> getLatestLiveLocCountByDist(ReadTravelTrackingSetEvent request) {
			return vehicleLiveLocationReportSQLRepository.getVehicleStatusInfo(request.getDistrictId(),request.getSearchDate());
		}

		@Override
		public List<TripSummaryVO> getTripsSummary(ReadTripDetailsSetEvent request) {
			return tripSummaryReportSQLRepository.tripSummaryReport(request.getSearchValue(), request.getIsDistrictWise(), request.getDistrictId());
		}

		@Override
		public List<TripSummaryVO> getRouteDeviatedAndPanicSummary(ReadTripDetailsSetEvent request) {
			return tripSummaryReportSQLRepository.routeDeviatedAndPanicSummaryReport(request.getSearchValue(), request.getIsDistrictWise(), request.getDistrictId());
		}

		@Override
		public PageReadEvent<VehicleDashboardVO> getLiveVehicles(ReadTravelTrackingSetEvent request) 
		{
			VehicleDashboardVO vo = VehicleDashboardVO.builder()
					.cityId(request.getCityId())
					.districtId(request.getDistrictId())
					.stateId(request.getStateId())
					.districtName(request.getDistrictName())
					.cityName(request.getCityName())
					.build();
			List<VehicleDashboardVO> list = sqlRepository.getLiveVehicle(vo);
			Page<VehicleDashboardVO> page = new PageImpl<>(list,request.getPageable(),list != null ? list.size() : 0);
			return new PageReadEvent<>(page);
		}
	}
}

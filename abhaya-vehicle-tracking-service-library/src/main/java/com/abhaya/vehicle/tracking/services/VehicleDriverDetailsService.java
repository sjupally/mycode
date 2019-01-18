package com.abhaya.vehicle.tracking.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.abhaya.vehicle.tracking.data.model.VehicleDriverDetailsView;
import com.abhaya.vehicle.tracking.data.repos.VehicleDriverDetailsSpecifications;
import com.abhaya.vehicle.tracking.data.repos.VehicleDriverDetailsViewRepository;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadDriverDutyDetailsSetEvent;
import com.abhaya.vehicle.tracking.util.NepheleValidationUtils;
import com.abhaya.vehicle.tracking.vos.VehicleDriverDetailsVO;

public interface VehicleDriverDetailsService 
{
	
	public PageReadEvent<VehicleDriverDetailsVO> readVehicleDriverData(ReadDriverDutyDetailsSetEvent request);

	@Service
	public class impl implements VehicleDriverDetailsService
	{
		@Autowired private VehicleDriverDetailsViewRepository repository;

		@Override
		public PageReadEvent<VehicleDriverDetailsVO> readVehicleDriverData(ReadDriverDutyDetailsSetEvent request) 
		{
			Page<VehicleDriverDetailsView> dbContent = repository.findAll(new VehicleDriverDetailsSpecifications(request.getSearchValue(),request.getRcNumber(),request.getPacketDate(), request.getDistrictId(), request.getCityId(),request.getDate()),VehicleDriverDetailsSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize()));

			List<VehicleDriverDetailsVO> content = new ArrayList<>();
			for (VehicleDriverDetailsView detailsView : NepheleValidationUtils.nullSafe(dbContent)) 
			{
				VehicleDriverDetailsVO rec = VehicleDriverDetailsVO.builder()
						.driverContactNumber(detailsView.getDriverContactNumber())
						.dlExpiryDate(detailsView.getDlExpiryDate())
						.dlNumber(detailsView.getDlNumber())
						.driverName(detailsView.getDriverName())
						.gender(detailsView.getGender())
						.ownerContactNumber(detailsView.getOwnerContactNumber())
						.ownerName(detailsView.getOwnerName())
						.rcExpiryDate(detailsView.getRcExpiryDate())
						.rcNumber(detailsView.getRcNumber())
						.registrationDate(detailsView.getRegistrationDate())
						.id(detailsView.getId())
						.vehicleName(detailsView.getVehicleName())
						.rfId(detailsView.getRfId())
						.serialNumber(detailsView.getSerialNumber())
						.packetDate(detailsView.getPacketDate())
						.packetTime(detailsView.getPacketTime())
						.image(detailsView.getImage())
					.build();
				content.add(rec); 
				break;
			}
			Page<VehicleDriverDetailsVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}
	}
}

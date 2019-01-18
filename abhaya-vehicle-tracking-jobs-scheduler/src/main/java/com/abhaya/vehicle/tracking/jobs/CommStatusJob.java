package com.abhaya.vehicle.tracking.jobs;

import java.sql.Date;
import java.text.ParseException;
import java.time.Duration;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhaya.vehicle.tracking.data.model.DeviceCommunication;
import com.abhaya.vehicle.tracking.data.repos.DeviceCommunicationRepository;
import com.abhaya.vehicle.tracking.util.DateUitls;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommStatusJob implements Job 
{

	
	@Autowired
    private DeviceCommunicationRepository deviceCommunicationRepository;
	
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException 
	{
		log.info("Inside Comm status JOB::: ");
		List<DeviceCommunication> communicationStatusList = deviceCommunicationRepository.findAll();
		if (communicationStatusList != null && communicationStatusList.size() > 0)
		{
			for (DeviceCommunication item : communicationStatusList)
			{
				try 
				{
					Date currentDate = new Date(DateUitls.getCurrentSystemTimestamp().getTime());
					Date dbDate = new Date(DateUitls.getSqlTimestampFromString(item.getPacketDate()+ " "+item.getPacketTime(),"dd/MM/yyyy hh:mm:ss").getTime());
					long days = Duration.between(currentDate.toLocalDate().atStartOfDay(),dbDate.toLocalDate().atStartOfDay()).toDays();
					log.info("Days difference ::: "+days);
					if (days == 0)
					{
						log.info("Time difference ::: "+DateUitls.timeDifferenceInHours(DateUitls.getSqlTimestampFromString(item.getPacketDate()+ " "+item.getPacketTime(),"dd/MM/yyyy hh:mm:ss").getTime(),dbDate.getTime()));
						if (DateUitls.timeDifferenceInHours(DateUitls.getCurrentSystemTimestamp().getTime(),DateUitls.getSqlTimestampFromString(item.getPacketDate()+ " "+item.getPacketTime(),"dd/MM/yyyy hh:mm:ss").getTime()) >= 1)
						{
							log.info("Updating status for Serial Number::: "+item.getSerialNumber());
							item.setStatus("NotCommunicating"); 
							deviceCommunicationRepository.save(item);
						}
					}
					else
					{
						item.setStatus("NotCommunicating"); 
						deviceCommunicationRepository.save(item);
					}
				} 
				catch (ParseException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
}


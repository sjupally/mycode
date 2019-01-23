package com.abhaya.vehicle.tracking.services;


import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadRouteDeviationSetEvent;
import com.abhaya.vehicle.tracking.vos.RouteDeviationVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public interface RTIIntegrationService {
    String getDlDetailsWithDlNo(String dlNo);

    @Slf4j
    @Service
    public class impl implements RTIIntegrationService {
        @Autowired
        RestTemplate restTemplate;

        @Value("${rti.url}")
        private String rtiUrl;

        @Value("${rti.authKey}")
        private String rtiAuthKey;


        @Override
        public String getDlDetailsWithDlNo(String dlNo) {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("Authorization", rtiAuthKey);
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            String url = rtiUrl + "dl/getDlDetailsWithDlNo?dlNo=" + dlNo;
            return restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
        }

    }
}

package com.abhaya.vehicle.tracking.util;

import java.util.ArrayList;
import java.util.List;

import com.abhaya.vehicle.tracking.vos.UserLevelVO;

public enum UserLevelEnum {
	
	STATE("State", 1l), 
	DISTRICT("District", 2l),
	CITY("City", 3l);

    private final String key;
    private final Long value;

    UserLevelEnum(String key, Long value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    public Long getValue() {
        return value;
    }
    
    
    
    private static List<UserLevelVO> userLevelVO;

   
    

    public static List<UserLevelVO> getLevels(long level){
    	userLevelVO = new ArrayList<>();
        for(UserLevelEnum s : values()){
        	if(s.value >= level) {
        		userLevelVO.add(new UserLevelVO(s.value, s.key));
        	}
        }
        return userLevelVO;
    }


}

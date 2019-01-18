package com.abhaya.vehicle.tracking.vos;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventsVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String eventType;
	private String eventName;
	private String value;
	private Timestamp createdDate;
}

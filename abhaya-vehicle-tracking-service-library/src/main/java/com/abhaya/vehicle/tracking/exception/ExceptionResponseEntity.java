package com.abhaya.vehicle.tracking.exception;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponseEntity extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 6210985214828895610L;
	private String message;
	private String code;
	private String exceptionType;
	private String uri;
	
	
}

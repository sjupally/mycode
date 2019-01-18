package com.abhaya.vehicle.tracking.exception;

import javax.persistence.EntityNotFoundException;

public class ResourceNotFoundException extends EntityNotFoundException {
	private static final long serialVersionUID = 1L;
	public ResourceNotFoundException(String message) {
		super(message);
		
	}
	
	public ResourceNotFoundException(String entity,Long id) {
		super("Record for "+entity+" with "+entity+" id :"+id+" is not found");
		
	}
	
	public ResourceNotFoundException(String entity,String code) {
		super("Record for "+entity+" with "+entity+" code :"+code+" is not found");
		
	}
}

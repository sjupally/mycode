package com.abhaya.vehicle.tracking.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class EntityReadEvent<T> {
	private final boolean found;
	private final T entity;

	public EntityReadEvent(T entity) {
		this.found = (null != entity);
		this.entity = entity;
	}

	private EntityReadEvent() {
		this(null);
	}

	public static <T> EntityReadEvent<T> notFound() {
		return new EntityReadEvent<T>();
	}
}

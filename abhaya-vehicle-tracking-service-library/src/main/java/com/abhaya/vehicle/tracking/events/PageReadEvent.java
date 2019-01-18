package com.abhaya.vehicle.tracking.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@Getter
@ToString
@EqualsAndHashCode
public class PageReadEvent<T> {
	private final boolean found;
	private final Page<T> page;

	public PageReadEvent(Page<T> page) {
		this.found = (null != page);
		this.page = page;
	}

	private PageReadEvent() {
		this(null);
	}

	public static <T> PageReadEvent<T> notFound() {
		return new PageReadEvent<T>();
	}
}

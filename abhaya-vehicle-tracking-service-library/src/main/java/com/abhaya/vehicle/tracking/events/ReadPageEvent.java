package com.abhaya.vehicle.tracking.events;

import lombok.*;
import org.springframework.data.domain.Pageable;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ReadPageEvent<T extends ReadPageEvent<T>> {

	private Pageable pageable;

	public T setPageable(Pageable pageable) {
		this.pageable = pageable;

		@SuppressWarnings("unchecked")
		T ret = (T)this;
		return ret;
	}
}

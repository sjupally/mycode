package com.abhaya.vehicle.tracking.util;

import java.util.Collections;

/**
 * Utility class  which provides validation on various type of objects.
 * @author Vishnu Awasthi
 *
 * @param <T>
 */
public class NepheleValidationUtils<T> {
	
	/**
	 * This method checks whether an Iterable<T> iterable is null or not, If it finds it as null simply change it to empty
	 * @param iterable
	 * @return
	 */
	public static <T> Iterable<T> nullSafe(Iterable<T> iterable) {
	    return iterable == null ? Collections.<T>emptyList() : iterable;
	}
	
}

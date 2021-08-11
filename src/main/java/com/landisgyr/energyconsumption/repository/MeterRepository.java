package com.landisgyr.energyconsumption.repository;

import com.landisgyr.energyconsumption.exception.MeterRepositoryException;
import com.landisgyr.energyconsumption.model.Meter;

public interface MeterRepository {
	/**
	 * Finds Meter by meterNo.
	 * @param meterNo alphanumeric meter number.
	 * @return Returns the Meter object if found, returns null if not found.
	 */
	Meter findById(String meterNo);
	
	/**
	 * Persists the Meter object. Replaces any existing object.
	 * @param meter
	 * @return The saved Meter.
	 * @throws MeterRepositoryException Throws exception if the meter object is null or meter number is blank in the object.
	 */
	Meter save(Meter meter) throws MeterRepositoryException;
}

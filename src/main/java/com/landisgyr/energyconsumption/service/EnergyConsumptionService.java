package com.landisgyr.energyconsumption.service;

import com.landisgyr.energyconsumption.exception.BillingCalculationException;
import com.landisgyr.energyconsumption.exception.MeterRepositoryException;
import com.landisgyr.energyconsumption.model.Meter;

public interface EnergyConsumptionService {

	/**
	 * Calculater the billing given consumption, microgeneration and unit price.
	 * @throws BillingCalculationException Exception is thrown if the parameters are null.
	 */
	Double calculateBilling(Long consumption, Long microgeneration, Double unit) throws BillingCalculationException;

	/**
	 * Finds the meter by number.
	 * @param meterNumber alphanumeric number.
	 * @return Meter object or null if not found.
	 * @throws MeterRepositoryException IF the meterNumber is blank.
	 */
	Meter findById(String meterNumber) throws MeterRepositoryException;

	/**
	 * Creates and persists a new Meter using the meterNumber, will set consumption and microgeneration to zero.
	 * @param meterNumber alphanumeric number.
	 * @return The saved meter.
	 * @throws MeterRepositoryException The Exception is throw if meterNumber is blank. Blank means "", " " or null.
	 */
	Meter save(String meterNumber) throws MeterRepositoryException;

}
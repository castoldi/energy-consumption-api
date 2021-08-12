package com.landisgyr.energyconsumption.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.landisgyr.energyconsumption.exception.BillingCalculationException;
import com.landisgyr.energyconsumption.exception.MeterRepositoryException;
import com.landisgyr.energyconsumption.model.Meter;
import com.landisgyr.energyconsumption.repository.MeterRepository;

@Service
public class DefaultEnergyConsumptionService implements EnergyConsumptionService {
	
	private final MeterRepository meterRepo;

	public DefaultEnergyConsumptionService(MeterRepository meterRepo) {
		this.meterRepo = meterRepo;
	}

	@Override
	public Double calculateBilling(Long consumption, Long microgeneration, Double unit) throws BillingCalculationException {
		if (consumption == null || microgeneration == null || unit == null) {
			throw new BillingCalculationException("Cannot calculate billing due to invalid input data.");
		}
		
		return (consumption - microgeneration) * unit;
	}

	@Override
	public Meter findMeterById(String meterNumber) throws MeterRepositoryException {
		if (StringUtils.isBlank(meterNumber)) {
			return null;
		}

		return meterRepo.findById(meterNumber);
	}

	@Override
	public Meter saveMeter(String meterNumber) throws MeterRepositoryException {
		if (StringUtils.isBlank(meterNumber)) {
			throw new MeterRepositoryException("Cannot save when meter number is empty.");
		}
		
		Meter meter = new Meter(meterNumber, 0L, 0L);
		meterRepo.save(meter);
		
		return meter;
	}
	
}

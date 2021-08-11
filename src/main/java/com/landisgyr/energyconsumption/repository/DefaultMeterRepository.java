package com.landisgyr.energyconsumption.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.landisgyr.energyconsumption.exception.MeterRepositoryException;
import com.landisgyr.energyconsumption.model.Meter;

@Component
public class DefaultMeterRepository implements MeterRepository {
	private Map<String, Meter> meterData = new ConcurrentHashMap<>();

	@Override
	public Meter findById(String meterNo) throws MeterRepositoryException {
		if (StringUtils.isBlank(meterNo)) {
			throw new MeterRepositoryException("The meterNo is mandatory.");
		}

		return meterData.get(meterNo);
	}

	@Override
	public Meter save(Meter meter) throws MeterRepositoryException {
		if (meter == null || StringUtils.isBlank(meter.getMeterNo())) {
			throw new MeterRepositoryException("Cannot save null objects.");
		}
		
		meterData.put(meter.getMeterNo(), meter);
		return meterData.get(meter.getMeterNo());
	}
}
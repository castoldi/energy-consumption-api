package com.landisgyr.energyconsumption.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.landisgyr.energyconsumption.exception.MeterRepositoryException;
import com.landisgyr.energyconsumption.model.Meter;

@Component
public class DefaultMeterRepository implements MeterRepository {
	private Map<String, Meter> meterData = new HashMap<>();

	@Override
	public synchronized Meter findById(String meterNo) {
		return meterData.get(meterNo);
	}

	@Override
	public synchronized Meter save(Meter meter) throws MeterRepositoryException {
		if (meter == null || StringUtils.isBlank(meter.getMeterNo())) {
			throw new MeterRepositoryException("Cannot save null objects.");
		}
		
		meterData.put(meter.getMeterNo(), meter);
		return meterData.get(meter.getMeterNo());
	}
}
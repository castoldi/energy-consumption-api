package com.landisgyr.energyconsumption.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.landisgyr.energyconsumption.model.Meter;

/**
 * Info object to be used in the REST Endpoints.
 */
@JsonInclude(Include.NON_NULL)
public class MeterInfo extends Meter {

	public MeterInfo(Meter meter) {
		super();
		if (meter != null) {
			setMeterNo(meter.getMeterNo());
			setConsumption(meter.getConsumption());
			setMicrogeneration(meter.getMicrogeneration());
			setCash(meter.getCash());
		}
	}

	public MeterInfo(String meterNo, Double cash) {
		super(meterNo, cash);
	}

	public MeterInfo(String meterNo, Long consumption, Long microgeneration) {
		super(meterNo, consumption, microgeneration);
	}

}

package com.landisgyr.energyconsumption.rest;

import com.landisgyr.energyconsumption.model.Meter;

public class MeterResponse {
	private MeterInfo meter;

	public MeterResponse(Meter meter) {
		super();
		this.meter = new MeterInfo(meter);
	}

	public MeterInfo getMeter() {
		return meter;
	}

	public void setMeter(MeterInfo meter) {
		this.meter = meter;
	}
}
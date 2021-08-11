package com.landisgyr.energyconsumption.rest;

import com.landisgyr.energyconsumption.model.Meter;

public class MeterResponse {
	private Meter meter;

	public MeterResponse(Meter meter) {
		super();
		this.meter = meter;
	}

	public Meter getMeter() {
		return meter;
	}

	public void setMeter(Meter meter) {
		this.meter = meter;
	}
}
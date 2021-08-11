package com.landisgyr.energyconsumption.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Meter {
	private String meterNo;
	private Long consumption;
	private Long microgeneration;
	private Double cash;

	public Meter(String meterNo, Double cash) {
		this.meterNo = meterNo;
		this.cash = cash;
	}

	public Meter(String meterNo, Long consumption, Long microgeneration) {
		this.meterNo = meterNo;
		this.consumption = consumption;
		this.microgeneration = microgeneration;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public Long getConsumption() {
		return consumption;
	}

	public void setConsumption(Long consumption) {
		this.consumption = consumption;
	}

	public Long getMicrogeneration() {
		return microgeneration;
	}

	public void setMicrogeneration(Long microgeneration) {
		this.microgeneration = microgeneration;
	}

	public Double getCash() {
		return cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}
}

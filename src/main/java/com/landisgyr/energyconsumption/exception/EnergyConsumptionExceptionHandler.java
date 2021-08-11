package com.landisgyr.energyconsumption.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EnergyConsumptionExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(BillingCalculationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public void handleBillingCalculationException(BillingCalculationException exception) {
		logger.error("Billing calculation exception", exception);
	}

	@ExceptionHandler(MeterRepositoryException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public void handleMeterRepositoryException(MeterRepositoryException exception) {
		logger.error("Billing calculation exception", exception);
	}
}

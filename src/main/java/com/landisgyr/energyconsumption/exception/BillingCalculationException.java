package com.landisgyr.energyconsumption.exception;

public class BillingCalculationException extends Exception {
	private static final long serialVersionUID = 1L;

	public BillingCalculationException() {
		super();
	}

	public BillingCalculationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BillingCalculationException(String message, Throwable cause) {
		super(message, cause);
	}

	public BillingCalculationException(String message) {
		super(message);
	}

	public BillingCalculationException(Throwable cause) {
		super(cause);
	}
}

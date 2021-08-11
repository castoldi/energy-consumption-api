package com.landisgyr.energyconsumption.exception;

public class MeterRepositoryException extends Exception {
	private static final long serialVersionUID = 1L;

	public MeterRepositoryException() {
		super();
	}

	public MeterRepositoryException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MeterRepositoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public MeterRepositoryException(String message) {
		super(message);
	}

	public MeterRepositoryException(Throwable cause) {
		super(cause);
	}
}

package com.landisgyr.energyconsumption.rest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.landisgyr.energyconsumption.EnergyConsumptionConstants;
import com.landisgyr.energyconsumption.exception.BillingCalculationException;
import com.landisgyr.energyconsumption.exception.MeterRepositoryException;
import com.landisgyr.energyconsumption.model.Meter;
import com.landisgyr.energyconsumption.service.EnergyConsumptionService;

/**
 * REST Controller for the /event endpoint.
 */
@RestController
@RequestMapping(value = EnergyConsumptionConstants.EVENT_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private final EnergyConsumptionService energyConsumptionService;

	public EventController(EnergyConsumptionService energyConsumptionService) {
		this.energyConsumptionService = energyConsumptionService;
	}

	/**
	 * Content based routing. This endpoint will process the payload given the type. Valid types are:<br>
	 * "import": Creates a new meter. <br>
	 * "push": Sets consumption and microgeneration.<br>
	 * "billing": Calculates billing based on unit price.
	 * 
	 * @param meterRequest
	 * @return Meter object in case of "import" and "push". The cash calculation in case of "billing".<br> 
	 * Can also return NOT_FOUND if MeterNumber is not found or BAD_REQUEST if the parameters are invalid.
	 * @throws BillingCalculationException When data is invalid to calculate the billing. Applies only for type="billing".
	 * @throws MeterRepositoryException When data is invalid to persist the object. Applies only for type="import".
	 */
	@PostMapping
	public ResponseEntity<Object> processEvent(@RequestBody MeterRequest meterRequest) throws BillingCalculationException, MeterRepositoryException {
		logger.info("Event received. {}", meterRequest);
		
		if (StringUtils.isBlank(meterRequest.getMeterNumber())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Meter meter = energyConsumptionService.findMeterById(meterRequest.getMeterNumber());
		ResponseEntity<Object> response = null;

		if (EnergyConsumptionConstants.EVENT_TYPE_IMPORT.equals(meterRequest.getType())) {
			response = importMeter(meterRequest);

		} else if (EnergyConsumptionConstants.EVENT_TYPE_PUSH.equals(meterRequest.getType())) {
			response = pushMeter(meterRequest, meter);

		} else if (EnergyConsumptionConstants.EVENT_TYPE_BILLING.equals(meterRequest.getType())) {
			response = billingMeter(meterRequest, meter);
		} else {
			response = new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}

		return response;
	}

	private ResponseEntity<Object> importMeter(MeterRequest meterRequest) throws MeterRepositoryException {
		logger.info("Creating meterNumber {}.", meterRequest.getMeterNumber());
		
		Meter meterSaved = energyConsumptionService.saveMeter(meterRequest.getMeterNumber());

		return new ResponseEntity<>(new MeterResponse(meterSaved), HttpStatus.CREATED);
	}

	private ResponseEntity<Object> pushMeter(MeterRequest meterRequest, Meter meter) {
		logger.info("Pushing energy data consumption for meterNumber={}.", meterRequest.getMeterNumber());

		if (meter == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		meter.setConsumption(meterRequest.getActiveEnergy());
		meter.setMicrogeneration(meterRequest.getInjectedEnergy());

		MeterResponse meterResponse = new MeterResponse(meter);
		return new ResponseEntity<>(meterResponse, HttpStatus.CREATED);
	}
	
	private ResponseEntity<Object> billingMeter(MeterRequest meterRequest, Meter meter) throws BillingCalculationException {
		logger.info("Billing energy consumption for meterNumber={}.", meterRequest.getMeterNumber());

		if (meter == null) {
			return new ResponseEntity<>("0", HttpStatus.NOT_FOUND);
		}

		Double cash = energyConsumptionService.calculateBilling(meter.getConsumption(), meter.getMicrogeneration(), meterRequest.getUnit());
		MeterResponse meterResponse = new MeterResponse(new Meter(meter.getMeterNo(), cash));
		return new ResponseEntity<>(meterResponse, HttpStatus.CREATED);
	}

}
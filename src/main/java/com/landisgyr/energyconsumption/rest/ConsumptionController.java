package com.landisgyr.energyconsumption.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.landisgyr.energyconsumption.EnergyConsumptionConstants;
import com.landisgyr.energyconsumption.model.Meter;
import com.landisgyr.energyconsumption.repository.MeterRepository;

@RestController
@RequestMapping
public class ConsumptionController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private final MeterRepository meterRepo;

	public ConsumptionController(MeterRepository meterRepo) {
		this.meterRepo = meterRepo;
	}

	@GetMapping(value = EnergyConsumptionConstants.CONSUMPTION_ENDPOINT)
	public ResponseEntity<String> getConsumption(@RequestParam(EnergyConsumptionConstants.METER_NUMBER) String meterNumber) {
		Meter meter = meterRepo.findById(meterNumber);
		if (meter == null) {
			return new ResponseEntity<String>("0", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String>(String.valueOf(meter.getConsumption()), HttpStatus.OK);
	}

	@GetMapping(value = EnergyConsumptionConstants.MICROGENERATION_ENDPOINT)
	public ResponseEntity<String> getMicroGeneration(@RequestParam(EnergyConsumptionConstants.METER_NUMBER) String meterNumber) {
		Meter meter = meterRepo.findById(meterNumber);

		if (meter == null) {
			logger.error("Unable to get microgeneration, meter={} not found.", meterNumber);
			return new ResponseEntity<String>("0", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String>(String.valueOf(meter.getMicrogeneration()), HttpStatus.OK);
	}

}
package com.landisgyr.energyconsumption.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.landisgyr.energyconsumption.exception.MeterRepositoryException;
import com.landisgyr.energyconsumption.model.Meter;

public class DefaultMeterRepositoryTest {

	DefaultMeterRepository meterRepo = new DefaultMeterRepository();

	@Test
	void testSave() throws MeterRepositoryException {
		Meter meter = meterRepo.save(new Meter("ABC", 1L, 2L));
		assertNotNull(meter);
		assertEquals("ABC", meter.getMeterNo());
		assertEquals(1L, meter.getConsumption());
		assertEquals(2L, meter.getMicrogeneration());
		assertNull(meter.getCash());
	}

	@Test
	void testSaveNullMeter() throws MeterRepositoryException {
		Assertions.assertThrows(MeterRepositoryException.class, () -> {
			meterRepo.save(null);
		});
	}

	@Test
	void testFindById() throws MeterRepositoryException {
		meterRepo.save(new Meter("ABC", 1L, 2L));
		Meter meter = meterRepo.findById("ABC");
		assertNotNull(meter);
		assertEquals("ABC", meter.getMeterNo());
	}
	
	@Test
	void testFindByIdNullMeter() throws MeterRepositoryException {
		Meter meter = meterRepo.findById(null);
		assertNull(meter);
	}
}

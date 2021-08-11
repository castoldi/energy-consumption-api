package com.landisgyr.energyconsumption.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.landisgyr.energyconsumption.exception.BillingCalculationException;
import com.landisgyr.energyconsumption.exception.MeterRepositoryException;
import com.landisgyr.energyconsumption.model.Meter;
import com.landisgyr.energyconsumption.repository.MeterRepository;

@ExtendWith(MockitoExtension.class)
class DefaultEnergyConsumptionServiceTest {

	@InjectMocks
	DefaultEnergyConsumptionService service;

	@Mock
	MeterRepository meterRepo;

	@Test
	void testPositiveScenario() throws BillingCalculationException {
		Double cash = service.calculateBilling(377L, 47L, 0.42);
		assertEquals(138.6, cash);
	}

	@Test
	void testPositiveScenario_NegativeUnit() throws BillingCalculationException {
		Double cash = service.calculateBilling(377L, 47L, -0.42);
		assertEquals(-138.6, cash);
	}

	@Test
	void testNullUnit() {
		Assertions.assertThrows(BillingCalculationException.class, () -> {
			service.calculateBilling(377L, 47L, null);
		});
	}

	@Test
	void testNullConsumption() {
		Assertions.assertThrows(BillingCalculationException.class, () -> {
			service.calculateBilling(null, 47L, 0.42);
		});
	}

	@Test
	void testNullMicrogeneration() {
		Assertions.assertThrows(BillingCalculationException.class, () -> {
			service.calculateBilling(377L, null, 0.42);
		});
	}

	@Test
	void testSave() throws MeterRepositoryException {
		Meter meter = service.save("ABC");
		assertNotNull(meter);
		assertEquals("ABC", meter.getMeterNo());
		assertEquals(0L, meter.getConsumption());
		assertEquals(0L, meter.getMicrogeneration());
		assertNull(meter.getCash());
	}

	@Test
	void testSaveNullMeterNumber() throws MeterRepositoryException {
		Assertions.assertThrows(MeterRepositoryException.class, () -> {
			service.save(null);
		});
	}

	@Test
	void testSaveEmptyMeterNumber() throws MeterRepositoryException {
		Assertions.assertThrows(MeterRepositoryException.class, () -> {
			service.save("");
		});
	}

	@Test
	void testFindById() {
		when(meterRepo.findById("ABC")).thenReturn(new Meter("ABC", 0L, 0L));

		Meter meter = service.findById("ABC");

		assertNotNull(meter);
		assertEquals("ABC", meter.getMeterNo());
		assertEquals(0L, meter.getConsumption());
		assertEquals(0L, meter.getMicrogeneration());
		assertNull(meter.getCash());
	}

	@Test
	void testFindByIdNullMeterNumber() {
		Meter meter = service.findById(null);
		assertNull(meter);
	}
	
	@Test
	void testFindByIdEmptyMeterNumber() {
		Meter meter = service.findById(null);
		assertNull(meter);
	}

}

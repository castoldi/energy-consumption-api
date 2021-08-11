package com.landisgyr.energyconsumption;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.landisgyr.energyconsumption.model.Meter;
import com.landisgyr.energyconsumption.repository.MeterRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ConsumptionControllerApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MeterRepository meterRepo;

	@Test
	void getConsumptionForNonExistentMeter() throws Exception {
		 mockMvc.perform(get("/consumption")
		            .contentType("application/json")
		            .queryParam("meter_number", "1234"))
		 			.andDo(print())
		 			.andExpect(content().string("0"))
		            .andExpect(status().isNotFound());
	}
	
	@Test
	void getConsumptionForExistingMeter() throws Exception {
		Meter meter = new Meter("1714A6", 377L, 47L);
		when(meterRepo.findById("1714A6")).thenReturn(meter);

		mockMvc.perform(get("/consumption")
		            .contentType("application/json")
		            .queryParam("meter_number", "1714A6"))
		 			.andDo(print())
		 			.andExpect(content().string("377"))
		            .andExpect(status().isOk());
	}
	

	@Test
	void getMicrogenerationForExistingMeter() throws Exception {
		Meter meter = new Meter("1714A6", 377L, 47L);
		when(meterRepo.findById("1714A6")).thenReturn(meter);

		mockMvc.perform(get("/microgeneration")
		            .contentType("application/json")
		            .queryParam("meter_number", "1714A6"))
		 			.andDo(print())
		 			.andExpect(content().string("47"))
		            .andExpect(status().isOk());
	}
	
	@Test
	void getMicrogenerationNullMeterNumber() throws Exception {
		when(meterRepo.findById("1714A6")).thenReturn(null);

		mockMvc.perform(get("/microgeneration")
		            .contentType("application/json")
		            .queryParam("meter_number", "1714A6"))
		 			.andDo(print())
		 			.andExpect(content().string("0"))
		            .andExpect(status().isNotFound());
	}
}

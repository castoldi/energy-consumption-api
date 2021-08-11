package com.landisgyr.energyconsumption;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
class EventControllerApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MeterRepository meterRepo;

	@Test
	void createMeter() throws Exception {
		String requestPayload = "{\"type\":\"import\", \"meterNumber\":\"1714A6\"}";
		
		 mockMvc.perform(post("/event")
		            .contentType("application/json")
		            .content(requestPayload))
		 			.andDo(print())
		 			.andExpect(content().string(containsString("\"meterNo\":\"1714A6\"")))
		 			.andExpect(content().string(containsString("\"consumption\":0")))
		 			.andExpect(content().string(containsString("\"microgeneration\":0")))
		            .andExpect(status().isCreated());
	}
	
	@Test
	void createMeterNullMeterNumber() throws Exception {
		String requestPayload = "{\"type\":\"import\"}";
		
		 mockMvc.perform(post("/event")
		            .contentType("application/json")
		            .content(requestPayload))
		 			.andDo(print())
		            .andExpect(status().isBadRequest());
	}
	
	@Test
	void receiveEnergyData() throws Exception {
		Meter meter = new Meter("1714A6", 377L, 47L);
		when(meterRepo.findById("1714A6")).thenReturn(meter);

		String requestPayload = " {\"type\": \"push\", \"meterNumber\": \"1714A6\", \"activeEnergy\": 377,  \"injectedEnergy\": 47}";
		
		 mockMvc.perform(post("/event")
		            .contentType("application/json")
		            .content(requestPayload))
		 			.andDo(print())
		 			.andExpect(content().string(containsString("\"meterNo\":\"1714A6\"")))
		 			.andExpect(content().string(containsString("\"consumption\":377")))
		 			.andExpect(content().string(containsString("\"microgeneration\":47")))
		            .andExpect(status().isCreated());
	}
	
	@Test
	void receiveEnergyDataMeterNotFound() throws Exception {
		when(meterRepo.findById("1714A6")).thenReturn(null);

		String requestPayload = " {\"type\": \"push\", \"meterNumber\": \"1714A6\", \"activeEnergy\": 377,  \"injectedEnergy\": 47}";
		
		 mockMvc.perform(post("/event")
		            .contentType("application/json")
		            .content(requestPayload))
		 			.andDo(print())
		            .andExpect(status().isNotFound());
	}
	
	@Test
	void billing() throws Exception {
		Meter meter = new Meter("1714A6", 377L, 47L);
		when(meterRepo.findById("1714A6")).thenReturn(meter);

		String requestPayload = "{\"type\": \"billing\",  \"meterNumber\": \"1714A6\", \"unit\": 0.42}";
		
		 mockMvc.perform(post("/event")
		            .contentType("application/json")
		            .content(requestPayload))
		 			.andDo(print())
		 			.andExpect(content().string(containsString("\"meterNo\":\"1714A6\"")))
		 			.andExpect(content().string(containsString("\"cash\":138.6")))
		            .andExpect(status().isCreated());
	}
	
	@Test
	void billing_null_unit() throws Exception {
		Meter meter = new Meter("1714A6", 377L, 47L);
		when(meterRepo.findById("1714A6")).thenReturn(meter);

		String requestPayload = "{\"type\": \"billing\",  \"meterNumber\": \"1714A6\"}";
		
		 mockMvc.perform(post("/event")
		            .contentType("application/json")
		            .content(requestPayload))
		 			.andDo(print())
		            .andExpect(status().isBadRequest());
	}
	
	@Test
	void billing_meter_not_found() throws Exception {
		when(meterRepo.findById("1714A6")).thenReturn(null);

		String requestPayload = "{\"type\": \"billing\",  \"meterNumber\": \"1714A6\"}";
		
		 mockMvc.perform(post("/event")
		            .contentType("application/json")
		            .content(requestPayload))
		 			.andDo(print())
		            .andExpect(status().isNotFound());
	}
	
	@Test
	void createMeterInvalidType() throws Exception {
		String requestPayload = "{\"type\":\"invalid\", \"meterNumber\": \"1714A6\"}";
		
		 mockMvc.perform(post("/event")
		            .contentType("application/json")
		            .content(requestPayload))
		 			.andDo(print())
		            .andExpect(status().isBadRequest());
	}
	
	@Test
	void createMeterEmptyPayloadType() throws Exception {
		String requestPayload = "{}";
		
		 mockMvc.perform(post("/event")
		            .contentType("application/json")
		            .content(requestPayload))
		 			.andDo(print())
		            .andExpect(status().isBadRequest());
	}
	
	@Test
	void createMeterNullPayloadType() throws Exception {
		String requestPayload = "";
		
		 mockMvc.perform(post("/event")
		            .contentType("application/json")
		            .content(requestPayload))
		 			.andDo(print())
		            .andExpect(status().isBadRequest());
	}
}

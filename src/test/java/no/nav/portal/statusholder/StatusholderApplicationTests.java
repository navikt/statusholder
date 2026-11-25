package no.nav.portal.statusholder;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StatusholderApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void statusEndpointsPreserveJsonAndUpdates() throws Exception {
		UUID serviceId = UUID.randomUUID();
		UUID secondServiceId = UUID.randomUUID();
		String initialStatus = """
				{"serviceId":"%s","status":"OK","description":"Available"}
				""".formatted(serviceId);

		mockMvc.perform(post("/status")
				.contentType(MediaType.APPLICATION_JSON)
				.content(initialStatus))
				.andExpect(status().isOk());

		mockMvc.perform(get("/status/{uuid}", serviceId))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json(initialStatus));

		String updatedStatus = """
				{"serviceId":"%s","status":"DOWN","description":"Unavailable"}
				""".formatted(serviceId);
		String secondStatus = """
				{"serviceId":"%s","status":"ISSUE","description":"Degraded"}
				""".formatted(secondServiceId);

		mockMvc.perform(post("/statuses")
				.contentType(MediaType.APPLICATION_JSON)
				.content("[" + updatedStatus + "," + secondStatus + "]"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/status/{uuid}", serviceId))
				.andExpect(status().isOk())
				.andExpect(content().json(updatedStatus));

		mockMvc.perform(get("/status/{uuid}", secondServiceId))
				.andExpect(status().isOk())
				.andExpect(content().json(secondStatus));

		mockMvc.perform(get("/status"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[*].serviceId", hasItem(serviceId.toString())))
				.andExpect(jsonPath("$[*].serviceId", hasItem(secondServiceId.toString())));
	}

	@Test
	void invalidUuidReturnsBadRequest() throws Exception {
		mockMvc.perform(get("/status/not-a-uuid"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void livenessEndpointPreservesResponse() throws Exception {
		mockMvc.perform(get("/isAlive"))
				.andExpect(status().isOk())
				.andExpect(content().string("Status: Ok"));
	}

}

package LRprueba.prueb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import app.controllers.RadarRestController;
import app.dto.CoordinatesDTO;
import app.dto.EnemiesDTO;
import app.dto.RadarBodyDTO;
import app.dto.ScanDTO;
import app.services.ServicioDroide;

@RunWith(MockitoJUnitRunner.class)
public class ServiceClienteTest {

	private MockMvc mockMvc;
	
	/** The Constant APPLICATION_JSON. */
	// Constants
	private static final MediaType APPLICATION_JSON = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype());
	
	private Gson parser;
	
	@Spy
	@InjectMocks
	private ServicioDroide service;
	
	@InjectMocks
	private RadarRestController controller;
	
	@Before
	public void configure() {
		mockMvc = standaloneSetup(controller).build();
    	MockitoAnnotations.initMocks(this);
    	parser = new GsonBuilder().create();
	}
	
	@Test
	public void ingresarTest() throws Exception{
		List<ScanDTO> scans = new ArrayList<>();
		List<String> protocols = new ArrayList<>();
		protocols.add("avoid-mech");
		
		CoordinatesDTO coord = new CoordinatesDTO(0,40);
		EnemiesDTO enemies = new EnemiesDTO("soldier", 10);
		
		ScanDTO scan = new ScanDTO(coord, enemies);
		scans.add(scan);

		coord = new CoordinatesDTO(0,80);
		enemies = new EnemiesDTO("mech", 1);
		
		scan = new ScanDTO(coord, enemies, 5);
		scans.add(scan);
		
		RadarBodyDTO radar = new RadarBodyDTO(protocols, scans);
		
		Matcher<Integer> matcher = new IsEqual<Integer>(0);
		
		mockMvc.perform(post("/radar").contentType(APPLICATION_JSON).content(parser.toJson(radar)))
		.andExpect(status().isOk()).andExpect(jsonPath("$.x", matcher));

		matcher = new IsEqual<Integer>(40);
		mockMvc.perform(post("/radar").contentType(APPLICATION_JSON).content(parser.toJson(radar)))
		.andExpect(status().isOk()).andExpect(jsonPath("$.y", matcher));
		
	}

}

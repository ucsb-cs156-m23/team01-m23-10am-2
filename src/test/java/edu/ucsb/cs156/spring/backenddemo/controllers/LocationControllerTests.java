package edu.ucsb.cs156.spring.backenddemo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import edu.ucsb.cs156.spring.backenddemo.services.LocationQueryService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(value = LocationController.class)
public class LocationControllerTests {
  private ObjectMapper mapper = new ObjectMapper();
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  LocationQueryService mockLocationQueryService;


  @Test
  public void test_getLocations() throws Exception {
  
    String fakeJsonResult="{ \"fake\" : \"result\" }";
    String location = "Paris";
    when(mockLocationQueryService.getJSON(eq(location))).thenReturn(fakeJsonResult);

    String url = String.format("/api/locations/get?location=%s", location);

    MvcResult response = mockMvc
        .perform( get(url).contentType("application/json"))
        .andExpect(status().isOk()).andReturn();

    String responseString = response.getResponse().getContentAsString();

    assertEquals(fakeJsonResult, responseString);
  }

  //NOTE: since this test doesn't test the ability to connect to the web, simply the code in the controller,
  //    which only has the function of calling the get_JSON func,
  //    I'm assuming that's why for location = 'New York', I don't need to replace the spaces with URI escape chars
  @Test
  public void test_getLocations_Space() throws Exception {
  
    String fakeJsonResult="{ \"fake\" : \"result\" }";
    String location = "New York";
    when(mockLocationQueryService.getJSON(eq(location))).thenReturn(fakeJsonResult);

    String url = String.format("/api/locations/get?location=%s", location);

    MvcResult response = mockMvc
        .perform( get(url).contentType("application/json"))
        .andExpect(status().isOk()).andReturn();

    String responseString = response.getResponse().getContentAsString();

    assertEquals(fakeJsonResult, responseString);
  }

}
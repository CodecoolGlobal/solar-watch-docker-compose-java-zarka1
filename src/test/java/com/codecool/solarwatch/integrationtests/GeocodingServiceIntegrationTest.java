package com.codecool.solarwatch.integrationtests;

import com.codecool.solarwatch.SolarWatchApplication;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.DataInput;
import java.io.IOException;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SolarWatchApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GeocodingServiceIntegrationTest {

    String jwt;

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;

    TestRestTemplate restTemplate = new TestRestTemplate();

    public void setUp() throws JSONException, IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("username", "Username");
        personJsonObject.put("password", "Password");
        HttpEntity<String> entity = new HttpEntity<String>(personJsonObject.toString(), headers);
        restTemplate.exchange(createURLWithPort(
                "/api/user/register"), HttpMethod.POST, entity, String.class);
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(
                "/api/user/signin"), HttpMethod.POST, entity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(response.getBody());
        System.out.println(node.toString());
        jwt = node.get("jwt").asText();
        System.out.println("jwt: " + jwt);
    }

    @Test
    public void testIfGeocodingServiceSendsTheRightDataForLondon() throws Exception {
        setUp();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",  "Bearer " + jwt);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(
                "/city?city=London"), HttpMethod.GET, entity, String.class);
        String expected = "{name:London,latitude:51.50732,longitude:-0.1276474,country:GB,state:England}";
        System.out.println(response);
        JSONAssert.assertEquals(expected, response.getBody(), true);
    }

    @Test
    public void testIfSunriseSunsetServiceSendsTheRightDataForLondon() throws Exception {
        setUp();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",  "Bearer " + jwt);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(
                "/api/suncalculator?city=London&date=2024-06-08"), HttpMethod.GET, entity, String.class);
        String expected = "{sunrise:\"3:42:07 AM\",sunset:\"8:17:15 PM\"}";
        System.out.println(response);
        JSONAssert.assertEquals(expected, response.getBody(), true);
    }

    @Test
    public void testIfUserRegisteredAsAdminWorking() throws Exception {
        setUp();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",  "Bearer " + jwt);
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("username", "Username");
        personJsonObject.put("password", "Password");
        mvc.perform(post("/api/admin/register")
                        .content(personJsonObject.toString())
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


}

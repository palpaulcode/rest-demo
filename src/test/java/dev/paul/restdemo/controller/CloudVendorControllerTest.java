package dev.paul.restdemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.paul.restdemo.model.CloudVendor;
import dev.paul.restdemo.service.CloudVendorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CloudVendorController.class)
class CloudVendorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CloudVendorService cloudVendorService;

    CloudVendor cloudVendorOne;
    CloudVendor cloudVendorTwo;
    List<CloudVendor> cloudVendorList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        cloudVendorOne = new CloudVendor("1", "Amazon",
                "USA", "xxxxxx");
        cloudVendorTwo = new CloudVendor("2", "GCP",
                "UK", "yyyyyy");
        cloudVendorList.add(cloudVendorOne);
        cloudVendorList.add(cloudVendorTwo);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetCloudVendorDetails() throws Exception {
        when(cloudVendorService.getCloudVendor("1")).thenReturn(cloudVendorOne);
        this.mockMvc.perform(get("/cloudvendor/id/1")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllCloudVendorDetails() throws Exception {
        when(cloudVendorService.getAllCloudVendors()).thenReturn(cloudVendorList);
        this.mockMvc.perform(get("/cloudvendor")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testGetCloudVendorByName() throws Exception {
        when(cloudVendorService.getByVendorName("Amazon")).thenReturn(cloudVendorList);
        this.mockMvc.perform(get("/cloudvendor/name/Amazon")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testCreateCloudVendorDetails() throws Exception {
        // Convert object to JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(cloudVendorOne);

        when(cloudVendorService.createCloudVendor(cloudVendorOne)).thenReturn("Success");
        this.mockMvc.perform(post("/cloudvendor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testUpdateCloudVendorDetails() throws Exception {
        // Convert object to JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(cloudVendorOne);

        when(cloudVendorService.updateCloudVendor(cloudVendorOne)).thenReturn("Success");
        this.mockMvc.perform(put("/cloudvendor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testDeleteCloudVendorDetails() throws Exception {
        when(cloudVendorService.deleteCloudVendor("1")).thenReturn("Success");
        this.mockMvc.perform(delete("/cloudvendor/1"))
                .andDo(print()).andExpect(status().isOk());
    }
}
package com.voxloud.provisioning;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ProvisioningControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testDeskPhoneWithoutOverride() throws Exception {
        this.mockMvc.perform(get("/api/v1/provisioning/aa-bb-cc-dd-ee-ff"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/plain"))
            .andExpect(content().string(containsString("username=john")))
            .andExpect(content().string(containsString("password=doe")))
            .andExpect(content().string(containsString("domain=sip.voxloud.com")))
            .andExpect(content().string(containsString("port=5060")))
            .andExpect(content().string(containsString("codecs=G711,G729,OPUS")))
            .andExpect(content().string(containsString("timeout=0")));
    }

    @Test
    public void testConferencePhoneWithoutOverride() throws Exception {
        mockMvc.perform(get("/api/v1/provisioning/f1-e2-d3-c4-b5-a6"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.username").value("sofia"))
            .andExpect(jsonPath("$.password").value("red"))
            .andExpect(jsonPath("$.domain").value("sip.voxloud.com"))
            .andExpect(jsonPath("$.port").value("5060"))
            .andExpect(jsonPath("$.timeout").value(0))
            .andExpect(jsonPath("$.codecs").isArray())
            .andExpect(jsonPath("$.codecs[0]").value("G711"));
    }

    @Test
    public void testDeskPhoneWithOverride() throws Exception {
        mockMvc.perform(get("/api/v1/provisioning/a1-b2-c3-d4-e5-f6"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain"))
                .andExpect(content().string(containsString("username=walter")))
                .andExpect(content().string(containsString("password=white")))
                .andExpect(content().string(containsString("domain=sip.anotherdomain.com")))
                .andExpect(content().string(containsString("port=5161")))
                .andExpect(content().string(containsString("codecs=G711,G729,OPUS")))
                .andExpect(content().string(containsString("timeout=10")));
    }

    @Test
    public void testConferencePhoneWithOverride() throws Exception {
        mockMvc.perform(get("/api/v1/provisioning/1a-2b-3c-4d-5e-6f"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.username").value("eric"))
                .andExpect(jsonPath("$.password").value("blue"))
                .andExpect(jsonPath("$.domain").value("sip.anotherdomain.com"))
                .andExpect(jsonPath("$.port").value("5161"))
                .andExpect(jsonPath("$.timeout").value(10))
                .andExpect(jsonPath("$.codecs").isArray())
                .andExpect(jsonPath("$.codecs[2]").value("OPUS"));
    }

    @Test
    public void testNonExistentDevice() throws Exception {
        mockMvc.perform(get("/api/v1/provisioning/aa-aa-aa-aa-aa-aa"))
                .andExpect(status().isNotFound());
    }
}

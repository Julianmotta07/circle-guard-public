package com.circleguard.promotion.controller;

import com.circleguard.promotion.service.HealthStatusService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class HealthStatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HealthStatusService statusService;

    @Test
    @WithMockUser(username = "admin", authorities = {"HEALTH_CENTER"})
    void confirmPositive_WithPermission_CallsUpdateStatus() throws Exception {
        String json = "{\"anonymousId\": \"user-1\"}";

        mockMvc.perform(post("/api/v1/health/confirmed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(statusService).updateStatus("user-1", "CONFIRMED");
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"HEALTH_CENTER"})
    void resolve_WithPermission_CallsResolveStatus() throws Exception {
        String json = "{\"anonymousId\": \"user-1\"}";

        mockMvc.perform(post("/api/v1/health/resolve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(statusService).resolveStatus("user-1", false);
    }

    @Test
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    void resolve_WithoutPermission_Returns403() throws Exception {
        String json = "{\"anonymousId\": \"user-1\"}";

        mockMvc.perform(post("/api/v1/health/resolve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    void resolve_Unauthenticated_Returns403() throws Exception {
        String json = "{\"anonymousId\": \"user-1\"}";

        mockMvc.perform(post("/api/v1/health/resolve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }
}
package com.example.vpn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VpnControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnServerList() throws Exception {
        mockMvc.perform(get("/api/v1/vpn/servers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void shouldCreateConnection() throws Exception {
        String payload = """
                {
                  \"username\": \"demo-user\",
                  \"serverId\": \"us-nyc-01\",
                  \"protocol\": \"WIREGUARD\"
                }
                """;

        mockMvc.perform(post("/api/v1/vpn/connect")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONNECTED"));
    }
}

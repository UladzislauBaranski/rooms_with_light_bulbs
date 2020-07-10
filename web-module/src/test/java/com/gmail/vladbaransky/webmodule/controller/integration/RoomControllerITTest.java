package com.gmail.vladbaransky.webmodule.controller.integration;

import com.gmail.vladbaransky.repository.model.CountryEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("/application-integration.properties")
public class RoomControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql("/data.sql")
    public void getRoomsByPage_returnRooms() throws Exception {
        mockMvc.perform(get("/rooms")
                .contentType(MediaType.TEXT_HTML)
                .param("page", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString("name")))
                .andExpect(content().string(containsString("BELARUS")))
                .andExpect(content().string(containsString("true")));
    }

    @Test
    public void getNewRoomPage_returnRoomPage() throws Exception {
        mockMvc.perform(get("/rooms/add")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @Sql("/data.sql")
    void addRoomWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(post("/rooms")
                .param("name", "name")
                .param("country", String.valueOf(CountryEnum.BELARUS))
                .param("lightIsEnable", String.valueOf(true)))
                .andExpect(redirectedUrl("/rooms"));
    }

    @Test
    @Sql("/data.sql")
    public void setStateTrue_updateRooms() throws Exception {
        mockMvc.perform(post("/rooms/enable")
                .contentType(MediaType.TEXT_HTML)
                .param("id", String.valueOf(1L)))
                .andExpect(redirectedUrl("/rooms/1"));
    }

    @Test
    @Sql("/data.sql")
    public void setStateFalse_updateRooms() throws Exception {
        mockMvc.perform(post("/rooms/disable")
                .contentType(MediaType.TEXT_HTML)
                .param("id", String.valueOf(1L)))
                .andExpect(redirectedUrl("/rooms/1"));
    }
}


package com.gmail.vladbaransky.webmodule.controller;

import com.gmail.vladbaransky.repository.model.CountryEnum;
import com.gmail.vladbaransky.service.RoomService;
import com.gmail.vladbaransky.service.exception.AccessDeniedException;
import com.gmail.vladbaransky.service.model.RoomDTO;
import com.gmail.vladbaransky.service.model.UserDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RoomController.class)
@AutoConfigureMockMvc
class RoomControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @MockBean
    private RoomService roomService;

    //---------------------------getRoomByPage------------------------------------
    @Test
    void getRooms_returnStatusOk() throws Exception {
        mockMvc.perform(get("/rooms"))
                .andExpect(status().isOk());
    }

    @Test
    void getRoomsWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(get("/rooms")
                .contentType(MediaType.TEXT_HTML)
                .param("page", String.valueOf(1L)))
                .andExpect(status().isOk());
    }

    @Test
    void getRoomsWithInvalidParam_returnBadRequest() throws Exception {
        mockMvc.perform(get("/rooms")
                .contentType(MediaType.TEXT_HTML)
                .param("page", "inv")).andExpect(status().isBadRequest());
    }

    @Test
    void getRooms_callBusinessLogic() throws Exception {
        mockMvc.perform(get("/rooms")
                .contentType(MediaType.TEXT_HTML)
                .param("page", String.valueOf(1))).andExpect(status().isOk());
        verify(roomService, times(1)).getRoomsByPage(eq(1));
    }

    @Test
    void getRooms_returnRooms() throws Exception {
        List<RoomDTO> rooms = getAddedRoomListDTO();
        when(roomService.getRoomsByPage(1)).thenReturn(rooms);
        MvcResult result = mockMvc.perform(get("/rooms")
                .contentType(MediaType.TEXT_HTML)
                .param("page", String.valueOf(1))).andReturn();

        String expectedReturnedContent = result.getResponse().getContentAsString();
        Assertions.assertThat(expectedReturnedContent).contains("Name");
        Assertions.assertThat(expectedReturnedContent).contains(String.valueOf(CountryEnum.BELARUS));
        Assertions.assertThat(expectedReturnedContent).contains(String.valueOf(Boolean.TRUE));
    }

    @Test
    void getAddArticlesPage_returnStatusOk() throws Exception {
        mockMvc.perform(get("/rooms/add"))
                .andExpect(status().isOk());
    }

    //---------------------------addRoom------------------------------------
    @Test
    void addRoomWithParam_returnStatusOk() throws Exception {
        RoomDTO room = getRoomDTO();
        RoomDTO returnedRoom = getAddedRoomDTO();
        when(roomService.addRoom(room)).thenReturn(returnedRoom);
        mockMvc.perform(post("/rooms")
                .param("name", "name")
                .param("country", String.valueOf(CountryEnum.BELARUS))
                .param("lightIsEnable", String.valueOf(true)))
                .andExpect(redirectedUrl("/rooms"));
    }

    @Test
    void addRoom_callBusinessLogic() throws Exception {
        RoomDTO room = getRoomDTO();
        RoomDTO returnedRoom = getAddedRoomDTO();

        when(roomService.addRoom(room)).thenReturn(returnedRoom);
        mockMvc.perform(post("/rooms")
                .param("name", "Name")
                .param("country", String.valueOf(CountryEnum.BELARUS))
                .param("lightIsEnable", String.valueOf(true)))
                .andExpect(redirectedUrl("/rooms"));
        verify(roomService, times(1)).addRoom(eq(room));
    }

    //------------------------getRoomById---------------------------------------------
    @Test
    void getRoomById_returnStatusOk() throws Exception {
        mockMvc.perform(get("/rooms/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getRoomWithInvalidId_returnBadRequest() throws Exception {
        mockMvc.perform(get("/rooms/inv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getRoomById_callBusinessLogic() throws Exception, AccessDeniedException {
        RoomDTO room = getRoomDTO();
        RoomDTO addedRoom = getRoomDTO();
        UserDTO user = getUserDTO();
        when(roomService.getRoomByIdWithVerifyUser(1L, user)).thenReturn(addedRoom);
        mockMvc.perform(get("/rooms/1")
                .contentType(MediaType.TEXT_HTML)).andExpect(status().isOk());
    }

    //----------------------------enable------------------------------
    @Test
    void getMethodEnableWithInvalidParam_returnRooms() throws Exception {
        mockMvc.perform(post("/rooms/enable")
                .contentType(MediaType.TEXT_HTML)
                .param("id", "inv")).andExpect(status().isBadRequest());
    }

    @Test
    void getMethodEnable_returnRooms() throws Exception {
        RoomDTO room = getRoomDTO();
        RoomDTO addedRoom = getRoomDTO();
        when(roomService.getRoomById(1L)).thenReturn(room);
        when(roomService.updateRoom(room)).thenReturn(addedRoom);
        mockMvc.perform(post("/rooms/enable")
                .contentType(MediaType.TEXT_HTML)
                .param("id", String.valueOf(1L))).andExpect(redirectedUrl("/rooms/1"));
        verify(roomService, times(1)).getRoomById(eq(1L));
        verify(roomService, times(1)).updateRoom(eq(room));
    }

    //----------------------------disable-----------------------
    @Test
    void getMethodDisableWithInvalidParam_returnRooms() throws Exception {
        mockMvc.perform(post("/rooms/disable")
                .contentType(MediaType.TEXT_HTML)
                .param("id", "inv")).andExpect(status().isBadRequest());
    }

    @Test
    void getMethodDisable_returnRooms() throws Exception {
        RoomDTO room = getRoomDTO();
        RoomDTO addedRoom = getAddedRoomDTO();
        when(roomService.getRoomById(1L)).thenReturn(room);
        when(roomService.updateRoom(room)).thenReturn(addedRoom);
        mockMvc.perform(post("/rooms/disable")
                .contentType(MediaType.TEXT_HTML)
                .param("id", String.valueOf(1L))).andExpect(redirectedUrl("/rooms/1"));
        verify(roomService, times(1)).getRoomById(eq(1L));
        verify(roomService, times(1)).updateRoom(eq(room));
    }

    //--------------------model-------------------------------
    private RoomDTO getRoomDTO() {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName("Name");
        roomDTO.setCountry(CountryEnum.BELARUS);
        roomDTO.setLightIsEnable(Boolean.TRUE);
        return roomDTO;
    }

    private RoomDTO getAddedRoomDTO() {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(1L);
        roomDTO.setName("Name");
        roomDTO.setCountry(CountryEnum.BELARUS);
        roomDTO.setLightIsEnable(Boolean.TRUE);
        return roomDTO;
    }

    private List<RoomDTO> getAddedRoomListDTO() {
        List<RoomDTO> rooms = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            RoomDTO room = getRoomDTO();
            room.setId(i);
            rooms.add(room);
        }
        return rooms;
    }

    private UserDTO getUserDTO() {
        UserDTO user = new UserDTO();
        user.setCountry(String.valueOf(CountryEnum.BELARUS));
        return user;
    }
}

package com.gmail.vladbaransky.service;

import com.gmail.vladbaransky.repository.RoomRepository;
import com.gmail.vladbaransky.repository.model.CountryEnum;
import com.gmail.vladbaransky.repository.model.Room;
import com.gmail.vladbaransky.service.exception.AccessDeniedException;
import com.gmail.vladbaransky.service.impl.RoomServiceImpl;
import com.gmail.vladbaransky.service.model.RoomDTO;
import com.gmail.vladbaransky.service.model.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    public void setup() {
        this.roomService = new RoomServiceImpl(roomRepository);
    }

    @Test
    public void getRoomsByPage_returnRooms() {
        List<Room> rooms = getAddedRoomList();

        when(roomRepository.getObjectByPage(0, 10)).thenReturn(rooms);
        List<RoomDTO> returnedRooms = roomService.getRoomsByPage(1);
        verify(roomRepository, times(1)).getObjectByPage(0, 10);
        for (int i = 0; i < rooms.size(); i++) {
            getAssertion(returnedRooms.get(i), rooms.get(i));
        }
    }

    @Test
    public void addRoom_returnRoom() {
        Room room = getRoom();
        Room addedRoom = getAddedRoom();
        RoomDTO roomDTO = getRoomDTO();

        when(roomRepository.addObject(room)).thenReturn(addedRoom);
        RoomDTO returnedRoom = roomService.addRoom(roomDTO);
        verify(roomRepository, times(1)).addObject(room);
        getAssertion(returnedRoom, addedRoom);
    }

    @Test
    public void getRoomByIdWithVerifyUser_returnRoom() throws AccessDeniedException {
        Long id = 1L;
        UserDTO userDTO = getUserDTO();
        Room room = getAddedRoom();

        when(roomRepository.getObjectById(id)).thenReturn(room);
        RoomDTO returnedRoom = roomService.getRoomByIdWithVerifyUser(id, userDTO);
        verify(roomRepository, times(1)).getObjectById(id);
        getAssertion(returnedRoom, room);
    }

    @Test
    public void getRoomById_returnRoom() {
        Long id = 1L;
        Room room = getAddedRoom();

        when(roomRepository.getObjectById(id)).thenReturn(room);
        RoomDTO returnedRoom = roomService.getRoomById(id);
        verify(roomRepository, times(1)).getObjectById(id);
        getAssertion(returnedRoom, room);
    }

    @Test
    public void updateRoom_returnRoom() throws AccessDeniedException {
        Room room = getAddedRoom();
        RoomDTO roomDTO = getAddedRoomDTO();

        when(roomRepository.updateObject(room)).thenReturn(room);
        RoomDTO returnedRoom = roomService.updateRoom(roomDTO);
        verify(roomRepository, times(1)).updateObject(room);
        getAssertion(returnedRoom, room);
    }

    private Room getRoom() {
        Room room = new Room();
        room.setName("Name");
        room.setCountry(CountryEnum.BELARUS);
        room.setLightIsEnable(Boolean.TRUE);
        return room;
    }

    private Room getAddedRoom() {
        Room room = new Room();
        room.setId(1L);
        room.setName("Name");
        room.setCountry(CountryEnum.BELARUS);
        room.setLightIsEnable(Boolean.TRUE);
        return room;
    }

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

    private List<Room> getAddedRoomList() {
        List<Room> rooms = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            Room room = getRoom();
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

    private void getAssertion(RoomDTO returnedRoom, Room room) {
        assertThat(returnedRoom).isNotNull();
        assertThat(returnedRoom.getId()).isEqualTo(room.getId());
        assertThat(returnedRoom.getName()).isEqualTo(room.getName());
        assertThat(returnedRoom.getCountry()).isEqualTo(room.getCountry());
        assertThat(returnedRoom.getLightIsEnable()).isEqualTo(room.getLightIsEnable());
    }
}
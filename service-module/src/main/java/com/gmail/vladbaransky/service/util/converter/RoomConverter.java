package com.gmail.vladbaransky.service.util.converter;

import com.gmail.vladbaransky.repository.model.Room;
import com.gmail.vladbaransky.service.model.RoomDTO;

public class RoomConverter {
    public static Room getObjectFromDTO(RoomDTO roomDTO) {
        Room room = new Room();
        room.setId(roomDTO.getId());
        room.setName(roomDTO.getName());
        room.setCountry(roomDTO.getCountry());
        room.setLightIsEnable(roomDTO.getLightIsEnable());
        return room;
    }

    public static RoomDTO getDTOFromObject(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setName(room.getName());
        roomDTO.setCountry(room.getCountry());
        roomDTO.setLightIsEnable(room.getLightIsEnable());
        return roomDTO;
    }
}

package com.gmail.vladbaransky.service;

import com.gmail.vladbaransky.service.exception.AccessDeniedException;
import com.gmail.vladbaransky.service.model.RoomDTO;
import com.gmail.vladbaransky.service.model.UserDTO;

import java.util.List;

public interface RoomService {
    List<RoomDTO> getRoomsByPage(int page);

    RoomDTO addRoom(RoomDTO roomDTO);

    RoomDTO getRoomByIdWithVerifyUser(Long id, UserDTO userDTO) throws AccessDeniedException;

    RoomDTO updateRoom(RoomDTO roomDTO);

    RoomDTO getRoomById(Long id);
}

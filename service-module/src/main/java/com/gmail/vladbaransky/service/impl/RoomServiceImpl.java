package com.gmail.vladbaransky.service.impl;

import com.gmail.vladbaransky.repository.RoomRepository;
import com.gmail.vladbaransky.repository.model.Room;
import com.gmail.vladbaransky.service.RoomService;
import com.gmail.vladbaransky.service.exception.AccessDeniedException;
import com.gmail.vladbaransky.service.model.RoomDTO;
import com.gmail.vladbaransky.service.model.UserDTO;
import com.gmail.vladbaransky.service.util.converter.RoomConverter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    private static final int OBJECT_BY_PAGE = 10;
    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomDTO> getRoomsByPage(int page) {
        int startPosition = (page - 1) * OBJECT_BY_PAGE;
        List<Room> articles = roomRepository.getObjectByPage(startPosition, OBJECT_BY_PAGE);
        return articles.stream()
                .map(RoomConverter::getDTOFromObject)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public RoomDTO addRoom(RoomDTO roomDTO) {
        Room room = RoomConverter.getObjectFromDTO(roomDTO);
        Room returnedRoom = roomRepository.addObject(room);
        return RoomConverter.getDTOFromObject(returnedRoom);
    }

    @Override
    public RoomDTO getRoomByIdWithVerifyUser(Long id, UserDTO userDTO) throws AccessDeniedException {
        Room room = roomRepository.getObjectById(id);
        if (userDTO.getCountry().equals(String.valueOf(room.getCountry()))) {
            return RoomConverter.getDTOFromObject(room);
        } else {
            throw new AccessDeniedException();
        }
    }

    @Override
    public RoomDTO getRoomById(Long id) {
        Room room = roomRepository.getObjectById(id);
        return RoomConverter.getDTOFromObject(room);
    }

    @Transactional
    @Override
    public RoomDTO updateRoom(RoomDTO roomDTO) {
        Room room = RoomConverter.getObjectFromDTO(roomDTO);
        Room returnedRoom = roomRepository.updateObject(room);
        return RoomConverter.getDTOFromObject(returnedRoom);
    }
}


package com.gmail.vladbaransky.repository.impl;

import com.gmail.vladbaransky.repository.RoomRepository;
import com.gmail.vladbaransky.repository.model.Room;
import org.springframework.stereotype.Repository;

@Repository
public class RoomRepositoryImpl extends GenericDAORepositoryImpl<Long, Room> implements RoomRepository {
}

package io.github.octcarp.sustech.cs309.assignment.a1backend.service;

import io.github.octcarp.sustech.cs309.assignment.a1backend.repository.RoomRepository;

public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void listRooms() {
        roomRepository.findAll();
    }
}

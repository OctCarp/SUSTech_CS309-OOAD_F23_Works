package io.github.octcarp.sustech.cs309.assignment.a1backend.repository;

import io.github.octcarp.sustech.cs309.assignment.a1backend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByRoomNameContaining(String roomName);

    List<Room> findByRoomNameContainingAndDate(String roomName, LocalDate date);
}

package io.github.octcarp.sustech.cs309.assignment.a1backend.contorller;

import io.github.octcarp.sustech.cs309.assignment.a1backend.model.Room;
import io.github.octcarp.sustech.cs309.assignment.a1backend.repository.RoomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping(value = {"", "/", "/list"})
    public String listRooms(Model model) {
        List<Room> rooms = roomRepository.findAll();
        model.addAttribute("rooms", rooms);
        return "rooms/list";
    }

    @GetMapping("/add")
    public String showAddRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "rooms/add"; // Thymeleaf template for adding a room
    }

    @PostMapping("/add")
    public String addRoom(@ModelAttribute Room room) {
        roomRepository.save(room);
        return "redirect:/rooms";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomRepository.deleteById(id);
        return "redirect:/rooms";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid room id: " + id));
        model.addAttribute("room", room);
        return "rooms/edit"; // Thymeleaf template for editing a room
    }

    @PostMapping("/edit/{id}")
    public String updateRoom(@PathVariable Long id, @ModelAttribute Room room) {
        room.setId(id);  // Make sure the ID is set so it updates the correct row
        roomRepository.save(room);
        return "redirect:/rooms";
    }

    @GetMapping("/search")
    public String searchRooms(@RequestParam(required = false) String roomName, @RequestParam(required = false) String date, Model model) {
        List<Room> rooms;
        if (roomName != null && !roomName.isEmpty() && date != null && !date.isEmpty()) {
            rooms = roomRepository.findByRoomNameContainingAndDate(roomName, LocalDate.parse(date));
        } else if (roomName != null && !roomName.isEmpty()) {
            rooms = roomRepository.findByRoomNameContaining(roomName);
        } else {
            rooms = roomRepository.findAll();
        }
        model.addAttribute("rooms", rooms);
        return "rooms/list"; // Display the filtered list
    }
}

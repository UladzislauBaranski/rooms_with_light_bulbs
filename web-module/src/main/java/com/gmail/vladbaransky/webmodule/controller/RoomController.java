package com.gmail.vladbaransky.webmodule.controller;

import com.gmail.vladbaransky.repository.model.CountryEnum;
import com.gmail.vladbaransky.service.RoomService;
import com.gmail.vladbaransky.service.exception.AccessDeniedException;
import com.gmail.vladbaransky.service.model.RoomDTO;
import com.gmail.vladbaransky.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;

import static com.gmail.vladbaransky.webmodule.constant.DefaultValue.DEFAULT_PAGE;
import static com.gmail.vladbaransky.webmodule.constant.DefaultValue.KEY_FOR_SESSION;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public String getRoomsByPage(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page, Model model) {
        List<RoomDTO> rooms = roomService.getRoomsByPage(page);
        model.addAttribute("rooms", rooms);
        return "rooms";
    }

    @GetMapping("/add")
    public String addRoomPage(Model model) {
        model.addAttribute("room", new RoomDTO());
        model.addAttribute("countries", CountryEnum.values());
        return "add_room";
    }

    @PostMapping
    public String addNewRoom(
            @ModelAttribute(name = "room") @Valid RoomDTO room,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("room", room);
            model.addAttribute("countries", CountryEnum.values());
            return "add_room";
        } else {
            room.setLightIsEnable(true);
            RoomDTO roomDTO = roomService.addRoom(room);
            logger.info(roomDTO + " - was added");
        }
        return "redirect:/rooms";
    }

    @GetMapping("/{id}")
    public String getRoom(@PathVariable Long id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(KEY_FOR_SESSION);
        RoomDTO roomDTO;
        try {
            roomDTO = roomService.getRoomByIdWithVerifyUser(id, user);
        } catch (AccessDeniedException e) {
            e.getMessage();
            return "access_denied_page";
        }
        model.addAttribute("room", roomDTO);
        return "room_page";
    }

    @PostMapping("/enable")
    public String setStateTrue(@RequestParam Long id) {
        RoomDTO room = roomService.getRoomById(id);
        room.setLightIsEnable(true);
        RoomDTO roomDTO = roomService.updateRoom(room);
        logger.info(roomDTO + " - was updated");
        return "redirect:/rooms/" + id;
    }

    @PostMapping("/disable")
    public String setStateFalse(@RequestParam Long id) {
        RoomDTO room = roomService.getRoomById(id);
        room.setLightIsEnable(false);
        RoomDTO roomDTO = roomService.updateRoom(room);
        logger.info(roomDTO + " - was updated");
        return "redirect:/rooms/" + id;
    }
}

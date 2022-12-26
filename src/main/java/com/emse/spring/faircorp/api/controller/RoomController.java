package com.emse.spring.faircorp.api.controller;

import com.emse.spring.faircorp.api.dto.RoomDto;
import com.emse.spring.faircorp.api.dto.WindowDto;
import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.model.*;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A REST controller of the room.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/rooms")
@Transactional
public class RoomController {
    private final RoomDao roomDao;
    private final HeaterDao heaterDao;
    private final WindowDao windowDao;

    private final BuildingDao buildingDao;

    public RoomController(RoomDao roomDao, HeaterDao heaterDao, WindowDao windowDao, BuildingDao buildingDao) {
        this.roomDao = roomDao;
        this.heaterDao = heaterDao;
        this.windowDao = windowDao;
        this.buildingDao = buildingDao;
    }

    /**
     * A GET request fetch all the rooms.
     * @return List of RoomDto.
     */
    @GetMapping
    public List<RoomDto> findAll() {
        return roomDao.findAll().stream().map(RoomDto::new).collect(Collectors.toList());
    }

    /**
     * A Get request to fetch the room by id.
     * @return RoomDto.
     * @param id the id of the room to get.
     */
    @GetMapping(path = "/{id}")
    public RoomDto findById(@PathVariable Long id) {
        return roomDao.findById(id).map(RoomDto::new).orElse(null);
    }

    /**
     * A POST request to Create a room.
     * @return RoomDto.
     * @param dto a room with a constructor (id, current_temperature, windows, heaters).
     */
    @PostMapping
    public RoomDto create(@RequestBody RoomDto dto) {
        Room room = null;
        Set<Window> new_windows;
        Set<Heater> new_heaters;
        Building building = buildingDao.getReferenceById(dto.getBuildingId());
        // On creation id is not defined
        if (dto.getWindows()== null){
             new_windows=null;
        }else {
            new_windows = dto.getWindows().stream().map(window -> windowDao.findById(window.getId())).flatMap(Optional::stream).collect(Collectors.toSet());
        }

        if (dto.getHeaters()== null){
            new_heaters=null;
        }else {
            new_heaters = dto.getHeaters().stream().map(heater -> heaterDao.findById(heater.getId())).flatMap(Optional::stream).collect(Collectors.toSet());
        }

        if (dto.getId() == null) {
            room = roomDao.save(new Room(dto.getName(), dto.getFloor(), dto.getCurrent_temperature(), dto.getTarget_temperature(), new_heaters, new_windows, building));
        } else {
            room = roomDao.getReferenceById(dto.getId());
            room.setCurrent_temperature(dto.getCurrent_temperature());
        }
        return new RoomDto(room);
    }

    /**
     * A PUT request to switch the windows of a room.
     * @return RoomDto.
     * @param id the id of the room modify.
     */
    @PutMapping(path = "/{id}/switchWindow")
    public RoomDto switchWindowStatus(@PathVariable Long id) {
        Room room = roomDao.findById(id).orElseThrow(IllegalArgumentException::new);
        room.getWindows().stream().forEach(window -> window.setWindowStatus(window.getWindowStatus() == WindowStatus.OPEN ? WindowStatus.CLOSED: WindowStatus.OPEN));
        return new RoomDto(room);
    }

    /**
     * A PUT request to switch the heaters of a room.
     * @return RoomDto.
     * @param id the id of the room modify.
     */
    @PutMapping(path = "/{id}/switchHeater")
    public RoomDto switchHeaterStatus(@PathVariable Long id) {
        Room room = roomDao.findById(id).orElseThrow(IllegalArgumentException::new);
        room.getHeaters().stream().forEach(heater -> heater.setHeaterStatus(heater.getHeaterStatus() == HeaterStatus.ON ? HeaterStatus.OFF: HeaterStatus.ON));
        return new RoomDto(room);
    }

    /**
     * A Delete request to remove the room by id.
     * @param id the id of the room to delete.
     */
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {

        Room room=roomDao.getReferenceById(id);
        for (Heater heater:room.getHeaters() ){
            heaterDao.deleteById(heater.getId());
        }
        for (Window window:room.getWindows() ){
            windowDao.deleteById(window.getId());
        }
        roomDao.deleteById(id);
    }





}

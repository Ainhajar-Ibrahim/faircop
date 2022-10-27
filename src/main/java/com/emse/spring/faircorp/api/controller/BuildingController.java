package com.emse.spring.faircorp.api.controller;

import com.emse.spring.faircorp.api.dto.BuildingDto;
import com.emse.spring.faircorp.api.dto.RoomDto;
import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.model.*;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/buildings")
@Transactional
public class BuildingController {
    private final RoomDao roomDao;

    private final HeaterDao heaterDao;
    private final WindowDao windowDao;
    private final BuildingDao buildingDao;

    public BuildingController(RoomDao roomDao, HeaterDao heaterDao, WindowDao windowDao, BuildingDao buildingDao) {
        this.roomDao = roomDao;
        this.heaterDao = heaterDao;
        this.windowDao = windowDao;
        this.buildingDao=buildingDao;
    }

    @GetMapping
    public List<BuildingDto> findAll() {
        return buildingDao.findAll().stream().map(BuildingDto::new).collect(Collectors.toList());
    }
    @GetMapping(path = "/{id}")
    public BuildingDto findById(@PathVariable Long id) {
        return buildingDao.findById(id).map(BuildingDto::new).orElse(null);
    }

    @PostMapping
    public BuildingDto create(@RequestBody BuildingDto dto) {
        Building building = null;
        Set<Room> new_room;
        // On creation id is not defined

        if (dto.getRooms()== null){
            new_room=null;
        }else {
            new_room = dto.getRooms().stream().map(room -> roomDao.findById(room.getId())).flatMap(Optional::stream).collect(Collectors.toSet());
        }


        if (dto.getId() == null) {
            building = buildingDao.save(new Building(dto.getName(), dto.getAdress(),new_room));
        } else {
            building = buildingDao.getReferenceById(dto.getId());
            building.setName(dto.getName());
        }
        return new BuildingDto(building);
    }

    @PutMapping(path = "/{id}/switchtemproom")
    public BuildingDto switchRoomTemperature(@PathVariable Long id, @RequestParam RoomDto dto) {
        Building building = buildingDao.findById(id).orElseThrow(IllegalArgumentException::new);
        building.getRooms().stream().forEach(room -> room.setCurrent_temperature(dto.getCurrent_temperature()));
        return new BuildingDto(building);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {

        Building building=buildingDao.getReferenceById(id);
        building.getRooms().stream().forEach(room -> room.getWindows().stream().forEach(window -> windowDao.deleteById(window.getId())));
        building.getRooms().stream().forEach(room -> room.getHeaters().stream().forEach(heater -> heaterDao.deleteById(heater.getId())));
        building.getRooms().stream().forEach(room -> roomDao.deleteById(room.getId()));
        buildingDao.deleteById(id);
    }
}

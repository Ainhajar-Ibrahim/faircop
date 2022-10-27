package com.emse.spring.faircorp.api.dto;

import com.emse.spring.faircorp.model.Building;
import com.emse.spring.faircorp.model.Room;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.stream.Collectors;

public class BuildingDto {

    private Long id;


    private String name;


    private Set<RoomDto> rooms;


    private String adress;

    public BuildingDto(Long id, String name, Set<RoomDto> rooms, String adress) {
        this.id = id;
        this.name = name;
        this.rooms = rooms;
        this.adress = adress;
    }

    public BuildingDto() {

    }

    public BuildingDto(Building building) {
        this.id= building.getId();
        this.adress= building.getAdress();
        this.name= building.getName();
        if (building.getRooms()== null){
            this.rooms=null;
        }else {
            this.rooms=building.getRooms().stream().map(room -> new RoomDto(room.getId(),room.getName())).collect(Collectors.toSet());
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RoomDto> getRooms() {
        return rooms;
    }

    public void setRooms(Set<RoomDto> rooms) {
        this.rooms = rooms;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}

package com.emse.spring.faircorp.api.dto;

import com.emse.spring.faircorp.model.Room;

import java.util.Set;
import java.util.stream.Collectors;

public class RoomDto {
    private Long id;
    private String name;
    private Integer floor;
    private Double current_temperature;
    private Double target_temperature;
    private Set<HeaterDto> heaters;
    private Set<WindowDto> windows;

    private String buildingName;

    private Long buildingId;

    public RoomDto(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.floor = room.getFloor();
        this.current_temperature = room.getCurrent_temperature();
        this.target_temperature = room.getTarget_temperature();
        if (room.getWindows()== null){
            this.windows=null;
        }else {
            this.windows =room.getWindows().stream().map(window -> new WindowDto(window.getId(),window.getName())).collect(Collectors.toSet());
        }
        if (room.getHeaters()== null){
            this.heaters=null;
        }else {
            this.heaters = room.getHeaters().stream().map(heater -> new HeaterDto(heater.getId(),heater.getName())).collect(Collectors.toSet());
        }
        this.buildingId=room.getBuilding().getId();
        this.buildingName=room.getBuilding().getName();
    }

    public RoomDto() {
    }

    public RoomDto(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Double getCurrent_temperature() {
        return current_temperature;
    }

    public void setCurrent_temperature(Double current_temperature) {
        this.current_temperature = current_temperature;
    }

    public Double getTarget_temperature() {
        return target_temperature;
    }

    public void setTarget_temperature(Double target_temperature) {
        this.target_temperature = target_temperature;
    }

   public Set<HeaterDto> getHeaters() {
        return heaters;
    }

    public void setHeaters(Set<HeaterDto> heaters) {
        this.heaters = heaters;
    }

     public Set<WindowDto> getWindows() {
        return windows;
    }

    public void setWindows(Set<WindowDto> windows) {
        this.windows = windows;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }
}

package com.emse.spring.faircorp.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity that represents the room model.
 */
@Entity
@Table(name = "ROOM")
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private Integer floor;

    @Column()
    private Double current_temperature;

    @Column()
    private Double target_temperature;

    @OneToMany(mappedBy = "room")
    private Set<Heater> heaters;

    @OneToMany(mappedBy = "room")
    private Set<Window> windows;

    @ManyToOne
    private Building building;

    public Room(String name, Integer floor) {
        this.name = name;
        this.floor = floor;
    }

    public Room(String name, Integer floor, Double current_temperature, Double target_temperature, Set<Heater> heaters, Set<Window> windows, Building building) {
        this.name = name;
        this.floor = floor;
        this.current_temperature = current_temperature;
        this.target_temperature = target_temperature;
        this.heaters = heaters;
        this.windows = windows;
        this.building = building;

    }

    public Room() {
    }

    /**
     * A getter of the id of the room.
     * @return (Long) the id of the room.
     */
    public Long getId() {
        return id;
    }

    /**
     * A setter of the id of the room.
     * @param id the id of the room.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * A getter of the name of the room.
     * @return (Long) the name of the room.
     */
    public String getName() {
        return name;
    }

    /**
     * A setter of the name of the room.
     * @param name the name of the room.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A getter of the floor of the room.
     * @return (Integer) the floor of the room.
     */
    public Integer getFloor() {
        return floor;
    }

    /**
     * A setter of the floor of the room.
     * @param floor the floor of the room.
     */
    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    /**
     * A getter of the current temperature of the room.
     * @return (Double) the current temperature of the room.
     */
    public Double getCurrent_temperature() {
        return current_temperature;
    }

    /**
     * A setter of the  current temperature of the room.
     * @param  current_temperature the  current temperature of the room.
     */
    public void setCurrent_temperature(Double current_temperature) {
        this.current_temperature = current_temperature;
    }

    /**
     * A getter of the target temperature of the room.
     * @return (Double) the target temperature of the room.
     */
    public Double getTarget_temperature() {
        return target_temperature;
    }

    /**
     * A setter of the  target temperature of the room.
     * @param  target_temperature the target temperature of the room.
     */
    public void setTarget_temperature(Double target_temperature) {
        this.target_temperature = target_temperature;
    }

    /**
     * A getter of the list of the heaters of the room.
     * @return (Set) the list of the heaters of the room.
     */
    public Set<Heater> getHeaters() {
        return heaters;
    }

    /**
     * A setter of the heaters of the room.
     * @param  heaters the heaters of the room.
     */
    public void setHeaters(Set<Heater> heaters) {
        this.heaters = heaters;
    }

    /**
     * A getter of the list of the windows of the room.
     * @return (Set) the list of the windows of the room.
     */
    public Set<Window> getWindows() {
        return windows;
    }
    /**
     * A setter of the windows of the room.
     * @param  window the windows of the room.
     */

    public void setWindow(Set<Window> window) {
        this.windows = windows;
    }

    /**
     * A getter of the building where the room is.
     * @return (Building) he building where the room is.
     */
    public Building getBuilding() {
        return building;
    }

    /**
     * A setter of the building where the room is.
     * @param  building the building where the room is.
     */
    public void setBuilding(Building building) {
        this.building = building;
    }
}

package com.emse.spring.faircorp.model;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

/**
 * Entity that represents the building model.
 */
@Entity
@Table(name = "BUILDING")
public class Building {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false)
    private String name;

    @OneToMany(mappedBy = "building")
    private Set<Room> rooms;

    @Column
    private String adress;


    public Building(Long id, String name, Set<Room> rooms, String adress) {
        this.id = id;
        this.name = name;
        this.rooms = rooms;
        this.adress = adress;
    }

    public Building() {

    }



    public Building(String name, String adress, Set<Room> new_room) {
        this.name = name;
        this.rooms = new_room;
        this.adress = adress;
    }

    public Building(String name, String adress) {
        this.name = name;
        this.adress = adress;
    }

    /**
     * A getter of the id of the building.
     * @return (Long) the id of the building.
     */
    public Long getId() {
        return id;
    }

    /**
     * A setter of the id of the building.
     * @param id the id of the building.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * A getter of the name of the building.
     * @return (String) the name of the building.
     */
    public String getName() {
        return name;
    }

    /**
     * A setter of the name of the building.
     * @param name the name of the building.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A getter of the rooms of the building.
     * @return (Set) the rooms of the building.
     */
    public Set<Room> getRooms() {
        return rooms;
    }

    /**
     * A setter of the rooms of the building.
     * @param rooms the rooms of the building.
     */
    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * A getter of the Adress of the building.
     * @return (String) the Adress of the building.
     */
    public String getAdress() {
        return adress;
    }

    /**
     * A setter of the Adress of the building.
     * @param adress the adress of the building.
     */
    public void setAdress(String adress) {
        this.adress = adress;
    }
}

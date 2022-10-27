package com.emse.spring.faircorp.model;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

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

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}

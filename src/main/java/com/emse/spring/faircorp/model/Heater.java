package com.emse.spring.faircorp.model;

import javax.persistence.*;

/**
 * Entity that represents the heater model.
 */
@Entity
@Table(name = "HEATER")
public class Heater {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column
    private Long power;

    public Heater(String name, Room room, HeaterStatus heaterStatus, Long power) {
        this.name = name;
        this.room = room;
        this.heaterStatus = heaterStatus;
        this.power=power;
    }

    @ManyToOne
    private Room room;

    @Enumerated(EnumType.STRING)
    private HeaterStatus heaterStatus;

    public Heater() {
    }

    public Heater(String name,Long power, HeaterStatus status) {
        this.heaterStatus = status;
        this.name = name;
        this.power = power;
    }
    /**
     * A getter of the id of the heater.
     * @return (Long) the id of the heater.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * A setter of the id of the heater.
     * @param id the id of the heater.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * A getter of the name of the heater.
     * @return (Long) the name of the heater.
     */
    public String getName() {
        return name;
    }

    /**
     * A setter of the name of the heater.
     * @param name the name of the heater.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A getter of the power of the heater.
     * @return (Long) the power of the heater.
     */
    public Long getPower() {
        return power;
    }

    /**
     * A setter of the power of the heater.
     * @param power the power of the heater.
     */
    public void setPower(Long power) {
        this.power = power;
    }
    /**
     * A getter of the room where the heater is.
     * @return (Room) the room where the heater is.
     */
    public Room getRoom() {
        return room;
    }

    /**
     * A setter of the room where the heater is.
     * @param room the room where the heater is.
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * A getter of the status of the heater.
     * @return (HeaterStatus) the status of the heater.
     */
    public HeaterStatus getHeaterStatus() {
        return heaterStatus;
    }

    /**
     * A setter of the heaterStatus of the heater.
     * @param heaterStatus the heaterStatus of the heater.
     */
    public void setHeaterStatus(HeaterStatus heaterStatus) {
        this.heaterStatus = heaterStatus;
    }
}

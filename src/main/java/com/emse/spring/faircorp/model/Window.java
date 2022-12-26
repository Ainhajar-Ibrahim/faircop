package com.emse.spring.faircorp.model;


import javax.persistence.*;

/**
 * Entity that represents the window model.
 */
@Entity
@Table(name = "RWINDOW")
public class Window {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false)
    private String name;

    @Enumerated(EnumType.STRING)
    private WindowStatus windowStatus;


    @ManyToOne
    private Room room;

    public Window(String name, WindowStatus windowStatus, Room room) {
        this.name = name;
        this.windowStatus = windowStatus;
        this.room = room;
    }

    public Window() {
    }

    public Window(String name, WindowStatus status) {
        this.windowStatus = status;
        this.name = name;
    }

    /**
     * A getter of the id of the window.
     * @return (Long) the id of the window.
     */
    public Long getId() {
        return this.id;
    }
    /**
     * A setter of the id of the window.
     * @param id the id of the window.
     */

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * A getter of the name of the window.
     * @return (Long) the name of the window.
     */
    public String getName() {
        return name;
    }

    /**
     * A setter of the name of the window.
     * @param name the name of the window.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A getter of the status of the window.
     * @return (WindowStatus) the status of the window.
     */
    public WindowStatus getWindowStatus() {
        return windowStatus;
    }
    /**
     * A setter of the windowStatus of the window.
     * @param windowStatus the heaterStatus of the window.
     */

    public void setWindowStatus(WindowStatus windowStatus) {
        this.windowStatus = windowStatus;
    }

    /**
     * A getter of the room where the window is.
     * @return (Room) the room where the window is.
     */
    public Room getRoom() {
        return room;
    }

    /**
     * A setter of the room where the window is.
     * @param room the room where the window is.
     */
    public void setRoom(Room room) {
        this.room = room;
    }
}
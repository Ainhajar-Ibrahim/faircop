package com.emse.spring.faircorp.model;

import javax.persistence.*;

@Entity
@Table(name = "RWINDOW")
public class Heater {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false)
    private String name;

    @Enumerated(EnumType.STRING)
    private HeaterStatus heaterStatus;

    public Heater() {
    }

    public Heater(String name, HeaterStatus status) {
        this.heaterStatus = status;
        this.name = name;
    }

    public Long getId() {
        return this.id;
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

    public HeaterStatus getHeaterStatus() {
        return heaterStatus;
    }

    public void setHeaterStatus(HeaterStatus heaterStatus) {
        this.heaterStatus = heaterStatus;
    }
}

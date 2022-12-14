package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Heater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HeaterDao extends JpaRepository<Heater, Long>, HeaterDaoCustom {
    @Query("select c from Heater c where c.name=:name")
    Heater findByName(@Param("name") String name);
    @Query("select c from Heater c where c.id=:id")
    Heater getReferenceById(@Param("id") Long id);
}

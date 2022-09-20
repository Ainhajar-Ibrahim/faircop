package com.emse.spring.faircorp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class HeaterDaoCustomImpl implements HeaterDaoCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void deleteHeatersbyRoom(Long id) {
        String jpql = "delete from Heater w where w.room.id = :id";
        em.createQuery(jpql)
                .setParameter("id", id).executeUpdate();
    }
}

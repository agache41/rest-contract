package model.spring.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class DataAccess {

    @PersistenceContext
    private EntityManager entityManager;
}

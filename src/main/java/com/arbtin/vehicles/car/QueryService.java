package com.arbtin.vehicles.car;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class QueryService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<CarRecord> findAllBooks() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CarRecord> query = cb.createQuery(CarRecord.class);
        Root<Car> root = query.from(Car.class);
        query.select(cb.construct(CarRecord.class, root.get("id"), root.get("frame"), root.get("operator")));
        return entityManager.createQuery(query).getResultList();
    }

    public CarRecord findCarByFrame(String frame) {
        TypedQuery<CarRecord> query = entityManager
                .createQuery("SELECT new com.arbtin.vehicles.car.CarRecord(c.id, c.frame, c.operator) " +
                        "FROM Car c WHERE c.frame = :frame", CarRecord.class);
        query.setParameter("frame", frame);
        return query.getSingleResult();
    }
}


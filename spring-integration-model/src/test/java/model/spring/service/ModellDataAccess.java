package model.spring.service;

import io.github.agache41.rest.contract.dataAccess.DataAccess;
import io.github.agache41.rest.contract.entities.Modell;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

//@Service
public class ModellDataAccess extends DataAccess<Modell,Long> {

    public ModellDataAccess() {
        super(Modell.class, Long.class);
    }

    @PersistenceContext(/*unitName = "rest-contract-core-test"*/)
    private EntityManager entityManager;
}

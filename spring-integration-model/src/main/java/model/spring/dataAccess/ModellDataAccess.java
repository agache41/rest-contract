package model.spring.dataAccess;


import io.github.agache41.rest.contract.dataAccess.DataAccess;
import io.github.agache41.rest.contract.entities.Modell;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModellDataAccess extends DataAccess<Modell, Long> {

    public ModellDataAccess() {
        super(Modell.class, Long.class);
    }

    public List<Modell> getAllModellsOver100() {
        CriteriaBuilder criteriaBuilder = em().getCriteriaBuilder();
        CriteriaQuery<Modell> query = criteriaBuilder.createQuery(type);
        Root<Modell> entity = query.from(type);
        return em().createQuery(query.select(entity)
                                     .where(criteriaBuilder.greaterThan(entity.get("age"), 100L)))
                   .getResultList();
    }


//    public List<Modell> getAllModellsOver100() {
//        return em().createQuery(" select t from Modell where t.age > 100")
//                   .getResultList();
//    }
}

package modell.quarkus.dataAccess;


import io.github.agache41.rest.contract.dataAccess.DataAccess;
import io.github.agache41.rest.contract.entities.Modell;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.util.List;

@Dependent
public class ModellDataAccess extends DataAccess<Modell, Long> {
    @Inject
    public ModellDataAccess() {
        super(Modell.class, Long.class);
    }

//    public List<Modell> getAllModellsOver100() {
//        CriteriaBuilder criteriaBuilder = em().getCriteriaBuilder();
//        CriteriaQuery<Modell> query = criteriaBuilder.createQuery(type);
//        Root<Modell> entity = query.from(type);
//        return em().createQuery(query.select(entity)
//                                     .where(criteriaBuilder.greaterThan(entity.get("age"), 100L)))
//                   .getResultList();
//    }


    public List<Modell> getAllModellsOver100() {
        return em().createQuery(" select t from Modell where t.age > 100")
                   .getResultList();
    }
}

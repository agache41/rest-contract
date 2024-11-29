package modell.quarkus.service;

import io.github.agache41.rest.contract.entities.ModellFks;
import io.github.agache41.rest.contract.entities.SubModellAFks;
import io.github.agache41.rest.contract.producer.Producer;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImplTest;
import io.github.agache41.rest.contract.resourceService.ResourceService;
import io.github.agache41.rest.contract.resourceService.ResourceServiceTestClient;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
//import modell.quarkus.entities.ModellFks;
//import modell.quarkus.entities.SubModellAFks;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@Transactional
public class ModellFksResourceServiceTest extends AbstractResourceServiceImplTest<ModellFks, Long> {

    static final String path = "/modellFks";
    private static final String stringField = "name";
    private static final Producer<ModellFks> producer;
    private static final List<ModellFks> insertData;
    private static final List<ModellFks> updateData;

//    static {
//        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//    }

    static {
        producer = Producer.ofClass(ModellFks.class)
                           .withList(LinkedList::new)
                           .withMap(LinkedHashMap::new)
                           .withSize(Config.collectionSize);
        insertData = producer.produceList();
        updateData = producer.changeList(insertData);
    }

    public ModellFksResourceServiceTest() {
        super(ModellFks.class, //
              path, //
              insertData, //
              updateData, //
              stringField, //
              producer); //
    }

    @Test
    @Order(200)
    public void testCascade() {
        final List<ModellFks> res = this.getClient()
                                        .postListAsList(insertData);
        assertEquals(res.size(), insertData.size());
        super.deleteAll();
        ResourceService subModellFksResourceService = new ResourceServiceTestClient(SubModellAFks.class, "/subModellAFks");
        final List<SubModellAFks> subModellFksList = subModellFksResourceService.getAllAsList(subModellFksResourceService.getConfig()
                                                                                                                         .getFirstResult(), subModellFksResourceService.getConfig()
                                                                                                                                                                       .getMaxResults(), null);
        assertEquals(0, subModellFksList.size());
    }
}

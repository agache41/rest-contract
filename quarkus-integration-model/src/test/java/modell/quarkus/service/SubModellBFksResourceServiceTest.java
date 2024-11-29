package modell.quarkus.service;

import io.github.agache41.rest.contract.entities.SubModellBFks;
import io.github.agache41.rest.contract.producer.Producer;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImplTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
//import modell.quarkus.entities.SubModellBFks;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;


@QuarkusTest
@Transactional
public class SubModellBFksResourceServiceTest extends AbstractResourceServiceImplTest<SubModellBFks, Long> {

    static final String path = "/subModellAFks";
    private static final String stringField = "subName";
    private static final Producer<SubModellBFks> producer;
    private static final List<SubModellBFks> insertData;
    private static final List<SubModellBFks> updateData;

//    static {
//        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//    }

    static {
        producer = Producer.ofClass(SubModellBFks.class)
                           .withList(LinkedList::new)
                           .withMap(LinkedHashMap::new)
                           .withSize(Config.collectionSize);
        insertData = producer.produceList();
        updateData = producer.changeList(insertData);
    }

    public SubModellBFksResourceServiceTest() {
        super(SubModellBFks.class, //
              path, //
              insertData, //
              updateData, //
              stringField, //
              producer); //
    }
}

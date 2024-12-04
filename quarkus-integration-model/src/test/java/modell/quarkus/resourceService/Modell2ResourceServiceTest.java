package modell.quarkus.resourceService;

import io.github.agache41.rest.contract.entities.Modell2;
import io.github.agache41.rest.contract.producer.Producer;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImplTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

@QuarkusTest
@Transactional
public class Modell2ResourceServiceTest extends AbstractResourceServiceImplTest<Modell2, String> {

    static final String path = "/modell2";
    private static final String stringField = "name";
    private static final Producer<Modell2> producer;
    private static final List<Modell2> insertData;
    private static final List<Modell2> updateData;

//    static {
//        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//    }

    static {
        producer = Producer.ofClass(Modell2.class)
                           .withList(LinkedList::new)
                           .withMap(LinkedHashMap::new)
                           .withSize(Config.collectionSize);
        insertData = producer.produceList();
        updateData = producer.changeList(insertData);
    }

    public Modell2ResourceServiceTest() {
        super(Modell2.class, //
                path, //
                insertData, //
                updateData, //
                stringField,//
                producer); //
    }
}

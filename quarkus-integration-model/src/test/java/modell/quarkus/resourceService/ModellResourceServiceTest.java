package modell.quarkus.resourceService;

import io.github.agache41.rest.contract.entities.Modell;
import io.github.agache41.rest.contract.producer.Producer;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImplTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
//import modell.quarkus.entities.Modell;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

@QuarkusTest
@Transactional
public class ModellResourceServiceTest extends AbstractResourceServiceImplTest<Modell, Long> {

    static final String path = "/modell";
    private static final String stringField = "stringVal";
    private static final Producer<Modell> producer;
    private static final List<Modell> insertData;
    private static final List<Modell> updateData;

//    static {
//        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//    }

    static {
        producer = Producer.ofClass(Modell.class)
                           .withList(LinkedList::new)
                           .withMap(LinkedHashMap::new)
                           .withSize(Config.collectionSize);
        insertData = producer.produceList();
        updateData = producer.changeList(insertData);
    }

    public ModellResourceServiceTest() {
        super(Modell.class, //
              path, //
              insertData, //
              updateData, //
              stringField,//
              producer); //
    }
}

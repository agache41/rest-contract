package model.spring.resourceService;

import io.github.agache41.rest.contract.configuration.RestContractCoreTestPersistenceConfiguration;
import io.github.agache41.rest.contract.entities.Modell2;
import io.github.agache41.rest.contract.producer.Producer;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImplTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@Import(RestContractCoreTestPersistenceConfiguration.class)
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

package model.spring.resourceService;

import io.github.agache41.rest.contract.configuration.RestContractCoreTestPersistenceConfiguration;
import io.github.agache41.rest.contract.entities.SubModellAFks;
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
public class SubModellAFksResourceServiceTest extends AbstractResourceServiceImplTest<SubModellAFks, Long> {

    static final String path = "/subModellAFks";
    private static final String stringField = "subName";
    private static final Producer<SubModellAFks> producer;
    private static final List<SubModellAFks> insertData;
    private static final List<SubModellAFks> updateData;

//    static {
//        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//    }

    static {
        producer = Producer.ofClass(SubModellAFks.class)
                           .withList(LinkedList::new)
                           .withMap(LinkedHashMap::new)
                           .withSize(Config.collectionSize);
        insertData = producer.produceList();
        updateData = producer.changeList(insertData);
    }

    public SubModellAFksResourceServiceTest() {
        super(SubModellAFks.class, //
                path, //
                insertData, //
                updateData, //
                stringField, //
                producer); //
    }
}

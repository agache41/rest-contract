package model.spring.resourceService;

import io.github.agache41.rest.contract.configuration.RestContractCoreTestPersistenceConfiguration;
import io.github.agache41.rest.contract.entities.SubModellBFks;
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

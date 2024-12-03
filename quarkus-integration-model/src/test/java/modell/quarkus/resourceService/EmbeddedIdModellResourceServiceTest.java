package modell.quarkus.resourceService;

import io.github.agache41.rest.contract.entities.EmbeddedIdModell;
import io.github.agache41.rest.contract.entities.EmbeddedIdSubModell2;
import io.github.agache41.rest.contract.entities.EmbeddedKeys;
import io.github.agache41.rest.contract.producer.Producer;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImplTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
//import modell.quarkus.entities.EmbeddedIdModell;
//import modell.quarkus.entities.EmbeddedIdSubModell2;
//import modell.quarkus.entities.EmbeddedKeys;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@QuarkusTest
@Transactional
public class EmbeddedIdModellResourceServiceTest extends AbstractResourceServiceImplTest<EmbeddedIdModell, EmbeddedKeys> {

    static final String path = "/embeddedIdModell";
    private static final String stringField = "stringVal";
    private static final Producer<EmbeddedIdModell> producer;
    private static final List<EmbeddedIdModell> insertData;
    private static final List<EmbeddedIdModell> updateData;

    private static final Consumer<EmbeddedIdModell> havingPK = embeddedIdModell -> {
        EmbeddedKeys id = embeddedIdModell.getId();
        embeddedIdModell.getEmbeddedIdSubModells1()
                        .stream()
                        .forEach(embeddedIdSubModell1 -> {
                            embeddedIdSubModell1.setKey1(id.getKey1());
                            embeddedIdSubModell1.setKey2(id.getKey2());
                            embeddedIdSubModell1.setKey3(id.getKey3());
                        });
        embeddedIdModell.getEmbeddedIdSubModells2()
                        .stream()
                        .map(EmbeddedIdSubModell2::getId)
                        .forEach(id1 -> {
                            id1.setKey1(id.getKey1());
                            id1.setKey2(id.getKey2());
                            id1.setKey3(id.getKey3());
                        });
        if (embeddedIdModell.getEmbeddedIdSubModell3() == null) {
            return;
        }
        EmbeddedKeys id1 = embeddedIdModell.getEmbeddedIdSubModell3()
                                           .getId();
        id1.setKey1(id.getKey1());
        id1.setKey2(id.getKey2());
        id1.setKey3(id.getKey3());
    };

//    static {
//        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//    }

    static {
        producer = Producer.ofClass(EmbeddedIdModell.class)
                           .withPostProduce(havingPK)
                           .withPostChange(havingPK)
                           .withList(LinkedList::new)
                           .withMap(LinkedHashMap::new)
                           .withSize(Config.collectionSize);
        insertData = producer.produceList();
        updateData = producer.changeList(insertData);
        //setPK(insertData);
        //setPK(updateData);
    }

    public EmbeddedIdModellResourceServiceTest() {
        super(EmbeddedIdModell.class, //
              path, //
              insertData, //
              updateData, //
              stringField, //
              producer); //
    }

    private static void setPK(final List<EmbeddedIdModell> data) {
        data.forEach(embeddedIdModell -> {
            embeddedIdModell.getEmbeddedIdSubModells1()
                            .stream()
                            .forEach(embeddedIdSubModell1 -> {
                                embeddedIdSubModell1.setKey1(embeddedIdModell.getId()
                                                                             .getKey1());
                                embeddedIdSubModell1.setKey2(embeddedIdModell.getId()
                                                                             .getKey2());
                                embeddedIdSubModell1.setKey3(embeddedIdModell.getId()
                                                                             .getKey3());
                            });
            embeddedIdModell.getEmbeddedIdSubModells2()
                            .stream()
                            .forEach(embeddedIdSubModell2 -> {
                                embeddedIdSubModell2.getId()
                                                    .setKey1(embeddedIdModell.getId()
                                                                             .getKey1());
                                embeddedIdSubModell2.getId()
                                                    .setKey2(embeddedIdModell.getId()
                                                                             .getKey2());
                                embeddedIdSubModell2.getId()
                                                    .setKey3(embeddedIdModell.getId()
                                                                             .getKey3());
                            });
            embeddedIdModell.getEmbeddedIdSubModell3()
                            .getId()
                            .setKey1(embeddedIdModell.getId()
                                                     .getKey1());
            embeddedIdModell.getEmbeddedIdSubModell3()
                            .getId()
                            .setKey2(embeddedIdModell.getId()
                                                     .getKey2());
            embeddedIdModell.getEmbeddedIdSubModell3()
                            .getId()
                            .setKey3(embeddedIdModell.getId()
                                                     .getKey3());

        });
    }
}

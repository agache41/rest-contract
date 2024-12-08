# REST Contract

REST Contract API and Data Access over JPA for Quarkus(JEE) or SpringBoot

**REST Contract** is a [Java](https://en.wikipedia.org/wiki/Java_(programming_language)) library targeted on
creating generic [REST](https://en.wikipedia.org/wiki/REST) Resource Services and Data Access Layers on top
of [JPA](https://en.wikipedia.org/wiki/Jakarta_Persistence)
using [CDI](https://docs.oracle.com/javaee/6/tutorial/doc/giwhl.html) or [Spring](https://spring.io/).

It develops REST Services starting from
the [CRUD](https://en.wikipedia.org/wiki/Create,_read,_update_and_delete) pattern and extends it by adding frequently
needed methods to each domain entity.

The idea behind it is that just by adding these generic services on each entity of your database you get a general 70 -
90% implementation of the services needed by the application, thus allowing the developer to focus just on the complex
cases, in other words removing as much as "boilerplate code" as possible.

And that comes with already developed [test units](https://junit.org/junit5/) that bring 100% coverage to
all provided service methods.

The library can be very easily extended to add more functionality by reusing and extending the provided components.

Initially designed for [Quarkus](https://quarkus.io/) it can also be used in
any [JEE](https://en.wikipedia.org/wiki/Jakarta_EE)( [CDI](https://docs.oracle.com/javaee/6/tutorial/doc/giwhl.html) / [JPA](https://en.wikipedia.org/wiki/Jakarta_Persistence))
or [SpringBoot](https://spring.io/projects/spring-boot) / [JPA](https://en.wikipedia.org/wiki/Jakarta_Persistence)
compliant environment.

- [Quick start](#quick-start)
    - [Entity](#entity)
    - [Resource Service](#resource-service)
    - [Updating](#updating)
    - [Data Access](#data-access)
    - [Resource Service again](#resource-service-again)
    - [Testing](#testing)
    - [Testing my own methods](#testing-my-own-methods)
- [Demo](#demo)
- [Requirements](#requirements)
- [Installation](#installation)
- [Features](#features)
- [Structure](#structure)

## Quick start

Let's start with a database table named Modell and the associated JPA Entity.

### Entity

Let the **entity** implement
the [PrimaryKey](rest-contract-core/src/main/java/io/github/agache41/rest/contract/dataAccessBase/PrimaryKey.java)
and
the [SelfTransferObject](rest-contract-core/src/main/java/io/github/agache41/rest/contract/update/SelfTransferObject.java)
interfaces :

```java

@Data
@NoArgsConstructor
@Entity
public class Modell implements PrimaryKey<Long>, SelfTransferObject<Modell> {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Update
    private String name;

    @Update(dynamic = false)
    private String street;

    @Update(dynamic = false)
    private Integer number;

    @EqualsAndHashCode.Exclude
    private long age;

}
```

Notice the used @Data annotation from [Lombok](https://projectlombok.org/).

### Resource Service

Extend your **resource service**
from [AbstractResourceServiceImpl](quarkus-rest-contract/src/main/java/io/github/agache41/rest/contract/resourceService/AbstractResourceServiceImpl.java).

in [Quarkus](quarkus-integration-model/src/main/java/modell/quarkus/resourceService/ModellResourceService.java):

```java

@Path("/modell")
public class ModellResourceService extends AbstractResourceServiceImpl<Modell, Modell, Long> {
}
```

or [SpringBoot](spring-integration-model/src/main/java/model/spring/resourceService/ModellResourceService.java):

```java

@Getter
@RestController
@RequestMapping("/modell")
public class ModellResourceService extends AbstractResourceServiceImpl<Modell, Modell, Long> {

    @Autowired
    protected DataAccess<Modell, Long> dataAccess;


    @Autowired
    protected DataBinder<Modell, Modell, Long> dataBinder;
}
```

and ... you're pretty much done. More Infos [here](https://quarkus.io/guides/rest-json)
or [here](https://spring.io/guides/gs/rest-service).

For the **Modell** entity the following REST services are available :

- GET /modell/{id} - finds and returns the corresponding entity for the given path id.
- POST /modell/byId - finds and returns the corresponding entity for the given body id using POST.
- GET /modell/all/asList - returns all the entities for the given table.**Filter Parameters** and **Pagination
  Parameters** can be used to filter the list.
- GET /modell/byIds/{ids}/asList - finds and returns the corresponding entity for the given list of id's.
- POST /modell/byIds/asList - finds and returns the corresponding entity for the given list of id's in the body using
  POST.
- GET /modell/filter/{stringField}/equals/{value}/asList - finds all entities whose value in a specified string field is
  equal to the given value. **Pagination Parameters** can be applied.
- GET /modell/filter/{stringField}/like/{value}/asList - finds all entities whose value in a specified field is like the
  given path value. **Pagination Parameters** can be applied.
- GET /modell/filter/{stringField}/in/{values}/asList - finds all entities whose value in a specified field is in the
  given path values list. **Pagination Parameters** can be applied.
- GET /modell/autocomplete/{stringField}/like/{value}/asSortedSet - finds all values in a field whose value is like the
  given value.**Autocomplete Parameters**,**Filter Parameters** and **Pagination Parameters** can be applied.
- GET /modell/autocompleteIds/{stringField}/like/{value}/asList - finds all entities whose value in a field is like the
  given value, groups them, and returns for each a group of ids with the corresponding id.**Autocomplete Parameters**,*
  *Filter Parameters** and **Pagination Parameters** can be used to filter the list.
- POST /modell/filter/content/equals/value/asList - finds all entities that equals a given body content object. *
  *Pagination Parameters** can be applied.
- POST /modell/filter/content/in/values/asList - finds all entities that are in a given body content list of given
  values. **Pagination Parameters** can be applied.
- POST /modell/ - inserts a new entity in the database.
- POST /modell/list/asList - inserts a list of new entities in the database.
- PUT /modell/ - updates an existing entity by id.
- PUT /modell/list/asList - updates existing entities by id.
- DELETE /modell/{id}/ - deletes the entity for the given id.
- DELETE /modell/byIds - deletes all the entities for the given ids in the request body
- DELETE /modell/byIds/{ids} - deletes all the entities for the given ids.

**Pagination Parameters**

For every request returning a list items with a variable count two parameters can be used for pagination or simply to
limit the result count.

- firstResult - position of the first result, numbered from 0. If unspecified, it defaults to 0 or it can be configured
  using
  the [ResourceServiceConfig](rest-contract-core/src/main/java/io/github/agache41/rest/contract/resourceServiceBase/ResourceServiceConfig.java)
- maxResults - maximum number of results to retrieve. If unspecified, it defaults to 256 or it can be configured using
  the [ResourceServiceConfig](rest-contract-core/src/main/java/io/github/agache41/rest/contract/resourceServiceBase/ResourceServiceConfig.java)
  Examples:
- GET /modell/all/asList?firstResult=0&maxResults=100 - return a maximum 100 results list.
- GET /modell/all/asList?firstResult=40&maxResults=10 - returns the page 5 out of a 10 per page sequence.

**Filter Parameters**

When using filter parameters the query can be appended with column based values to filter for. Also orderBy parameters
can be added.
Examples:

- GET /modell/all/asList?number=10 - returns all the models that have number 10
- GET /modell/all/asList?number=10&number=12 - returns all the models that have number 10 or 12
- GET /modell/all/asList?orderBy=number - returns all the models, ordered by number
- GET /modell/all/asList?orderBy=number desc - returns all the models, ordered by number descending order

**Autocomplete Parameters**

When using autocomplete type Queries two parameters come into action:

- cut - the minimum character input count for the query to produce results. If unspecified, it defaults to 3 or it can
  be configured using
  the [ResourceServiceConfig](rest-contract-core/src/main/java/io/github/agache41/rest/contract/resourceServiceBase/ResourceServiceConfig.java)
- maxResults - maximum number of results to retrieve. If unspecified, it defaults to 16 or it can be configured using
  the [ResourceServiceConfig](rest-contract-core/src/main/java/io/github/agache41/rest/contract/resourceServiceBase/ResourceServiceConfig.java)

### Updating

What does the [@Update](rest-contract-core/src/main/java/io/github/agache41/rest/contract/update/Update.java)
annotation do ?

The Resource Service uses the entity as both [DAO](https://en.wikipedia.org/wiki/Data_access_object)
and [DTO](https://en.wikipedia.org/wiki/Data_transfer_object). Upon update though it is important to be able to
configure which fields participate in the update process and how null values impact that. The  [@Update](rest-contract-core/src/main/java/io/github/agache41/rest/contract/update/Update.java) annotation marks the fields accordingly. 
#### Using @Update
The Annotation can be used on every field or only once on the class.

When a field is annotated, it will be updated from the provided source during a PUT or POST operation.

When used on the class, all fields will be updated, except the ones annotated with [@Update.excluded](rest-contract-core/src/main/java/io/github/agache41/rest/contract/update/Update.java) annotation.

If a field is not annotated (or excluded), it will not participate in the update process. That is for example the case for
the id field and for our last field in the example (age).

#### Dynamic Update
A dynamic Update means that during the update process, the request contains only the fields that are to be changed.
During the dynamic update the value of a field can not be set to null, so if a null value is received, it will be ignored.
This is the default case using the [@Update](rest-contract-core/src/main/java/io/github/agache41/rest/contract/update/Update.java) annotation, or [@Update](rest-contract-core/src/main/java/io/github/agache41/rest/contract/update/Update.java)(dynamic = true)

If we decide for a field to not use dynamic update, this can be enforced with [@Update](rest-contract-core/src/main/java/io/github/agache41/rest/contract/update/Update.java)(dynamic = false).
For non-nullable fields this can lead though, that the field value is actually set to null, leading to a runtime-exception.
So the actual value of the field must be always received in the Request.

As a best practice the non-dynamic update is generally recommended to be used on all the fields of the entity.
Then the update source transfer object must be always complete.

#### Child Entities
One-to-one relations pointing to a child-entity can also participate in the update process.
The @Update entity must be used on the child-entity field.
The child entity must implement the required interfaces and its fields have to be marked for the update accordingly with the @Update annotation. 

#### Updating collections and maps
One-to-many relations and @ElementCollection can also participate in the update process and the previous rules apply.
For non-entity collections and maps the update will be applied completely, the current content will be removed and the new content from the request will be inserted.
For child-entity collections and maps the algorithm is more complex:
- the child-entities having the same id in the request and in the database will each be updated with its counterpart matching the id.
- the child-entities that are missing (do not have an id in the request) will be deleted.
- the child-entities having a new id (or no id for autogenerated ids) will be inserted.
- partial inserts or updates are not available. So is also the case for targeted deletes. 
- for the algorithm to work correctly, all collections and maps must be initialized accordingly and nulls must be avoided. 
- for dynamic updates if the field in the incoming request is null, then no changes will be done to the collection.  

The best practice here is though to update these collection entities through a dedicated REST service belonging to the child-entity.   

#### Interfaces
Every entity participating in the update process must implement the [SelfTransferObject](rest-contract-core/src/main/java/io/github/agache41/rest/contract/update/SelfTransferObject.java) interface.
The entity must also implement the [PrimaryKey](rest-contract-core/src/main/java/io/github/agache41/rest/contract/dataAccessBase/PrimaryKey.java) interface and provide a unique id field.
If the primary key of the table is composed of several database columns, [@EmbeddedId](https://jakarta.ee/specifications/persistence/3.2/apidocs/jakarta.persistence/jakarta/persistence/embeddedid) can be used like [here](rest-contract-core/src/test/java/io/github/agache41/rest/contract/entities/EmbeddedIdModell.java).

### Data Access

Extending the [DAO](https://en.wikipedia.org/wiki/Data_access_object) layer

In complex cases the **Data Access** of the entity must be extended, by adding the new data methods.
Let's start by extending [DataAccess](src/main/java/io/klebrit/generic/api/dataAccess/DataAccess.java).

in [Quarkus](quarkus-integration-model/src/main/java/modell/quarkus/dataAccess/ModellDataAccess.java):

```java

@Dependent
public class ModellDataAccess extends DataAccess<Modell, Long> {
    @Inject
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

}

```

or [SpringBoot](spring-integration-model/src/main/java/model/spring/dataAccess/ModellDataAccess.java):

```java

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
}
```

The method getAllModellsOver100() uses the underlining em() method to access
the available [EntityManager](https://docs.oracle.com/javaee%2F7%2Fapi%2F%2F/javax/persistence/EntityManager.html) and
builds the query using
the [CriteriaBuilder](https://docs.jboss.org/hibernate/stable/entitymanager/reference/en/html/querycriteria.html).

Or if a [JPQL](https://en.wikibooks.org/wiki/Java_Persistence/JPQL) approach is to be considered :

```java
// ...  //
public class ModellDataAccess extends DataAccess<Modell, Long> {
    // ... //
    public List<Modell> getAllModellsOver100() {
        return em().createQuery(" select t from Modell where t.age > 100")
                   .getResultList();
    }
}
```

### Resource Service again

Now let's use this newly ModellDataAccess in our Resource Service.

in [Quarkus](quarkus-integration-model/src/main/java/modell/quarkus/resourceService/ModellResourceService.java):
```java

@Getter
@Path("/modell")
public class ModellResourceService extends AbstractResourceServiceImpl<Modell, Long> {

    @Inject
    ModellDataAccess dataAccess;

    /**
     * Finds and returns all the models over 100
     *
     * @return the models list.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/over/100")
    public List<Modell> getAllModellsOver100() {
        return this.getDataAccess()
                   .getAllModellsOver100();
    }
}

```
or [SpringBoot](spring-integration-model/src/main/java/model/spring/resourceService/ModellResourceService.java):
```java

@Getter
@RestController
@RequestMapping("/modell")
public class ModellResourceService extends AbstractResourceServiceImpl<Modell, Modell, Long> {


  @Autowired
  protected ModellDataAccess dataAccess;


  @Autowired
  protected DataBinder<Modell, Modell, Long> dataBinder;


  /**
   * Finds and returns all the models over 100
   *
   * @return the models list.
   */
  @GetMapping(path = "/over/100", produces = APPLICATION_JSON_VALUE)
  public List<Modell> getOver100AsList() {
    return getDataAccess().getAllModellsOver100();
  }
}

```
and we're ready to go:

- [GET] /modell/over/100 - Finds and returns all the models over 100

Please do notice the this.getDataAccess() method that gets overridden behind the scenes
with [Lombok](https://projectlombok.org/)

### Testing

So far so good. But how can I be sure that the generated services do really work on my platform or with my entities ?
Not to mention that there are already 17 methods in the service, and that goes for each entity.

Let's start by creating the **TestUnit** by extending  [AbstractResourceServiceImplTest](quarkus-rest-contract/src/main/java/io/github/agache41/rest/contract/resourceService/AbstractResourceServiceImpl.java).

in [Quarkus](quarkus-integration-model/src/test/java/modell/quarkus/resourceService/ModellResourceServiceTest.java):
```java

@QuarkusTest
@Transactional
public class ModellResourceServiceTest extends AbstractResourceServiceImplTest<Modell, Long> {

    static final String path = "/modell";
    private static final String stringField = "stringVal";
    private static final Producer<Modell> producer;
    private static final List<Modell> insertData;
    private static final List<Modell> updateData;

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

```

or [Springboot](spring-integration-model/src/test/java/model/spring/resourceService/ModellResourceServiceTest.java):
```java
@SpringBootTest(webEnvironment = DEFINED_PORT)
@Import(RestContractCoreTestPersistenceConfiguration.class)
public class ModellResourceServiceTest extends AbstractResourceServiceImplTest<Modell, Long> {

  static final String path = "/modell";
  private static final String stringField = "stringVal";
  private static final Producer<Modell> producer;
  private static final List<Modell> insertData;
  private static final List<Modell> updateData;

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
```

Notice the use of the [Producer](rest-contract-core/src/main/java/io/github/agache41/rest/contract/producer/Producer.java) class that
generates automatically complete lists with instance objects for tests.

The test goes through all the provided methods :

![ModelResourcesServiceTest](/readme.res/ModelResourcesServiceTest.png)

Notice that the actual entities used in the test are omitted for simplicity from the example.

### Testing my own methods

The ModellResourceServiceTest is a UnitTest where test methods can be further added :

```java

@QuarkusTest
public class ModellResourceServiceTest extends AbstractResourceServiceImplTest<Modell, Long> {
    public ModellResourceServiceTest() {
        /// .....
    }

    @Test
    @Order(1000)
    void testGetAllModellsOver100() {

        /// your favorite method gets tested here 
    }

}

```

Notice the use of the @Order(1000) annotation, this will ensure the correct order of running.

### Generating Front End model classes and services.

My application grows steadily and every day I add new entities. It's time to present the resource services to my clients in a ready to code manner.

For Quarkus the smallrye-openapi dependency (`io.quarkus:quarkus-smallrye-openapi`) ensures the generation of the open API yaml and json file.
This can be further customized in the properties [file](quarkus-integration-model/src/main/resources/application.properties).
```properties
quarkus.smallrye-openapi.store-schema-directory=openapi/api
quarkus.smallrye-openapi.open-api-version=3.0.3
```
For Springboot the springdoc-openapi dependency (`org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0`) ensures the generation of the open API json file.
This can be further customized in the pom [file](spring-integration-model/pom.xml)

Then the `org.openapitools:openapi-generator-maven-plugin:7.0.1` plugin will generate the classes for the front end.
Here is an example for [Angular](https://angular.io/) using [Typescript](https://www.typescriptlang.org/).

```xml
<plugin>
  <groupId>org.openapitools</groupId>
  <artifactId>openapi-generator-maven-plugin</artifactId>
  <version>7.10.0</version>
  <executions>
    <execution>
      <phase>package</phase>
      <goals>
        <goal>generate</goal>
      </goals>
      <configuration>
        <inputSpec>${project.basedir}/openapi/api/openapi.json</inputSpec>
        <generatorName>typescript-angular</generatorName>
        <configOptions>
          <sourceFolder>src/gen/java/main</sourceFolder>
        </configOptions>
        <output>${project.basedir}/openapi/generated</output>
        <verbose>false</verbose>
        <cleanupOutput>true</cleanupOutput>
      </configuration>
    </execution>
  </executions>
</plugin>
```

Here ist an example of the generated files:

![OpenapiGeneration](/readme.res/OpenapiGeneration.png)

## Demo

А comprehensive example of using the library you can find in the **[Quarkus Example](quarkus-integration-model)**  or **[Spring Example](spring-integration-model)** module.

## Requirements

The library works with Java 17+, Quarkus 3.6.7+, Spring Boot 3.0+, JPA 2+

## Installation

Simply add  `io.github.agache41:quarkus-rest-contract:version` or `io.github.agache41:spring-rest-contract:version` dependency to your project.

The current version today at sunset:

```xml

<version>1.0.0</version>
```
The dependency for the main jar:

```xml

<dependency>
    <groupId>io.github.agache41</groupId>
    <artifactId>quarkus-rest-contract</artifactId>
    <version>${version}</version>
</dependency>
```
or
```xml

<dependency>
    <groupId>io.github.agache41</groupId>
    <artifactId>spring-rest-contract</artifactId>
    <version>${version}</version>
</dependency>
```

For the test context the tests-classified jar is needed:

```xml

<dependency>
    <groupId>io.github.agache41</groupId>
    <artifactId>quarkus-rest-contract</artifactId>
    <version>${version}</version>
    <classifier>tests</classifier>
    <type>test-jar</type>
    <scope>test</scope>
</dependency>
```
or
```xml

<dependency>
    <groupId>io.github.agache41</groupId>
    <artifactId>spring-rest-contract</artifactId>
    <version>${version}</version>
    <classifier>tests</classifier>
    <type>test-jar</type>
    <scope>test</scope>
</dependency>
```
## Features

- Easy to install, use and extend.
- Test coverage provided on the fly.
- Works with both [Jackson](https://github.com/FasterXML/jackson) and [JSONB](https://javaee.github.io/jsonb-spec/). No
  support yet for reactive mode.
- Tested with Quarkus 3.6.7 and Spring Boot 3.4.0

## Structure

The library is packaged as a single jar. An auxiliary test classifier jar is provided for test context.

## Dependencies

Execution dependencies consist in the needed packages from [Jakarta](https://jakarta.ee/)
and [JbossLogging](https://github.com/jboss-logging/jboss-logging) and are not transitive.

```xml

<dependencies>
    <dependency>
        <groupId>io.github.agache41</groupId>
        <artifactId>rest-contract-core</artifactId>
        <version>1.0.0</version>
        <scope>compile</scope>
    </dependency>
    <dependency>
        <groupId>jakarta.enterprise</groupId>
        <artifactId>jakarta.enterprise.cdi-api</artifactId>
        <version>4.0.1</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>jakarta.persistence</groupId>
        <artifactId>jakarta.persistence-api</artifactId>
        <version>3.1.0</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>jakarta.ws.rs</groupId>
        <artifactId>jakarta.ws.rs-api</artifactId>
        <version>3.1.0</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>jakarta.validation</groupId>
        <artifactId>jakarta.validation-api</artifactId>
        <version>3.0.2</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>org.jboss.logging</groupId>
        <artifactId>jboss-logging</artifactId>
        <version>3.5.3.Final</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

Testing dependencies are listed here. Please note the Lombok is used only in the test context.

```xml

<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>5.10.1</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>5.4.0</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.30</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.hibernate.orm</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>6.4.1.Final</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <version>2.2.224</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

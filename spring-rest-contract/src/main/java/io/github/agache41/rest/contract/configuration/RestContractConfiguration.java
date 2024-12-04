package io.github.agache41.rest.contract.configuration;

import io.github.agache41.rest.contract.dataAccess.DataAccess;
import io.github.agache41.rest.contract.dataAccess.DataBinder;
import io.github.agache41.rest.contract.dataAccessBase.PrimaryKey;
import io.github.agache41.rest.contract.update.TransferObject;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Field;

/**
 * The type Rest contract configuration.
 */
@Configuration
@ComponentScan("io.github.agache41.rest.contract.paramConverter")
public class RestContractConfiguration {

    /**
     * The constant log.
     */
    protected static final Logger log = Logger.getLogger(RestContractConfiguration.class);

    /**
     * <pre>
     * Injection Point Configuration Bean
     * Example how to inject a DataAccess for a class TypeClass with Primary Key PKey:
     * &#064;Autowired
     * DataAccess &#x3C;MyClass, PKey&#x3E; myClassDataAccess;
     * </pre>
     *
     * @param <ENTITY> the type parameter
     * @param <PK>     the type parameter
     * @param ip       the underlining injection point, provided by Spring Framework.
     * @return the data access
     */
    @Bean
    @Scope(value = "prototype")
    public <ENTITY extends PrimaryKey<PK>, PK> DataAccess<ENTITY, PK> dataAccess(InjectionPoint ip) {
        final Field field = ip.getField();
        if (field != null) {
            final ResolvableType resolvableType = ResolvableType.forField(field);
            @SuppressWarnings("unchecked") final Class<ENTITY> firstGenParam = (Class<ENTITY>) resolvableType.getGeneric(0)
                                                                                                             .resolve();
            if (firstGenParam == null)
                throw new RuntimeException("Failure resolving generic parameter(0) for DataAccess in class " + field.getDeclaringClass()
                                                                                                                    .getSimpleName());
            @SuppressWarnings("unchecked") final Class<PK> secondGenParam = (Class<PK>) resolvableType.getGeneric(1)
                                                                                                      .resolve();
            if (secondGenParam == null)
                throw new RuntimeException("Failure resolving generic parameter(1) for DataAccess in class " + field.getDeclaringClass()
                                                                                                                    .getSimpleName());
            log.infof("Autowiring field %s.%s with new DataAccess<%s,%s>(...);", field.getDeclaringClass()
                                                                                     .getSimpleName(), field.getName(), firstGenParam.getSimpleName(), secondGenParam.getSimpleName());
            return new DataAccess<>(firstGenParam, secondGenParam);
        } else throw new RuntimeException("Only implemented for field autowiring.");
    }


    /**
     * <pre>
     * Injection Point Configuration Bean
     * Example how to inject a DataBinder with a TypeClassTO for a class TypeClass with Primary Key PKey:
     * &#064;Autowired
     * DataBinder &#x3C;TypeClassTO, MyClass, PKey&#x3E; myClassDataBinder;
     * </pre>
     *
     * @param <TO>     the type parameter
     * @param <ENTITY> the type parameter
     * @param <PK>     the type parameter
     * @param ip       the underlining injection point, provided by Spring Framework.
     * @return the data binder
     */
    @Bean
    @Scope(value = "prototype")
    public <TO extends PrimaryKey<PK> & TransferObject<TO, ENTITY>, ENTITY extends PrimaryKey<PK>, PK> DataBinder<TO, ENTITY, PK> dataBinder(InjectionPoint ip) {
        final Field field = ip.getField();
        if (field != null) {
            final ResolvableType resolvableType = ResolvableType.forField(field);
            @SuppressWarnings("unchecked") final Class<TO> firstGenParam = (Class<TO>) resolvableType.getGeneric(0)
                                                                                                     .resolve();
            if (firstGenParam == null)
                throw new RuntimeException("Failure resolving generic parameter(0) for DataBinder in class " + field.getDeclaringClass()
                                                                                                                    .getSimpleName());
            @SuppressWarnings("unchecked") final Class<ENTITY> secondGenParam = (Class<ENTITY>) resolvableType.getGeneric(1)
                                                                                                              .resolve();
            if (secondGenParam == null)
                throw new RuntimeException("Failure resolving generic parameter(1) for DataBinder in class " + field.getDeclaringClass()
                                                                                                                    .getSimpleName());
            @SuppressWarnings("unchecked") final Class<PK> thirdGenParam = (Class<PK>) resolvableType.getGeneric(2)
                                                                                                     .resolve();
            if (thirdGenParam == null)
                throw new RuntimeException("Failure resolving generic parameter(2) for DataBinder in class " + field.getDeclaringClass()
                                                                                                                    .getSimpleName());
            log.infof("Autowiring field %s.%s with new DataBinder<%s,%s,%s>(...);", field.getDeclaringClass()
                                                                                        .getSimpleName(), field.getName(), firstGenParam.getSimpleName(), secondGenParam.getSimpleName(), thirdGenParam.getSimpleName());
            return new DataBinder<>(firstGenParam, secondGenParam, thirdGenParam);
        } else throw new RuntimeException("Only implemented for field autowiring.");
    }
}

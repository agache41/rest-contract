package io.github.agache41.rest.contract.configuration;

import io.github.agache41.rest.contract.dataAccess.DataAccess;
import io.github.agache41.rest.contract.dataAccess.DataBinder;
import io.github.agache41.rest.contract.dataAccess.PrimaryKey;
import io.github.agache41.rest.contract.update.TransferObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableTransactionManagement
public class RestContractConfiguration {

    Logger log = LoggerFactory.getLogger(RestContractConfiguration.class);

    /**
     * <pre>
     * Injection Point Configuration Bean
     * Example how to inject a DataAccess for a class TypeClass with Primary Key PKey:
     * &#064;Autowired
     * DataAccess &#x3C;MyClass, PKey&#x3E; myClassDataAccess;
     * </pre>
     *
     * @param ip the underlining injection point, provided by Spring Framework.
     */
    @Bean
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
            log.info("Autowiring field {}.{} with new DataAccess<{},{}>(...);", field.getDeclaringClass()
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
     * @param ip the underlining injection point, provided by Spring Framework.
     */
    @Bean
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
            log.info("Autowiring field {}.{} with new DataBinder<{},{},{}>(...);", field.getDeclaringClass()
                                                                                        .getSimpleName(), field.getName(), firstGenParam.getSimpleName(), secondGenParam.getSimpleName(), thirdGenParam.getSimpleName());
            return new DataBinder<>(firstGenParam, secondGenParam, thirdGenParam);
        } else throw new RuntimeException("Only implemented for field autowiring.");
    }

//    @Bean
//    public PlatformTransactionManager platformTransactionManager(){
//        return new JtaTransactionManager();
//    }


//    @Bean(name="conversionService")
//    public ConversionServiceFactoryBean getConversionService() {
//        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
//
//        Set<Converter> converters = new HashSet<>();
//
//        converters.add(new LongListConverter());
//
//        bean.setConverters(converters);
//        return bean;
//    }
}

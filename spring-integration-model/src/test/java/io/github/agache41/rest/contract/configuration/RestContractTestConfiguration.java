package io.github.agache41.rest.contract.configuration;

import io.github.agache41.rest.contract.dataAccess.DataAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

@Configuration
public class RestContractTestConfiguration {

    Logger log = LoggerFactory.getLogger(RestContractTestConfiguration.class);

    @Bean
    public LocalEntityManagerFactoryBean entityManagerFactory() {
        LocalEntityManagerFactoryBean factoryBean = new LocalEntityManagerFactoryBean();
        factoryBean.setPersistenceUnitName("rest-contract-core-test");
        return factoryBean;
    }
}

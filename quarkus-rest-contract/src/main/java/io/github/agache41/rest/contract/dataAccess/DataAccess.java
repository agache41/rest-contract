
/*
 *    Copyright 2022-2023  Alexandru Agache
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.github.agache41.rest.contract.dataAccess;

import io.github.agache41.rest.contract.dataAccessBase.AbstractDataAccess;
import io.github.agache41.rest.contract.dataAccessBase.PrimaryKey;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;

import java.lang.reflect.ParameterizedType;


/**
 * <pre>
 * Generic superset of data access layer methods over an EntityManager.
 *
 * The class is meant to be used standalone and also as a base class.
 * In standalone use it can be injected directly in the code:
 *
 *
 * </pre>
 *
 * @param <ENTITY> the type parameter
 * @param <PK>     the type parameter
 */
@Dependent
@Named("DataAccess")
public class DataAccess<ENTITY extends PrimaryKey<PK>, PK> extends AbstractDataAccess<ENTITY, PK> {


    /**
     * <pre>
     * The default EntityManager in use.
     * </pre>
     */
    @Inject
    protected EntityManager em;

    /**
     * Constructs a new DataAccess, based on the generic types
     * @param type - the class for the type
     * @param keyType - the class for the key
     */
    public DataAccess(Class<ENTITY> type, Class<PK> keyType) {
        super(type, keyType);
    }

    /**
     * <pre>
     * Injection Point Constructor
     * Example how to inject a DataAccess for a class TypeClass with Primary Key PKey:
     * &#064;Inject DataAccess &#x3C;MyClass, PKey&#x3E; myClassDao;
     * </pre>
     *
     * @param ip the underlining injection point, provided by CDI.
     */
    @Inject
    public DataAccess(final InjectionPoint ip) {
        this(((Class<ENTITY>) (((ParameterizedType) ip.getType()).getActualTypeArguments()[0])),//
                ((Class<PK>) (((ParameterizedType) ip.getType()).getActualTypeArguments()[1])));//
    }

    @Override
    protected EntityManager em() {
        return this.em;
    }
}

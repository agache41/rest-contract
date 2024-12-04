
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

import io.github.agache41.rest.contract.dataAccessBase.AbstractDataBinder;
import io.github.agache41.rest.contract.dataAccessBase.PrimaryKey;
import io.github.agache41.rest.contract.update.TransferObject;
import org.springframework.stereotype.Service;

/**
 * The type Data binder.
 *
 * @param <TO>     the type parameter
 * @param <ENTITY> the type parameter
 * @param <PK>     the type parameter
 */
@Service("DataBinder")
public class DataBinder<TO extends PrimaryKey<PK> & TransferObject<TO, ENTITY>, ENTITY extends PrimaryKey<PK>, PK> extends AbstractDataBinder<TO, ENTITY, PK> {

    /**
     * Instantiates a new Data binder.
     *
     * @param toCLass     the to c lass
     * @param entityClass the entity class
     * @param pkClass     the pk class
     */
    public DataBinder(final Class<TO> toCLass,
                      final Class<ENTITY> entityClass,
                      final Class<PK> pkClass) {
        super(toCLass, entityClass, pkClass);
    }
}

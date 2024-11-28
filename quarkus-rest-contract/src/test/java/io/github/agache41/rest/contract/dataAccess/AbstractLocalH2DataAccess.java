
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

import io.github.agache41.rest.contract.update.SelfTransferObject;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class AbstractLocalH2DataAccess<ENTITY extends PrimaryKey<PK> & SelfTransferObject<ENTITY>, PK> extends DataAccess<ENTITY, PK> {

    private static EntityManagerFactory entityManagerFactory;

    private static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null)
            entityManagerFactory = Persistence.createEntityManagerFactory("test");
        return entityManagerFactory;
    }

    public AbstractLocalH2DataAccess(final Class<ENTITY> type,
                                     final Class<PK> keyType) {
        super(type, keyType);
        this.em = getEntityManagerFactory().createEntityManager();
    }
}

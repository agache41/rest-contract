
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

package io.github.agache41.rest.contract.modell.resources;


import io.github.agache41.rest.contract.entities.EmbeddedIdModell;
import io.github.agache41.rest.contract.entities.EmbeddedKeys;
import io.github.agache41.rest.contract.modell.dataBinder.EmbeddedIdModellDataBinder;
import io.github.agache41.rest.contract.modell.dataaccess.EmbeddedIdModellDataAccess;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImpl;
import lombok.Getter;

@Getter
public class EmbeddedIdModellResourceService extends AbstractResourceServiceImpl<EmbeddedIdModell, EmbeddedIdModell, EmbeddedKeys> {
    protected EmbeddedIdModellDataBinder dataBinder = new EmbeddedIdModellDataBinder();
    protected EmbeddedIdModellDataAccess dataAccess = new EmbeddedIdModellDataAccess();

    public EmbeddedIdModellResourceService() {
        this.postConstruct();
    }
}
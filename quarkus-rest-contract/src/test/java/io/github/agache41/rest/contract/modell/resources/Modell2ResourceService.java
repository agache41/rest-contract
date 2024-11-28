
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


import io.github.agache41.rest.contract.modell.dataBinder.Modell2DataBinder;
import io.github.agache41.rest.contract.modell.dataaccess.Modell2DataAccess;
import io.github.agache41.rest.contract.entities.Modell2;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImpl;
import lombok.Getter;

@Getter
public class Modell2ResourceService extends AbstractResourceServiceImpl<Modell2, Modell2, String> {
    protected Modell2DataBinder dataBinder = new Modell2DataBinder();
    protected Modell2DataAccess dataAccess = new Modell2DataAccess();

    public Modell2ResourceService() {
        this.postConstruct();
    }
}
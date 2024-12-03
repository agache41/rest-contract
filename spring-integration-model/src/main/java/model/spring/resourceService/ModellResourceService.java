
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

package model.spring.resourceService;


import io.github.agache41.rest.contract.dataAccess.DataAccess;
import io.github.agache41.rest.contract.dataAccess.DataBinder;
import io.github.agache41.rest.contract.entities.Modell;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static jakarta.transaction.Transactional.TxType.REQUIRED;

@Getter
@RestController
@RequestMapping("/modell")
@Transactional
@jakarta.transaction.Transactional(REQUIRED)
public class ModellResourceService extends AbstractResourceServiceImpl<Modell, Modell, Long> {


    @Autowired
    protected DataAccess<Modell,Long> dataAccess;


    @Autowired
    protected DataBinder<Modell,Modell,Long> dataBinder;


//    protected ModellDataBinder dataBinder = new ModellDataBinder();
//    protected ModellDataAccess dataAccess = new ModellDataAccess();
//
//    public ModellResourceService() {
//        this.postConstruct();
//    }
}
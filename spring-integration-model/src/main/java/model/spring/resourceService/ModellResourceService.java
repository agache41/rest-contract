
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


import io.github.agache41.rest.contract.dataAccess.DataBinder;
import io.github.agache41.rest.contract.entities.Modell;
import io.github.agache41.rest.contract.resourceService.AbstractResourceServiceImpl;
import lombok.Getter;
import model.spring.dataAccess.ModellDataAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
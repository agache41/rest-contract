
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

package io.github.agache41.rest.contract.resourceService;


import io.github.agache41.rest.contract.dataAccess.DataAccess;
import io.github.agache41.rest.contract.dataAccess.DataBinder;
import io.github.agache41.rest.contract.dataAccess.IdGroup;
import io.github.agache41.rest.contract.dataAccess.PrimaryKey;
import io.github.agache41.rest.contract.update.TransferObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * <pre>
 * Base class for resource REST APIs.
 * The class implements methods for basic REST operations on the underlying Class
 * </pre>
 *
 * @param <TO>     the type parameter
 * @param <ENTITY> the type parameter
 * @param <PK>     the type parameter
 */
public abstract class AbstractResourceServiceImpl<TO extends PrimaryKey<PK> & TransferObject<TO, ENTITY>, ENTITY extends PrimaryKey<PK>, PK> implements ResourceService<TO, PK>, InitializingBean {
    /**
     * <pre>
     * Default data access layer , used for communicating with the database.
     * </pre>
     */
    @Autowired
    //@Named("DataAccess")
    protected DataAccess<ENTITY, PK> dataAccess;

    /**
     * The data binder handling the binding between TO and ENTITY
     */
    @Autowired
    //@Named("DataBinder")
    protected DataBinder<TO, ENTITY, PK> dataBinder;


    /**
     * afterPropertiesSet() is called to assure correct init
     */
    @Override
    public void afterPropertiesSet() {
        this.getDataBinder()
            .setDataAccess(this.getDataAccess());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public TO get(@PathVariable("id") final PK id) {
        return this.getDataBinder()
                   .findById(id);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @PostMapping(path = "/byId", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public TO postById(final PK id) {
        return this.get(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = "/all/asList", produces = APPLICATION_JSON_VALUE)
    public List<TO> getAllAsList(@RequestParam(name = "firstResult", required = false) final Integer firstResult,
                                 @RequestParam(name = "maxResults", required = false) final Integer maxResults,
                                 @RequestParam final MultiValueMap<String, String> requestParameters /* @Context final UriInfo uriInfo*/) {
        return this.getDataBinder()
                   .listAll(this.getConfig()
                                .getFirstResult(firstResult), this.getConfig()
                                                                  .getMaxResults(maxResults), requestParameters);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = "/byIds/{ids}/asList", produces = APPLICATION_JSON_VALUE)
    public List<TO> getByIdsAsList(@PathVariable("ids") final List<PK> ids) {
        return this.getDataBinder()
                   .listByIds(ids);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PostMapping(path = "/byIds/asList", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<TO> postByIdsAsList(final List<PK> ids) {
        return this.getByIdsAsList(ids);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = "/filter/{stringField}/equals/{value}/asList", produces = APPLICATION_JSON_VALUE)
    public List<TO> getFilterStringFieldEqualsValueAsList(@PathVariable("stringField") final String stringField,
                                                          @PathVariable("value") final String value,
                                                          @RequestParam(name = "firstResult", required = false) final Integer firstResult,
                                                          @RequestParam(name = "maxResults", required = false) final Integer maxResults) {
        return this.getDataBinder()
                   .listByColumnEqualsValue(stringField, value, this.getConfig()
                                                                    .getFirstResult(firstResult), this.getConfig()
                                                                                                      .getMaxResults(maxResults));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = "/filter/{stringField}/like/{value}/asList", produces = APPLICATION_JSON_VALUE)
    public List<TO> getFilterStringFieldLikeValueAsList(@PathVariable("stringField") final String stringField,
                                                        @PathVariable("value") final String value,
                                                        @RequestParam(name = "firstResult", required = false) final Integer firstResult,
                                                        @RequestParam(name = "maxResults", required = false) final Integer maxResults) {
        return this.getDataBinder()
                   .listByColumnLikeValue(stringField, value, this.getConfig()
                                                                  .getFirstResult(firstResult), this.getConfig()
                                                                                                    .getMaxResults(maxResults));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = "/filter/{stringField}/in/{values}/asList", produces = APPLICATION_JSON_VALUE)
    public List<TO> getFilterStringFieldInValuesAsList(@PathVariable("stringField") final String stringField,
                                                       @PathVariable("values") final List<String> values,
                                                       @RequestParam(name = "firstResult", required = false) final Integer firstResult,
                                                       @RequestParam(name = "maxResults", required = false) final Integer maxResults) {
        return this.getDataBinder()
                   .listByColumnInValues(stringField, values, this.getConfig()
                                                                  .getFirstResult(firstResult), this.getConfig()
                                                                                                    .getMaxResults(maxResults));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = "autocomplete/{stringField}/like/{value}/asSortedSet", produces = APPLICATION_JSON_VALUE)
    public List<String> getAutocompleteStringFieldLikeValueAsSortedSet(@PathVariable("stringField") final String stringField,
                                                                       @PathVariable("value") final String value,
                                                                       @RequestParam(name = "cut", required = false) final Integer cut,
                                                                       @RequestParam(name = "maxResults", required = false) final Integer maxResults,
                                                                       @RequestParam final MultiValueMap<String, String> requestParameters /* @Context final UriInfo uriInfo*/) {
        if (value == null || value.length() < this.getConfig()
                                                  .getAutocompleteCut(cut)) {
            return Collections.emptyList();
        }
        return this.getDataAccess()
                   .autocompleteByColumnLikeValue(stringField, value, this.getConfig()
                                                                          .getAutocompleteMaxResults(maxResults), requestParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = "autocompleteIds/{stringField}/like/{value}/asList", produces = APPLICATION_JSON_VALUE)
    public List<IdGroup<PK>> getAutocompleteIdsStringFieldLikeValueAsList(@PathVariable("stringField") final String stringField,
                                                                          @PathVariable("value") final String value,
                                                                          @RequestParam(name = "cut", required = false) final Integer cut,
                                                                          @RequestParam(name = "maxResults", required = false) final Integer maxResults,
                                                                          @RequestParam final MultiValueMap<String, String> requestParameters /* @Context final UriInfo uriInfo*/) {
        if (value == null || value.length() < this.getConfig()
                                                  .getAutocompleteCut(cut)) {
            return Collections.emptyList();
        }
        return this.getDataAccess()
                   .autocompleteIdsByColumnLikeValue(stringField, value, this.getConfig()
                                                                             .getAutocompleteMaxResults(maxResults), requestParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PostMapping(path = "/filter/content/equals/value/asList", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<TO> postFilterContentEqualsAsList(final Map<String, Object> value,
                                                  @RequestParam(name = "firstResult", required = false) final Integer firstResult,
                                                  @RequestParam(name = "maxResults", required = false) final Integer maxResults) {
        return this.getDataBinder()
                   .listByContentEquals(value, this.getConfig()
                                                   .getFirstResult(firstResult), this.getConfig()
                                                                                     .getMaxResults(maxResults));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PostMapping(path = "/filter/content/in/values/asList", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<TO> postFilterContentInAsList(final Map<String, List<Object>> values,
                                              @RequestParam(name = "firstResult", required = false) final Integer firstResult,
                                              @RequestParam(name = "maxResults", required = false) final Integer maxResults) {
        return this.getDataBinder()
                   .listByContentInValues(values, this.getConfig()
                                                      .getFirstResult(firstResult), this.getConfig()
                                                                                        .getMaxResults(maxResults));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PostMapping(path = "", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public TO post(final TO to) {
        final TO persisted = this.getDataBinder()
                                 .persist(to);
        return this.doVerify(persisted);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PostMapping(path = "/list/asList", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<TO> postListAsList(final List<TO> toList) {
        final List<TO> persisted = this.getDataBinder()
                                       .persist(toList);
        return this.doVerify(persisted);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PutMapping(path = "", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public TO put(final TO to) {
        final TO updated = this.getDataBinder()
                               .updateById(to);
        return this.doVerify(updated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PutMapping(path = "/list/asList", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<TO> putListAsList(final List<TO> toList) {
        final List<TO> updated = this.getDataBinder()
                                     .updateByIds(toList, true);
        return this.doVerify(updated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") final PK id) {
        this.getDataBinder()
            .removeById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DeleteMapping(path = "/byIds")
    public void deleteByIds(final List<PK> ids) {
        this.getDataBinder()
            .removeByIds(ids);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @DeleteMapping(path = "/byIds/{ids}")
    public void deleteByIdsInPath(@PathVariable("ids") final List<PK> ids) {
        this.getDataBinder()
            .removeByIds(ids);
    }

    /**
     * Verifies that the updated Entity has the same content in the database.
     *
     * @param updated the updated
     * @return the t
     */
    protected TO doVerify(final TO updated) {
        if (!this.getConfig()
                 .getVerify()) {
            return updated;
        }
        final PK id = updated.getId();
        if (id == null) {
            throw new RuntimeException(" Verify fail " + updated + " has null id! ");
        }
        final TO actual = this.get(id);
        if (!updated.equals(actual)) {
            throw new RuntimeException(" Verify fail " + updated + " <> " + actual);
        }
        return actual;
    }

    /**
     * Does verify methode on a list.
     *
     * @param updated the updated
     * @return the list
     */
    protected List<TO> doVerify(final List<TO> updated) {
        return updated.stream()
                      .map(this::doVerify)
                      .collect(Collectors.toList());
    }

    /**
     * The data binder getter
     *
     * @return the data binder
     */
    public DataBinder<TO, ENTITY, PK> getDataBinder() {
        return this.dataBinder;
    }

    /**
     * The data access getter
     *
     * @return the data binder
     */
    public DataAccess<ENTITY, PK> getDataAccess() {
        return this.dataAccess;
    }
}
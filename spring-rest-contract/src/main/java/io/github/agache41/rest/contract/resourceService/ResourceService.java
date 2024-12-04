
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

import io.github.agache41.rest.contract.dataAccessBase.IdGroup;
import io.github.agache41.rest.contract.dataAccessBase.PrimaryKey;
import io.github.agache41.rest.contract.resourceServiceBase.ResourceServiceConfig;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

/**
 * The interface Resource service defines the REST Methods to be used on the Entity Domain Model
 *
 * @param <TO> the type parameter
 * @param <K>  the type parameter
 */
public interface ResourceService<TO extends PrimaryKey<K>, K> {

    /**
     * <pre>
     * Finds and returns the corresponding entity for the given id.
     * The id type must be basic (e.g. String, Long) or have a simple rest representation that can be used in a url path segment.
     *
     * </pre>
     *
     * @param id the id
     * @return the corresponding entity at the provided id. If no entity is found, an Expected will be thrown.
     */
    TO get(K id);

    /**
     * <pre>
     * Finds and returns the corresponding entity for the given id.
     * The id type must be basic (e.g. String, Long) or have a simple rest representation that can be used in a url path segment.
     *
     * </pre>
     *
     * @param id the id
     * @return the corresponding entity at the provided id. If no entity is found, an Expected will be thrown.
     */
    TO postById(K id);

    /**
     * <pre>
     * Returns all the entities for the given table.
     * </pre>
     *
     * @param firstResult       the first result
     * @param maxResults        the max results
     * @param requestParameters the uri info
     * @return the list of entities
     */
    List<TO> getAllAsList(Integer firstResult,
                          Integer maxResults,
                          MultiValueMap<String, String> requestParameters);

    /**
     * <pre>
     * Finds and returns the corresponding entity for the given list of ids.
     * The id type must be basic (e.g. String, Long) or have a simple rest representation that can be used in a url path segment.
     * </pre>
     *
     * @param ids the list of ids
     * @return the list of entities
     */
    List<TO> getByIdsAsList(List<K> ids);

    /**
     * <pre>
     * Finds and returns the corresponding entity for the given list of ids.
     * </pre>
     *
     * @param ids the list of ids
     * @return the list of entities
     */
    List<TO> postByIdsAsList(List<K> ids);

    /**
     * <pre>
     * Finds all entities whose value in a specified field is equal the given value.
     * The field can only be of String type.
     * FirstResult parameter will be applied on the sql Query.If not provided it will default to configured value.
     * MaxResults parameter will be applied on the sql Query.If not provided it will default to configured value.
     * </pre>
     *
     * @param stringField the field to use in filter, can only be a string value
     * @param value       the string value to equal
     * @param firstResult the first result
     * @param maxResults  the max results
     * @return the list of entities matching
     */
    List<TO> getFilterStringFieldEqualsValueAsList(String stringField,
                                                   String value,
                                                   Integer firstResult,
                                                   Integer maxResults);

    /**
     * <pre>
     * Finds all entities whose value in a specified field is like the given value.
     * The SQL Like operator will be used.
     * The field can only be of String type.
     * FirstResult parameter will be applied on the sql Query.If not provided it will default to configured value.
     * MaxResults parameter will be applied on the sql Query.If not provided it will default to configured value.
     * </pre>
     *
     * @param stringField the field to use in filter, can only be a string value
     * @param value       the string value to equal
     * @param firstResult the first result
     * @param maxResults  the max results
     * @return the list of entities matching
     */
    List<TO> getFilterStringFieldLikeValueAsList(String stringField,
                                                 String value,
                                                 Integer firstResult,
                                                 Integer maxResults);

    /**
     * <pre>
     * Finds all entities whose value in a specified field is in the given values list.
     *
     * The field can only be of String type.
     * FirstResult parameter will be applied on the sql Query.If not provided it will default to configured value.
     * MaxResults parameter will be applied on the sql Query.If not provided it will default to configured value.
     * </pre>
     *
     * @param stringField the field to use in filter, can only be a string value
     * @param values      the values list
     * @param firstResult the first result
     * @param maxResults  the max results
     * @return the list of entities matching
     */
    List<TO> getFilterStringFieldInValuesAsList(String stringField,
                                                List<String> values,
                                                Integer firstResult,
                                                Integer maxResults);

    /**
     * <pre>
     * Finds all values in a database column whose value is like the given value.
     * The SQL Like operator will be used.
     * The field can only be of String type.
     * Cut parameter tells the minimum value length.If not provided it will default to configured value.
     * MaxResults parameter will be applied on the sql Query.If not provided it will default to configured value.
     * </pre>
     *
     * @param stringField       the field to use in filter, can only be a string value
     * @param value             the string value to equal
     * @param cut               the cut
     * @param maxResults        the max results
     * @param requestParameters the uri info
     * @return the list of entities matching
     */
    List<String> getAutocompleteStringFieldLikeValueAsSortedSet(String stringField,
                                                                String value,
                                                                Integer cut,
                                                                Integer maxResults,
                                                                MultiValueMap<String, String> requestParameters);

    /**
     * <pre>
     * Finds all values in a database column whose value is like the given value.
     * The SQL Like operator will be used.
     * The field can only be of String type.
     * Result is a aggregation list containing max(id),value, count(id).
     * Specifically if on a row count = 1 then the id can be used as unique for the given value.
     * Cut parameter tells the minimum value length.If not provided it will default to configured value.
     * MaxResults parameter will be applied on the sql Query.If not provided it will default to configured value.
     * </pre>
     *
     * @param stringField       the field to use in filter, can only be a string value
     * @param value             the string value to equal
     * @param cut               the cut
     * @param maxResults        the max results
     * @param requestParameters the uri info
     * @return the list of IdGroup object matching the input value
     */
    List<IdGroup<K>> getAutocompleteIdsStringFieldLikeValueAsList(String stringField,
                                                                  String value,
                                                                  Integer cut,
                                                                  Integer maxResults,
                                                                  MultiValueMap<String, String> requestParameters);

    /**
     * <pre>
     * Finds in Database the entities that equals a given content object.
     * The content object must contain non null values just in the fields that are taking part in the filtering.
     * The other null fields are to be ignored.
     * No nulls can be used in the filtering.
     * Example :
     * content = [name ="abcd", no=2, street=null]
     * result is where name = "abcd" and no = 2
     * FirstResult parameter will be applied on the sql Query.If not provided it will default to configured value.
     * MaxResults parameter will be applied on the sql Query.If not provided it will default to configured value.
     * </pre>
     *
     * @param mapValues   the map values
     * @param firstResult the first result
     * @param maxResults  the max results
     * @return the list of entities matching
     */
    List<TO> postFilterContentEqualsAsList(Map<String, Object> mapValues,
                                           Integer firstResult,
                                           Integer maxResults);


    /**
     * <pre>
     * Finds in Database the entities that are in a given content list of given values.
     * The content object must contain non null values just in the fields that are taking part in the filtering.
     * The other null fields are to be ignored.
     * No nulls can be used in the filtering.
     * Example :
     * content = [name =["abcd","bcde","1234"], no=[2,3], street=null]
     * result is where name in ("abcd","bcde","1234") and no in (2,3)
     * FirstResult parameter will be applied on the sql Query.If not provided it will default to configured value.
     * MaxResults parameter will be applied on the sql Query.If not provided it will default to configured value.
     * </pre>
     *
     * @param values      the source
     * @param firstResult the first result
     * @param maxResults  the max results
     * @return the list of entities matching
     */
    List<TO> postFilterContentInAsList(Map<String, List<Object>> values,
                                       Integer firstResult,
                                       Integer maxResults);

    /**
     * <pre>
     * Inserts a new entity in the database or updates an existing one.
     * </pre>
     *
     * @param source the source
     * @return the inserted entity
     */
    TO post(TO source);

    /**
     * <pre>
     * Inserts a list of new entities in the database or updates the existing ones.
     * </pre>
     *
     * @param sources the list of new data
     * @return the inserted entities
     */
    List<TO> postListAsList(List<TO> sources);

    /**
     * <pre>
     * Updates an existing entity by id.
     * The Entity with the given id must exist in the Database or a UnexpectedException is thrown.
     * </pre>
     *
     * @param source the source
     * @return the updated entity
     */
    TO put(TO source);

    /**
     * <pre>
     * Updates existing entities by id.
     * The Entities with the given ids must exist in the Database or a UnexpectedException is thrown.
     * </pre>
     *
     * @param sources the source
     * @return the updated entities
     */
    List<TO> putListAsList(List<TO> sources);

    /**
     * <pre>
     * Deletes the entity for the given id.
     * </pre>
     *
     * @param id the id
     */
    void delete(K id);

    /**
     * <pre>
     * Deletes all the entities for the given ids in the request Body
     * </pre>
     *
     * @param ids the ids
     */
    void deleteByIds(List<K> ids);

    /**
     * <pre>
     * Deletes all the entities for the given ids.
     * </pre>
     *
     * @param ids the ids
     */
    void deleteByIdsInPath(List<K> ids);

    /**
     * Gets the default configuration object.
     * The deriving classes can override this method by means of a simple getter.
     *
     * @return the config object
     */
    default ResourceServiceConfig getConfig() {
        return new ResourceServiceConfig() {
        };
    }
}

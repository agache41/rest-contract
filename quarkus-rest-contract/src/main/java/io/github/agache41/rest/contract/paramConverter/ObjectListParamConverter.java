
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

package io.github.agache41.rest.contract.paramConverter;

import org.jboss.logging.Logger;

import java.util.function.Function;

/**
 * The type Long list param converter.
 */
public class ObjectListParamConverter extends ListParamConvertor<Object> {
    /**
     * Instantiates a new Long list param converter.
     *
     * @param parse the parse
     * @param log   the log
     */
    public ObjectListParamConverter(final Function<String, Object> parse,
                                    final Logger log) {
        super(parse, log);
    }
}
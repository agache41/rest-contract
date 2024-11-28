
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

package io.github.agache41.rest.contract.paramConverter.provider;

import io.github.agache41.rest.contract.paramConverter.*;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The converter handling lists as parameters in urls.
 */
@Provider
public class ListParamConverterProvider implements ParamConverterProvider {
    private final Map<Type, ParamConverter<?>> paramConverterMap = new HashMap<>();
    /**
     * Logger
     */
    @Inject
    Logger log;

    /**
     * Post construct.
     */
    @PostConstruct
    public void postConstruct() {
        this.paramConverterMap.put(String.class, new StringListParamConverter(this.log));
        this.paramConverterMap.put(Integer.class, new IntegerListParamConverter(this.log));
        this.paramConverterMap.put(Long.class, new LongListParamConverter(this.log));
        this.paramConverterMap.put(LocalDate.class, new LocalDateParamConverter(this.log));
    }

    @Override
    public <T> ParamConverter<T> getConverter(final Class<T> rawType,
                                              final Type genericType,
                                              final Annotation[] annotations) {
        if (rawType.equals(List.class)) {
            ParamConverter<?> paramConverter = null;
            if (genericType instanceof ParameterizedType) {
                final ParameterizedType ptype = (ParameterizedType) genericType;
                final Type[] actualTypeArguments = ptype.getActualTypeArguments();
                if (actualTypeArguments.length > 0) {
                    final Type parameterType = actualTypeArguments[0];
                    if (this.paramConverterMap.containsKey(parameterType)) {
                        paramConverter = this.paramConverterMap.get(parameterType);
                    } else {
                        final Class<?> parameterClass = (Class<?>) parameterType;
                        try {
                            final Constructor<?> constructor = parameterClass.getConstructor(String.class);
                            paramConverter = new ObjectListParamConverter(string -> {
                                try {
                                    return constructor.newInstance(string);
                                } catch (final Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }, this.log);
                        } catch (final NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            if (paramConverter == null) {
                this.log.errorf("No Parameter Converter found for Class %s with generic Type %s", rawType.getSimpleName(), genericType);
                throw new RuntimeException(String.format("No Parameter Converter found for Class %s with generic Type %s", rawType.getSimpleName(), genericType));
            } else {
                this.log.infof(" Binding %s to param of type %s ", paramConverter.getClass()
                                                                                 .getSimpleName(), genericType);
            }
            return (ParamConverter<T>) paramConverter;
        }
        return null;
    }
}
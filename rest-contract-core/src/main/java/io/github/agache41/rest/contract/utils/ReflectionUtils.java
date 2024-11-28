
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

package io.github.agache41.rest.contract.utils;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Reflection utils.
 */
public class ReflectionUtils {

    private static final String GETTER_PREFIX = "get";
    private static final String GETTER_IS_BOOL_PREFIX = "is";
    private static final String GETTER_HAS_BOOL_PREFIX = "has";
    private static final String SETTER_PREFIX = "set";
    private static final Pattern GETTER_PATTERN = Pattern.compile("(get)([A-Z]\\w+)");
    private static final Pattern SETTER_PATTERN = Pattern.compile("(set)([A-Z]\\w+)");

    /**
     * <pre>
     * Returns a list of declared fields in the class and in the base class(es).
     * </pre>
     *
     * @param cls the cls
     * @return the declared fields
     */
    public static List<Field> getDeclaredFields(Class<?> cls) {
        final List<Field> declaredFields = new LinkedList<>();
        while (cls != null && !cls.equals(Object.class)) {
            Collections.addAll(declaredFields, cls.getDeclaredFields());
            cls = cls.getSuperclass();
        }
        return declaredFields;
    }

    /**
     * <pre>
     * Returns a list of declared fields in the class and in the base class(es).
     * </pre>
     *
     * @param cls the cls
     * @return the declared fields
     */
    public static List<Method> getDeclaredMethods(Class<?> cls) {
        final List<Method> declaredMethods = new LinkedList<>();
        while (cls != null && !cls.equals(Object.class)) {
            Collections.addAll(declaredMethods, cls.getDeclaredMethods());
            cls = cls.getSuperclass();
        }
        return declaredMethods;
    }


    /**
     * Gets getter.
     *
     * @param <T>            the type parameter
     * @param <V>            the type parameter
     * @param enclosingClass the enclosing class
     * @param name           the name
     * @param type           the type
     * @return the getter
     */
    public static <T, V> Function<T, V> getGetter(final Class<T> enclosingClass,
                                                  final String name,
                                                  final Class<V> type) {
        if (enclosingClass == null || name == null || type == null) {
            return null;
        }
        final Method getterMethod = getGetterMethod(enclosingClass, name, type);
        if (getterMethod == null) {
            return null;
        }
        return object -> {
            try {
                return (V) getterMethod.invoke(object);
            } catch (final IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        };
    }


    /**
     * Gets setter.
     *
     * @param <T>            the type parameter
     * @param <V>            the type parameter
     * @param enclosingClass the enclosing class
     * @param name           the name
     * @param type           the type
     * @return the setter
     */
    public static <T, V> BiConsumer<T, V> getSetter(final Class<T> enclosingClass,
                                                    final String name,
                                                    final Class<V> type) {
        if (enclosingClass == null || name == null || type == null) {
            return null;
        }
        final Method setterMethod = getSetterMethod(enclosingClass, name, type);
        if (setterMethod == null) {
            return null;
        }
        return (object, value) -> {
            try {
                setterMethod.invoke(object, value);
            } catch (final IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        };
    }


    /**
     * <pre>
     * Locates the setter Method in the class.
     * </pre>
     *
     * @param <T>            the type parameter
     * @param <V>            the type parameter
     * @param enclosingClass the enclosing class
     * @param name           the name
     * @param type           the type
     * @return the setter
     */
    public static <T, V> Method getSetterMethod(final Class<T> enclosingClass,
                                                final String name,
                                                final Class<V> type) {
        try {
            // the setter method to use

            return enclosingClass.getMethod(
                    SETTER_PREFIX +
                    StringUtils.capitalize(name),
                    type);
        } catch (final NoSuchMethodException e) { // no setter
            if (name.startsWith("is") && (boolean.class.equals(type) || Boolean.class.equals(type))) {
                try {
                    // for boolean isValue field we use the field name without is
                    return enclosingClass.getMethod(SETTER_PREFIX + StringUtils.capitalize(name.substring(2)), type);
                } catch (final NoSuchMethodException e1) { // no is getter
                }
            }
        } catch (final SecurityException e) { // setter is faulty
            throw new IllegalArgumentException(e.getMessage() + " looking for method " + enclosingClass.getSimpleName() + "." + SETTER_PREFIX +
                                               StringUtils.capitalize(name) + "( " + type.getSimpleName() + " value )", e);
        }
        return null;
    }

    /**
     * <pre>
     * Locates the getter Method in the Class.
     * </pre>
     *
     * @param <T>            the type parameter
     * @param <V>            the type parameter
     * @param enclosingClass the enclosing class
     * @param name           the name
     * @param type           the type
     * @return the getter
     */
    public static <T, V> Method getGetterMethod(final Class<T> enclosingClass,
                                                final String name,
                                                final Class<V> type) {
        final String capitalizedName = StringUtils.capitalize(name);
        try {
            // the getter method to use
            return enclosingClass.getMethod(GETTER_PREFIX + capitalizedName);
        } catch (final NoSuchMethodException e) { // no getter
            if (boolean.class.equals(type) || Boolean.class.equals(type)) {
                try {
                    // for boolean isValue field we use the field name
                    if (name.startsWith("is")) {
                        return enclosingClass.getMethod(name);
                    } else { // add is
                        return enclosingClass.getMethod(GETTER_IS_BOOL_PREFIX + capitalizedName);
                    }
                } catch (final NoSuchMethodException e1) { // no is getter
                    try {
                        // add has
                        return enclosingClass.getMethod(GETTER_HAS_BOOL_PREFIX + capitalizedName);
                    } catch (final NoSuchMethodException e2) { // no is getter
                    }
                }
            }
        } catch (final SecurityException e) { // getter is faulty
            throw new IllegalArgumentException(e.getMessage() + " looking for method " + enclosingClass.getCanonicalName() + "." + GETTER_PREFIX + capitalizedName + "()", e);
        }
        return null;
    }

    /**
     * Gets the associated field name for the given setter method, if the method has a setter name
     * Returns null otherwise
     *
     * @param method the method
     * @return the field name
     */
    public static String getSetterFieldName(final Method method) {
        final Matcher matcher = SETTER_PATTERN.matcher(method.getName());
        if (!matcher.matches()) {
            return null;
        }
        return StringUtils.deCapitalize(matcher.group(2));
    }


    /**
     * Gets the no args constructor.
     *
     * @param <T>            the type parameter
     * @param enclosingClass the enclosing class
     * @return the no args constructor
     */
    public static <T> Constructor<T> getNoArgsConstructor(final Class<T> enclosingClass) {
        try {
            return enclosingClass.getConstructor();
        } catch (final NoSuchMethodException e) {
            throw new IllegalArgumentException(e.getMessage() + " looking for no arguments constructor " + enclosingClass.getCanonicalName() + "." + enclosingClass.getCanonicalName() + "()", e);
        }
    }

    /**
     * Gets the no args constructor.
     *
     * @param <T>            the type parameter
     * @param enclosingClass the enclosing class
     * @return the no args constructor
     */
    public static <T> Supplier<T> supplierOf(final Class<T> enclosingClass) {
        final Constructor<T> constructor = getNoArgsConstructor(enclosingClass);
        return () -> {
            try {
                return constructor.newInstance();
            } catch (final InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e.getMessage() + "by new " + enclosingClass.getCanonicalName() + "()", e);
            }
        };
    }

    /**
     * <pre>
     * Tells if the given class is a Collection.
     * </pre>
     *
     * @param c the c
     * @return the boolean
     */
    public static boolean isClassCollection(final Class<?> c) {
        return Collection.class.isAssignableFrom(c);
    }

    /**
     * <pre>
     * Tells if the given object is a Collection.
     * </pre>
     *
     * @param ob the ob
     * @return the boolean
     */
    public static boolean isCollection(final Object ob) {
        return ob != null && isClassCollection(ob.getClass());
    }

    /**
     * <pre>
     * Tells if the given class is a Map.
     * </pre>
     *
     * @param c the c
     * @return the boolean
     */
    public static boolean isClassMap(final Class<?> c) {
        return Map.class.isAssignableFrom(c);
    }

    /**
     * <pre>
     * Tells if the given objects is a Map.
     * </pre>
     *
     * @param ob the ob
     * @return the boolean
     */
    public static boolean isMap(final Object ob) {
        return ob != null && isClassMap(ob.getClass());
    }


    /**
     * Gets parameter type for the Type of a field
     *
     * @param field                 the field
     * @param genericParameterIndex the genericParameterIndex
     * @return the parameter type
     */
    public static Class<?> getParameterType(final Field field,
                                            final int genericParameterIndex) {
        return getParameterType(field.getGenericType(), genericParameterIndex);
    }

    /**
     * Gets parameter type for the call arguments of a method
     *
     * @param method                the method
     * @param argumentIndex         the argument index
     * @param genericParameterIndex the genericParameterIndex
     * @return the parameter type
     */
    public static Class<?> getParameterType(final Method method,
                                            final int argumentIndex,
                                            final int genericParameterIndex) {
        final Type[] genericParameterTypes = method.getGenericParameterTypes();
        if (genericParameterTypes.length <= argumentIndex) {
            return null;
        }
        return getParameterType(genericParameterTypes[argumentIndex], genericParameterIndex);
    }

    /**
     * Gets parameter type.
     *
     * @param genericType           the generic type
     * @param genericParameterIndex the generic parameter index
     * @return the parameter type
     */
    public static Class<?> getParameterType(final Type genericType,
                                            final int genericParameterIndex) {
        if (!(genericType instanceof ParameterizedType)) {
            return null;
        }
        final ParameterizedType pType = (ParameterizedType) genericType;
        final Type[] actualTypeArguments = pType.getActualTypeArguments();
        if (actualTypeArguments.length <= genericParameterIndex) {
            return null;
        }
        return (Class<?>) actualTypeArguments[genericParameterIndex];
    }
}

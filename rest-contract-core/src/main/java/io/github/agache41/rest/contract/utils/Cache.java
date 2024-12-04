package io.github.agache41.rest.contract.utils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;

/**
 * The type Cache.
 */
//todo: javadoc
public final class Cache {
    private Cache() {
    }

    /**
     * Map by unique key map.
     *
     * @param <E>          the type parameter
     * @param <T>          the type parameter
     * @param source       the source
     * @param getUniqueKey the get unique key
     * @return the map
     */
    public static <E, T> Map<E, T> mapByUniqueKey(Collection<T> source, Function<T, E> getUniqueKey) {
        return source.stream()
                     .collect(Collectors.toMap(getUniqueKey, Function.identity(), (existing, replacement) -> replacement));
    }

    /**
     * Map by key map.
     *
     * @param <E>      the type parameter
     * @param <F>      the type parameter
     * @param <T>      the type parameter
     * @param source   the source
     * @param getKey   the get key
     * @param getValue the get value
     * @return the map
     */
    public static <E, F, T> Map<E, Set<F>> mapByKey(Collection<T> source, Function<T, E> getKey, Function<T, F> getValue) {
        return source.stream()
                     .collect(groupingBy(getKey, mapping(getValue, Collectors.toCollection(TreeSet::new))));
    }

    /**
     * Find by key in f.
     *
     * @param <E>   the type parameter
     * @param <F>   the type parameter
     * @param cache the cache
     * @param key   the key
     * @param value the value
     * @return the f
     */
    public static <E, F> F findByKeyIn(final Map<E, Set<F>> cache, final E key, final F value) {
        if (value == null) {
            return null;
        }
        final Set<F> localCache = cache.get(key);
        if (localCache == null || !localCache.contains(value)) {
            return null;
        }
        return value;
    }

    /**
     * Map by unique keys map.
     *
     * @param <E>           the type parameter
     * @param <F>           the type parameter
     * @param <T>           the type parameter
     * @param source        the source
     * @param getUniqueKey1 the get unique key 1
     * @param getUniqueKey2 the get unique key 2
     * @return the map
     */
    public static <E, F, T> Map<E, Map<F, T>> mapByUniqueKeys(Collection<T> source, Function<T, E> getUniqueKey1, Function<T, F> getUniqueKey2) {
        return source.stream()
                     .collect(groupingBy(getUniqueKey1, Collectors.toMap(getUniqueKey2, Function.identity(), (existing, replacement) -> replacement)));
    }

    /**
     * Find by unique keys in t.
     *
     * @param <E>   the type parameter
     * @param <F>   the type parameter
     * @param <T>   the type parameter
     * @param cache the cache
     * @param key1  the key 1
     * @param key2  the key 2
     * @return the t
     */
    public static <E, F, T> T findByUniqueKeysIn(final Map<E, Map<F, T>> cache, final E key1, final F key2) {
        final Map<F, T> localCache = cache.get(key1);
        if (localCache == null) {
            return null;
        }
        return localCache.get(key2);
    }

    /**
     * Map unique key set.
     *
     * @param <E>          the type parameter
     * @param <T>          the type parameter
     * @param source       the source
     * @param getUniqueKey the get unique key
     * @return the set
     */
    public static <E, T> Set<E> mapUniqueKey(Collection<T> source, Function<T, E> getUniqueKey) {
        return source.stream()
                     .map(getUniqueKey)
                     .collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * Add by unique keys in t.
     *
     * @param <E>           the type parameter
     * @param <F>           the type parameter
     * @param <T>           the type parameter
     * @param cache         the cache
     * @param getUniqueKey1 the get unique key 1
     * @param getUniqueKey2 the get unique key 2
     * @param value         the value
     * @return the t
     */
    public static <E, F, T> T addByUniqueKeysIn(final Map<E, Map<F, T>> cache, Function<T, E> getUniqueKey1, Function<T, F> getUniqueKey2, T value) {
        return cache
                .computeIfAbsent(getUniqueKey1.apply(value), key1 -> new HashMap<>())
                .put(getUniqueKey2.apply(value), value);
    }

}

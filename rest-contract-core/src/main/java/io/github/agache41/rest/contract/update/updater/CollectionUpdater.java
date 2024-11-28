
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

package io.github.agache41.rest.contract.update.updater;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The updater for collection of simple types (String, Integer).
 * It updates the field value in the target based on the value of the field value in the source.
 *
 * @param <TO>     the type parameter
 * @param <ENTITY> the type parameter
 * @param <VALUE>  the type parameter
 */
public class CollectionUpdater<TO, ENTITY, VALUE> extends ValueUpdater<TO, ENTITY, Collection<VALUE>> {

    /**
     * Instantiates a new Collection updater.
     *
     * @param toGetter     the target toGetter
     * @param toSetter     the target entity
     * @param dynamic      if the update should be dynamic processed and nulls will be ignored
     * @param entityGetter the source toGetter
     * @param entitySetter the entity setter
     */
    public CollectionUpdater(final Function<TO, Collection<VALUE>> toGetter,
                             final BiConsumer<TO, Collection<VALUE>> toSetter,
                             final boolean dynamic,
                             final Function<ENTITY, Collection<VALUE>> entityGetter,
                             final BiConsumer<ENTITY, Collection<VALUE>> entitySetter) {
        super(toGetter, toSetter, dynamic, entityGetter, entitySetter);
    }

    /**
     * Convenient static method.
     * It updates the field value in the target based on the value of the field value in the source.
     *
     * @param <T>          the type parameter of the target object
     * @param <S>          the type parameter of the source object
     * @param <V>          the type parameter of the collection values
     * @param toGetter     the target toGetter
     * @param toSetter     the target toSetter
     * @param dynamic      if the update should be dynamic processed and nulls will be ignored
     * @param entityGetter the source toGetter
     * @param entitySetter the entity setter
     * @param target       the target
     * @param source       the source
     * @param context      the context
     * @return true if the target changed
     */
    public static <T, S, V> boolean updateCollection(final Function<T, Collection<V>> toGetter,
                                                     final BiConsumer<T, Collection<V>> toSetter,
                                                     final boolean dynamic,
                                                     final Function<S, Collection<V>> entityGetter,
                                                     final BiConsumer<S, Collection<V>> entitySetter,
                                                     final T target,
                                                     final S source,
                                                     final Object context) {
        return new CollectionUpdater<>(toGetter, toSetter, dynamic, entityGetter, entitySetter).update(target, source, context);
    }

    /**
     * {@inheritDoc}
     *
     * @param transferObject the transfer object
     * @param entity         the entity
     * @return the boolean
     */
    @Override
    public boolean update(final TO transferObject,
                          final ENTITY entity,
                          final Object context) {
        // the sourceValue to be updated
        final Collection<VALUE> toValue = this.toGetter.apply(transferObject);
        // nulls
        if (toValue == null) {
            if (this.dynamic || this.entityGetter.apply(entity) == null) // null ignore
            {
                return false;
            } else {
                this.warnNullCollection(transferObject);
                this.entitySetter.accept(entity, null);
                return true;
            }
        }
        final Collection<VALUE> enValue = this.entityGetter.apply(entity);
        // collection not initialized
        if (enValue == null) {
            this.warnNullCollection(entity);
            this.entitySetter.accept(entity, toValue);
            return true;
        }
        // empty
        if (toValue.isEmpty()) {
            if (enValue.isEmpty()) {
                return false;
            }
            enValue.clear();
            return true;
        }
        // empty

        // collection work
        enValue.clear();
        enValue.addAll(toValue);
        // collection work
        // re-set it
        this.entitySetter.accept(entity, enValue);
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @param transferObject the transfer object
     * @param entity         the entity
     */
    @Override
    public void render(final TO transferObject,
                       final ENTITY entity,
                       final Object context) {
        final Collection<VALUE> enValue = this.entityGetter.apply(entity);
        // no data
        if (enValue == null) {
            // no data no fun
            this.warnNullCollection(entity);
            return;
        }
        // the sourceValue to be updated
        final Collection<VALUE> toValue = this.toGetter.apply(transferObject);
        // nulls
        if (toValue == null) {
            this.warnNullCollection(transferObject);
            this.toSetter.accept(transferObject, enValue);
            return;
        }
        if (enValue.isEmpty()) {
            return;
        }
        toValue.addAll(enValue);
    }
}
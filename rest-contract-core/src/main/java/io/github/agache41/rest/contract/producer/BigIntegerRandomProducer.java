
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

package io.github.agache41.rest.contract.producer;

import java.math.BigInteger;

/**
 * The type Big integer random producer.
 */
public class BigIntegerRandomProducer extends Producer<BigInteger> {
    /**
     * The Max.
     */
    final short max = 10000;

    /**
     * Instantiates a new Big integer random producer.
     */
    public BigIntegerRandomProducer() {
        super(BigInteger.class);
    }

    @Override
    public BigInteger produce() {
        return BigInteger.valueOf((long) (this.random.nextFloat() * this.max));
    }

    @Override
    public BigInteger change(final BigInteger result) {
        return this.produce();
    }
}

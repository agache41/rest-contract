
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

/**
 * The type String utils.
 */
public class StringUtils {


    /**
     * <pre>
     * Capitalizes a string
     * </pre>
     *
     * @param input the input
     * @return string
     */
    public static String capitalize(final String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1)
                    .toUpperCase() + input.substring(1);
    }

    /**
     * <pre>
     * Decapitalizes a string
     * </pre>
     *
     * @param input the input
     * @return string
     */
    public static String deCapitalize(final String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1)
                    .toLowerCase() + input.substring(1);
    }

    /**
     * Quotes a string.
     * <p>
     * abcd into "abcd"
     *
     * @param input the input
     * @return the string
     */
    public static String quote(final String input) {
        if (input == null) {
            return null;
        }
        return "\"" + input + "\"";
    }

    /**
     * Un-quotes a string.
     * <p>
     * "abcd" into abcd
     *
     * @param input the input
     * @return the string
     */
    public static String unQuote(final String input) {
        if (input == null) {
            return null;
        }
        if (input.length() < 2) {
            return input;
        }
        return input.substring(1, input.length() - 1);
    }
}

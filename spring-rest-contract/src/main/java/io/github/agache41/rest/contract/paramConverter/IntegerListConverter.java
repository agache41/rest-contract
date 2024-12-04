package io.github.agache41.rest.contract.paramConverter;

import org.springframework.stereotype.Component;

/**
 * The type Integer list converter.
 */
@Component
public class IntegerListConverter extends ListParamConvertor<Integer> {

    /**
     * Instantiates a new Abstract list param convertor.
     */
    public IntegerListConverter() {
        super(Integer::parseInt);
    }
}
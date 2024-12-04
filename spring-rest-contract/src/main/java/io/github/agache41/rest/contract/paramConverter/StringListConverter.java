package io.github.agache41.rest.contract.paramConverter;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class StringListConverter extends ListParamConvertor<String> {
    /**
     * Instantiates a new Abstract list param convertor.
     */
    public StringListConverter() {
        super(Function.identity());
    }
}
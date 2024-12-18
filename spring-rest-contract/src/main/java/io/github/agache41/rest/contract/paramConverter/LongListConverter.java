package io.github.agache41.rest.contract.paramConverter;

import org.springframework.stereotype.Component;

/**
 * The type Long list converter.
 */
@Component
public class LongListConverter extends ListParamConvertor<Long> {

    /**
     * Instantiates a new Abstract list param convertor.
     */
    public LongListConverter() {
        super(Long::parseLong);
    }
}
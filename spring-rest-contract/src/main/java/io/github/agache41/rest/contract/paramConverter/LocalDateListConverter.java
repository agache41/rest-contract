package io.github.agache41.rest.contract.paramConverter;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * The type Local date list converter.
 */
@Component
public class LocalDateListConverter extends ListParamConvertor<LocalDate> {

    /**
     * Instantiates a new Abstract list param convertor.
     */
    public LocalDateListConverter() {
        super(LocalDate::parse);
    }
}
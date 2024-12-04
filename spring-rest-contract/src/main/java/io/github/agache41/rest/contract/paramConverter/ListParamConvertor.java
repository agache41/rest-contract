package io.github.agache41.rest.contract.paramConverter;

import io.github.agache41.rest.contract.configuration.RestContractConfiguration;
import org.jboss.logging.Logger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type List param convertor.
 *
 * @param <T> the type parameter
 */
@Component
public abstract class ListParamConvertor<T> implements Converter<String, List<T>> {
    /**
     * The constant log.
     */
    protected static final Logger log = Logger.getLogger(RestContractConfiguration.class);
    private final Function<String, T> parse;

    /**
     * Instantiates a new Abstract list param convertor.
     *
     * @param parse the parse
     */
    public ListParamConvertor(final Function<String, T> parse) {
        this.parse = parse;
    }

    @Override
    public List<T> convert(String source) {
        log.infof("Parsing list %s",source);
        return Stream.of(source.split(","))
                     .map(this.parse)
                     .collect(Collectors.toList());
    }
}
package io.github.agache41.rest.contract.paramConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public abstract class ListParamConvertor<T> implements Converter<String, List<T>> {
    private static final Logger log = LoggerFactory.getLogger(ListParamConvertor.class);
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
        return Stream.of(source//.substring(1, source.length() - 1)
                               .split(","))
                     .map(this.parse)
                     .collect(Collectors.toList());
    }
}
package model.spring;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class LongListConverter implements Converter<String, List<Long>> {

    @Override
    public List<Long> convert(String source) {
        return Stream.of(source.substring(1, source.length() - 1)
                              .split(","))
                     .filter(Objects::nonNull)
                     .map(Long::parseLong)
                     .collect(Collectors.toList());
    }
}
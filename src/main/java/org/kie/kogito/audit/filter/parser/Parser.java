package org.kie.kogito.audit.filter.parser;

public interface Parser<T> {

    T parse(ParserContext context);
    
}

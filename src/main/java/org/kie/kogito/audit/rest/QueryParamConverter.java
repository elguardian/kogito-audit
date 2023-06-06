package org.kie.kogito.audit.rest;

import javax.ws.rs.ext.ParamConverter;

import org.kie.kogito.audit.filter.Filter;
import org.kie.kogito.audit.filter.interpreter.WhereInterpreter;
import org.kie.kogito.audit.filter.parser.FilterParser;
import org.kie.kogito.audit.filter.parser.ParserContext;

public class QueryParamConverter implements ParamConverter<Filter> {

    private FilterParser filterParser;
    
    public QueryParamConverter() {
        filterParser = new FilterParser();
    }
    
    @Override
    public Filter fromString(String value) {
        Filter filter = filterParser.parse(new ParserContext(value));
        return new Filter(filter);
    }

    @Override
    public String toString(Filter value) {
        WhereInterpreter interpreter = new WhereInterpreter();
        value.accept(interpreter);
        return interpreter.consume();
    }

}

package org.kie.kogito.audit.filter.parser;

import org.kie.kogito.audit.filter.Filter;

public class FilterParser implements Parser<Filter> {
   
    private ExpressionParser expressionInterpreter;
    
    public FilterParser() {
        expressionInterpreter = new ExpressionParser();
    }
    
    @Override
    public Filter parse(ParserContext context) {
        return new Filter(expressionInterpreter.parse(context));
    }


}

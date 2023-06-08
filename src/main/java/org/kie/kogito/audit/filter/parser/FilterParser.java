package org.kie.kogito.audit.filter.parser;

import org.kie.kogito.audit.filter.Filter;
import org.kie.kogito.audit.filter.VoidExpression;

public class FilterParser implements Parser<Filter> {
   
    private ExpressionParser expressionInterpreter;
    
    public FilterParser() {
        expressionInterpreter = new ExpressionParser();
    }
    
    @Override
    public Filter parse(ParserContext context) {
        if(!context.hasNext()) {
            return new Filter(new VoidExpression());
        }
        return new Filter(expressionInterpreter.parse(context));
    }


}

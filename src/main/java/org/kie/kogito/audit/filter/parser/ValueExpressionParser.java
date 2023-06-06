package org.kie.kogito.audit.filter.parser;

import org.kie.kogito.audit.filter.ValueExpression;

public class ValueExpressionParser implements Parser<ValueExpression> {

    @Override
    public ValueExpression parse(ParserContext context) {
        String value = context.currentToken().asString();
        context.consume();
        return new ValueExpression(value);
    }

}

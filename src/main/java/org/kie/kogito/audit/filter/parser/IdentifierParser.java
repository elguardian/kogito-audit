package org.kie.kogito.audit.filter.parser;

import org.kie.kogito.audit.filter.IdentifierExpression;

public class IdentifierParser implements Parser<IdentifierExpression> {

    @Override
    public IdentifierExpression parse(ParserContext context) {
        String name = context.currentToken().asString();
        context.consume();
        return new IdentifierExpression(name);
    }

}

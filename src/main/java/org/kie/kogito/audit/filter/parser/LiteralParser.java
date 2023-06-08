package org.kie.kogito.audit.filter.parser;

import org.kie.kogito.audit.filter.LiteralExpression;

public class LiteralParser implements Parser<LiteralExpression> {

    @Override
    public LiteralExpression parse(ParserContext context) {
        String literal = context.currentToken().asString();
        context.consume();
        return new LiteralExpression(literal);
    }

}

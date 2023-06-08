package org.kie.kogito.audit.filter.parser;

import org.kie.kogito.audit.filter.MemberExpression;

public class MemberExpressionParser implements Parser<MemberExpression> {

    @Override
    public MemberExpression parse(ParserContext context) {
        String type = context.currentToken().asString();
        context.consume();

        String token = context.currentToken().asString();
        context.consume();
        
        return new MemberExpression(type, token);
    }

}

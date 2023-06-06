package org.kie.kogito.audit.filter.parser;

import org.kie.kogito.audit.filter.Expression;
import org.kie.kogito.audit.filter.GroupExpression;

public class GroupExpressionParser implements Parser<GroupExpression> {

    @Override
    public GroupExpression parse(ParserContext context) {
        context.consume(); // consume open parenthesis

        Expression expression = new ExpressionParser().parse(context);

        if (!TokenType.CLOSE_PARENTHESIS.equals(context.currentToken().getType())) {
            throw new IllegalStateException("Expected ')' found " + context.currentToken().asString());
        }

        context.consume(); // consume close parenthesis
        return new GroupExpression(expression);
    }

}

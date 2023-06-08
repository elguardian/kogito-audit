package org.kie.kogito.audit.filter.parser;

import org.kie.kogito.audit.filter.Expression;
import org.kie.kogito.audit.filter.ExpressionOperand;
import org.kie.kogito.audit.filter.UnaryExpression;

public class UnaryExpressionParser implements Parser<Expression> {
   
    @Override
    public Expression parse(ParserContext context) {
        ExpressionOperand unaryOperand = new UnaryOperandParser().parse(context);
        Expression expression = new ExpressionParser().parse(context);
        return new UnaryExpression(unaryOperand, expression);       
    }

}

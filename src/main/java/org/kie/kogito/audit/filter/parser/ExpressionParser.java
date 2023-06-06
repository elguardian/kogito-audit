package org.kie.kogito.audit.filter.parser;

import org.kie.kogito.audit.filter.BinaryExpression;
import org.kie.kogito.audit.filter.Expression;

public class ExpressionParser implements Parser<Expression> {

    @Override
    public Expression parse(ParserContext context) {
        Token token = context.currentToken();
        Expression leafExpression = null;
        boolean shouldReduce = false;
        switch (token.getType()) {
            case IS_EMPTY_OPERAND:
            case IS_NULL_OPERAND:
            case NOT_OPERAND:
                leafExpression = new UnaryExpressionParser().parse(context);
                break;
            case NUMBER:
                leafExpression = new ValueExpressionParser().parse(context);
                shouldReduce = expect(context, TokenType.AND, TokenType.OR, TokenType.CLOSE_PARENTHESIS);
                break;
            case IDENTIFIER:
                leafExpression = new IdentifierParser().parse(context);
                shouldReduce = expect(context, TokenType.AND, TokenType.OR, TokenType.CLOSE_PARENTHESIS);
                break;
            case OPEN_PARENTHESIS:
                leafExpression = new GroupExpressionParser().parse(context);
                break;
            default:
                throw new IllegalStateException("Illegal token found" + token);
        }

        while (!shouldReduce && context.hasNext()) {
            ExpressionOperand binaryOperand = new BinaryOperandParser().parse(context);
            Expression rm = new ExpressionParser().parse(context);
            leafExpression = new BinaryExpression(leafExpression, binaryOperand, rm);
            shouldReduce = expect(context, TokenType.CLOSE_PARENTHESIS);
        }


        return leafExpression;
    }

    private boolean expect(ParserContext context, TokenType... types) {
        boolean reduce = false;
        for (TokenType type : types) {
            reduce |= context.hasNext() && type.equals(context.currentToken().getType());
        }
        return reduce;
    }
}

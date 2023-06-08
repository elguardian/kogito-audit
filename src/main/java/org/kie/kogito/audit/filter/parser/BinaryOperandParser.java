package org.kie.kogito.audit.filter.parser;

import org.kie.kogito.audit.filter.ExpressionOperand;

public class BinaryOperandParser implements Parser<ExpressionOperand> {

    @Override
    public ExpressionOperand parse(ParserContext context) {
        switch(context.currentToken().getType()) {
            case AND:
                context.consume();
                return ExpressionOperand.AND;
            case OR:
                context.consume();
                return ExpressionOperand.OR;
            case GE_OPERAND:
                context.consume();
                return ExpressionOperand.GE;
            case GT_OPERAND:
                context.consume();
                return ExpressionOperand.GT;
            case EQUAL_OPERAND:
                context.consume();
                return ExpressionOperand.EQUAL;
            case LT_OPERAND:
                context.consume();
                return ExpressionOperand.LT;
            case LE_OPERAND:
                context.consume();
                return ExpressionOperand.LE;
            default:
                throw new IllegalStateException("expected unary operand, found " + context.currentToken().getType());
        }
    }

}

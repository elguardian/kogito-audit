package org.kie.kogito.audit.filter.parser;

public class UnaryOperandParser implements Parser<ExpressionOperand> {

    @Override
    public ExpressionOperand parse(ParserContext context) {
        switch(context.currentToken().getType()) {
            case NOT_OPERAND:
                context.consume();
                return ExpressionOperand.NOT;
            case IS_EMPTY_OPERAND:
                context.consume();
                return ExpressionOperand.EMPTY;
            case IS_NULL_OPERAND:
                context.consume();
                return ExpressionOperand.IS_NULL;
            default:
                throw new IllegalStateException("expected unary operand, found " + context.currentToken().getType());
        }
    }

}

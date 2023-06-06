package org.kie.kogito.audit.filter;

import org.kie.kogito.audit.filter.interpreter.ExpressionInterpreter;
import org.kie.kogito.audit.filter.parser.ExpressionOperand;

public class UnaryExpression implements Expression {

    private ExpressionOperand operand;
    private Expression expression;
    
    public UnaryExpression(ExpressionOperand operand, Expression expression) {
        this.operand = operand;
        this.expression = expression;
    }    
    
    @Override
    public void accept(ExpressionInterpreter<?> visitor) {
        expression.accept(visitor);
        visitor.visit(this);
    }
    
    public ExpressionOperand getOperand() {
        return operand;
    }
    
    public Expression getExpression() {
        return expression;
    }
    
    @Override
    public String toString() {    
        return operand + " " + expression;
    }
}
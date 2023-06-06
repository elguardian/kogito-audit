package org.kie.kogito.audit.filter;

import org.kie.kogito.audit.filter.interpreter.ExpressionInterpreter;

public class GroupExpression implements Expression {

    private Expression expression;
    
    public GroupExpression(Expression expression) {
        this.expression = expression;
    }
 
    @Override
    public void accept(ExpressionInterpreter<?> visitor) {
        expression.accept(visitor);
        visitor.visit(this);        
    }
    
    @Override
    public String toString() {
        return "(" + expression + ")";
    }
}
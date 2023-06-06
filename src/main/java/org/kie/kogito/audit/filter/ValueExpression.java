package org.kie.kogito.audit.filter;

import org.kie.kogito.audit.filter.interpreter.ExpressionInterpreter;

public class ValueExpression implements Expression {

    private Object value;
    
    public ValueExpression(Object value) {
        this.value = value;
    }
    
    
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
    
    
    @Override
    public void accept(ExpressionInterpreter<?> visitor) {
        visitor.visit(this);
    }
    
}

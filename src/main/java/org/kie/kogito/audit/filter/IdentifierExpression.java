package org.kie.kogito.audit.filter;

import org.kie.kogito.audit.filter.interpreter.ExpressionInterpreter;

public class IdentifierExpression implements Expression {

    private String name;

    public IdentifierExpression(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public void accept(ExpressionInterpreter<?> visitor) {
        visitor.visit(this);
    }
    
    @Override
    public String toString() {
        return name;
    }
}

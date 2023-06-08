package org.kie.kogito.audit.filter;

import org.kie.kogito.audit.filter.interpreter.ExpressionInterpreter;

public class LiteralExpression implements Expression {

    private String literal;

    public LiteralExpression(String literal) {
        this.literal = literal;
    }

    @Override
    public void accept(ExpressionInterpreter<?> visitor) {
        visitor.visit(this);
        
    }
    
    @Override
    public String toString() {
        return "\'" + literal + "\'";
    }

    public String getLiteral() {
        return literal;
    }
    
}

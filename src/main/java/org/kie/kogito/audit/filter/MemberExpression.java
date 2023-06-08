package org.kie.kogito.audit.filter;

import org.kie.kogito.audit.filter.interpreter.ExpressionInterpreter;

public class MemberExpression implements Expression {

    private String member;

    public MemberExpression(String member) {
        this.member = member;
    }
    
    @Override
    public void accept(ExpressionInterpreter<?> visitor) {
        visitor.visit(this);
    }

    public String getMember() {
        return member;
    }
}

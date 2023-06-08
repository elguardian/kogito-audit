package org.kie.kogito.audit.filter;

import org.kie.kogito.audit.filter.interpreter.ExpressionInterpreter;

public class MemberExpression implements Expression {

    private String type;
    private String member;

    public MemberExpression(String member) {
        this.type = "STRING";
        this.member = member;
    }
    
    public MemberExpression(String type, String member) {
        this.member = member;
        this.type = type;
    }
    
    @Override
    public void accept(ExpressionInterpreter<?> visitor) {
        visitor.visit(this);
    }

    public String getMember() {
        return member;
    }
    
    public String getType() {
        return type;
    }
}

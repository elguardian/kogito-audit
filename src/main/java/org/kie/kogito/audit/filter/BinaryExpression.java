package org.kie.kogito.audit.filter;

import org.kie.kogito.audit.filter.interpreter.ExpressionInterpreter;

public class BinaryExpression implements Expression {

    private Expression leftMember;
    private ExpressionOperand binaryOperand;
    private Expression rightMember;

    public BinaryExpression(Expression leftMember, ExpressionOperand binaryOperand, Expression rightMember) {
        this.leftMember = leftMember;
        this.binaryOperand = binaryOperand;
        this.rightMember = rightMember;
    }

    @Override
    public void accept(ExpressionInterpreter<?> visitor) {
        leftMember.accept(visitor);
        rightMember.accept(visitor);
        visitor.visit(this);
    }

    public ExpressionOperand getBinaryOperand() {
        return binaryOperand;
    }

    @Override
    public String toString() {
        return leftMember + " " + binaryOperand + " " + rightMember;
    }

    public static Expression opEquals(Expression left, Expression right) {
        return new BinaryExpression(left, ExpressionOperand.EQUAL, right);
    }
    
    public static Expression opExtract(String identifier, String type, String member) {
        return new BinaryExpression(new IdentifierExpression(identifier), ExpressionOperand.EXTRACT, new MemberExpression(type, member));
    }
    
    public static Expression opEquals(String identifier, Object value) {
        return new BinaryExpression(new IdentifierExpression(identifier), ExpressionOperand.EQUAL, new ValueExpression(value));
    }

    public static Expression opAnd(Expression leftMember, Expression rightMember) {
        return new BinaryExpression(leftMember, ExpressionOperand.AND, rightMember);
    }
}
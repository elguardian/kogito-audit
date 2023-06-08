package org.kie.kogito.audit.filter;

import static org.kie.kogito.audit.filter.BinaryExpression.opAnd;

import org.kie.kogito.audit.filter.interpreter.ExpressionInterpreter;

public class Filter implements Expression {

    private Expression expression;

    public Filter(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void accept(ExpressionInterpreter<?> visitor) {
        expression.accept(visitor);
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Filter {" + expression + "}";
    }

    public Filter compose(Filter filter) {
        return new Filter(opAnd(this, filter.getExpression()));
    }
    
    public static Filter filterWithExpression(Expression expression) {
        return new Filter(expression);
    }

    public static Filter emptyFilter() {
        return new Filter(new VoidExpression());
    }
}
package org.kie.kogito.audit.filter;

import org.kie.kogito.audit.filter.interpreter.ExpressionInterpreter;

public class VoidExpression implements Expression {

    @Override
    public void accept(ExpressionInterpreter<?> visitor) {
        visitor.visit(this);
    }

}

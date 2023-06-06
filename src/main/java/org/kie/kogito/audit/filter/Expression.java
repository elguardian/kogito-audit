package org.kie.kogito.audit.filter;

import org.kie.kogito.audit.filter.interpreter.ExpressionInterpreter;

public interface Expression {

    void accept(ExpressionInterpreter<?> visitor);

    default ExpressionType getType() {
        return ExpressionType.STRING;
    }
   
}
package org.kie.kogito.audit.filter.interpreter;

public interface ExpressionInterpretable {

    void visit(ExpressionInterpreter<?> visitor);

}

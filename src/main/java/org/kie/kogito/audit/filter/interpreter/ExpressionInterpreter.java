package org.kie.kogito.audit.filter.interpreter;

import org.kie.kogito.audit.filter.BinaryExpression;
import org.kie.kogito.audit.filter.Filter;
import org.kie.kogito.audit.filter.GroupExpression;
import org.kie.kogito.audit.filter.IdentifierExpression;
import org.kie.kogito.audit.filter.UnaryExpression;
import org.kie.kogito.audit.filter.ValueExpression;
import org.kie.kogito.audit.filter.VoidExpression;

public interface ExpressionInterpreter<T> {
    

    void visit(IdentifierExpression identifierExpression);

    void visit(UnaryExpression unaryExpression);

    void visit(Filter filter);

    void visit(ValueExpression valueExpression);

    void visit(GroupExpression groupExpression);

    T consume();

    void visit(BinaryExpression binaryExpression);

    default void visit(VoidExpression voidExpression) {
        
    }

}

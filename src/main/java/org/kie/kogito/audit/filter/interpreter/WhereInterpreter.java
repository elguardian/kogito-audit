package org.kie.kogito.audit.filter.interpreter;

import java.util.Stack;

import org.kie.kogito.audit.filter.BinaryExpression;
import org.kie.kogito.audit.filter.Filter;
import org.kie.kogito.audit.filter.GroupExpression;
import org.kie.kogito.audit.filter.IdentifierExpression;
import org.kie.kogito.audit.filter.UnaryExpression;
import org.kie.kogito.audit.filter.ValueExpression;

public class WhereInterpreter implements ExpressionInterpreter<String> {

    private Stack<String> stack;

    public WhereInterpreter() {
        stack = new Stack<>();
    }

    @Override
    public void visit(Filter filter) {

    }

    @Override
    public void visit(IdentifierExpression identifierExpression) {
        stack.push(identifierExpression.getName());
    }

    @Override
    public void visit(GroupExpression groupExpression) {
        String expression = stack.pop();
        stack.push("(" + expression + ")");
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
        String right = stack.pop();
        String left = stack.pop();

        switch (binaryExpression.getBinaryOperand()) {
            case AND:
                stack.push(left + " AND " + right);
                break;
            case OR:
                stack.push(left + " OR " + right);
                break;
            case GE:
                stack.push(left + " >= " + right);
                break;
            case GT:
                stack.push(left + " > " + right);
                break;
            case LE:
                stack.push(left + " < " + right);
                break;
            case LT:
                stack.push(left + " <= " + right);
                break;
            case EQUAL:
                stack.push(left + " = " + right);
                break;
            default:
                break;
        }
    }

    @Override
    public void visit(UnaryExpression unaryExpression) {
        String expression = stack.pop();
        switch (unaryExpression.getOperand()) {
            case NOT:
                stack.push("NOT " + expression);
                break;
            case EMPTY:
                stack.push("EMTPY " + expression);
                break;
            case IS_NULL:
                stack.push("IS NULL " + expression);
                break;
            default:
                break;
        }
    }

    @Override
    public void visit(ValueExpression valueExpression) {
        stack.push(valueExpression.toString());
    }

    public String consume() {
        return stack.pop();
    }

}

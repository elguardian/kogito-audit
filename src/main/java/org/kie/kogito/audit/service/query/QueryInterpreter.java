package org.kie.kogito.audit.service.query;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.kie.kogito.audit.filter.BinaryExpression;
import org.kie.kogito.audit.filter.Filter;
import org.kie.kogito.audit.filter.GroupExpression;
import org.kie.kogito.audit.filter.IdentifierExpression;
import org.kie.kogito.audit.filter.UnaryExpression;
import org.kie.kogito.audit.filter.ValueExpression;
import org.kie.kogito.audit.filter.VoidExpression;
import org.kie.kogito.audit.filter.interpreter.ExpressionInterpreter;

public class QueryInterpreter<T> implements ExpressionInterpreter<Query<T>> {

    private static final int IDENTIFIER = 1;
    private static final int VALUE = 2;
    private static final int EXPR = 3;

    private int idx;
    
    private class Node {
        private String computed;
        private int type;

        public Node(String computed) {
            this(computed, EXPR);
        }

        public Node(String computed, int type) {
            this.computed = computed;
            this.type = type;
        }
        
        public String getComputed() {
            return computed;
        }

        public int getType() {
            return type;
        }
    }

    private Class<T> clazz;
    
    private Stack<Node> stack;

    private Map<String, Object> parameters;

    private String entity;
    
    private QueryInterpreter(Class<T> clazz) {
        this(clazz, "");
    }
    
    private QueryInterpreter(Class<T> clazz, String entity) {
        this.clazz = clazz;
        this.entity = entity;
        this.stack = new Stack<>();
        this.parameters = new HashMap<>();
        this.idx = 0;
    }

    @Override
    public Query<T> consume() {
        Query<T> query = new Query<T>(clazz, stack.pop().getComputed(), new HashMap<>(parameters));
        parameters.clear();
        stack.clear();
        return query;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public void visit(VoidExpression voidExpression) {
        stack.push(new Node("", EXPR));
    }

    @Override
    public void visit(Filter filter) {
        String where = stack.pop().getComputed();
        where = where.isEmpty() ? "" : "WHERE " + where;
        stack.push(new Node("SELECT " + entity + " FROM " + clazz.getSimpleName() + " " + entity + " " + where, EXPR));

    }

    @Override
    public void visit(IdentifierExpression identifierExpression) {
        stack.push(new Node(toEntity(identifierExpression.getName()), IDENTIFIER));
    }

    private String toEntity(String name) {
        return (this.entity.isEmpty()) ? name : entity + "." + name;
    }
    
    @Override
    public void visit(GroupExpression groupExpression) {
        Node expression = stack.pop();
        stack.push(new Node("(" + expression.getComputed() + ")", expression.getType()));
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
        Node right = stack.pop();
        Node left = stack.pop();

        switch (binaryExpression.getBinaryOperand()) {
            case AND:
                stack.push(new Node(left.getComputed() + " AND " + right.getComputed()));
                break;
            case OR:
                stack.push(new Node(left.getComputed() + " OR " + right.getComputed()));
                break;
            case GE:
                stack.push(new Node(left.getComputed() + " >= " + right.getComputed()));
                break;
            case GT:
                stack.push(new Node(left.getComputed() + " > " + right.getComputed()));
                break;
            case LE:
                stack.push(new Node(left.getComputed() + " < " + right.getComputed()));
                break;
            case LT:
                stack.push(new Node(left.getComputed() + " <= " + right.getComputed()));
                break;
            case EQUAL:
                stack.push(new Node(left.getComputed() + " = " + right.getComputed()));
                break;
            default:
                System.out.println("FAIL!");
                break;
        }
    }

    @Override
    public void visit(UnaryExpression unaryExpression) {
        Node expression = stack.pop();
        switch (unaryExpression.getOperand()) {
            case NOT:
                stack.push(new Node("NOT " + expression.getComputed()));
                break;
            case EMPTY:
                stack.push(new Node("EMTPY " + expression.getComputed()));
                break;
            case IS_NULL:
                stack.push(new Node(expression.getComputed() + " IS NULL"));
                break;
            default:
                System.out.println("FAIL!");
                break;
        }
    }

    @Override
    public void visit(ValueExpression valueExpression) {
        String paramName = "param_" + idx++;
        parameters.put(paramName, valueExpression.getValue());

        stack.push(new Node(":" + paramName, VALUE));

    }
           
    static public class QueryInterpreterBuilder<B> {
        private Class<B> clazz;
        private String entity;
        private Filter filter;
        
        public QueryInterpreterBuilder(Class<B> clazz) {
            this.clazz = clazz;
            this.entity = "o";
        }
        
        public QueryInterpreterBuilder<B> prefix(String entity) {
            this.entity = entity;
            return this;
        }
        
        public QueryInterpreterBuilder<B> filter(Filter filter) {
            this.filter = filter;
            return this;
        }
        
        public Query<B> build() {
            QueryInterpreter<B> interpreter = new QueryInterpreter<B>(clazz, entity);
            this.filter.accept(interpreter);
            return interpreter.consume();
        }
        
    }
    
    static public <C> QueryInterpreterBuilder<C> newBuilder(Class<C> clazz) {
        return new QueryInterpreterBuilder<C>(clazz);
    }
}

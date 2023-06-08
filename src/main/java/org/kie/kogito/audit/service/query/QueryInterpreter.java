package org.kie.kogito.audit.service.query;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.kie.kogito.audit.filter.BinaryExpression;
import org.kie.kogito.audit.filter.Filter;
import org.kie.kogito.audit.filter.GroupExpression;
import org.kie.kogito.audit.filter.IdentifierExpression;
import org.kie.kogito.audit.filter.LiteralExpression;
import org.kie.kogito.audit.filter.MemberExpression;
import org.kie.kogito.audit.filter.UnaryExpression;
import org.kie.kogito.audit.filter.ValueExpression;
import org.kie.kogito.audit.filter.VoidExpression;
import org.kie.kogito.audit.filter.interpreter.ExpressionInterpreter;

public class QueryInterpreter<T> implements ExpressionInterpreter<Query<T>> {

    private int idx;
    
    private class Node {       
        private String computed;
        
        private Object value;

        private Class<?> type;

        private String parameterName;
        
        public Node() {
            this("", null, Object.class);
        }

        public Node(String computed, Class<?> type) {
            this(computed, computed, type);
        }
        
        public Node(String computed, Object value, Class<?> type) {
            this.value = value;
            this.computed = computed;
            this.type = type;
        }
        
        public String getComputed() {
            return computed;
        }

        public Object getValue() {
            return value;
        }

        public Class<?> getType() {
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
        stack.push(new Node());
    }

    @Override
    public void visit(Filter filter) {
        Node node = stack.pop();
        String where = node.getComputed();
        where = where.isEmpty() ? "" : "WHERE " + where;
        stack.push(new Node("SELECT " + entity + " FROM " + clazz.getSimpleName() + " " + entity + " " + where, node.getType()));
    }

    @Override
    public void visit(LiteralExpression literalExpression) {        
        stack.push(createParameterNode(literalExpression.getLiteral(), String.class));
    }
    
    @Override
    public void visit(MemberExpression memberExpression) {
        stack.push(new Node("'" + memberExpression.getMember() + "'", String.class));
        
    }

    @Override
    public void visit(ValueExpression valueExpression) {
        stack.push(createParameterNode(valueExpression.getValue(), valueExpression.getValue().getClass()));
    }
           
    private Node createParameterNode(Object value, Class<?> type) {
        String paramName = "param_" + idx++;
        parameters.put(paramName, value);
        Node node = new Node(":" + paramName, value, type);
        node.parameterName = paramName;
        return node;
    }
    
    @Override
    public void visit(IdentifierExpression identifierExpression) {
        Class<?> type;
        try {
            type = clazz.getDeclaredField(identifierExpression.getName()).getType();
        } catch (NoSuchFieldException | SecurityException e) {
            type = Object.class;
        }
        stack.push(new Node(toEntity(identifierExpression.getName()), type));
    }

    private String toEntity(String name) {
        return (this.entity.isEmpty()) ? name : entity + "." + name;
    }
    
    @Override
    public void visit(GroupExpression groupExpression) {
        Node expression = stack.pop();
        expression.computed = "(" + expression.getComputed() + ")"; // derive the node just changing this
        stack.push(expression);
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
        Node right = stack.pop();
        Node left = stack.pop();

        coarce(right, left);
        switch (binaryExpression.getBinaryOperand()) {
            case AND:
                stack.push(new Node(left.getComputed() + " AND " + right.getComputed(), Boolean.class));
                break;
            case OR:
                stack.push(new Node(left.getComputed() + " OR " + right.getComputed(), Boolean.class));
                break;
            case GE:

                stack.push(new Node(left.getComputed() + " >= " + right.getComputed(), Boolean.class));
                break;
            case GT:
                stack.push(new Node(left.getComputed() + " > " + right.getComputed(), Boolean.class));
                break;
            case LE:
                stack.push(new Node(left.getComputed() + " < " + right.getComputed(), Boolean.class));
                break;
            case LT:
                stack.push(new Node(left.getComputed() + " <= " + right.getComputed(), Boolean.class));
                break;
            case EQUAL:
                stack.push(new Node(left.getComputed() + " = " + right.getComputed(), Boolean.class));
                break;
            case EXTRACT:
                stack.push(new Node("JSON_EXTRACT(" +left.getComputed() + ", " + right.getComputed() + ", '" + left.type.getSimpleName().toUpperCase() + "')", left.type));
                break;
            default:
                System.out.println("FAIL! " + binaryExpression.getBinaryOperand());
                break;
        }
    }

    private void coarce(Node right, Node left) {
        if(Enum.class.isAssignableFrom(right.getType()) && left.type.equals(String.class)) {
            cast(left,right.getType());
        } else if (Enum.class.isAssignableFrom(left.getType()) && right.type.equals(String.class)) {
            cast(right, left.getType());
        }
    }

    private void cast(Node left, Class<?> type) {
        Object [] items = type.getEnumConstants();
        for (Object item : items) {
            if( ((Enum) item).name().equalsIgnoreCase((String) left.getValue())) {
                if(parameters.containsKey(left.parameterName)) {
                    parameters.put(left.parameterName, item);
                }
                left.value = item;
                left.type = type;
                return;
            }
        }
        
    }

    @Override
    public void visit(UnaryExpression unaryExpression) {
        Node expression = stack.pop();
        switch (unaryExpression.getOperand()) {
            case NOT:
                stack.push(new Node("NOT " + expression.getComputed(), Boolean.class));
                break;
            case EMPTY:
                stack.push(new Node("EMTPY " + expression.getComputed(), Boolean.class));
                break;
            case IS_NULL:
                stack.push(new Node(expression.getComputed() + " IS NULL", Boolean.class));
                break;
            default:
                System.out.println("FAIL!");
                break;
        }
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

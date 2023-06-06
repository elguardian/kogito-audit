package org.kie.kogito.audit.service.query;

import java.util.Map;

public final class Query<T> {

    private Class<T> clazz;
    private String sql;
    private Map<String, Object> parameters;

    Query(Class<T> clazz, String sql, Map<String, Object> parameters) {
        this.clazz = clazz;
        this.sql = sql;
        this.parameters = parameters;
    }

    public Class<T> getType() {
        return clazz;
    }
    
    public String getSQL() {
        return sql;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }


}

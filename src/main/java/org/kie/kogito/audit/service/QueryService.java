package org.kie.kogito.audit.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.kie.kogito.audit.filter.Filter;
import org.kie.kogito.audit.service.query.Query;
import org.kie.kogito.audit.service.query.QueryInterpreter;

@ApplicationScoped
public class QueryService {

    @Inject
    protected EntityManager em;
    
    public <T> List<T> execute(Class<T> clazz, Filter filter) {
        Query<T> query = QueryInterpreter.newBuilder(clazz).filter(filter).build();
        TypedQuery<T> jpaQuery = em.createQuery(query.getSQL(), query.getType());
        query.getParameters().forEach(jpaQuery::setParameter);
        return jpaQuery.getResultList();
    }
}

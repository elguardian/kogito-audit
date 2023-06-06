package org.kie.kogito.audit.rest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import org.kie.kogito.audit.filter.Filter;

@Provider
public class QueryParamConverterProvider implements ParamConverterProvider {

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if(Filter.class.equals(rawType)) {
            return (ParamConverter<T>) new QueryParamConverter();
        }
        return null;
    }

}

package org.kie.kogito.audit.event.hibernate;

import java.util.List;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

public final class JsonExtractFunction implements SQLFunction {

    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public boolean hasParenthesesIfNoArguments() {
        return true;
    }

    @Override
    public Type getReturnType(Type firstArgumentType, Mapping mapping) throws QueryException {
        return new StringType();
    }

    @Override
    public String render(Type firstArgumentType, List arguments, SessionFactoryImplementor factory) throws QueryException {
        String cast = "";
        if (arguments.size() > 2) {
            switch ((String) arguments.get(2)) {
                case "'INTEGER'":
                    cast = "::numeric";
                    break;
            }
        }

        return "(" + arguments.get(0) + " -> " + arguments.get(1) + ")::text" + cast;
    }
}
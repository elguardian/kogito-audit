package org.kie.kogito.audit.service.query;

import org.kie.kogito.audit.filter.Filter;
import org.kie.kogito.audit.filter.interpreter.WhereInterpreter;
import org.kie.kogito.audit.filter.parser.FilterParser;
import org.kie.kogito.audit.filter.parser.ParserContext;
import org.kie.kogito.audit.model.ProcessInstanceLog;

public class Snippet {

    public static void main(String[] args) throws Exception {         
        String filterExpression2 = "NOT exit AND (value1 = 2 OR value2 >= 4) OR (value3 = 3)";
        FilterParser filterParser = new FilterParser();
        Filter filter = filterParser.parse(new ParserContext(filterExpression2));
        
        System.out.println(filter);
        
        WhereInterpreter stringInterpreter = new WhereInterpreter();
        
        filter.accept(stringInterpreter);
        
        System.out.println(stringInterpreter.consume());
        
        Query<ProcessInstanceLog> query = QueryInterpreter.newBuilder(ProcessInstanceLog.class).filter(filter).build();
        
        System.out.println(query.getSQL());
        System.out.println(query.getParameters());
    }
    
}


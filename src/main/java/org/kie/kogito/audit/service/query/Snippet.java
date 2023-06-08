package org.kie.kogito.audit.service.query;

import static org.kie.kogito.audit.filter.BinaryExpression.opEquals;
import static org.kie.kogito.audit.filter.BinaryExpression.opExtract;

import org.kie.kogito.audit.filter.Filter;
import org.kie.kogito.audit.filter.ValueExpression;
import org.kie.kogito.audit.filter.interpreter.WhereInterpreter;
import org.kie.kogito.audit.filter.parser.FilterParser;
import org.kie.kogito.audit.filter.parser.ParserContext;
import org.kie.kogito.audit.model.JobExecutionLog;

public class Snippet {

    enum EnumTest {
        ONE, TWO;
    }
    
    public static void main(String[] args) throws Exception {        
        WhereInterpreter whereInterpreter = new WhereInterpreter();
        Filter filter4 = new FilterParser().parse(new ParserContext("requestData -> INTEGER key = 1"));
        filter4.accept(whereInterpreter);
        System.out.println(whereInterpreter.consume());
        
        Filter filter3 = Filter.filterWithExpression(opEquals(opExtract("requestData", "INTEGER", "key"), new ValueExpression(1)));
        filter3.accept(whereInterpreter);
        System.out.println(whereInterpreter.consume());
        
        System.out.println(QueryInterpreter.newBuilder(JobExecutionLog.class).filter(filter3).build().getSQL());
        
        String filterExpression2 = "NOT exit AND (value1 = 'ONE' OR value2 >= 4) OR (value3 = 3)";
        FilterParser filterParser = new FilterParser();
        Filter filter = filterParser.parse(new ParserContext(filterExpression2));
        
        System.out.println(filter);
        
        WhereInterpreter stringInterpreter = new WhereInterpreter();
        
        filter.accept(stringInterpreter);
        
        System.out.println(stringInterpreter.consume());
        

        class Test {
            EnumTest value1;
            Integer value2;
            Integer value3;
        }
        
        Query<Test> query = QueryInterpreter.newBuilder(Test.class).filter(filter).build();
        
        System.out.println(query.getSQL());
        System.out.println(query.getParameters());
    }
    
}


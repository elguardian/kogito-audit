package org.kie.kogito.data.index.service.messaging;

import static org.kie.kogito.audit.filter.BinaryExpression.opEquals;
import static org.kie.kogito.audit.filter.BinaryExpression.opExtract;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.kogito.audit.filter.Filter;
import org.kie.kogito.audit.filter.IdentifierExpression;
import org.kie.kogito.audit.filter.LiteralExpression;
import org.kie.kogito.audit.filter.ValueExpression;
import org.kie.kogito.audit.model.JobExecutionLog;
import org.kie.kogito.audit.model.ProcessInstanceLog;
import org.kie.kogito.audit.model.ProcessInstanceLog.LogType;
import org.kie.kogito.audit.service.QueryService;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(PostgreSQLTestProfile.class)
public class QueryServiceTest {

    @Inject
    protected EntityManager em;

    @Inject
    protected QueryService queryService;

    @Inject
    protected ObjectMapper mapper;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
    class JsonData {

        private Integer key;

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }
    }

    @BeforeEach
    @Transactional
    public void init() throws Exception {
        ProcessInstanceLog log = new ProcessInstanceLog();
        log.setProcessInstanceId("1");
        log.setEventType(LogType.STARTED);
        em.persist(log);

        JobExecutionLog log2 = new JobExecutionLog();

        JsonData data = new JsonData();
        data.key = 1;
        String stringData = mapper.writeValueAsString(data);
        System.out.println(stringData);
        log2.setRequestData(stringData);
        em.persist(log2);
    }

    @AfterEach
    @Transactional
    public void tear() {
        em.createQuery("DELETE FROM ProcessInstanceLog").executeUpdate();
        em.createQuery("DELETE FROM JobExecutionLog").executeUpdate();
    }

    @Test
    @Transactional
    public void testFilterBasicStringWithEmptyOutcome() {
        Filter filter = Filter.filterWithExpression(opEquals("processInstanceId", "2"));
        Assertions.assertEquals(0, queryService.execute(ProcessInstanceLog.class, filter).size());
    }

    @Test
    @Transactional
    public void testFilterBasicString() {
        Filter filter = Filter.filterWithExpression(opEquals("processInstanceId", "1"));
        Assertions.assertEquals(1, queryService.execute(ProcessInstanceLog.class, filter).size());
    }

    @Test
    @Transactional
    public void testFilterBasicEnumerationWithCoerce() {
        Filter filter = Filter.filterWithExpression(opEquals(new IdentifierExpression("eventType"), new LiteralExpression("STARTED")));
        Assertions.assertEquals(1, queryService.execute(ProcessInstanceLog.class, filter).size());
    }

    @Test
    @Transactional
    public void testFilterBasicEnumerationWithoutCoerce() {
        Filter filter = Filter.filterWithExpression(opEquals("eventType", LogType.STARTED));
        Assertions.assertEquals(1, queryService.execute(ProcessInstanceLog.class, filter).size());
    }

    @Test
    @Transactional
    public void testFilterJsonColumn() {
        List<JobExecutionLog> logs = queryService.execute(JobExecutionLog.class, Filter.emptyFilter());
        Assertions.assertEquals(1, logs.size());
    }

    @Test
    @Transactional
    public void testJsonColumnSearch() {
        Filter filter = Filter.filterWithExpression(opEquals(opExtract("requestData", "INTEGER", "key"), new ValueExpression(1)));
        List<JobExecutionLog> logs = queryService.execute(JobExecutionLog.class, filter);
        Assertions.assertEquals(1, logs.size());
    }

    @Test
    @Transactional
    public void testJsonColumnSearchEmpty() {
        Filter filter = Filter.filterWithExpression(opEquals(opExtract("requestData", "INTEGER", "key"), new ValueExpression(2)));
        List<JobExecutionLog> logs = queryService.execute(JobExecutionLog.class, filter);
        Assertions.assertEquals(0, logs.size());
    }

}

package org.kie.kogito.audit.rest;

import static org.kie.kogito.audit.filter.BinaryExpression.opAnd;
import static org.kie.kogito.audit.filter.BinaryExpression.opEquals;
import static org.kie.kogito.audit.filter.Filter.emptyFilter;
import static org.kie.kogito.audit.filter.Filter.filterWithExpression;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.kie.kogito.audit.filter.Filter;
import org.kie.kogito.audit.model.ProcessInstanceLog;
import org.kie.kogito.audit.model.ProcessInstanceNodeLog;
import org.kie.kogito.audit.model.ProcessInstanceVariableLog;
import org.kie.kogito.audit.service.ProcessInstanceHistoryService;
import org.kie.kogito.audit.service.QueryService;

@Path("q/events")
@Produces("application/json")
public class EventsEndpoint {

    @Inject
    protected QueryService queryService;

    @Inject
    protected ProcessInstanceHistoryService processInstanceHistoryService;

    @GET
    @Path("/process")
    public List<ProcessInstanceLog> eventsByProcess(@QueryParam("filter") @DefaultValue("") Filter filter, @QueryParam("value") Integer page, @QueryParam("size") Integer size) {
        return queryService.execute(ProcessInstanceLog.class, emptyFilter());
    }

    @GET
    @Path("/process/{pid}/")
    public List<ProcessInstanceLog> eventByProcessId(@PathParam("pid") String pid, @QueryParam("filter") @DefaultValue("") Filter filter) {
        return queryService.execute(ProcessInstanceLog.class, filter.compose(filterWithExpression(opEquals("pid", pid))));
    }

    @GET
    @Path("/process/{pid}/node")
    public List<ProcessInstanceNodeLog> eventNodesByProcessId(@PathParam("pid") String pid, @QueryParam("filter") @DefaultValue("") Filter filter) {
        return queryService.execute(ProcessInstanceNodeLog.class, filter.compose(filterWithExpression(opEquals("pid", pid))));
    }

    @GET
    @Path("/process/{pid}/node/{noid}")
    public List<ProcessInstanceNodeLog> eventNodesById(@PathParam("pid") String pid, @PathParam("nid") String nid, @QueryParam("filter") @DefaultValue("") Filter filter) {
        return queryService.execute(ProcessInstanceNodeLog.class, filter.compose(filterWithExpression(opAnd(opEquals("pid", pid), opEquals("nid", pid)))));
    }

    @GET
    @Path("/process/{pid}/variable")
    public List<ProcessInstanceVariableLog> eventsByVariable(@PathParam("pid") String pid, @QueryParam("filter") @DefaultValue("") Filter filter) {
        return queryService.execute(ProcessInstanceVariableLog.class, filter.compose(filterWithExpression(opEquals("pid", pid))));
    }

    @GET
    @Path("/process/{pid}/variable/{vid}")
    public List<ProcessInstanceVariableLog> eventsByVariableId(@PathParam("pid") String pid, @QueryParam("filter") @DefaultValue("") Filter filter) {
        return queryService.execute(ProcessInstanceVariableLog.class, filter.compose(filterWithExpression(opEquals("pid", pid))));
    }

    @GET
    @Path("/task/}")
    public List<ProcessInstanceVariableLog> eventsByTask(@QueryParam("filter") @DefaultValue("") Filter filter) {
        return queryService.execute(ProcessInstanceVariableLog.class, filter);
    }

    @GET
    @Path("/task/{tid}}")
    public List<ProcessInstanceVariableLog> eventsByTask(@PathParam("tid") String tid, @QueryParam("filter") @DefaultValue("") Filter filter) {
        return queryService.execute(ProcessInstanceVariableLog.class, filter.compose(filterWithExpression(opEquals("pid", tid))));
    }

    @GET
    @Path("/job/")
    public List<ProcessInstanceVariableLog> eventsByJob(@QueryParam("filter") @DefaultValue("") Filter filter) {
        return queryService.execute(ProcessInstanceVariableLog.class, filter);
    }

    @GET
    @Path("/job/{jid}/")
    public List<ProcessInstanceVariableLog> eventsByJobById(@PathParam("jid") String jid, @QueryParam("filter") @DefaultValue("") Filter filter) {
        return queryService.execute(ProcessInstanceVariableLog.class, filter.compose(filterWithExpression(opEquals("jid", jid))));
    }
}

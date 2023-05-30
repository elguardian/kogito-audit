/*
 * Copyright 2023 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.kogito.audit.transport.streams;

import static org.kie.kogito.audit.transport.streams.Transport.KOGITO_PROCESS_INSTANCES_EVENTS;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.kie.kogito.audit.event.engine.ProcessInstanceErrorEvent;
import org.kie.kogito.audit.event.engine.ProcessInstanceEvent;
import org.kie.kogito.audit.event.engine.ProcessInstanceNodeEvent;
import org.kie.kogito.audit.event.engine.ProcessInstanceVariableEvent;
import org.kie.kogito.audit.event.job.JobExecutorEvent;
import org.kie.kogito.audit.event.usertask.TaskInstanceEvent;
import org.kie.kogito.audit.event.usertask.TaskInstanceVariableEvent;
import org.kie.kogito.audit.service.index.engine.ProcessInstanceHistoryService;
import org.kie.kogito.audit.service.index.job.JobHistoryService;
import org.kie.kogito.audit.service.index.usertask.UserTaskHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class MessagingEventConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingEventConsumer.class);

    @Inject 
    protected ObjectMapper mapper;
    
    @Inject 
    protected ProcessInstanceHistoryService processInstanceHistoryService;
    
    @Inject
    protected JobHistoryService jobHistoryService;
    
    @Inject
    protected UserTaskHistoryService userTaskHistoryService;
    
    @Incoming(KOGITO_PROCESS_INSTANCES_EVENTS)
    @NonBlocking
    @Transactional
    public Uni<Void> onProcessInstanceEvent(Message<JsonNode> event) throws IllegalArgumentException, ClassNotFoundException {
        LOGGER.debug("Node instance consumer received ProcessInstanceDataEvent: \n{}", event);
        return Uni.createFrom().item(event)
                .invoke(this::execute)
                .onFailure()
                .invoke(t -> LOGGER.error("Error processing process instance ProInstanceDataEvent: {}", t.getMessage(), t))
                .onItem().ignore().andContinueWithNull();

    }

    private void execute(Message<JsonNode> event) {
        String type = event.getPayload().get("@type").asText();
        LOGGER.info("onProcessInstanceEvent: {}\n", event.getPayload());

        switch(type) {
            case "ProcessInstanceErrorEvent":
                processInstanceHistoryService.index(transfromTo(ProcessInstanceErrorEvent.class, event.getPayload()));
                break;
            case "ProcessInstanceEvent":
                processInstanceHistoryService.index(transfromTo(ProcessInstanceEvent.class, event.getPayload()));
                break;
            case "ProcessInstanceNodeEvent":
                processInstanceHistoryService.index(transfromTo(ProcessInstanceNodeEvent.class, event.getPayload()));
                break;
            case "ProcessInstanceVariableEvent":
                processInstanceHistoryService.index(transfromTo(ProcessInstanceVariableEvent.class, event.getPayload()));
                break;
            case "JobExecutorEvent":
                jobHistoryService.index(transfromTo(JobExecutorEvent.class, event.getPayload()));
                break;
            case "TaskInstanceEvent":
                userTaskHistoryService.index(transfromTo(TaskInstanceEvent.class, event.getPayload()));
                break;
            case "TaskInstanceVariableEvent":
                userTaskHistoryService.index(transfromTo(TaskInstanceVariableEvent.class, event.getPayload()));
                break;
        }
    }
    private <T> T transfromTo(Class<T> clazz, JsonNode node) {
        return mapper.convertValue(node, clazz);
    }
}

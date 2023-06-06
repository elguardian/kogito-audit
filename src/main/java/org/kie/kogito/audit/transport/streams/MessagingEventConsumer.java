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

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.kie.kogito.audit.event.JobExecutorEvent;
import org.kie.kogito.audit.event.ProcessInstanceErrorEvent;
import org.kie.kogito.audit.event.ProcessInstanceEvent;
import org.kie.kogito.audit.event.ProcessInstanceNodeEvent;
import org.kie.kogito.audit.event.ProcessInstanceVariableEvent;
import org.kie.kogito.audit.event.TaskInstanceEvent;
import org.kie.kogito.audit.service.JobHistoryService;
import org.kie.kogito.audit.service.ProcessInstanceHistoryService;
import org.kie.kogito.audit.service.UserTaskHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.smallrye.common.annotation.NonBlocking;

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
    public void onSystemEvent(JsonNode event) throws IllegalArgumentException, ClassNotFoundException {
        LOGGER.debug("Node instance consumer received ProcessInstanceDataEvent: \n{}", event);
        List<JsonNode> objects = new ArrayList<>();

        if (!event.isArray()) {
            objects.add(event);
        } else {
            for (int i = 0; i < event.size(); i++) {
                objects.add(event.get(i));
            }
        }

        objects.stream().forEach(this::execute);
    }

    private void execute(JsonNode event) {
        String type = event.get("@type").asText();
        LOGGER.info("onProcessInstanceEvent: {}", event);

        switch (type) {
            case "ProcessInstanceErrorEvent":
                processInstanceHistoryService.index(transfromTo(ProcessInstanceErrorEvent.class, event));
                break;
            case "ProcessInstanceEvent":
                processInstanceHistoryService.index(transfromTo(ProcessInstanceEvent.class, event));
                break;
            case "ProcessInstanceNodeEvent":
                processInstanceHistoryService.index(transfromTo(ProcessInstanceNodeEvent.class, event));
                break;
            case "ProcessInstanceVariableEvent":
                processInstanceHistoryService.index(transfromTo(ProcessInstanceVariableEvent.class, event));
                break;
            case "JobExecutorEvent":
                jobHistoryService.index(transfromTo(JobExecutorEvent.class, event));
                break;
            case "TaskInstanceEvent":
                userTaskHistoryService.index(transfromTo(TaskInstanceEvent.class, event));
                break;
            case "TaskInstanceVariableEvent":
                break;
        }
    }

    private <T> T transfromTo(Class<T> clazz, JsonNode node) {
        return mapper.convertValue(node, clazz);
    }
}

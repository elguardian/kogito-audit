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

package org.kie.kogito.audit.service.index.usertask;

import static org.kie.kogito.audit.model.util.EnumUtil.toEnum;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.kie.kogito.audit.event.usertask.TaskInstanceEvent;
import org.kie.kogito.audit.event.usertask.TaskInstanceVariableEvent;
import org.kie.kogito.audit.model.usertask.TaskInstanceLog;
import org.kie.kogito.audit.model.usertask.TaskInstanceVariableLog;

@ApplicationScoped
public class UserTaskHistoryService {

    @Inject
    protected EntityManager em;
      
    public void index(TaskInstanceEvent event) {
        TaskInstanceLog log = new TaskInstanceLog();
        log.setTaskId(event.getTaskId());
        log.setName(event.getName());
        log.setDescription(event.getDescription());
        log.setActualUser(event.getActualUser());
        log.setDeploymentId(event.getDeploymentId());
        log.setProcessId(event.getProcessId());
        log.setNodeId(event.getNodeId());
        log.setProcessInstanceId(event.getProcessInstanceId());
        log.setNodeInstanceId(event.getNodeInstanceId());
        log.setEventDate(event.getEventDate());
        log.setEventType(toEnum(TaskInstanceLog.LogType.class, event.getEventType()));

        em.persist(log);
    }
    
    public void index(TaskInstanceVariableEvent event) {
        TaskInstanceVariableLog log = new TaskInstanceVariableLog();
        log.setProcessId(event.getProcessId());
        log.setProcessInstanceId(event.getProcessInstanceId());
        log.setTaskId(event.getTaskId());
        log.setName(event.getName());
        log.setNewValue(event.getNewValue());
        log.setOldValue(event.getOldValue());
        log.setType(TaskInstanceVariableLog.VariableType.values()[event.getType()]);
        log.setEventDate(event.getEventDate());
        log.setEventType(TaskInstanceVariableLog.LogType.values()[event.getEventType()]);
        log.setEventUser(event.getEventUser());

        em.persist(log);
    }
}

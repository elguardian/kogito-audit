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

package org.kie.kogito.audit.service.index.engine;

import static org.kie.kogito.audit.model.util.EnumUtil.toEnum;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.kie.kogito.audit.event.engine.ProcessInstanceErrorEvent;
import org.kie.kogito.audit.event.engine.ProcessInstanceEvent;
import org.kie.kogito.audit.event.engine.ProcessInstanceNodeEvent;
import org.kie.kogito.audit.event.engine.ProcessInstanceVariableEvent;
import org.kie.kogito.audit.model.engine.ProcessInstanceErrorLog;
import org.kie.kogito.audit.model.engine.ProcessInstanceLog;
import org.kie.kogito.audit.model.engine.ProcessInstanceNodeLog;
import org.kie.kogito.audit.model.engine.ProcessInstanceVariableLog;

@ApplicationScoped
public class ProcessInstanceHistoryService {

    @Inject
    protected EntityManager em;

    public void index(ProcessInstanceEvent event) {
        ProcessInstanceLog log = new ProcessInstanceLog();
        log.setDeploymentId(event.getDeploymentId());
        log.setProcessType(event.getProcessType());
        log.setProcessId(event.getProcessId());
        log.setProcessVersion(event.getProcessVersion());
        log.setProcessName(event.getProcessName());
        log.setParentProcessInstanceId(event.getParentProcessInstanceId());
        log.setProcessInstanceId(event.getProcessInstanceId());
        log.setBusinessKey(event.getBusinessKey());
        log.setProcessInstanceDescription(event.getProcessInstanceDescription());
        log.setEventDate(event.getEventDate());
        log.setEventType(toEnum(ProcessInstanceLog.LogType.class, event.getEventType()));
        log.setEventUser(event.getEventUser());
        log.setOutcome(event.getOutcome());
        log.setSlaDueDate(event.getSlaDueDate());
        em.persist(log);
    }

    public void index(ProcessInstanceErrorEvent event) {
        ProcessInstanceErrorLog log = new ProcessInstanceErrorLog();
        log.setDeploymentId(event.getDeploymentId());
        log.setProcessId(event.getProcessId());
        log.setProcessInstanceId(event.getProcessInstanceId());
        log.setType(event.getType());
        log.setJobId(event.getJobId());
        log.setActivityId(event.getActivityId());
        log.setActivityName(event.getActivityName());
        log.setError(event.getError());
        log.setErrorMessage(event.getErrorMessage());
        log.setErrorDate(event.getErrorDate());
        em.persist(log);
    }

    public void index(ProcessInstanceVariableEvent event) {
        ProcessInstanceVariableLog log = new ProcessInstanceVariableLog();
        log.setDeploymentId(event.getDeploymentId());
        log.setProcessId(event.getProcessId());
        log.setProcessInstanceId(event.getProcessInstanceId());
        log.setEventDate(event.getEventDate());
        log.setEventType(toEnum(ProcessInstanceVariableLog.LogType.class, event.getEventType()));
        log.setEventUser(event.getEventUser());
        log.setNewValue(event.getNewValue());
        log.setOldValue(event.getOldValue());
        log.setVariableId(event.getVariableId());
        log.setVariableInstanceId(event.getVariableInstanceId());
        em.persist(log);
    }

    public void index(ProcessInstanceNodeEvent event) {
        ProcessInstanceNodeLog log = new ProcessInstanceNodeLog();
        log.setDeploymentId(event.getDeploymentId());
        log.setProcessId(event.getProcessId());
        log.setProcessInstanceId(event.getProcessInstanceId());
        log.setEventDate(event.getEventDate());
        log.setEventType(toEnum(ProcessInstanceNodeLog.LogType.class, event.getEventType()));
        log.setEventData(event.getEventData());
        log.setNodeContainerId(event.getNodeContainerId());
        log.setNodeId(event.getNodeId());
        log.setNodeInstanceId(event.getNodeInstanceId());
        log.setNodeName(event.getNodeName());
        log.setSlaDueDate(event.getSlaDueDate());
        log.setWorkItemId(event.getWorkItemId());
        log.setEventData(event.getEventData());
        em.persist(log);

    }

    List<ProcessInstanceEvent> findByProcessInstanceId(String processInstanceId) {
        return null;
    }
}

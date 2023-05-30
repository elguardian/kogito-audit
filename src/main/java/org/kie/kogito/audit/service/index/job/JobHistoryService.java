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

package org.kie.kogito.audit.service.index.job;

import static org.kie.kogito.audit.model.util.EnumUtil.toEnum;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.kie.kogito.audit.event.job.JobExecutorEvent;
import org.kie.kogito.audit.model.job.JobExecutionLog;

@ApplicationScoped
public class JobHistoryService {

    @Inject
    protected EntityManager em;

    public void index(JobExecutorEvent event) {
        JobExecutionLog log = new JobExecutionLog();
        log.setDeploymentId(event.getDeploymentId());
        log.setBusinessKey(event.getBusinessKey());
        log.setEventDate(event.getEventDate());
        log.setEventType(toEnum(JobExecutionLog.LogType.class, event.getEventType()));
        log.setEventUser(event.getEventUser());
        log.setMessage(event.getMessage());
        log.setNumberOfExecution(event.getNumberOfExecution());
        log.setNumberOfRetry(event.getNumberOfRetry());
        log.setProcessId(event.getProcessId());
        log.setProcessInstanceId(event.getProcessInstanceId());
        log.setRequestData(event.getRequestData());
        log.setResponseData(event.getResponseData());
        em.persist(log);
    }

}

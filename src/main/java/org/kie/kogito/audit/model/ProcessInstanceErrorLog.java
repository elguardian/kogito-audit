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

package org.kie.kogito.audit.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "ProcessInstanceErrorHistory")
@SequenceGenerator(name = "processInstanceErrorHistorySeq", sequenceName = "PROCESS_INSTANCE_ERROR_HISTORY_SEQ_ID", allocationSize = 1)
public class ProcessInstanceErrorLog {

    @Transient
    private static final Logger logger = LoggerFactory.getLogger(ProcessInstanceErrorLog.class);

    @Transient
    private final int ERROR_LOG_LENGTH = Integer.parseInt(System.getProperty("org.kie.jbpm.error.log.length", "255"));

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="processInstanceErrorHistorySeq")
    private Long id;
    
    @Column(name = "deployment_id")
    private String deploymentId;

    @Column(name = "process_id")
    private String processId;

    @Column(name = "process_instance_id")
    private String processInstanceId;

    @Column(name = "job_id")
    private String jobId;

    protected String type;

    @Column(name = "activity_id")
    private String activityId;
    
    @Column(name = "activity_name")
    private String activityName;

    
    private String error;
    
    @Column(name ="error_message")
    private String errorMessage;
    
    @Column(name = "error_date")
    private Date errorDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "processInstanceErrorHistorySeq")
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        String trimmedErrorMessage = errorMessage;
        if (trimmedErrorMessage != null && trimmedErrorMessage.length() > ERROR_LOG_LENGTH) {
            trimmedErrorMessage = trimmedErrorMessage.substring(0, ERROR_LOG_LENGTH);
            logger.warn("Error message content was trimmed as it was too long (more than {} characters)", ERROR_LOG_LENGTH);
        }
        this.errorMessage = trimmedErrorMessage;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Date getErrorDate() {
        return errorDate;
    }

    public void setErrorDate(Date errorDate) {
        this.errorDate = errorDate;
    }    
    
}
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

package org.kie.kogito.audit.model.job;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "JobExecutionHistory")
@SequenceGenerator(name = "jobExecutionHistoryIdSeq", sequenceName = "JOB_EXECUTION_HISTORY_ID_SEQ")
public class JobExecutionLog {

    public enum LogType {
        QUEUED,
        RETRY,
        ERROR,
        RUNNING,
        DONE,
        CANCELLED;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "jobExecutionHistoryIdSeq")
    private Long id;

    @Column(name="deployment_id")
    private String deploymentId;

    @Column(name="process_id")
    private String processId;

    @Column(name="process_instance_id")
    private String processInstanceId;

    @Column(name = "business_key")
    private String businessKey;

    @Column(name = "event_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventDate;
    
    @Column(name="event_type")
    @Enumerated(EnumType.STRING)
    private LogType eventType;

    @Column(name="event_user")
    private String eventUser;
    
    private String message;

    @Column(name="execution")
    private Integer numberOfExecution = 0;
    
    @Column(name="retry")
    private Integer numberOfRetry = 0;

    @Column(name = "request_data")
    private String requestData;

    @Column(name = "response_data")
    private String responseData;

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

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public LogType getEventType() {
        return eventType;
    }

    public void setEventType(LogType eventType) {
        this.eventType = eventType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getNumberOfRetry() {
        return numberOfRetry;
    }

    public void setNumberOfRetry(Integer numberOfRetry) {
        this.numberOfRetry = numberOfRetry;
    }

    public Integer getNumberOfExecution() {
        return numberOfExecution;
    }

    public void setNumberOfExecution(Integer numberOfExecution) {
        this.numberOfExecution = numberOfExecution;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getEventUser() {
        return eventUser;
    }
    
    public void setEventUser(String eventUser) {
        this.eventUser = eventUser;
    }
}
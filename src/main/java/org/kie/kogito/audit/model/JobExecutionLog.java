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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


@Entity
@Table(name = "JobExecutionHistory")
@SequenceGenerator(name = "jobExecutionHistoryIdSeq", sequenceName = "JOB_EXECUTION_HISTORY_ID_SEQ")
public class JobExecutionLog extends AbstractEntityLog {

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
    
    @Column(name="event_type")
    @Enumerated(EnumType.STRING)
    private LogType eventType;

    private String message;

    @Column(name="execution")
    private Integer numberOfExecution = 0;
    
    @Column(name="retry")
    private Integer numberOfRetry = 0;

    @Type(type = "json")
    @Column(name = "request_data", columnDefinition = "json")
    private String requestData;

    @Type(type = "json")
    @Column(name = "response_data", columnDefinition = "json")
    private String responseData;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LogType getEventType() {
        return eventType;
    }

    public void setEventType(LogType eventType) {
        this.eventType = eventType;
    }
}
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "ProcessInstanceVariableHistory")
@SequenceGenerator(name="processInstanceVariableHistoryIdSeq", sequenceName="PROCESS_INSTANCE_VARIABLE_HISTORY_ID_SEQ", allocationSize=1)
public class ProcessInstanceVariableLog {
    
    @Transient
	private static final Logger logger = LoggerFactory.getLogger(ProcessInstanceVariableLog.class);

	@Transient
	private static final int VARIABLE_LOG_LENGTH = Integer.parseInt(System.getProperty("org.jbpm.var.log.length", "255"));

	public enum LogType {
	    INITIALIZED,
	    CHANGED
	}
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="processInstanceVariableHistoryIdSeq")
	private long id;

    @Column(name="deployment_id")
    private String deploymentId;

    @Column(name="process_id")
    private String processId;
    
    @Column(name="process_instance_id")
    private String processInstanceId;
   
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "event_date")
    private Date eventDate;
    
    @Column(name = "event_type")
    private LogType eventType;

    @Column(name = "event_user")
    private String eventUser;
    
    @Column(name="variable_id")
    private String variableId;

    @Column(name="variable_instance_id")
    private String variableInstanceId;
    
    @Column(name="new_value")
    private String newValue;
    
    @Column(name="old_value")
    private String oldValue;
    


    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getVariableId() {
        return variableId;
    }

    public void setVariableId(String variableId) {
        this.variableId = variableId;
    }

    public String getVariableInstanceId() {
        return variableInstanceId;
    }

    public void setVariableInstanceId(String variableInstanceId) {
        this.variableInstanceId = variableInstanceId;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getEventUser() {
        return eventUser;
    }
    
    public void setEventUser(String eventUser) {
        this.eventUser = eventUser;
    }
    
}
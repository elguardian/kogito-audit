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

package org.kie.kogito.audit.model.engine;

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

@Entity
@Table(name = "ProcessInstanceNodeHistory")
@SequenceGenerator(name="processInstanceNodeHistoryIdSeq", sequenceName="PROCESS_INSTANCE_NODE_HISTORY_ID_SEQ", allocationSize=1)
public class ProcessInstanceNodeLog {

    public enum LogType {
        ENTER,
        EXIT,
        ABORTED,
        ASYNC_ENTER,
        OBSOLETE,
        SKIPPED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="processInstanceNodeHistoryIdSeq")
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

    @Column(name= "node_container_id")
    private String nodeContainerId;

    @Column(name = "node_id")
    private String nodeId;

    @Column(name="node_name")
    private String nodeName;
    
    @Column(name="node_type")
    private String nodeType;

    @Column(name="node_instance_id")
    private String nodeInstanceId;

    @Column(name="connection")
    private String connection;
    
    @Column(name = "work_item_id")
    private String workItemId;    
         
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sla_due_date")
    private Date slaDueDate;
    
    @Column(name="event_data")
    private String eventData;

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

    public String getNodeContainerId() {
        return nodeContainerId;
    }

    public void setNodeContainerId(String nodeContainerId) {
        this.nodeContainerId = nodeContainerId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeInstanceId() {
        return nodeInstanceId;
    }

    public void setNodeInstanceId(String nodeInstanceId) {
        this.nodeInstanceId = nodeInstanceId;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
    }

    public Date getSlaDueDate() {
        return slaDueDate;
    }

    public void setSlaDueDate(Date slaDueDate) {
        this.slaDueDate = slaDueDate;
    }

    public String getEventData() {
        return eventData;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }

    

}
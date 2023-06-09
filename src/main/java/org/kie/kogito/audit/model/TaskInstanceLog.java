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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "TaskInstanceHistory")
@SequenceGenerator(name = "taskInstanceHistoryIdSeq", sequenceName = "TASK_INSTANCE_HISTORY_ID_SEQ")
public class TaskInstanceLog extends AbstractEntityLog {

    public enum LogType {
        CREATED,
        READY,
        CLAIMED,
        STARTED,
        COMPLETED,
        ABORTED,
        NOMINATED,
        FORWARDED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "taskInstanceHistoryIdSeq")
    private Long id;

    private String taskId;

    private String name;

    private String description;
    
    private String actualUser;
    
    @Column(name="node_id")
    private String nodeId;

    @Column(name="node_instance_id")
    private String nodeInstanceId;
    
    @Column(name="event_type")
    @Enumerated(EnumType.STRING)
    private LogType eventType;
   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActualUser() {
        return actualUser;
    }

    public void setActualUser(String actualUser) {
        this.actualUser = actualUser;
    }


    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }


    public String getNodeInstanceId() {
        return nodeInstanceId;
    }

    public void setNodeInstanceId(String nodeInstanceId) {
        this.nodeInstanceId = nodeInstanceId;
    }

    public LogType getEventType() {
        return eventType;
    }

    public void setEventType(LogType eventType) {
        this.eventType = eventType;
    }
   
}
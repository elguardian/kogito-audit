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

import com.fasterxml.jackson.databind.JsonNode;

@Entity
@Table(name = "TaskInstanceVariableHistory")
@SequenceGenerator(name = "taskInstanceVariableHistoryIdSeq", sequenceName = "TASK_INSTANCE_VARIABLE_HISTORY_ID_SEQ")
public class TaskInstanceVariableLog extends AbstractEntityLog {

    public enum VariableType {
        INPUT,
        OUTPUT;
    }
    
    public enum LogType {
        INITIALIZED,
        CHANGED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "taskInstanceVariableHistoryIdSeq")
    private Long id;

    @Column(name = "task_id")
    private String taskId;

    private String name;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String newValue;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String oldValue;
    
    @Enumerated(EnumType.ORDINAL)
    private VariableType type;
    
    @Column(name="event_type")
    private LogType eventType;
    
    public Long getId() {
        return id;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VariableType getType() {
        return type;
    }

    public void setType(VariableType type) {
        this.type = type;
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

    public LogType getEventType() {
        return eventType;
    }

    public void setEventType(LogType eventType) {
        this.eventType = eventType;
    }

}
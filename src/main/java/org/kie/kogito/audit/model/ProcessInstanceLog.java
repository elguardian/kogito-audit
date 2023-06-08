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
@Table(name = "ProcessInstanceHistory")
@SequenceGenerator(name = "processInstanceHistoryIdSeq", sequenceName = "PROCESS_INSTANCE_HISTORY_ID_SEQ", allocationSize = 1)
public class ProcessInstanceLog extends AbstractEntityLog {

    public enum LogType {
        STARTED,
        COMPLETED,
        ABORTED,
        SLA_VIOLATION
    }



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "processInstanceHistoryIdSeq")
    private long id;


    

    @Column(name = "event_type", nullable = false)
    private LogType eventType;

    @Column(nullable = true)
    private String outcome;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sla_due_date")
    private Date slaDueDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public LogType getEventType() {
        return eventType;
    }

    public void setEventType(LogType eventType) {
        this.eventType = eventType;
    }



    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }


    public Date getSlaDueDate() {
        return slaDueDate;
    }

    public void setSlaDueDate(Date slaDueDate) {
        this.slaDueDate = slaDueDate;
    }

}
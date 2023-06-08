package org.kie.kogito.audit.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.hypersistence.utils.hibernate.type.json.JsonType;

/**
 * json Type definition is only supported by this databases
 * Oracle, SQL Server, PostgreSQL, MySQL, H2.
 */

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@TypeDefs({ @TypeDef(name = "json", typeClass = JsonType.class) })
public abstract class AbstractEntityLog {

    @Transient
    private static final Logger logger = LoggerFactory.getLogger(ProcessInstanceLog.class);
    
    @Transient
    private final int CORRELATION_KEY_LOG_LENGTH = Integer.parseInt(System.getProperty("org.jbpm.correlationkey.length", "255"));

    @Column(name = "deployment_id")
    private String deploymentId;

    @Column(name = "process_type")
    private Integer processType;

    @Column(name = "process_id")
    private String processId;

    @Column(name = "process_version")
    private String processVersion;

    @Column(name = "process_name")
    private String processName;

    @Column(name = "parent_process_instance_id")
    private String parentProcessInstanceId;

    @Column(name = "process_instance_id")
    private String processInstanceId;

    @Column(name = "process_instance_description")
    private String processInstanceDescription;

    @Column(name = "business_key")
    private String businessKey;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "event_date")
    private Date eventDate;

    @Column(name = "event_user")
    private String eventUser;

    public Integer getProcessType() {
        return processType;
    }

    public void setProcessType(Integer processType) {
        this.processType = processType;
    }
    
    public String getProcessVersion() {
        return processVersion;
    }

    public void setProcessVersion(String processVersion) {
        this.processVersion = processVersion;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
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

    public String getProcessInstanceDescription() {
        return processInstanceDescription;
    }

    public void setProcessInstanceDescription(String processInstanceDescription) {
        this.processInstanceDescription = processInstanceDescription;
    }

    public String getParentProcessInstanceId() {
        return parentProcessInstanceId;
    }

    public void setParentProcessInstanceId(String parentProcessInstanceId) {
        this.parentProcessInstanceId = parentProcessInstanceId;
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
        String trimmedBusinesskey = null;
        if (businessKey != null && businessKey.length() > CORRELATION_KEY_LOG_LENGTH) {
            trimmedBusinesskey = businessKey.substring(0, CORRELATION_KEY_LOG_LENGTH);
            logger.warn("CorrelationKey content was trimmed as it was too long (more than {} characters)", CORRELATION_KEY_LOG_LENGTH);
        } else {
            trimmedBusinesskey = businessKey;
        }
        this.businessKey = trimmedBusinesskey;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventUser() {
        return eventUser;
    }

    public void setEventUser(String eventUser) {
        this.eventUser = eventUser;
    }
}

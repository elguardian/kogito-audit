    create table AuditTaskImpl (
        id int8 not null,
        activationTime timestamp,
        actualOwner varchar(255),
        createdBy varchar(255),
        createdOn timestamp,
        deploymentId varchar(255),
        description varchar(255),
        dueDate timestamp,
        name varchar(255),
        parentId int8 not null,
        priority int4 not null,
        processId varchar(255),
        processInstanceId int8 not null,
        processSessionId int8 not null,
        status varchar(255),
        taskId int8,
        workItemId int8,
        lastModificationDate timestamp,
        primary key (id)
    );

    create table NodeInstanceLog (
        id int8 not null,
        connection varchar(255),
        log_date timestamp,
        externalId varchar(255),
        nodeId varchar(255),
        nodeInstanceId varchar(255),
        nodeName varchar(255),
        nodeType varchar(255),
        processId varchar(255),
        processInstanceId int8 not null,
        sla_due_date timestamp,
        slaCompliance int4,
        type int4 not null,
        workItemId int8,
        nodeContainerId varchar(255),
        referenceId int8,
        observation varchar(255),
        primary key (id)
    );

    create table ProcessInstanceLog (
        id int8 not null,
        correlationKey varchar(255),
        duration int8,
        end_date timestamp,
        externalId varchar(255),
        user_identity varchar(255),
        outcome varchar(255),
        parentProcessInstanceId int8,
        processId varchar(255),
        processInstanceDescription varchar(255),
        processInstanceId int8 not null,
        processName varchar(255),
        processType int4,
        processVersion varchar(255),
        sla_due_date timestamp,
        slaCompliance int4,
        start_date timestamp,
        status int4,
        primary key (id)
    );


    create table Task (
        id int8 not null,
        archived int2,
        allowedToDelegate varchar(255),
        description varchar(255),
        formName varchar(255),
        name varchar(255),
        priority int4 not null,
        subTaskStrategy varchar(255),
        subject varchar(255),
        activationTime timestamp,
        createdOn timestamp,
        deploymentId varchar(255),
        documentAccessType int4,
        documentContentId int8 not null,
        documentType varchar(255),
        expirationTime timestamp,
        faultAccessType int4,
        faultContentId int8 not null,
        faultName varchar(255),
        faultType varchar(255),
        outputAccessType int4,
        outputContentId int8 not null,
        outputType varchar(255),
        parentId int8 not null,
        previousStatus int4,
        processId varchar(255),
        processInstanceId int8 not null,
        processSessionId int8 not null,
        skipable boolean not null,
        status varchar(255),
        workItemId int8 not null,
        taskType varchar(255),
        OPTLOCK int4,
        taskInitiator_id varchar(255),
        actualOwner_id varchar(255),
        createdBy_id varchar(255),
        primary key (id)
    );


    create table TaskEvent (
        id int8 not null,
        logTime timestamp,
        message varchar(255),
        processInstanceId int8,
        taskId int8,
        type varchar(255),
        userId varchar(255),
        OPTLOCK int4,
        workItemId int8,
        correlationKey varchar(255),
        processType int4,
        currentOwner varchar(255),
        primary key (id)
    );

    create table TaskVariableImpl (
        id int8 not null,
        modificationDate timestamp,
        name varchar(255),
        processId varchar(255),
        processInstanceId int8,
        taskId int8,
        type int4,
        value varchar(4000),
        primary key (id)
    );

    create table VariableInstanceLog (
        id int8 not null,
        log_date timestamp,
        externalId varchar(255),
        oldValue varchar(255),
        processId varchar(255),
        processInstanceId int8 not null,
        value varchar(255),
        variableId varchar(255),
        variableInstanceId varchar(255),
        primary key (id)
    );


    create table task_comment (
        id int8 not null,
        addedAt timestamp,
        text text,
        addedBy_id varchar(255),
        TaskData_Comments_Id int8,
        primary key (id)
    );

   create table Attachment (
        id int8 not null,
        accessType int4,
        attachedAt timestamp,
        attachmentContentId int8 not null,
        contentType varchar(255),
        name varchar(255),
        attachment_size int4,
        attachedBy_id varchar(255),
        TaskData_Attachments_Id int8,
        primary key (id)
    );


   create table Deadline (
        id int8 not null,
        deadline_date timestamp,
        escalated int2,
        Deadlines_StartDeadLine_Id int8,
        Deadlines_EndDeadLine_Id int8,
        primary key (id)
    );



    create table Escalation (
        id int8 not null,
        name varchar(255),
        Deadline_Escalation_Id int8,
        primary key (id)
    );

    create table Reassignment (
        id int8 not null,
        Escalation_Reassignments_Id int8,
        primary key (id)
    );


    create table RequestInfo (
        id int8 not null,
        commandName varchar(255),
        deploymentId varchar(255),
        executions int4 not null,
        businessKey varchar(255),
        message varchar(255),
        owner varchar(255),
        priority int4 not null,
        processInstanceId int8,
        requestData oid,
        responseData oid,
        retries int4 not null,
        status varchar(255),
        timestamp timestamp,
        primary key (id)
    );


    create table ExecutionErrorInfo (
        id int8 not null,
        ERROR_ACK int2,
        ERROR_ACK_AT timestamp,
        ERROR_ACK_BY varchar(255),
        ACTIVITY_ID int8,
        ACTIVITY_NAME varchar(255),
        DEPLOYMENT_ID varchar(255),
        ERROR_INFO text,
        ERROR_DATE timestamp,
        ERROR_ID varchar(255),
        ERROR_MSG varchar(255),
        INIT_ACTIVITY_ID int8,
        JOB_ID int8,
        PROCESS_ID varchar(255),
        PROCESS_INST_ID int8,
        ERROR_TYPE varchar(255),
        primary key (id)
    );


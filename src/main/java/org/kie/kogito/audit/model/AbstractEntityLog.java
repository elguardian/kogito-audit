package org.kie.kogito.audit.model;

import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import io.hypersistence.utils.hibernate.type.json.JsonType;

/**
 * json Type definition is only supported by this databases
 * Oracle, SQL Server, PostgreSQL, MySQL, H2.
 */

@MappedSuperclass
@TypeDefs({ @TypeDef(name = "json", typeClass = JsonType.class) })
public abstract class AbstractEntityLog {

}

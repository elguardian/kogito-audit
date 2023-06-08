package org.kie.kogito.data.index.service.messaging;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.kie.kogito.testcontainers.quarkus.PostgreSqlQuarkusTestResource;

import io.quarkus.test.junit.QuarkusTestProfile;

public class PostgreSQLTestProfile implements QuarkusTestProfile {
    @Override
    public List<TestResourceEntry> testResources() {
        return Arrays.asList(new TestResourceEntry(PostgreSqlQuarkusTestResource.class, Collections.emptyMap(), true));
    }

    @Override
    public String getConfigProfile() {
        return "database";
    }
}

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

package org.kie.kogito.data.index.service.messaging;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.kogito.audit.event.ProcessInstanceEvent;
import org.kie.kogito.audit.transport.streams.Transport;
import org.kie.kogito.test.quarkus.kafka.KafkaTestClient;
import org.kie.kogito.testcontainers.quarkus.KafkaQuarkusTestResource;
import org.testcontainers.shaded.org.apache.commons.io.output.ByteArrayOutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.DefaultBaseTypeLimitingValidator;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
@QuarkusTest
@TestProfile(DataIndexKafkaTestProfile.class)
public class DataIndexMessagingKafkaConsumerIT  {

    @ConfigProperty(name = KafkaQuarkusTestResource.KOGITO_KAFKA_PROPERTY, defaultValue = "localhost:9092")
    public String kafkaBootstrapServers;

    private KafkaTestClient kafkaClient;

    @BeforeEach
    void setup() {
        kafkaClient = new KafkaTestClient(kafkaBootstrapServers);
    }

    @AfterEach
    void close() {
        if (kafkaClient != null) {
            kafkaClient.shutdown();
        }
    }

    @Test
    void testProcessInstanceEvent() throws Exception {
        ProcessInstanceEvent event1 = new ProcessInstanceEvent();
        event1.setEventType(2);
        ProcessInstanceEvent event2 = new ProcessInstanceEvent();
        event2.setEventType(3);
        
        ProcessInstanceEvent[] events = new ProcessInstanceEvent[] {event1, event2};
        
        kafkaClient.produce(toJson(event1), Transport.KOGITO_PROCESS_INSTANCES_EVENTS);
        
        given()
        .when().get("/")
        .then()
           .statusCode(200)
           .body(is("Hello, World!"));
    }
    
    
    private String toJson(Object pojo) throws Exception {
        ObjectMapper mapper = new ObjectMapper()
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        String json = "";
        //Object to JSON in file
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            mapper.writeValue(out, pojo);
            json = new String(out.toByteArray());
        }
        return json;
    }
}

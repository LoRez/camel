/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.rest;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.RestConfiguration;
import org.apache.camel.spi.RestProducerFactory;
import org.apache.camel.support.DefaultComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestEndpointTest {

    public static class MockRest extends DefaultComponent implements RestProducerFactory {
        @Override
        public Producer createProducer(
                final CamelContext camelContext, final String host, final String verb, final String basePath,
                final String uriTemplate,
                final String queryParameters, final String consumes, final String produces, RestConfiguration configuration,
                final Map<String, Object> parameters) {
            return null;
        }

        @Override
        protected Endpoint createEndpoint(final String uri, final String remaining, final Map<String, Object> parameters) {
            return null;
        }
    }

    final RestComponent restComponent;

    final CamelContext context;

    public RestEndpointTest() {
        context = new DefaultCamelContext();
        context.addComponent("mock-rest", new MockRest());

        restComponent = new RestComponent();
        restComponent.setCamelContext(context);
    }

    @Test
    public void shouldConfigureBindingMode() throws Exception {
        final RestEndpoint restEndpoint = new RestEndpoint("rest:GET:/path", restComponent);
        restEndpoint.setConsumerComponentName("mock-rest");
        restEndpoint.setParameters(new HashMap<>());
        restEndpoint.setHost("http://localhost");

        restEndpoint.setBindingMode("json");

        final RestProducer producer = (RestProducer) restEndpoint.createProducer();

        assertEquals("json", producer.getBindingMode().name());
    }

    @Test
    public void shouldCreateQueryParametersFromUnusedEndpointParameters() throws Exception {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("host", "http://localhost");
        parameters.put("bindingMode", "json");
        parameters.put("foo", "bar");

        final RestEndpoint endpoint = (RestEndpoint) restComponent
                .createEndpoint("rest:GET:/path?host=http://localhost&bindingMode=json&foo=bar", "GET:/path", parameters);

        assertEquals("http://localhost", endpoint.getHost());
        assertEquals("json", endpoint.getBindingMode().name());
        assertEquals("GET", endpoint.getMethod());
        assertEquals("/path", endpoint.getPath());
        assertEquals("foo=bar", endpoint.getQueryParameters());
    }

    @Test
    public void shouldSupportQueryParametersSetViaEndpointUri() throws Exception {
        RestEndpoint endpoint = (RestEndpoint) context.getComponent("rest")
                .createEndpoint(
                        "rest:GET:/path?host=http://localhost&bindingMode=json&foo=bar&queryParameters=RAW(a%3Db%26c%3Dd)");

        assertEquals("http://localhost", endpoint.getHost());
        assertEquals("json", endpoint.getBindingMode().name());
        assertEquals("GET", endpoint.getMethod());
        assertEquals("/path", endpoint.getPath());
        assertEquals("foo=bar&a=b&c=d", endpoint.getQueryParameters());
    }
}

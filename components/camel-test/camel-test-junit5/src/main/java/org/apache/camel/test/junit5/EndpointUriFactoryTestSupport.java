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
package org.apache.camel.test.junit5;

import java.util.Map;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.EndpointUriFactory;
import org.junit.jupiter.api.Test;

public abstract class EndpointUriFactoryTestSupport extends CamelTestSupport {

    @Test
    public void buildUri() {
        // let's see if the route starts successfully
    }

    protected abstract String scheme();

    protected abstract Map<String, Object> pathParameters();

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() throws Exception {
                EndpointUriFactory factory = getContext().getCamelContextExtension().getEndpointUriFactory(scheme());
                String uri = factory.buildUri(scheme(), pathParameters(), false);
                from(uri).to("mock:out");
            }
        };
    }
}

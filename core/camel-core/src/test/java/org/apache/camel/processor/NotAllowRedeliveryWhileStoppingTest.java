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
package org.apache.camel.processor;

import org.apache.camel.ContextTestSupport;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.util.StopWatch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NotAllowRedeliveryWhileStoppingTest extends ContextTestSupport {

    @Test
    public void testRedelivery() throws Exception {
        StopWatch watch = new StopWatch();

        MockEndpoint before = getMockEndpoint("mock:foo");
        before.expectedMessageCount(1);

        template.sendBody("seda:start", "Hello World");

        assertMockEndpointsSatisfied();

        Thread.sleep(500);

        context.stop();

        // we should reject the task and stop quickly
        assertTrue(watch.taken() < 5000, "Should stop quickly: " + watch.taken());
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                errorHandler(
                        defaultErrorHandler().maximumRedeliveries(5).redeliveryDelay(5000).allowRedeliveryWhileStopping(false));

                from("seda:start").to("mock:foo").throwException(new IllegalArgumentException("Forced"));
            }
        };
    }
}

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
package org.apache.camel.test.junit5.patterns;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.apache.camel.test.junit5.DebugBreakpoint;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebugTest extends CamelTestSupport {

    private static final Logger LOG = LoggerFactory.getLogger(DebugTest.class);

    // START SNIPPET: e1
    @Override
    public boolean isUseDebugger() {
        // must enable debugger
        return true;
    }

    protected DebugBreakpoint createBreakpoint() {
        return new TestDebugBreakpoint();
    }
    // END SNIPPET: e1

    @Test
    public void testDebugger() throws Exception {
        // set mock expectations
        getMockEndpoint("mock:a").expectedMessageCount(1);
        getMockEndpoint("mock:b").expectedMessageCount(1);

        // send a message
        template.sendBody("direct:start", "World");

        // assert mocks
        MockEndpoint.assertIsSatisfied(context);
    }

    @Test
    public void testTwo() throws Exception {
        // set mock expectations
        getMockEndpoint("mock:a").expectedMessageCount(2);
        getMockEndpoint("mock:b").expectedMessageCount(2);

        // send a message
        template.sendBody("direct:start", "World");
        template.sendBody("direct:start", "Camel");

        // assert mocks
        MockEndpoint.assertIsSatisfied(context);
    }

    // START SNIPPET: e2
    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                // this is the route we want to debug
                from("direct:start").to("mock:a").transform(body().prepend("Hello ")).to("mock:b");
            }
        };
    }
    // END SNIPPET: e2
}

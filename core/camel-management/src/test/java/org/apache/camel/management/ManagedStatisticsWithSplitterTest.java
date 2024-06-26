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
package org.apache.camel.management;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.camel.builder.RouteBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.apache.camel.management.DefaultManagementObjectNameStrategy.TYPE_PROCESSOR;
import static org.apache.camel.management.DefaultManagementObjectNameStrategy.TYPE_ROUTE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisabledOnOs(OS.AIX)
public class ManagedStatisticsWithSplitterTest extends ManagementTestSupport {

    @Test
    public void testManageStatistics() throws Exception {
        // get the status for the route
        MBeanServer mbeanServer = getMBeanServer();

        ObjectName on = getCamelObjectName(TYPE_ROUTE, "route-a");

        // use route to get the total time
        Long completed = (Long) mbeanServer.getAttribute(on, "ExchangesCompleted");
        assertEquals(0, completed.longValue());

        // send in 2 messages
        template.sendBody("direct:start", "A,B,C");
        template.sendBody("direct:start", "D,E");

        // should be 2 on the route
        completed = (Long) mbeanServer.getAttribute(on, "ExchangesCompleted");
        assertEquals(2, completed.longValue());

        // should be 2 on the foo
        ObjectName foo = getCamelObjectName(TYPE_PROCESSOR, "foo");
        completed = (Long) mbeanServer.getAttribute(foo, "ExchangesCompleted");
        assertEquals(2, completed.longValue());

        // should be 5 on the split sub route
        ObjectName bar = getCamelObjectName(TYPE_PROCESSOR, "bar");
        completed = (Long) mbeanServer.getAttribute(bar, "ExchangesCompleted");
        assertEquals(5, completed.longValue());

        // should be 2 on the mock
        ObjectName mock = getCamelObjectName(TYPE_PROCESSOR, "mock");
        completed = (Long) mbeanServer.getAttribute(mock, "ExchangesCompleted");
        assertEquals(2, completed.longValue());

        // should be 5 on route-b
        on = getCamelObjectName(TYPE_ROUTE, "route-b");
        completed = (Long) mbeanServer.getAttribute(on, "ExchangesCompleted");
        assertEquals(5, completed.longValue());
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:start").routeId("route-a")
                        .to("log:foo").id("foo")
                        .split(body().tokenize(","))
                        .to("direct:split")
                        .end()
                        .to("mock:result").id("mock");

                from("direct:split").routeId("route-b")
                        .to("log:bar").id("bar");
            }
        };
    }

}

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
package org.apache.camel.parser.java;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled // on purpose to ignore
public class MyNettyTest extends CamelTestSupport {

    public int getNextPort() {
        return 8080;
    }

    @Test
    void testFoo() {
        assertTrue(context.isStarted());
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            int port2 = getNextPort();

            @Override
            public void configure() {
                from("netty-http:http://0.0.0.0:{{port}}/foo")
                        .to("mock:input1")
                        .to("netty-http:http://0.0.0.0:" + port2 + "/bar");
                from("netty-http:http://0.0.0.0:" + port2 + "/bar")
                        .to("mock:input2")
                        .transform().constant("Bye World");
            }
        };
    }
}

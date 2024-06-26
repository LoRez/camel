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
package org.apache.camel.test.issues;

import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AdviceWithOnExceptionMultipleIssueTest extends CamelSpringTestSupport {

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("org/apache/camel/test/issues/AdviceWithOnExceptionMultipleIssueTest.xml");
    }

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Test
    public void testSimpleMultipleAdvice() throws Exception {
        AdviceWith.adviceWith(context, "RouteA", a -> a.interceptSendToEndpoint("mock:resultA").process());

        AdviceWith.adviceWith(context, "RouteB", a -> {
        });

        context.start();

        getMockEndpoint("mock:resultA").expectedMessageCount(1);
        template.sendBody("direct:startA", "a trigger");
        MockEndpoint.assertIsSatisfied(context);
    }

    @Test
    public void testMultipleAdviceWithExceptionThrown() throws Exception {
        AdviceWith.adviceWith(context, "RouteA", a -> a.interceptSendToEndpoint("mock:resultA").process(e -> {
            throw new Exception("my exception");
        }));

        context.start();

        getMockEndpoint("mock:resultA").expectedMessageCount(0);
        template.sendBody("direct:startA", "a trigger");
        MockEndpoint.assertIsSatisfied(context);
    }

    @Test
    public void testMultipleAdvice() throws Exception {
        AdviceWith.adviceWith(context.getRouteDefinition("RouteA"), context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint("mock:resultA").process(exchange -> {
                    throw new Exception("my exception");
                });
            }
        });

        AdviceWith.adviceWith(context.getRouteDefinition("RouteB"), context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
            }
        });

        context.start();

        getMockEndpoint("mock:resultA").expectedMessageCount(0);
        template.sendBody("direct:startA", "a trigger");
        MockEndpoint.assertIsSatisfied(context);
    }

}

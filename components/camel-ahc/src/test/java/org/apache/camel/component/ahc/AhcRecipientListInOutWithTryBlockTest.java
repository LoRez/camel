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
package org.apache.camel.component.ahc;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AhcRecipientListInOutWithTryBlockTest extends BaseAhcTest {

    @Test
    public void testRecipientListCalledBeforeComponentStarted() throws Exception {
        String response = template.requestBody("direct:start", "Hello", String.class);

        assertEquals("Bye", response);
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("timer://foo?delay=-1&?repeatCount=1")
                        .to("direct:start");

                from("direct:start")
                    .doTry()
                        .recipientList(constant(getAhcEndpointUri()))
                    .endDoTry()
                    .doCatch(Exception.class)
                        .log(LoggingLevel.ERROR, "Exception occured ${exception}")
                        .transform(constant("Exception occured"))
                    .end();

                from(getTestServerEndpointUri())
                        .transform(constant("Bye"));
            }
        };
    }

}
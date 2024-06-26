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
package org.apache.camel.impl.engine;

import org.apache.camel.AsyncProducer;
import org.apache.camel.ContextTestSupport;
import org.apache.camel.Endpoint;
import org.apache.camel.spi.ProducerCache;
import org.apache.camel.support.cache.EmptyProducerCache;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmptyProducerCacheTest extends ContextTestSupport {

    @Test
    public void testEmptyCache() {
        ProducerCache cache = new EmptyProducerCache(this, context);
        cache.start();

        assertEquals(0, cache.size(), "Size should be 0");

        // we never cache any producers
        Endpoint e = context.getEndpoint("direct:queue:1");
        AsyncProducer p = cache.acquireProducer(e);

        assertEquals(0, cache.size(), "Size should be 0");

        cache.releaseProducer(e, p);

        assertEquals(0, cache.size(), "Size should be 0");

        cache.stop();
    }

    @Test
    public void testCacheProducerAcquireAndRelease() {
        ProducerCache cache = new EmptyProducerCache(this, context);
        cache.start();

        assertEquals(0, cache.size(), "Size should be 0");

        // we never cache any producers
        for (int i = 0; i < 1003; i++) {
            Endpoint e = context.getEndpoint("direct:queue:" + i);
            AsyncProducer p = cache.acquireProducer(e);
            cache.releaseProducer(e, p);
        }

        assertEquals(0, cache.size(), "Size should be 1000");
        cache.stop();
    }

}

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
package org.apache.camel.http.common;

import java.util.Set;

import org.apache.camel.support.http.HttpUtil;

/**
 * @deprecated use {@link org.apache.camel.http.base.HttpHeaderFilterStrategy}
 */
@Deprecated
public class HttpHeaderFilterStrategy extends org.apache.camel.http.base.HttpHeaderFilterStrategy {

    public HttpHeaderFilterStrategy() {
        initialize();
    }

    protected void initialize() {
        final Set<String> outFilter = getOutFilter();
        HttpUtil.addCommonFilters(outFilter);

        setLowerCase(true);

        // filter headers begin with "Camel" or "org.apache.camel"
        // must ignore case for Http based transports
        setOutFilterStartsWith(CAMEL_FILTER_STARTS_WITH);
        setInFilterStartsWith(CAMEL_FILTER_STARTS_WITH);
    }

}

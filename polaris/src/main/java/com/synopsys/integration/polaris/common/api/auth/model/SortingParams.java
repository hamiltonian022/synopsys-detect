/**
 * polaris
 *
 * Copyright (c) 2020 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.polaris.common.api.auth.model;

import com.synopsys.integration.polaris.common.api.PolarisComponent;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created

public class SortingParams extends PolarisComponent {
    /**
     * Gets or Sets inner
     */
    @JsonAdapter(InnerEnum.Adapter.class)
    public enum InnerEnum {
        ASC("asc"),

        DESC("desc");

        private final String value;

        InnerEnum(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public static InnerEnum fromValue(final String text) {
            for (final InnerEnum b : InnerEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        public static class Adapter extends TypeAdapter<InnerEnum> {
            @Override
            public void write(final JsonWriter jsonWriter, final InnerEnum enumeration) throws IOException {
                jsonWriter.value(enumeration.getValue());
            }

            @Override
            public InnerEnum read(final JsonReader jsonReader) throws IOException {
                final String value = jsonReader.nextString();
                return InnerEnum.fromValue(String.valueOf(value));
            }
        }
    }

    @SerializedName("params")
    private Map<String, InnerEnum> params = null;

    public SortingParams putParamsItem(final String key, final InnerEnum paramsItem) {
        if (this.params == null) {
            this.params = new HashMap<>();
        }
        this.params.put(key, paramsItem);
        return this;
    }

    /**
     * Get params
     * @return params
     */
    public Map<String, InnerEnum> getParams() {
        return params;
    }

    public void setParams(final Map<String, InnerEnum> params) {
        this.params = params;
    }

}


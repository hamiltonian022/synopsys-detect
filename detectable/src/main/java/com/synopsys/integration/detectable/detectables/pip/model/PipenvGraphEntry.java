/**
 * detectable
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
package com.synopsys.integration.detectable.detectables.pip.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PipenvGraphEntry {
    @SerializedName("package_name")
    private final String name;
    @SerializedName("installed_version")
    private final String version;
    @SerializedName("dependencies")
    private final List<PipenvGraphDependency> children;

    public PipenvGraphEntry(final String name, final String version, final List<PipenvGraphDependency> children) {
        this.name = name;
        this.version = version;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public List<PipenvGraphDependency> getChildren() {
        return children;
    }
}

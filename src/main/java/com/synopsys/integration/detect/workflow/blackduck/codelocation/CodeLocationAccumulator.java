/**
 * synopsys-detect
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
package com.synopsys.integration.detect.workflow.blackduck.codelocation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.synopsys.integration.blackduck.codelocation.CodeLocationBatchOutput;
import com.synopsys.integration.blackduck.codelocation.CodeLocationCreationData;
import com.synopsys.integration.blackduck.codelocation.CodeLocationOutput;

public class CodeLocationAccumulator<O extends CodeLocationOutput, T extends CodeLocationBatchOutput<O>> {

    private final List<CodeLocationCreationData<T>> waitableCodeLocations = new ArrayList<>();
    private final Set<String> nonWaitableCodeLocations = new HashSet<>();

    public void addWaitableCodeLocation(CodeLocationCreationData<T> creationData) {
        waitableCodeLocations.add(creationData);
    }

    public void addNonWaitableCodeLocation(Set<String> names) {
        nonWaitableCodeLocations.addAll(names);
    }

    public List<CodeLocationCreationData<T>> getWaitableCodeLocations() {
        return waitableCodeLocations;
    }

    public Set<String> getNonWaitableCodeLocations() {
        return nonWaitableCodeLocations;
    }
}

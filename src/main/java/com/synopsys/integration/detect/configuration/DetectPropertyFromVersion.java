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
package com.synopsys.integration.detect.configuration;

public enum DetectPropertyFromVersion {
    VERSION_3_0_0("3.0.0"),
    VERSION_3_1_0("3.1.0"),
    VERSION_3_2_0("3.2.0"),
    VERSION_4_0_0("4.0.0"),
    VERSION_4_1_0("4.1.0"),
    VERSION_4_2_0("4.2.0"),
    VERSION_4_3_0("4.3.0"),
    VERSION_4_4_0("4.4.0"),
    VERSION_5_0_0("5.0.0"),
    VERSION_5_2_0("5.2.0"),
    VERSION_5_3_0("5.3.0"),
    VERSION_5_4_0("5.4.0"),
    VERSION_5_5_0("5.5.0"),
    VERSION_5_6_0("5.6.0"),
    VERSION_6_0_0("6.0.0"),
    VERSION_6_1_0("6.1.0"),
    VERSION_6_2_0("6.2.0"),
    VERSION_6_4_0("6.4.0"),
    VERSION_6_5_0("6.5.0"),
    VERSION_6_8_0("6.8.0");

    private final String version;

    DetectPropertyFromVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}

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
package com.synopsys.integration.detectable.detectables.yarn.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class YarnLockParser {
    private static final String COMMENT_PREFIX = "#";
    private static final String VERSION_PREFIX = "version \"";
    private static final String VERSION_SUFFIX = "\"";
    private static final String OPTIONAL_DEPENDENCIES_TOKEN = "optionalDependencies:";

    public YarnLock parseYarnLock(final List<String> yarnLockFileAsList) {
        final List<YarnLockEntry> entries = new ArrayList<>();
        String resolvedVersion = "";
        List<YarnLockDependency> dependencies = new ArrayList<>();
        List<YarnLockEntryId> ids = new ArrayList<>();
        boolean inOptionalDependencies = false;

        List<String> cleanedYarnLockFileAsList = yarnLockFileAsList
                                                     .stream()
                                                     .filter(StringUtils::isNotBlank)
                                                     .filter(line -> !line.trim().startsWith(COMMENT_PREFIX))
                                                     .collect(Collectors.toList());

        int index = cleanedYarnLockFileAsList
                        .stream()
                        .filter(this::isLevel0)
                        .findFirst()
                        .map(line -> cleanedYarnLockFileAsList.indexOf(line))
                        .orElse(-1);

        if (index == -1 || index == cleanedYarnLockFileAsList.size() - 1) {
            return new YarnLock(entries);
        }

        List<String> yarnLinesThatMatter = cleanedYarnLockFileAsList.subList(index + 1, cleanedYarnLockFileAsList.size());

        for (final String line : yarnLinesThatMatter) {

            final String trimmedLine = line.trim();
            final int level = countIndent(line);
            if (level == 0) {
                entries.add(new YarnLockEntry(ids, resolvedVersion, dependencies));
                resolvedVersion = "";
                dependencies = new ArrayList<>();
                inOptionalDependencies = false;
                ids = parseMultipleEntryLine(line);
            } else if (level == 1 && trimmedLine.startsWith(VERSION_PREFIX)) {
                resolvedVersion = getVersionFromLine(trimmedLine);
            } else if (level == 1 && trimmedLine.startsWith(OPTIONAL_DEPENDENCIES_TOKEN)) {
                inOptionalDependencies = true;
            } else if (level == 2) {
                dependencies.add(getDependencyFromLine(trimmedLine, inOptionalDependencies));
            }
        }
        if (StringUtils.isNotBlank(resolvedVersion)) {
            entries.add(new YarnLockEntry(ids, resolvedVersion, dependencies));
        }

        return new YarnLock(entries);
    }

    public int countIndent(String line) {
        int count = 0;
        while (line.startsWith("  ")) {
            count++;
            line = line.substring(2);
        }
        return count;
    }

    private YarnLockDependency getDependencyFromLine(final String line, final boolean optional) {
        final String[] pieces = StringUtils.split(line, " ", 2);
        return new YarnLockDependency(removeWrappingQuotes(pieces[0]), removeWrappingQuotes(pieces[1]), optional);
    }

    private String removeWrappingQuotes(final String s) {
        return StringUtils.removeStart(StringUtils.removeEnd(s.trim(), "\""), "\"");
    }

    //Takes a line of the form "entry \"entry\" entry:"
    public List<YarnLockEntryId> parseMultipleEntryLine(final String line) {
        final List<YarnLockEntryId> ids = new ArrayList<>();
        final String[] entries = line.split(",");
        for (final String entryRaw : entries) {
            final String entryNoColon = StringUtils.removeEnd(entryRaw.trim(), ":");
            final String entryNoColonOrQuotes = removeWrappingQuotes(entryNoColon);
            YarnLockEntryId entry = parseSingleEntry(entryNoColonOrQuotes);
            ids.add(entry);
        }
        return ids;
    }

    //Takes an entry of format "name@version" or "@name@version" where name has an @ symbol.
    public YarnLockEntryId parseSingleEntry(String entry) {
        if (StringUtils.countMatches(entry, "@") == 1 && entry.startsWith("@")) {
            return new YarnLockEntryId(entry, "");
        } else {
            final String name = StringUtils.substringBeforeLast(entry, "@");
            final String version = StringUtils.substringAfterLast(entry, "@");
            return new YarnLockEntryId(name, version);
        }
    }

    private String getVersionFromLine(final String line) {
        final String rawVersion = line.substring(VERSION_PREFIX.length(), line.lastIndexOf(VERSION_SUFFIX));
        return removeWrappingQuotes(rawVersion);
    }

    private boolean isLevel0(String line) {
        int level = countIndent(line);
        return level == 0;
    }
}

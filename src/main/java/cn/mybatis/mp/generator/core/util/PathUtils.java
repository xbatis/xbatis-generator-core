/*
 *  Copyright (c) 2024-2024, Ai东 (abc-127@live.cn).
 *
 *  Licensed under the Apache License, Version 2.0 (the "License").
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and limitations under the License.
 *
 */

package cn.mybatis.mp.generator.core.util;

import db.sql.api.impl.tookit.Objects;

import java.io.File;
import java.util.regex.Matcher;

public final class PathUtils {
    @SafeVarargs
    public static String buildPackage(String... packages) {
        StringBuilder builder = new StringBuilder();
        for (String pkg : packages) {
            if (Objects.isNull(pkg)) {
                continue;
            }
            pkg = pkg.trim();
            if (pkg.length() < 1) {
                continue;
            }
            builder.append(pkg).append(".");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    @SafeVarargs
    public static String buildFilePath(String... paths) {
        StringBuilder builder = new StringBuilder();
        for (String path : paths) {
            if (Objects.isNull(path)) {
                continue;
            }
            path = path.trim();
            if (path.trim().length() > 0) {
                if (builder.length() > 0) {
                    builder.append(File.separator);
                }
                builder.append(path);
            }
        }
        if (File.separator.equals("/")) {
            return builder.toString().replaceAll("\\\\", Matcher.quoteReplacement(File.separator));
        } else {
            return builder.toString().replaceAll("\\/", Matcher.quoteReplacement(File.separator));
        }
    }
}

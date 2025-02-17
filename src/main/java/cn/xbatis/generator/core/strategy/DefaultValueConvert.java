/*
 *  Copyright (c) 2024-2025, Ai东 (abc-127@live.cn).
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

package cn.xbatis.generator.core.strategy;

import cn.xbatis.core.util.StringPool;

import java.util.Objects;

public class DefaultValueConvert {

    public String convert(String defaultValue) {
        if (Objects.isNull(defaultValue)) {
            return null;
        }
        defaultValue = defaultValue.trim();
        if (StringPool.EMPTY.equals(defaultValue) || "''".equals(defaultValue)) {
            return "{BLANK}";
        } else if (defaultValue.equalsIgnoreCase("CURRENT_TIMESTAMP") || defaultValue.equalsIgnoreCase("CURRENT_DATE") || defaultValue.equalsIgnoreCase("LOCALTIMESTAMP")) {
            return "{NOW}";
        } else if (defaultValue.equalsIgnoreCase("b'0'")) {
            return "0";
        } else if (defaultValue.equalsIgnoreCase("b'1'")) {
            return "1";
        }
        return defaultValue;
    }
}

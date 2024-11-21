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

package cn.mybatis.mp.generator.core.config;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TableConfig {

    /**
     * 表名前缀 - 设置后 可忽略前缀
     */
    private final List<String> tablePrefixes = new ArrayList<>();

    /**
     * 需要生成的表名
     */
    private final List<String> includeTables = new ArrayList<>();

    /**
     * 需要排除的表名
     */
    private final List<String> excludeTables = new ArrayList<>();

    /**
     * 表的前缀
     *
     * @param prefixes
     * @return
     */
    public TableConfig tablePrefixes(String... prefixes) {
        this.tablePrefixes.addAll(Arrays.stream(prefixes)
                .map(item -> {
                    if (item.toLowerCase().equals(item)) {
                        return item.toLowerCase();
                    }
                    return item;
                })
                .sorted(Comparator.comparing(i -> i.length()))
                .sorted(Comparator.reverseOrder()
                ).collect(Collectors.toList()));
        return this;
    }

    /**
     * 设置需要生成的表
     *
     * @param tables
     * @return
     */
    public TableConfig includeTable(String... tables) {
        this.includeTables.addAll(Arrays.asList(tables));
        return this;
    }

    /**
     * 设置需要不生成的表
     *
     * @param tables
     * @return
     */
    public TableConfig excludeTable(String... tables) {
        this.excludeTables.addAll(Arrays.asList(tables));
        return this;
    }
}

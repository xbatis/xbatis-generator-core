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

package cn.xbatis.generator.core.config;

import lombok.Getter;

@Getter
public class MapperXmlConfig {

    /**
     * 是否启用
     */
    private boolean enable = false;

    /**
     * 生成resultMap
     */
    private boolean resultMap = false;

    /**
     * 生成查询列
     */
    private boolean columnList = false;

    /**
     * mapper xml 目录名字
     */
    private String packageName = "mappers";

    /**
     * mapper xml 后缀
     */
    private String suffix = "";

    /**
     * 设置是否启用
     */
    public MapperXmlConfig enable(boolean enable) {
        this.enable = enable;
        return this;
    }

    /**
     * 生成resultMap
     */
    public MapperXmlConfig resultMap(boolean resultMap) {
        this.resultMap = resultMap;
        return this;
    }

    /**
     * 生成查询列
     */
    public MapperXmlConfig columnList(boolean columnList) {
        this.columnList = columnList;
        return this;
    }

    /**
     * mapper xml 目录名字
     */
    public MapperXmlConfig packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    /**
     * mapper xml 后缀
     */
    public MapperXmlConfig suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }
}

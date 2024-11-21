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

package cn.mybatis.mp.generator.core.strategy;

import cn.mybatis.mp.core.util.NamingUtil;

/**
 * 名字策略
 */
public enum NamingStrategy {

    /**
     * 不做任何改变，原样输出
     */
    NO_CHANGE,

    /**
     * 下划线转驼峰命名
     */
    UNDERLINE_TO_CAMEL;

    /**
     * 获取名字
     *
     * @param sourceName     原始名字
     * @param firstLowerCase 首字母小写
     * @return
     */
    public String getName(String sourceName, boolean firstLowerCase) {
        if (this == NO_CHANGE) {
            return sourceName;
        }
        sourceName = NamingUtil.underlineToCamel(sourceName);
        return firstLowerCase ? NamingUtil.firstToLower(sourceName) : sourceName;
    }
}

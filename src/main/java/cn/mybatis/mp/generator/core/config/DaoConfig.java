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

package cn.mybatis.mp.generator.core.config;

import lombok.Getter;

@Getter
public class DaoConfig {

    /**
     * dao接口父类
     */
    private String superClass;

    /**
     * 是否启用
     */
    private boolean enable = true;

    /**
     * 启用泛型
     */
    private boolean generic = true;

    /**
     * 实体类包名
     */
    private String packageName = "dao";

    /**
     * dao接口后缀
     */
    private String suffix = "Dao";


    /**
     * dao接口父类
     */
    public DaoConfig superClass(String superClass) {
        this.superClass = superClass;
        return this;
    }

    /**
     * dao接口父类
     */
    public DaoConfig superClass(Class superClass) {
        this.superClass = superClass == null ? null : superClass.getName();
        return this;
    }

    /**
     * 设置是否启用
     */
    public DaoConfig enable(boolean enable) {
        this.enable = enable;
        return this;
    }

    /**
     * 启用泛型
     */
    public DaoConfig generic(boolean generic) {
        this.generic = generic;
        return this;
    }


    /**
     * 实体类包名
     */
    public DaoConfig packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    /**
     * dao接口后缀
     */
    public DaoConfig suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }
}

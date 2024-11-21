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

@Getter
public class ServiceConfig {

    /**
     * service接口父类
     */
    private String superClass;

    /**
     * 是否启用
     */
    private boolean enable = true;

    /**
     * 启用泛型
     */
    private boolean generic = false;

    /**
     * service接口包名
     */
    private String packageName = "service";

    /**
     * service接口后缀
     */
    private String suffix = "Service";

    /**
     * service接口父类
     */
    public ServiceConfig superClass(String superClass) {
        this.superClass = superClass;
        return this;
    }

    /**
     * 接口父类
     */
    public ServiceConfig superClass(Class superClass) {
        this.superClass = superClass.getName();
        return this;
    }

    /**
     * 设置是否启用
     */
    public ServiceConfig enable(boolean enable) {
        this.enable = enable;
        return this;
    }

    /**
     * 启用泛型
     */
    public ServiceConfig generic(boolean generic) {
        this.generic = generic;
        return this;
    }


    /**
     * service接口包名
     */
    public ServiceConfig packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    /**
     * service接口后缀
     */
    public ServiceConfig suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }
}

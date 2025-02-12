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

import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import lombok.Getter;

@Getter
public class MapperConfig {

    /**
     * 是否启用
     */
    private boolean enable = true;

    /**
     * Mapper接口父类
     */
    private String superClass = MybatisMapper.class.getName();

    /**
     * 是否使用 @Mapper
     */
    private boolean mapperAnnotation = true;

    /**
     * mapper接口包名
     */
    private String packageName = "mapper";

    /**
     * mapper接口后缀
     */
    private String suffix = "Mapper";

    /**
     * 设置是否启用
     */
    public MapperConfig enable(boolean enable) {
        this.enable = enable;
        return this;
    }


    /**
     * Mapper接口父类
     */
    public MapperConfig superClass(String superClass) {
        this.superClass = superClass;
        return this;
    }

    /**
     * Mapper接口父类
     */
    public MapperConfig superClass(Class superClass) {
        this.superClass = superClass == null ? null : superClass.getName();
        return this;
    }

    /**
     * 是否使用 @Mapper
     */
    public MapperConfig mapperAnnotation(boolean mapperAnnotation) {
        this.mapperAnnotation = mapperAnnotation;
        return this;
    }

    /**
     * mapper接口包名
     */
    public MapperConfig packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    /**
     * mapper接口后缀
     */
    public MapperConfig suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }
}

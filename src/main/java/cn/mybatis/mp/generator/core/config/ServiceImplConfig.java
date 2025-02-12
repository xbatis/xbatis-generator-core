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

import cn.mybatis.mp.generator.core.database.meta.EntityInfo;
import lombok.Getter;

@Getter
public class ServiceImplConfig {

    /**
     * 是否启用
     */
    private boolean enable = true;

    /**
     * 启用泛型
     */
    private boolean generic = false;

    /**
     * 接口父类
     */
    private String superClass;
    /**
     * 注入dao
     */
    private boolean injectDao = true;
    /**
     * 注入Mapper
     */
    private boolean injectMapper = false;

    /**
     * 是否生成XXChain方法
     */
    private boolean genChainMethod = true;
    /**
     * service实现类包名
     */
    private String packageName = "service.impl";
    /**
     * service实现后缀
     */
    private String suffix = "ServiceImpl";

    /**
     * 接口父类
     */
    public ServiceImplConfig superClass(String superClass) {
        this.superClass = superClass;
        return this;
    }

    /**
     * 接口父类
     */
    public ServiceImplConfig superClass(Class superClass) {
        this.superClass = superClass == null ? null : superClass.getName();
        return this;
    }

    public ServiceImplConfig enable(boolean enable) {
        this.enable = enable;
        return this;
    }

    /**
     * 启用泛型
     */
    public ServiceImplConfig generic(boolean generic) {
        this.generic = generic;
        return this;
    }

    /**
     * 注入dao
     */
    public ServiceImplConfig injectDao(boolean injectDao) {
        this.injectDao = injectDao;
        return this;
    }

    public boolean isInjectDao(GeneratorConfig generatorConfig) {
        return injectDao && generatorConfig.getDaoImplConfig().isEnable();
    }

    /**
     * 注入Mapper
     */
    public ServiceImplConfig injectMapper(boolean injectMapper) {
        this.injectMapper = injectMapper;
        return this;
    }

    public ServiceImplConfig setGenChainMethod(boolean genChainMethod) {
        this.genChainMethod = genChainMethod;
        return this;
    }

    /**
     * service实现类包名
     */
    public ServiceImplConfig packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    /**
     * service实现后缀
     */
    public ServiceImplConfig suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public String injectDaoClassName(GeneratorConfig generatorConfig, EntityInfo entityInfo) {
        if (generatorConfig.getDaoConfig().isEnable()) {
            return entityInfo.getDaoName();
        }
        if (generatorConfig.getDaoImplConfig().isEnable()) {
            return entityInfo.getDaoImplName();
        }
        throw new RuntimeException("dao层未开启");
    }

    public String mapperClassName(EntityInfo entityInfo) {
        return entityInfo.getMapperName();
    }
}

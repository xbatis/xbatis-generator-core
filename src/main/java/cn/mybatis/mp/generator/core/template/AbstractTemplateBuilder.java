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

package cn.mybatis.mp.generator.core.template;

import cn.mybatis.mp.generator.core.config.GeneratorConfig;
import cn.mybatis.mp.generator.core.database.meta.EntityInfo;

public abstract class AbstractTemplateBuilder implements ITemplateBuilder {

    protected final GeneratorConfig generatorConfig;

    protected final EntityInfo entityInfo;

    public AbstractTemplateBuilder(GeneratorConfig generatorConfig, EntityInfo entityInfo) {
        this.generatorConfig = generatorConfig;
        this.entityInfo = entityInfo;
    }

    @Override
    public boolean fileCover() {
        return this.generatorConfig.isFileCover();
    }

    @Override
    public EntityInfo entityInfo() {
        return entityInfo;
    }

    @Override
    public String charset() {
        return this.generatorConfig.getCharset();
    }
}

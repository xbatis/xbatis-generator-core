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

package cn.xbatis.generator.core.buidler;

import cn.xbatis.generator.core.JavaToTsType;
import cn.xbatis.generator.core.config.GeneratorConfig;
import cn.xbatis.generator.core.database.meta.EntityInfo;
import cn.xbatis.generator.core.template.AbstractTemplateBuilder;

import java.util.HashMap;
import java.util.Map;

public class TsTypeTemplateBuilder extends AbstractTemplateBuilder {

    public TsTypeTemplateBuilder(GeneratorConfig generatorConfig, EntityInfo entityInfo) {
        super(generatorConfig, entityInfo);
    }

    @Override
    public boolean enable() {
        return generatorConfig.getMapperXmlConfig().isEnable();
    }

    @Override
    public String targetFilePath() {
        return generatorConfig.getBaseFilePath() + "/tsTypes/" + entityInfo.getName().replaceAll("\\.", "/") + ".ts";
    }

    @Override
    public String templateFilePath() {
        return generatorConfig.getTemplateRootPath() + "/tsType";
    }

    @Override
    public Map<String, Object> contextData() {
        Map<String, Object> data = new HashMap<>();
        data.put("entityInfo", entityInfo);
        data.put("javaToTsType", new JavaToTsType());
        return data;
    }
}

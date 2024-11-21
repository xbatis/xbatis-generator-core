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
import cn.mybatis.mp.generator.core.util.PathUtils;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class MapperXmlTemplateBuilder extends AbstractTemplateBuilder {

    public MapperXmlTemplateBuilder(GeneratorConfig generatorConfig, EntityInfo entityInfo) {
        super(generatorConfig, entityInfo);
    }

    @Override
    public boolean enable() {
        return generatorConfig.getMapperXmlConfig().isEnable();
    }

    @Override
    public String targetFilePath() {
        return PathUtils.buildFilePath(
                generatorConfig.getBaseFilePath(),
                generatorConfig.getResourcePath(),
                generatorConfig.getMapperXmlConfig().getPackageName().replaceAll("\\.", Matcher.quoteReplacement(File.separator)),
                entityInfo.getName())
                + ".xml";
    }

    @Override
    public String templateFilePath() {
        return generatorConfig.getTemplateRootPath() + "/mapper.xml";
    }

    @Override
    public Map<String, Object> contextData() {
        Map<String, Object> data = new HashMap<>();
        data.put("date", LocalDate.now().toString());
        data.put("author", generatorConfig.getAuthor());
        data.put("entityInfo", entityInfo);
        data.put("entityConfig", generatorConfig.getEntityConfig());
        data.put("mapperXmlConfig", generatorConfig.getMapperXmlConfig());
        data.put("mapperConfig", generatorConfig.getMapperConfig());
        data.put("daoConfig", generatorConfig.getDaoConfig());
        data.put("daoImplConfig", generatorConfig.getDaoImplConfig());
        data.put("serviceConfig", generatorConfig.getServiceConfig());
        data.put("serviceImplConfig", generatorConfig.getServiceImplConfig());
        data.put("generatorConfig", generatorConfig);
        data.put("containerType", generatorConfig.getContainerType());
        return data;
    }
}

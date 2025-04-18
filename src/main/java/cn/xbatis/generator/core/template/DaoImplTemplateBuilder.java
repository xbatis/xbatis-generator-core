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

package cn.xbatis.generator.core.template;

import cn.xbatis.core.mvc.impl.BasicDaoImpl;
import cn.xbatis.core.mvc.impl.DaoImpl;
import cn.xbatis.generator.core.config.GeneratorConfig;
import cn.xbatis.generator.core.database.meta.EntityInfo;
import cn.xbatis.generator.core.util.GeneratorUtil;
import cn.xbatis.generator.core.util.PathUtils;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class DaoImplTemplateBuilder extends AbstractTemplateBuilder {

    public DaoImplTemplateBuilder(GeneratorConfig generatorConfig, EntityInfo entityInfo) {
        super(generatorConfig, entityInfo);
    }

    @Override
    public boolean enable() {
        return generatorConfig.getDaoImplConfig().isEnable();
    }

    @Override
    public String targetFilePath() {
        return PathUtils.buildFilePath(
                generatorConfig.getBaseFilePath(),
                generatorConfig.getJavaPath(),
                entityInfo.getDaoImplPackage().replaceAll("\\.", Matcher.quoteReplacement(File.separator)),
                entityInfo.getDaoImplName())
                + ".java";
    }

    @Override
    public String templateFilePath() {
        return generatorConfig.getTemplateRootPath() + "/dao.impl";
    }

    @Override
    public Map<String, Object> contextData() {
        Map<String, Object> data = new HashMap<>();
        if (generatorConfig.getDaoImplConfig().getSuperClass() != null) {
            if (!generatorConfig.getMapperConfig().isEnable() && generatorConfig.getDaoImplConfig().getSuperClass().equals(DaoImpl.class.getName())) {
                generatorConfig.getDaoImplConfig().superClass(BasicDaoImpl.class);
            }
        }
        GeneratorUtil.buildDaoImplImports(generatorConfig, entityInfo, data);

        if (generatorConfig.getDaoImplConfig().getSuperClass() != null) {
            int dotIndex = generatorConfig.getDaoImplConfig().getSuperClass().lastIndexOf(".");
            String superName;
            if (dotIndex > 0) {
                superName = generatorConfig.getDaoImplConfig().getSuperClass().substring(dotIndex + 1);
            } else {
                superName = generatorConfig.getDaoImplConfig().getSuperClass();
            }
            data.put("superExtend", " extends " + superName);
        } else {
            data.put("superExtend", "");
        }
        data.put("date", LocalDate.now().toString());
        data.put("author", generatorConfig.getAuthor());
        data.put("entityInfo", entityInfo);
        data.put("entityConfig", generatorConfig.getEntityConfig());
        data.put("mapperConfig", generatorConfig.getMapperConfig());
        data.put("daoConfig", generatorConfig.getDaoConfig());
        data.put("daoImplConfig", generatorConfig.getDaoImplConfig());
        data.put("serviceConfig", generatorConfig.getServiceConfig());
        data.put("serviceImplConfig", generatorConfig.getServiceImplConfig());
        data.put("actionConfig", generatorConfig.getActionConfig());
        data.put("generatorConfig", generatorConfig);
        data.put("containerType", generatorConfig.getContainerType());
        return data;
    }
}

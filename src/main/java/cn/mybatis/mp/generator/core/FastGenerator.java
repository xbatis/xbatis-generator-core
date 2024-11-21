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

package cn.mybatis.mp.generator.core;


import cn.mybatis.mp.generator.core.config.GeneratorConfig;
import cn.mybatis.mp.generator.core.database.meta.EntityInfo;
import cn.mybatis.mp.generator.core.database.meta.TableInfo;
import cn.mybatis.mp.generator.core.database.meta.TableMetaDataQuery;
import cn.mybatis.mp.generator.core.template.ITemplateBuilder;
import cn.mybatis.mp.generator.core.template.engine.FreemarkerTemplateEngine;
import cn.mybatis.mp.generator.core.template.engine.TemplateEngine;
import cn.mybatis.mp.generator.core.util.RuntimeUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FastGenerator {

    private final GeneratorConfig generatorConfig;

    public FastGenerator(GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
    }

    public void create() {
        List<EntityInfo> entityInfoList;
        try (Connection connection = generatorConfig.getDataBaseConfig().getConnection()) {
            TableMetaDataQuery tableMetaDataQuery = new TableMetaDataQuery(generatorConfig, connection);
            List<TableInfo> tableInfoList = tableMetaDataQuery.getTableInfoList(!generatorConfig.isIgnoreTable(), !generatorConfig.isIgnoreView());
            log.info("mybatis-mp-generator found {} tables and {} views",
                    tableInfoList.stream().filter(tableInfo -> !tableInfo.isView()).count(),
                    tableInfoList.stream().filter(tableInfo -> tableInfo.isView()).count());
            entityInfoList = tableInfoList.stream().map(item -> new EntityInfo(generatorConfig, item)).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        log.info("mybatis-mp-generator's base file path is {}", generatorConfig.getBaseFilePath());

        TemplateEngine templateEngine = generatorConfig.getTemplateEngine();
        templateEngine = templateEngine == null ? new FreemarkerTemplateEngine() : templateEngine;
        for (EntityInfo entityInfo : entityInfoList) {
            for (Class<? extends ITemplateBuilder> templateBuilderClass : generatorConfig.getTemplateBuilders()) {
                ITemplateBuilder templateBuilder;
                try {
                    templateBuilder = templateBuilderClass.getConstructor(GeneratorConfig.class, EntityInfo.class).newInstance(generatorConfig, entityInfo);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (templateBuilder.enable()) {
                    templateEngine.render(templateBuilder);
                }
            }
        }

        if (generatorConfig.isFinishOpen()) {
            RuntimeUtils.openDir(generatorConfig.getBaseFilePath());
        }
    }
}

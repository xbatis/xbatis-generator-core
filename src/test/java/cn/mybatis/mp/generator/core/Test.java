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

import cn.mybatis.mp.generator.core.buidler.TsTypeTemplateBuilder;
import cn.mybatis.mp.generator.core.config.ContainerType;
import cn.mybatis.mp.generator.core.config.GeneratorConfig;
import db.sql.api.DbType;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

public class Test {

    private static void oracleTest() {
        new FastGenerator(new GeneratorConfig(
                "jdbc:oracle:thin:@//localhost:1521/xe?currentSchema=SYSTEM",
                "system",
                "oracle")
                .baseFilePath(System.getProperty("user.dir") + "/target/generated-code")
                .basePackage("com.test")
                .swaggerVersion(3)
                .containerType(ContainerType.SPRING)
                .tableConfig(tableConfig -> {
                    tableConfig.includeTable("t_sys_user", "t_sys_user", "t_sys_user", "t_sys_user");
                })
                .columnConfig(columnConfig -> {
                    columnConfig.disableUpdateColumns("create_time");
                    columnConfig.versionColumn("phone");
                    columnConfig.logicDeleteColumn("free");
                    columnConfig.tenantIdColumn("state");
                })
                .entityConfig(entityConfig -> {
                    entityConfig.lombok(false);
                    entityConfig.swagger(true);
                    entityConfig.alwaysAnnotation(false);
                    entityConfig.logicDeleteCode("@LogicDelete(beforeValue=\"0\",afterValue=\"1\",deleteTimeField=\"create_time\")");
                })
                .mapperXmlConfig(mapperXmlConfig -> {
                    mapperXmlConfig.enable(true).resultMap(true).columnList(true);
                })
                .serviceImplConfig(serviceImplConfig -> {
                    serviceImplConfig.superClass(Integer.class).injectMapper(true);
                })
                .actionConfig(actionConfig -> {
                    actionConfig
                            .enableSave(true)
                            .enableUpdate(true)
                            .enableFind(true)
                            .enableGet(true)
                            .enableDelete(true)
                            .swagger(true)
                            .returnClass(Object.class.getName());
                })
        ).create();
    }

    private static void mysqlTest() {
        new FastGenerator(new GeneratorConfig(
                "jdbc:mysql://localhost:3306/test3",
                "root",
                "123456")
                //.baseFilePath(System.getProperty("user.dir") + "/xx")
                //.javaPath("src/main/java")
                //.resourcePath("src/main/resources")
                .fileCover(true)
                .baseFilePath(System.getProperty("user.dir") + "/target/generated-code")
                .basePackage("com.test")
                .swaggerVersion(3)
                .containerType(ContainerType.SPRING)
                .tableConfig(tableConfig -> {
                    tableConfig.tablePrefixes("i");
                })
                .templateBuilders(list -> {
                    list.add(TsTypeTemplateBuilder.class);
                })
                .columnConfig(columnConfig -> {
                    columnConfig.columnPrefixes("n");
                    columnConfig.disableUpdateColumns("create_time");
                    columnConfig.versionColumn("phone");
                    columnConfig.logicDeleteColumn("free");
                    columnConfig.tenantIdColumn("state");
                })
                .entityConfig(entityConfig -> {
                    entityConfig.lombok(true)
                            .comment(false)
                            .lombokBuilder(true)
                            .serial(false)
                            .swagger(true)
                            .superClass(Test.class)
                    ;
                    entityConfig.swagger(true).packageName("model");
                    entityConfig.logicDeleteCode("@LogicDelete(beforeValue=\"0\",afterValue=\"1\",deleteTimeField=\"create_time\")");
                })
                .mapperXmlConfig(mapperXmlConfig -> {
                    mapperXmlConfig.enable(true).resultMap(true).columnList(true);
                })
                //.daoConfig(daoConfig -> daoConfig.enable(false))
                //.daoImplConfig(daoImplConfig -> daoImplConfig.enable(false))
                .serviceConfig(serviceConfig -> serviceConfig.superClass(IService.class).generic(true))
                .serviceImplConfig(serviceImplConfig -> {
                    serviceImplConfig.superClass(ServiceImpl.class).injectMapper(true).generic(true).injectDao(false);
                })
                .actionConfig(actionConfig -> {
                    actionConfig
                            .comment(false)
                            .enableSave(true)
                            .enableUpdate(true)
                            .enableFind(true)
                            .enableGet(true)
                            .enableDelete(true)
                            .swagger(true)
                            .returnClass(Object.class.getName());
                })
        ).create();

    }


    private static void h2Test() {

        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("test3")
                .addScript("schema.sql")
                .build();


        //根据数据源生成
        new FastGenerator(new GeneratorConfig(
                DbType.H2,//数据库类型
                dataSource)
                .basePackage("com.test")//根包路径
                .baseFilePath(System.getProperty("user.dir") + "/target/generated-code")
                .templateBuilders(list -> {
                    list.add(TsTypeTemplateBuilder.class);
                })
//                .mapperConfig(mapperXmlConfig -> {mapperXmlConfig.enable(false).superClass(Test.class);})
//                .daoConfig(daoConfig -> daoConfig.enable(false))
//                .daoImplConfig(daoImplConfig -> daoImplConfig.enable(false))
//                .serviceImplConfig(serviceImplConfig -> serviceImplConfig.injectMapper(true))
                .mapperXmlConfig(mapperXmlConfig -> mapperXmlConfig.enable(false).columnList(true).resultMap(true))
        ).create();
    }

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        mysqlTest();
        //oracleTest();
        System.out.println(System.currentTimeMillis() - start);
    }

    @org.junit.jupiter.api.Test
    public void h2TestCase() {
        h2Test();
    }
}

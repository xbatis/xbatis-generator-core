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

package cn.xbatis.generator.core.util;

import cn.xbatis.core.mybatis.mapper.context.Pager;
import cn.xbatis.core.sql.executor.chain.DeleteChain;
import cn.xbatis.core.sql.executor.chain.InsertChain;
import cn.xbatis.core.sql.executor.chain.QueryChain;
import cn.xbatis.core.sql.executor.chain.UpdateChain;
import cn.xbatis.core.util.NamingUtil;
import cn.xbatis.db.IdAutoType;
import cn.xbatis.db.annotations.*;
import cn.xbatis.generator.core.config.ContainerType;
import cn.xbatis.generator.core.config.EntityConfig;
import cn.xbatis.generator.core.config.GeneratorConfig;
import cn.xbatis.generator.core.database.meta.ColumnInfo;
import cn.xbatis.generator.core.database.meta.EntityInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GeneratorUtil {

    /**
     * 获取实体类Base名字
     *
     * @param generatorConfig
     * @param tableName
     * @return
     */
    public static String getEntityBaseName(GeneratorConfig generatorConfig, String tableName) {
        EntityConfig entityConfig = generatorConfig.getEntityConfig();
        if (entityConfig.getNameConvert() == null) {
            entityConfig.nameConvert((table -> NamingUtil.firstToUpperCase(NamingUtil.underlineToCamel(table))));
        }
        return entityConfig.getNameConvert().apply(tableName);
    }

    /**
     * 获取实体类名字
     *
     * @param generatorConfig
     * @param tableName
     * @return
     */
    public static String getEntityName(GeneratorConfig generatorConfig, String tableName) {
        EntityConfig entityConfig = generatorConfig.getEntityConfig();
        if (entityConfig.getNameConvert() == null) {
            entityConfig.nameConvert((table -> NamingUtil.firstToUpperCase(NamingUtil.underlineToCamel(table))));
        }

        String name = getEntityBaseName(generatorConfig, tableName);
        if (entityConfig.getSuffix() != null) {
            name += entityConfig.getSuffix();
        }
        return name;
    }


    /**
     * 获取实体类字段名字
     *
     * @param generatorConfig
     * @param columnName
     * @return
     */
    public static String getEntityFieldName(GeneratorConfig generatorConfig, String columnName) {
        EntityConfig entityConfig = generatorConfig.getEntityConfig();
        if (entityConfig.getFieldNameConverter() == null) {
            return entityConfig.getFieldNamingStrategy().getName(columnName, true);
        }
        return entityConfig.getFieldNameConverter().apply(columnName);
    }

    /**
     * 获取实体类字段备注
     *
     * @param generatorConfig
     * @param columnInfo
     * @return
     */
    public static String getEntityFieldRemarks(GeneratorConfig generatorConfig, ColumnInfo columnInfo) {
        EntityConfig entityConfig = generatorConfig.getEntityConfig();
        if (entityConfig.getRemarksConverter() == null) {
            return columnInfo.getRemarks();
        }
        return entityConfig.getRemarksConverter().apply(columnInfo);
    }

    /**
     * 获取列的java 类型
     *
     * @param generatorConfig
     * @param columnInfo
     * @return
     */
    public static Class<?> getColumnType(GeneratorConfig generatorConfig, ColumnInfo columnInfo) {
        Class<?> type = generatorConfig.getEntityConfig().getColumnType(columnInfo);
        if (type == null) {
            return Object.class;
        }
        return type;
    }

    /**
     * 构建实体类的imports
     *
     * @param generatorConfig
     * @param entityInfo
     * @return
     */
    public static List<String> buildEntityImports(GeneratorConfig generatorConfig, EntityInfo entityInfo) {
        List<String> classList = new ArrayList<>();
        classList.add(Table.class.getName());
        if (!entityInfo.getIdFieldInfoList().isEmpty()) {
            classList.add(TableId.class.getName());
            classList.add(IdAutoType.class.getName());
        }

        if (generatorConfig.getEntityConfig().getSuperClass() != null) {
            classList.add(generatorConfig.getEntityConfig().getSuperClass());
        }
        if (generatorConfig.getEntityConfig().isLombok()) {
            classList.add("lombok.Data");

            if (generatorConfig.getEntityConfig().isLombokBuilder()) {
                classList.add(generatorConfig.getEntityConfig().hasSuperClass() ? "lombok.experimental.SuperBuilder" : "lombok.Builder");
                classList.add("lombok.NoArgsConstructor");
                classList.add("lombok.AllArgsConstructor");
            }

            if (generatorConfig.getEntityConfig().hasSuperClass()) {
                classList.add("lombok.EqualsAndHashCode");
                classList.add("lombok.ToString");
            }

            if (generatorConfig.getEntityConfig().isCreateFieldClass()) {
                classList.add("lombok.experimental.FieldNameConstants");
            }
        }
        entityInfo.getFieldInfoList().forEach(item -> {
            classList.add(item.getType().getName());
            if (item.isNeedTableField(generatorConfig.getEntityConfig())) {
                classList.add(TableField.class.getName());
            }
            if (item.getColumnInfo().isVersion()) {
                classList.add(Version.class.getName());
            } else if (item.getColumnInfo().isTenantId()) {
                classList.add(TenantId.class.getName());
            } else if (item.getColumnInfo().isLogicDelete()) {
                classList.add(LogicDelete.class.getName());
            }
        });
        return buildImports(classList);
    }

    /**
     * 构建实体类的imports
     *
     * @param generatorConfig
     * @param entityInfo
     * @return
     */
    public static List<String> buildMapperImports(GeneratorConfig generatorConfig, EntityInfo entityInfo) {
        List<String> classList = new ArrayList<>();
        if (generatorConfig.getMapperConfig().getSuperClass() != null) {
            classList.add(generatorConfig.getMapperConfig().getSuperClass());
        }
        if (generatorConfig.getMapperConfig().isMapperAnnotation()) {
            classList.add(Mapper.class.getName());
        }
        if (entityInfo.hasMultiId()) {
            entityInfo.getIdFieldInfoList().forEach(item -> {
                classList.add(item.getType().getName());
            });
        }
        classList.add(entityInfo.getEntityPackage() + "." + entityInfo.getName());
        return buildImports(classList);
    }

    /**
     * 构建Dao接口的imports
     *
     * @param generatorConfig
     * @param entityInfo
     * @return
     */
    public static List<String> buildDaoImports(GeneratorConfig generatorConfig, EntityInfo entityInfo) {
        List<String> classList = new ArrayList<>();
        if (generatorConfig.getDaoConfig().getSuperClass() != null) {
            classList.add(generatorConfig.getDaoConfig().getSuperClass());
        }
        if (entityInfo.hasMultiId()) {
            entityInfo.getIdFieldInfoList().forEach(item -> {
                classList.add(item.getType().getName());
            });
        }
        classList.add(entityInfo.getEntityPackage() + "." + entityInfo.getName());
        if (entityInfo.getIdFieldInfo() != null) {
            classList.add(entityInfo.getIdFieldInfo().getType().getName());
        }
        return buildImports(classList);
    }

    /**
     * 构建Dao实现类的imports
     *
     * @param generatorConfig
     * @param entityInfo
     * @return
     */
    public static void buildDaoImplImports(GeneratorConfig generatorConfig, EntityInfo entityInfo, Map<String, Object> data) {
        List<String> classList = new ArrayList<>();
        if (generatorConfig.getDaoImplConfig().getSuperClass() != null) {
            classList.add(generatorConfig.getDaoImplConfig().getSuperClass());
        }
        if (entityInfo.hasMultiId()) {
            entityInfo.getIdFieldInfoList().forEach(item -> {
                classList.add(item.getType().getName());
            });
        }
        classList.add(entityInfo.getEntityPackage() + "." + entityInfo.getName());
        if (entityInfo.getIdFieldInfo() != null) {
            classList.add(entityInfo.getIdFieldInfo().getType().getName());
        }
        classList.add(entityInfo.getMapperPackage() + "." + entityInfo.getMapperName());

        if (generatorConfig.getDaoConfig().isEnable()) {
            classList.add(entityInfo.getDaoPackage() + "." + entityInfo.getDaoName());
        }

        if (generatorConfig.getContainerType() == ContainerType.SPRING) {
            classList.add("org.springframework.stereotype.Repository");
            classList.add("org.springframework.beans.factory.annotation.Autowired");
            data.put("repositoryAnnotationName", "Repository");
            data.put("autowiredAnnotationName", "Autowired");
        } else if (generatorConfig.getContainerType() == ContainerType.SOLON) {
            classList.add("org.noear.solon.annotation.Component");
            classList.add("org.noear.solon.annotation.Inject");
            classList.add("org.noear.solon.annotation.Init");
            data.put("repositoryAnnotationName", "Component");
            data.put("autowiredAnnotationName", "Inject");
        }
        data.put("imports", buildImports(classList));
    }


    /**
     * 构建Dao接口的imports
     *
     * @param generatorConfig
     * @param entityInfo
     * @return
     */
    public static List<String> buildServiceImports(GeneratorConfig generatorConfig, EntityInfo entityInfo) {
        List<String> classList = new ArrayList<>();
        if (generatorConfig.getServiceConfig().getSuperClass() != null) {
            classList.add(generatorConfig.getServiceConfig().getSuperClass());
        }

        classList.add(entityInfo.getEntityPackage() + "." + entityInfo.getName());
        if (entityInfo.getIdFieldInfo() != null) {
            classList.add(entityInfo.getIdFieldInfo().getType().getName());
        }
        return buildImports(classList);
    }

    /**
     * 构建Dao实现类的imports
     *
     * @param generatorConfig
     * @param entityInfo
     * @return
     */
    public static void buildServiceImplImports(GeneratorConfig generatorConfig, EntityInfo entityInfo, Map<String, Object> data) {
        List<String> classList = new ArrayList<>();
        if (generatorConfig.getServiceImplConfig().getSuperClass() != null) {
            classList.add(generatorConfig.getServiceImplConfig().getSuperClass());
        }

        classList.add(entityInfo.getEntityPackage() + "." + entityInfo.getName());
        if (entityInfo.getIdFieldInfo() != null) {
            classList.add(entityInfo.getIdFieldInfo().getType().getName());
        }

        if (generatorConfig.getDaoConfig().isEnable()) {
            classList.add(entityInfo.getDaoPackage() + "." + entityInfo.getDaoName());
        } else if (generatorConfig.getDaoImplConfig().isEnable()) {
            classList.add(entityInfo.getDaoImplPackage() + "." + entityInfo.getDaoImplName());
        }

        if (generatorConfig.getServiceConfig().isEnable()) {
            classList.add(entityInfo.getServicePackage() + "." + entityInfo.getServiceName());
        }

        if (generatorConfig.getServiceImplConfig().isInjectMapper() || !generatorConfig.getServiceImplConfig().isInjectDao(generatorConfig)) {
            classList.add(entityInfo.getMapperPackage() + "." + entityInfo.getMapperName());
            classList.add(QueryChain.class.getName());
            classList.add(UpdateChain.class.getName());
            classList.add(InsertChain.class.getName());
            classList.add(DeleteChain.class.getName());
        }

        if (generatorConfig.getContainerType() == ContainerType.SPRING) {
            classList.add("org.springframework.stereotype.Service");
            classList.add("org.springframework.beans.factory.annotation.Autowired");
            data.put("serviceAnnotationName", "Service");
            data.put("autowiredAnnotationName", "Autowired");
        } else if (generatorConfig.getContainerType() == ContainerType.SOLON) {
            classList.add("org.noear.solon.annotation.Component");
            classList.add("org.noear.solon.annotation.Inject");
            data.put("serviceAnnotationName", "Component");
            data.put("autowiredAnnotationName", "Inject");
        }
        data.put("imports", buildImports(classList));
    }

    /**
     * 构建Action的imports
     *
     * @param generatorConfig
     * @param entityInfo
     * @return
     */
    public static void buildActionImports(GeneratorConfig generatorConfig, EntityInfo entityInfo, Map<String, Object> data) {
        List<String> classList = new ArrayList<>();
        if (generatorConfig.getActionConfig().getSuperClass() != null) {
            classList.add(generatorConfig.getActionConfig().getSuperClass());
        }

        if (generatorConfig.getActionConfig().getReturnClass() != null) {
            classList.add(generatorConfig.getActionConfig().getReturnClass());
        }
        if (entityInfo.hasMultiId() && (generatorConfig.getActionConfig().isEnableGet() || generatorConfig.getActionConfig().isEnableDelete())) {
            entityInfo.getIdFieldInfoList().forEach(item -> {
                classList.add(item.getType().getName());
            });
        }
        classList.add(entityInfo.getEntityPackage() + "." + entityInfo.getName());

        if (entityInfo.getIdFieldInfo() != null) {
            classList.add(entityInfo.getIdFieldInfo().getType().getName());
        }

        if (generatorConfig.getActionConfig().isInjectService(generatorConfig)) {
            if (generatorConfig.getServiceConfig().isEnable()) {
                classList.add(entityInfo.getServicePackage() + "." + entityInfo.getServiceName());
            } else if (generatorConfig.getServiceImplConfig().isEnable()) {
                classList.add(entityInfo.getServiceImplPackage() + "." + entityInfo.getServiceImplName());
            }
        }

        if (generatorConfig.getContainerType() == ContainerType.SPRING) {
            classList.add("org.springframework.web.bind.annotation.RestController");
            classList.add("org.springframework.web.bind.annotation.RequestMapping");
            classList.add("org.springframework.beans.factory.annotation.Autowired");

            data.put("controllerAnnotationName", "RestController");
            data.put("requestMappingAnnotationName", "RequestMapping");
            data.put("autowiredAnnotationName", "Autowired");
        } else if (generatorConfig.getContainerType() == ContainerType.SOLON) {
            classList.add("org.noear.solon.annotation.Controller");
            classList.add("org.noear.solon.annotation.Mapping");
            classList.add("org.noear.solon.core.handle.MethodType");
            classList.add("org.noear.solon.annotation.Inject");

            data.put("controllerAnnotationName", "Controller");
            data.put("requestMappingAnnotationName", "Mapping");
            data.put("autowiredAnnotationName", "Inject");
        }

        if (generatorConfig.getActionConfig().isEnableSave() || generatorConfig.getActionConfig().isEnableUpdate()) {
            if (generatorConfig.getContainerType() == ContainerType.SPRING) {
                classList.add("org.springframework.web.bind.annotation.PostMapping");
                data.put("postMappingAnnotationName", "PostMapping");
            } else {
                classList.add("org.noear.solon.annotation.Post");
                classList.add("org.noear.solon.annotation.Mapping");
                classList.add("org.noear.solon.core.handle.MethodType");
                data.put("postMappingAnnotationName", "Mapping");
            }
        }

        if (generatorConfig.getActionConfig().isEnableGet() || generatorConfig.getActionConfig().isEnableFind()) {
            if (generatorConfig.getActionConfig().isEnableFind()) {
                classList.add(Pager.class.getName());
            }

            if (generatorConfig.getContainerType() == ContainerType.SPRING) {
                classList.add("org.springframework.web.bind.annotation.GetMapping");
                data.put("getMappingAnnotationName", "GetMapping");
            } else {
                classList.add("org.noear.solon.annotation.Get");
                classList.add("org.noear.solon.annotation.Mapping");
                data.put("getMappingAnnotationName", "Mapping");
            }
        }


        if (generatorConfig.getActionConfig().isEnableDelete()) {
            if (generatorConfig.getContainerType() == ContainerType.SPRING) {
                classList.add("org.springframework.web.bind.annotation.DeleteMapping");
                data.put("deleteMappingAnnotationName", "DeleteMapping");
            } else {
                classList.add("org.noear.solon.annotation.Delete");
                classList.add("org.noear.solon.annotation.Mapping");
                data.put("deleteMappingAnnotationName", "Mapping");
            }
        }

        data.put("imports", buildImports(classList));
    }

    public static List<String> buildImports(List<String> classList) {
        return classList.stream().filter(item -> !item.startsWith("java.lang")).distinct().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }
}

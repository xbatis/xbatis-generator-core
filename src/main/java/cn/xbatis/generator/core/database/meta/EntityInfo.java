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

package cn.xbatis.generator.core.database.meta;

import cn.xbatis.core.mybatis.mapper.BasicMapper;
import cn.xbatis.core.mybatis.mapper.MybatisMapper;
import cn.xbatis.core.util.NamingUtil;
import cn.xbatis.generator.core.config.*;
import cn.xbatis.generator.core.util.ClassUtils;
import cn.xbatis.generator.core.util.GeneratorUtil;
import cn.xbatis.generator.core.util.PathUtils;
import db.sql.api.impl.tookit.Objects;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class EntityInfo {

    public final String serviceName;
    public final String servicePackage;
    public final String serviceImplName;
    public final String serviceImplPackage;
    public final String actionName;
    public final String actionPackage;
    private final TableInfo tableInfo;
    private final String name;
    private final String remarks;
    private final EntityFieldInfo idFieldInfo;
    private final List<EntityFieldInfo> idFieldInfoList;
    private final List<EntityFieldInfo> allFieldInfoList;
    private final List<EntityFieldInfo> fieldInfoList;
    private final List<EntityFieldInfo> excludeFieldInfoList;
    private final String entityPackage;
    private final String mapperName;
    private final String mapperPackage;
    private final String daoName;
    private final String daoPackage;
    private final String daoImplName;
    private final String daoImplPackage;
    private boolean hasIgnorePrefix = false;

    public EntityInfo(GeneratorConfig generatorConfig, TableInfo tableInfo) {
        String handledTableName = NamingUtil.removePrefix(tableInfo.getName(), generatorConfig.getTableConfig().getTablePrefixes());
        hasIgnorePrefix = handledTableName != tableInfo.getName();
        this.name = GeneratorUtil.getEntityName(generatorConfig, handledTableName);
        this.remarks = tableInfo.getRemarks();
        this.tableInfo = tableInfo;
        if (tableInfo.getIdColumnInfo() != null) {
            this.idFieldInfo = new EntityFieldInfo(generatorConfig, this, tableInfo.getIdColumnInfo());
        } else {
            this.idFieldInfo = null;
        }
        this.idFieldInfoList = tableInfo.getIdColumnInfoList().stream().map(item -> new EntityFieldInfo(generatorConfig, this, item)).collect(Collectors.toList());
        this.allFieldInfoList = tableInfo.getColumnInfoList().stream().map(item -> new EntityFieldInfo(generatorConfig, this, item)).collect(Collectors.toList());

        this.excludeFieldInfoList = allFieldInfoList.stream().filter(item -> generatorConfig.getEntityConfig().getExcludeColumns().contains(item.getColumnInfo().getName()) || generatorConfig.getEntityConfig().getExcludeColumns().contains(item.getColumnInfo().getName().toUpperCase())).collect(Collectors.toList());

        List<EntityFieldInfo> fieldInfoList = new ArrayList<>(this.allFieldInfoList);
        fieldInfoList.removeAll(this.excludeFieldInfoList);
        this.fieldInfoList = fieldInfoList;

        this.entityPackage = PathUtils.buildPackage(generatorConfig.getBasePackage(), generatorConfig.getEntityConfig().getPackageName());

        if (!generatorConfig.getMapperConfig().isEnable()) {
            if (generatorConfig.getMapperConfig().getSuperClass().equals(MybatisMapper.class.getName())) {
                this.mapperName = BasicMapper.class.getSimpleName();
                this.mapperPackage = BasicMapper.class.getPackage().getName();
            } else {
                int dotIndex = generatorConfig.getMapperConfig().getSuperClass().lastIndexOf(".");
                if (dotIndex > 0) {
                    this.mapperName = generatorConfig.getMapperConfig().getSuperClass().substring(dotIndex + 1);
                    this.mapperPackage = generatorConfig.getMapperConfig().getSuperClass().substring(dotIndex);
                } else {
                    this.mapperName = generatorConfig.getMapperConfig().getSuperClass();
                    this.mapperPackage = "";
                }
            }

        } else {
            this.mapperName = this.name + generatorConfig.getMapperConfig().getSuffix();
            this.mapperPackage = PathUtils.buildPackage(generatorConfig.getBasePackage(), generatorConfig.getMapperConfig().getPackageName());
        }

        this.daoName = this.name + generatorConfig.getDaoConfig().getSuffix();
        this.daoPackage = PathUtils.buildPackage(generatorConfig.getBasePackage(), generatorConfig.getDaoConfig().getPackageName());

        this.daoImplName = this.name + generatorConfig.getDaoImplConfig().getSuffix();
        this.daoImplPackage = PathUtils.buildPackage(generatorConfig.getBasePackage(), generatorConfig.getDaoImplConfig().getPackageName());

        this.serviceName = this.name + generatorConfig.getServiceConfig().getSuffix();
        this.servicePackage = PathUtils.buildPackage(generatorConfig.getBasePackage(), generatorConfig.getServiceConfig().getPackageName());

        this.serviceImplName = this.name + generatorConfig.getServiceImplConfig().getSuffix();
        this.serviceImplPackage = PathUtils.buildPackage(generatorConfig.getBasePackage(), generatorConfig.getServiceImplConfig().getPackageName());

        this.actionName = this.name + generatorConfig.getActionConfig().getSuffix();
        this.actionPackage = PathUtils.buildPackage(generatorConfig.getBasePackage(), generatorConfig.getActionConfig().getPackageName());
    }

    public boolean hasId() {
        return !idFieldInfoList.isEmpty();
    }

    public boolean hasMultiId() {
        return idFieldInfoList.size() > 1;
    }

    public String buildTable(EntityConfig entityConfig) {
        StringBuilder stringBuilder = new StringBuilder("@Table(");
        stringBuilder.append("value =\"").append(this.getTableInfo().getName()).append("\",");
        if (entityConfig.isSchema() && this.getTableInfo().getSchema() != null && !this.getTableInfo().getSchema().isEmpty()) {
            stringBuilder.append("schema = \"").append(this.getTableInfo().getSchema()).append("\",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public String buildClassFullName(EntityConfig entityConfig) {
        StringBuilder stringBuilder = new StringBuilder(this.name);
        if (entityConfig.getSuperClass() != null) {
            stringBuilder.append(" extends ").append(ClassUtils.getClassSimpleName(entityConfig.getSuperClass()));
        }
        if (entityConfig.isSerial()) {
            stringBuilder.append("  implements java.io.Serializable");
        }

        return stringBuilder.toString();
    }

    public String buildMapperClassFullName(MapperConfig mapperConfig) {
        StringBuilder stringBuilder = new StringBuilder(this.getMapperName());
        if (Objects.nonNull(mapperConfig.getSuperClass())) {
            stringBuilder.append(" extends ").append(ClassUtils.getClassSimpleName(mapperConfig.getSuperClass()));
        }
        if (Objects.nonNull(mapperConfig.getSuperClass())) {
            stringBuilder.append(this.buildGeneric());
        }
        return stringBuilder.toString();
    }

    public String buildDaoClassFullName(DaoConfig daoConfig) {
        StringBuilder stringBuilder = new StringBuilder(this.getDaoName());
        if (Objects.nonNull(daoConfig.getSuperClass())) {
            stringBuilder.append(" extends ").append(ClassUtils.getClassSimpleName(daoConfig.getSuperClass()));
        }
        if (Objects.nonNull(daoConfig.getSuperClass()) && daoConfig.isGeneric()) {
            stringBuilder.append(this.buildGenericWithId());
        }
        return stringBuilder.toString();
    }

    public String buildDaoImplClassFullName(DaoConfig daoConfig, DaoImplConfig daoImplConfig) {
        StringBuilder stringBuilder = new StringBuilder(this.getDaoImplName());
        if (Objects.nonNull(daoImplConfig.getSuperClass())) {
            stringBuilder.append(" extends ").append(ClassUtils.getClassSimpleName(daoImplConfig.getSuperClass()));
        }
        if (Objects.nonNull(daoImplConfig.getSuperClass()) && daoConfig.isGeneric()) {
            stringBuilder.append(this.buildGenericWithId());
        }
        if (daoConfig.isEnable()) {
            stringBuilder.append(" implements ").append(getDaoName());
        }
        return stringBuilder.toString();
    }

    public String buildServiceClassFullName(ServiceConfig serviceConfig) {
        StringBuilder stringBuilder = new StringBuilder(this.getServiceName());
        if (Objects.nonNull(serviceConfig.getSuperClass())) {
            stringBuilder.append(" extends ").append(ClassUtils.getClassSimpleName(serviceConfig.getSuperClass()));
        }
        if (Objects.nonNull(serviceConfig.getSuperClass()) && serviceConfig.isGeneric()) {
            stringBuilder.append(this.buildGenericWithId());
        }
        return stringBuilder.toString();
    }

    public String buildServiceImplClassFullName(ServiceConfig serviceConfig, ServiceImplConfig serviceImplConfig) {
        StringBuilder stringBuilder = new StringBuilder(this.getServiceImplName());
        if (Objects.nonNull(serviceImplConfig.getSuperClass())) {
            stringBuilder.append(" extends ").append(ClassUtils.getClassSimpleName(serviceImplConfig.getSuperClass()));
        }
        if (Objects.nonNull(serviceImplConfig.getSuperClass()) && serviceImplConfig.isGeneric()) {
            stringBuilder.append(this.buildGenericWithId());
        }
        if (serviceConfig.isEnable()) {
            stringBuilder.append(" implements ").append(getServiceName());
        }
        return stringBuilder.toString();
    }

    public String buildActionClassFullName(ActionConfig actionConfig) {
        StringBuilder stringBuilder = new StringBuilder(this.getActionName());
        if (Objects.nonNull(actionConfig.getSuperClass())) {
            stringBuilder.append(" extends ").append(ClassUtils.getClassSimpleName(actionConfig.getSuperClass()));
        }
        if (Objects.nonNull(actionConfig.getSuperClass()) && actionConfig.isGeneric()) {
            stringBuilder.append(this.buildGenericWithId());
        }
        return stringBuilder.toString();
    }

    public String buildGeneric() {
        return "<" + getName() + ">";
    }

    public String buildGenericWithId() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<").append(getName()).append(", ");
        if (Objects.nonNull(idFieldInfo)) {
            stringBuilder.append(Objects.nonNull(idFieldInfo) ? idFieldInfo.getTypeName() : "Void");
        }
        stringBuilder.append(">");
        return stringBuilder.toString();
    }
}

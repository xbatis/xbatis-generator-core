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

import cn.xbatis.core.util.NamingUtil;
import cn.xbatis.generator.core.config.EntityConfig;
import cn.xbatis.generator.core.config.GeneratorConfig;
import cn.xbatis.generator.core.util.GeneratorUtil;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@ToString
public class EntityFieldInfo {

    @ToString.Exclude
    private final EntityInfo entityInfo;

    private final ColumnInfo columnInfo;

    private final String name;

    private final Class<?> type;

    private final String typeName;

    private final String remarks;

    private final boolean select;

    private final boolean update;

    private final String defaultTableIdCode;

    private final boolean alwaysAnnotation;

    private boolean hasIgnorePrefix = false;


    public EntityFieldInfo(GeneratorConfig generatorConfig, EntityInfo entityInfo, ColumnInfo columnInfo) {
        this.entityInfo = entityInfo;
        this.columnInfo = columnInfo;
        String columnName = columnInfo.getName();
        String handledTableName = NamingUtil.removePrefix(columnName, generatorConfig.getColumnConfig().getColumnPrefixes());
        hasIgnorePrefix = handledTableName != columnName;

        this.update = !generatorConfig.getColumnConfig().getDisableUpdateColumns().contains(columnName);

        this.name = GeneratorUtil.getEntityFieldName(generatorConfig, handledTableName);
        this.remarks = GeneratorUtil.getEntityFieldRemarks(generatorConfig, columnInfo);
        this.type = GeneratorUtil.getColumnType(generatorConfig, columnInfo);
        if (this.type == byte[].class) {
            this.select = false;
        } else {
            this.select = !generatorConfig.getColumnConfig().getDisableSelectColumns().contains(columnInfo.getName());
        }
        this.typeName = this.type.getSimpleName();
        this.defaultTableIdCode = generatorConfig.getEntityConfig().getDefaultTableIdCode();
        this.alwaysAnnotation = generatorConfig.getEntityConfig().isAlwaysAnnotation();
    }

    public boolean isNeedTableField(EntityConfig entityConfig) {
        return alwaysAnnotation
                || hasIgnorePrefix
                || !select
                || !update
                || (!this.columnInfo.isPrimaryKey() && entityConfig.isDefaultValueEnable() && this.getColumnInfo().getDefaultValue() != null)
                || !this.getColumnInfo().getName().equals(NamingUtil.camelToUnderline(this.name))
                ;
    }

    public String buildTableField(EntityConfig entityConfig) {
        StringBuilder stringBuilder = new StringBuilder("@TableField(");
        if (alwaysAnnotation || hasIgnorePrefix || !this.getColumnInfo().getName().equals(NamingUtil.camelToUnderline(this.name))) {
            stringBuilder.append("value =\"").append(this.getColumnInfo().getName()).append("\",");
        }
        if (!select) {
            stringBuilder.append("select = false,");
        }
        if (!update) {
            stringBuilder.append("update = false,");
        }
        if (!this.columnInfo.isPrimaryKey() && entityConfig.isDefaultValueEnable() && this.getColumnInfo().getDefaultValue() != null) {
            stringBuilder.append("defaultValue = \"")
                    .append(this.getColumnInfo().getDefaultValue().replace("\"", "\\\""))
                    .append("\",");

            if ((this.type == LocalDate.class || this.type == LocalDateTime.class || this.type == Date.class) && (this.getName().equals("updateTime") || this.getName().equals("updateDate") || this.getName().equals("updatedAt") || this.getName().equals("updateAt"))) {
                stringBuilder.append("updateDefaultValue = \"")
                        .append(this.getColumnInfo().getDefaultValue().replace("\"", "\\\""))
                        .append("\",");
            }
        }
        if (stringBuilder.charAt(stringBuilder.length() - 1) == '(') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        } else {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(")");
        }
        return stringBuilder.toString();
    }

    public String getterMethodName() {
        return (this.type == Boolean.class ? "is" : "get") + NamingUtil.firstToUpperCase(this.name);
    }

    public String setterMethodName() {
        return "set" + NamingUtil.firstToUpperCase(this.name);
    }

    public String buildTableIdCode() {
        if (!this.columnInfo.isPrimaryKey()) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder("@TableId");
        if (this.columnInfo.isAutoIncrement()) {
            stringBuilder.append("(IdAutoType.AUTO)");
        } else {
            if (this.defaultTableIdCode != null) {
                return this.defaultTableIdCode;
            }
            stringBuilder.append("(IdAutoType.NONE)");
        }
        return stringBuilder.toString();
    }
}

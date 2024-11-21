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

package cn.mybatis.mp.generator.core.database.meta;

import cn.mybatis.mp.generator.core.config.GeneratorConfig;
import cn.mybatis.mp.generator.core.util.StringCodeSafeUtil;
import db.sql.api.impl.tookit.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class TableMetaDataQuery {

    protected final DatabaseMetaData metaData;

    protected final GeneratorConfig generatorConfig;

    protected final String connDatabaseName;

    protected final String connSchema;

    public TableMetaDataQuery(GeneratorConfig generatorConfig, Connection connection) throws SQLException {
        this.metaData = connection.getMetaData();
        this.connSchema = connection.getSchema();
        this.generatorConfig = generatorConfig;
        this.connDatabaseName = connection.getCatalog();
    }

    private boolean isTableNeedInclude(String tableName) {
        if (generatorConfig.getTableConfig().getExcludeTables().contains(tableName)) {
            return false;
        }

        return generatorConfig.getTableConfig().getIncludeTables().isEmpty() || generatorConfig.getTableConfig().getIncludeTables().contains(tableName);
    }

    private List<TableInfo> getTableInfoList(String databaseName, String schema, String tableNamePattern, String[] types) {
        List<TableInfo> tables = new ArrayList<>();
        try (ResultSet resultSet = metaData.getTables(databaseName, schema, tableNamePattern, types)) {
            TableInfo tableInfo;
            while (resultSet.next()) {
                String TABLE_NAME = resultSet.getString("TABLE_NAME");
                String tableName = TABLE_NAME;

                if (tableName.toUpperCase().equals(tableName)) {
                    tableName = tableName.toLowerCase();
                }

                if (!isTableNeedInclude(TABLE_NAME) && !isTableNeedInclude(tableName)) {
                    continue;
                }

                tableInfo = new TableInfo();
                tableInfo.setName(tableName);
                String remarks = resultSet.getString("REMARKS");
                tableInfo.setRemarks(StringCodeSafeUtil.removeSpecialCharacters(remarks));
                tableInfo.setTableType(resultSet.getString("TABLE_TYPE"));
                tableInfo.setSchema(resultSet.getString("TABLE_SCHEM"));
                tableInfo.setCatalog(resultSet.getString("TABLE_CAT"));

                tableInfo.setColumnInfoList(getColumnInfo(tableInfo, TABLE_NAME));
                List<ColumnInfo> idColumnInfoList = tableInfo.getColumnInfoList().stream().filter(item -> item.isPrimaryKey()).collect(Collectors.toList());
                if (!idColumnInfoList.isEmpty() && idColumnInfoList.size() == 1) {
                    tableInfo.setIdColumnInfo(idColumnInfoList.get(0));
                }
                tableInfo.setIdColumnInfoList(idColumnInfoList);
                tables.add(tableInfo);
            }
        } catch (SQLException e) {
            throw new RuntimeException("读取数据库表信息出现错误", e);
        }

        return tables;
    }

    public List<TableInfo> getTableInfoList(boolean includeTable, boolean includeView) {
        String[] types;
        if (includeTable && includeView) {
            types = new String[]{"TABLE", "VIEW"};
        } else {
            if (includeTable) {
                types = new String[]{"TABLE"};
            } else if (includeView) {
                types = new String[]{"VIEW"};
            } else {
                throw new RuntimeException("includeTable or includeView must be true");
            }
        }

        String databaseName = generatorConfig.getDataBaseConfig().getDatabaseName();
        if (databaseName == null) {
            databaseName = this.connDatabaseName;
        }

        String schema = generatorConfig.getDataBaseConfig().getSchema();

        schema = Objects.isNull(schema) ? connSchema : schema;

        if (generatorConfig.getTableConfig().getIncludeTables().isEmpty() || generatorConfig.getTableConfig().getIncludeTables().size() > 5) {
            return this.getTableInfoList(databaseName, schema, null, types);
        } else {
            List<TableInfo> tables = new ArrayList<>();
            for (String table : generatorConfig.getTableConfig().getIncludeTables()) {
                List<TableInfo> list = this.getTableInfoList(databaseName, schema, table, types);
                if (list.isEmpty()) {
                    list = this.getTableInfoList(databaseName, schema, table.toUpperCase(), types);
                }
                tables.addAll(list);
            }
            return tables;
        }
    }

    private List<ColumnInfo> getColumnInfo(TableInfo tableInfo, String tableName) {
        Set<String> primaryKeys = new HashSet<>();
        if (!tableInfo.isView()) {
            try (ResultSet primaryKeysResultSet = metaData.getPrimaryKeys(tableInfo.getCatalog(), tableInfo.getSchema(), tableName)) {
                while (primaryKeysResultSet.next()) {
                    String columnName = primaryKeysResultSet.getString("COLUMN_NAME");
                    if (columnName.toUpperCase().equals(columnName)) {
                        columnName = columnName.toLowerCase();
                    }
                    primaryKeys.add(columnName);
                }
            } catch (SQLException e) {
                throw new RuntimeException("读取表主键信息:" + tableInfo.getName() + "错误:", e);
            }
        }

        if (primaryKeys.isEmpty()) {
            log.warn("当前表:{}，存在无主键情况！", tableInfo.getName());
        }

        List<ColumnInfo> columnsInfoList = new ArrayList<>();
        try (ResultSet resultSet = metaData.getColumns(tableInfo.getCatalog(), tableInfo.getSchema(), tableName, "%")) {
            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                if (columnName.toUpperCase().equals(columnName)) {
                    columnName = columnName.toLowerCase();
                }

                ColumnInfo columnInfo = new ColumnInfo();
                columnInfo.setTableInfo(tableInfo);
                columnInfo.setName(columnName);
                columnInfo.setPrimaryKey(primaryKeys.contains(columnInfo.getName()));

                columnInfo.setTypeName(resultSet.getString("TYPE_NAME"));

                JdbcType jdbcType = JdbcType.forCode(resultSet.getInt("DATA_TYPE"));
                int length = resultSet.getInt("COLUMN_SIZE");
                if (jdbcType == JdbcType.CHAR && length != 1) {
                    jdbcType = JdbcType.VARCHAR;
                }
                columnInfo.setJdbcType(jdbcType);
                columnInfo.setLength(length);
                columnInfo.setScale(resultSet.getInt("DECIMAL_DIGITS"));

                String remarks = resultSet.getString("REMARKS");
                columnInfo.setRemarks(StringCodeSafeUtil.removeSpecialCharacters(remarks));


                columnInfo.setDefaultValue(generatorConfig.getColumnConfig().getDefaultValueConvert().apply(resultSet.getString("COLUMN_DEF")));
                columnInfo.setNullable(resultSet.getInt("NULLABLE") == DatabaseMetaData.columnNullable);

                columnInfo.setVersion(columnName.equals(generatorConfig.getColumnConfig().getVersionColumn()));
                columnInfo.setTenantId(columnName.equals(generatorConfig.getColumnConfig().getTenantIdColumn()));
                columnInfo.setLogicDelete(columnName.equals(generatorConfig.getColumnConfig().getLogicDeleteColumn()));
                try {
                    columnInfo.setAutoIncrement("YES".equals(resultSet.getString("IS_AUTOINCREMENT")));
                } catch (SQLException e) {
                    log.error("获取自增信息异常：", e);
                }
                columnsInfoList.add(columnInfo);
            }
            return columnsInfoList;
        } catch (SQLException e) {
            throw new RuntimeException("读取表字段信息:" + tableInfo.getName() + "错误:", e);
        }
    }
}

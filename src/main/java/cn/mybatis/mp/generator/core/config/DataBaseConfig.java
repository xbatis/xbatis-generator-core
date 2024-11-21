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

package cn.mybatis.mp.generator.core.config;


import cn.mybatis.mp.core.util.DbTypeUtil;
import db.sql.api.DbType;
import lombok.Getter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Getter
public class DataBaseConfig {

    private final DbType dbType;
    private final DataSource dataSource;
    private final String url;
    private final String username;
    private final String password;
    private String schema;
    private String databaseName;

    public DataBaseConfig(DbType dbType, DataSource dataSource) {
        this.dataSource = dataSource;
        this.dbType = dbType;

        this.url = null;
        this.username = null;
        this.password = null;
    }

    public DataBaseConfig(String url, String username, String password) {

        this.dbType = DbTypeUtil.getDbType(url);
        this.url = url;
        this.username = username;
        this.password = password;

        this.dataSource = null;
    }

    public static Connection getConnection(String url, String username, String password) {
        Properties properties = new Properties();
        properties.put("user", username);
        properties.put("password", password);
        DbType dbType = DbTypeUtil.getDbType(url);
        addAdditionalJdbcProperties(properties, dbType);
        try {
            return DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static void addAdditionalJdbcProperties(Properties properties, DbType dbType) {
        switch (dbType) {
            case MYSQL:
                properties.put("remarks", "true");
                properties.put("useInformationSchema", "true");
                break;
            case ORACLE:
                properties.put("remarks", "true");
                properties.put("remarksReporting", "true");
                break;
            default: {

            }
        }
    }

    public DataBaseConfig schema(String schema) {
        this.schema = schema;
        return this;
    }

    public DataBaseConfig databaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public Connection getConnection() {
        try {
            if (this.dataSource != null) {
                return this.getDataSource().getConnection();
            } else {
                return getConnection(this.url, this.username, this.password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

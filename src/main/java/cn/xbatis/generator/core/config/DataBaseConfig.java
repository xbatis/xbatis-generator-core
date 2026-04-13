/*
 *  Copyright (c) 2024-2026, Ai东 (abc-127@live.cn).
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

package cn.xbatis.generator.core.config;

import cn.xbatis.core.dbType.DbTypeUtil;
import db.sql.api.DbType;
import db.sql.api.IDbType;
import lombok.Getter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Getter
public class DataBaseConfig {

    private final IDbType dbType;
    private final DataSource dataSource;
    private final String url;
    private final String username;
    private final String password;
    private String schema;
    private String databaseName;

    public DataBaseConfig(IDbType dbType, DataSource dataSource) {
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
        IDbType dbType = DbTypeUtil.getDbType(url);
        addAdditionalJdbcProperties(properties, dbType);
        try {
            return DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static void addAdditionalJdbcProperties(Properties properties, IDbType dbType) {
        if (dbType == DbType.MYSQL) {
            properties.put("remarks", "true");
            properties.put("useInformationSchema", "true");
        } else if (dbType == DbType.ORACLE) {
            properties.put("remarks", "true");
            properties.put("remarksReporting", "true");
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

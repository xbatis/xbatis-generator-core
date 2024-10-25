package cn.mybatis.mp.generator.core.database.meta;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;

@Data
public class ColumnInfo {

    @ToString.Exclude
    private TableInfo tableInfo;

    private boolean primaryKey;

    private boolean autoIncrement;

    private String name;

    private int length;

    private boolean nullable;

    private String remarks;

    private String defaultValue;

    private int scale;

    private boolean version;

    private boolean tenantId;

    private boolean logicDelete;

    private JdbcType jdbcType;

    private String typeName;


}

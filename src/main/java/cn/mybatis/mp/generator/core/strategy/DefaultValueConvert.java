package cn.mybatis.mp.generator.core.strategy;

import cn.mybatis.mp.core.util.StringPool;

import java.util.Objects;

public class DefaultValueConvert {

    public String convert(String defaultValue) {
        if (Objects.isNull(defaultValue)) {
            return null;
        }
        defaultValue = defaultValue.trim();
        if (StringPool.EMPTY.equals(defaultValue) || "''".equals(defaultValue)) {
            return "{BLANK}";
        } else if (defaultValue.equals("CURRENT_TIMESTAMP") || defaultValue.equals("CURRENT_DATE") || defaultValue.equals("LOCALTIMESTAMP")) {
            return "{NOW}";
        }
        return defaultValue;
    }
}

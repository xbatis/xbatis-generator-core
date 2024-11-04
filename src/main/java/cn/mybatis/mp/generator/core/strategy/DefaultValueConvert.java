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
        } else if (defaultValue.equalsIgnoreCase("CURRENT_TIMESTAMP") || defaultValue.equalsIgnoreCase("CURRENT_DATE") || defaultValue.equalsIgnoreCase("LOCALTIMESTAMP")) {
            return "{NOW}";
        } else if (defaultValue.equalsIgnoreCase("b'0'")) {
            return "0";
        } else if (defaultValue.equalsIgnoreCase("b'1'")) {
            return "1";
        }
        return defaultValue;
    }
}

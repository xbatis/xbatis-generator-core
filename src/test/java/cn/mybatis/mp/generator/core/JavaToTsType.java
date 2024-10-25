package cn.mybatis.mp.generator.core;

import java.util.HashMap;
import java.util.Map;

public class JavaToTsType {

    private final static Map<String, String> ruleMap = new HashMap<>();

    static {
        ruleMap.put("String", "string");
        ruleMap.put("Integer", "number");
        ruleMap.put("Long", "number");
        ruleMap.put("Float", "number");
        ruleMap.put("Double", "number");
        ruleMap.put("Boolean", "boolean");
        ruleMap.put("Date", "Date");
        ruleMap.put("Timestamp", "Date");
        ruleMap.put("LocalDateTime", "number[]");
        ruleMap.put("Byte", "number");
    }


    public String convert(String javaType) {
        String result = ruleMap.get(javaType);
        if (result == null) {
            return "no find type change generator";
        }
        return result;
    }
}

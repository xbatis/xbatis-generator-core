package cn.mybatis.mp.generator.core.strategy;

import cn.mybatis.mp.core.util.NamingUtil;

/**
 * 名字策略
 */
public enum NamingStrategy {

    /**
     * 不做任何改变，原样输出
     */
    NO_CHANGE,

    /**
     * 下划线转驼峰命名
     */
    UNDERLINE_TO_CAMEL;

    /**
     * 获取名字
     *
     * @param sourceName     原始名字
     * @param firstUpperCase 首字母大小写
     * @return
     */
    public String getName(String sourceName, boolean firstUpperCase) {
        if (this == NO_CHANGE) {
            return sourceName;
        }
        sourceName = NamingUtil.underlineToCamel(sourceName);
        return firstUpperCase ? NamingUtil.firstToLower(sourceName) : sourceName;
    }
}

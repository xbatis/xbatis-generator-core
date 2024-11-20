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
     * @param firstLowerCase 首字母小写
     * @return
     */
    public String getName(String sourceName, boolean firstLowerCase) {
        if (this == NO_CHANGE) {
            return sourceName;
        }
        sourceName = NamingUtil.underlineToCamel(sourceName);
        return firstLowerCase ? NamingUtil.firstToLower(sourceName) : sourceName;
    }
}

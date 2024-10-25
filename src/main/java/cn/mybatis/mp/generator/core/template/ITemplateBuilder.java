package cn.mybatis.mp.generator.core.template;

import cn.mybatis.mp.generator.core.database.meta.EntityInfo;

import java.util.Map;

public interface ITemplateBuilder {

    default boolean fileCover() {
        return true;
    }

    default boolean enable() {
        return true;
    }

    EntityInfo entityInfo();

    String targetFilePath();

    String templateFilePath();

    Map<String, Object> contextData();

    String charset();

}

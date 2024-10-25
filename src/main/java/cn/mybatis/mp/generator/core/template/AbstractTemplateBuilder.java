package cn.mybatis.mp.generator.core.template;

import cn.mybatis.mp.generator.core.config.GeneratorConfig;
import cn.mybatis.mp.generator.core.database.meta.EntityInfo;

public abstract class AbstractTemplateBuilder implements ITemplateBuilder {

    protected final GeneratorConfig generatorConfig;

    protected final EntityInfo entityInfo;

    public AbstractTemplateBuilder(GeneratorConfig generatorConfig, EntityInfo entityInfo) {
        this.generatorConfig = generatorConfig;
        this.entityInfo = entityInfo;
    }

    @Override
    public boolean fileCover() {
        return this.generatorConfig.isFileCover();
    }

    @Override
    public EntityInfo entityInfo() {
        return entityInfo;
    }

    @Override
    public String charset() {
        return this.generatorConfig.getCharset();
    }
}

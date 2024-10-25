package cn.mybatis.mp.generator.core.config;

import cn.mybatis.mp.core.mvc.Dao;
import lombok.Getter;

@Getter
public class DaoConfig {

    /**
     * dao接口父类
     */
    private String superClass = Dao.class.getName();

    /**
     * 是否启用
     */
    private boolean enable = true;

    /**
     * 启用泛型
     */
    private boolean generic = true;

    /**
     * 实体类包名
     */
    private String packageName = "dao";

    /**
     * dao接口后缀
     */
    private String suffix = "Dao";


    /**
     * dao接口父类
     */
    public DaoConfig superClass(String superClass) {
        this.superClass = superClass;
        return this;
    }

    /**
     * dao接口父类
     */
    public DaoConfig superClass(Class superClass) {
        this.superClass = superClass.getName();
        return this;
    }

    /**
     * 设置是否启用
     */
    public DaoConfig enable(boolean enable) {
        this.enable = enable;
        return this;
    }

    /**
     * 启用泛型
     */
    public DaoConfig generic(boolean generic) {
        this.generic = generic;
        return this;
    }


    /**
     * 实体类包名
     */
    public DaoConfig packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    /**
     * dao接口后缀
     */
    public DaoConfig suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }
}

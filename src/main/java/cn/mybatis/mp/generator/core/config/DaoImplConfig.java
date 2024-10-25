package cn.mybatis.mp.generator.core.config;

import cn.mybatis.mp.core.mvc.impl.DaoImpl;
import lombok.Getter;

@Getter
public class DaoImplConfig {

    /**
     * dao实现类的父类
     */
    private String superClass = DaoImpl.class.getName();

    /**
     * dao实现类包名
     */
    private String packageName = "dao.impl";

    /**
     * dao实现类后缀
     */
    private String suffix = "DaoImpl";

    /**
     * 是否启用
     */
    private boolean enable = true;


    /**
     * dao实现类的父类
     */
    public DaoImplConfig superClass(String superClass) {
        this.superClass = superClass;
        return this;
    }

    /**
     * dao实现类的父类
     */
    public DaoImplConfig superClass(Class superClass) {
        this.superClass = superClass.getName();
        return this;
    }

    public DaoImplConfig enable(boolean enable) {
        this.enable = enable;
        return this;
    }

    /**
     * dao实现类包名
     */
    public DaoImplConfig packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    /**
     * dao实现类后缀
     */
    public DaoImplConfig suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }
}

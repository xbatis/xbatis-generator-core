package cn.mybatis.mp.generator.core.config;

import cn.mybatis.mp.generator.core.template.*;
import cn.mybatis.mp.generator.core.template.engine.TemplateEngine;
import db.sql.api.DbType;
import lombok.Getter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class GeneratorConfig {

    /**
     * 忽略表
     */
    private final boolean ignoreTable = false;

    private final List<Class<? extends ITemplateBuilder>> templateBuilders = new ArrayList<>();
    /**
     * 数据库配置
     */
    private DataBaseConfig dataBaseConfig;
    /**
     * 列配置
     */
    private ColumnConfig columnConfig = new ColumnConfig();
    /**
     * 表配置
     */
    private TableConfig tableConfig = new TableConfig();
    /**
     * mapper xml 配置
     */
    private MapperXmlConfig mapperXmlConfig = new MapperXmlConfig();
    /**
     * mapper 配置
     */
    private MapperConfig mapperConfig = new MapperConfig();
    /**
     * 实体类配置
     */
    private EntityConfig entityConfig = new EntityConfig();
    /**
     * Dao 配置
     */
    private DaoConfig daoConfig = new DaoConfig();
    /**
     * Dao 实现类配置
     */
    private DaoImplConfig daoImplConfig = new DaoImplConfig();
    /**
     * Service 配置
     */
    private ServiceConfig serviceConfig = new ServiceConfig();
    /**
     * Service 实现类配置
     */
    private ServiceImplConfig serviceImplConfig = new ServiceImplConfig();
    /**
     * Action 实现类配置
     */
    private ActionConfig actionConfig = new ActionConfig();
    private String charset = "utf-8";

    private ContainerType containerType = ContainerType.SPRING;
    /**
     * 是否忽略试图
     */
    private boolean ignoreView = false;
    /**
     * 完成后是否打开目录
     */
    private boolean finishOpen = false;

    /**
     * 根文件路径 默认取 System.getProperty("user.dir") +"/generate"
     */
    private String baseFilePath = System.getProperty("user.dir") + "/generate";

    /**
     * 基于baseFilePath的resource文件相对路径
     */
    private String resourcePath;

    /**
     * 基于baseFilePath的java源码文件相对路径
     */
    private String javaPath;
    /**
     * 根包路径
     */
    private String basePackage;
    /**
     * 模板根目录
     */
    private String templateRootPath = "templates";
    /**
     * 作者
     */
    private String author;

    /**
     * swagger版本 1：代表 1.x ，2：代表2.x， 3 代表3.x
     * 默认 3
     */
    private int swaggerVersion = 3;

    /**
     * 文件覆盖
     */
    private boolean fileCover = true;

    /**
     * 模板引擎
     */
    private TemplateEngine templateEngine;

    {
        templateBuilders.add(EntityTemplateBuilder.class);
        templateBuilders.add(MapperTemplateBuilder.class);
        templateBuilders.add(MapperXmlTemplateBuilder.class);
        templateBuilders.add(DaoTemplateBuilder.class);
        templateBuilders.add(DaoImplTemplateBuilder.class);
        templateBuilders.add(ServiceTemplateBuilder.class);
        templateBuilders.add(ServiceImplTemplateBuilder.class);
        templateBuilders.add(ActionTemplateBuilder.class);
    }

    public GeneratorConfig(String jdbcUrl, String username, String password) {
        this.dataBaseConfig = new DataBaseConfig(jdbcUrl, username, password);
    }

    public GeneratorConfig(DbType dbType, DataSource dataSource) {
        this.dataBaseConfig = new DataBaseConfig(dbType, dataSource);
    }

    /**
     * 设置文件编码
     *
     * @param charset
     */
    public GeneratorConfig charset(String charset) {
        this.charset = charset;
        return this;
    }

    /**
     * 容器类型
     *
     * @param containerType
     * @return
     */
    public GeneratorConfig containerType(ContainerType containerType) {
        this.containerType = containerType;
        return this;
    }

    public GeneratorConfig fileCover(boolean cover) {
        this.fileCover = cover;
        return this;
    }

    /**
     * 数据库配置
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig dataBaseConfig(Consumer<DataBaseConfig> consumer) {
        consumer.accept(this.dataBaseConfig);
        return this;
    }

    /**
     * 设置模板引擎
     *
     * @param templateEngine
     * @return
     */
    public GeneratorConfig templateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        return this;
    }

    /**
     * 设置模板的根目录 默认：templates
     *
     * @param templateRootPath
     * @return
     */
    public GeneratorConfig templateRootPath(String templateRootPath) {
        this.templateRootPath = templateRootPath;
        return this;
    }

    /**
     * 设置 模板生成器
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig templateBuilders(Consumer<List<Class<? extends ITemplateBuilder>>> consumer) {
        consumer.accept(this.templateBuilders);
        return this;
    }

    /**
     * 设置文件生成目标目录 默认： System.getProperty("user.dir") +"/generate"
     *
     * @param baseFilePath
     * @return
     */
    public GeneratorConfig baseFilePath(String baseFilePath) {
        this.baseFilePath = baseFilePath;
        return this;
    }

    /**
     * 基于baseFilePath的resource文件相对路径
     *
     * @param resourcePath
     * @return
     */
    public GeneratorConfig resourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
        return this;
    }

    /**
     * 基于baseFilePath的java源码文件相对路径
     *
     * @param javaPath
     * @return
     */
    public GeneratorConfig javaPath(String javaPath) {
        this.javaPath = javaPath;
        return this;
    }

    /**
     * 设置 基础包路径
     *
     * @param basePackage
     * @return
     */
    public GeneratorConfig basePackage(String basePackage) {
        this.basePackage = basePackage;
        return this;
    }

    /**
     * 设置作者
     *
     * @param author
     * @return
     */
    public GeneratorConfig author(String author) {
        this.author = author;
        return this;
    }

    /**
     * 设置是否忽略试图
     *
     * @param ignoreView
     * @return
     */
    public GeneratorConfig ignoreView(boolean ignoreView) {
        this.ignoreView = ignoreView;
        return this;
    }


    /**
     * 设置表的配置
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig tableConfig(Consumer<TableConfig> consumer) {
        consumer.accept(this.tableConfig);
        return this;
    }

    /**
     * 设置列的配置
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig columnConfig(Consumer<ColumnConfig> consumer) {
        consumer.accept(this.columnConfig);
        return this;
    }

    /**
     * 设置实体类的配置
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig entityConfig(Consumer<EntityConfig> consumer) {
        consumer.accept(this.entityConfig);
        return this;
    }


    /**
     * 设置mapper配置
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig mapperConfig(Consumer<MapperConfig> consumer) {
        consumer.accept(this.mapperConfig);
        return this;
    }

    /**
     * 设置mapper配置
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig mapperXmlConfig(Consumer<MapperXmlConfig> consumer) {
        consumer.accept(this.mapperXmlConfig);
        return this;
    }

    /**
     * 设置dao配置
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig daoConfig(Consumer<DaoConfig> consumer) {
        consumer.accept(this.daoConfig);
        return this;
    }

    /**
     * 设置dao 实现类的配置
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig daoImplConfig(Consumer<DaoImplConfig> consumer) {
        consumer.accept(this.daoImplConfig);
        return this;
    }

    /**
     * 设置service配置
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig serviceConfig(Consumer<ServiceConfig> consumer) {
        consumer.accept(this.serviceConfig);
        return this;
    }

    /**
     * 设置 service 实现类配置
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig serviceImplConfig(Consumer<ServiceImplConfig> consumer) {
        consumer.accept(this.serviceImplConfig);
        return this;
    }

    /**
     * 设置 控制器的配置
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig actionConfig(Consumer<ActionConfig> consumer) {
        consumer.accept(this.actionConfig);
        return this;
    }

    /**
     * 完成后打开
     */
    public GeneratorConfig finishOpen(boolean finishOpen) {
        this.finishOpen = finishOpen;
        return this;
    }

    /**
     * 完成后打开
     */
    public GeneratorConfig swaggerVersion(int swaggerVersion) {
        this.swaggerVersion = swaggerVersion;
        return this;
    }

    public void setDataBaseConfig(DataBaseConfig dataBaseConfig) {
        this.dataBaseConfig = dataBaseConfig;
    }

    public void setColumnConfig(ColumnConfig columnConfig) {
        this.columnConfig = columnConfig;
    }

    public void setTableConfig(TableConfig tableConfig) {
        this.tableConfig = tableConfig;
    }

    public void setEntityConfig(EntityConfig entityConfig) {
        this.entityConfig = entityConfig;
    }

    public void setMapperXmlConfig(MapperXmlConfig mapperXmlConfig) {
        this.mapperXmlConfig = mapperXmlConfig;
    }

    public void setMapperConfig(MapperConfig mapperConfig) {
        this.mapperConfig = mapperConfig;
    }

    public void setDaoConfig(DaoConfig daoConfig) {
        this.daoConfig = daoConfig;
    }

    public void setDaoImplConfig(DaoImplConfig daoImplConfig) {
        this.daoImplConfig = daoImplConfig;
    }

    public void setServiceConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    public void setServiceImplConfig(ServiceImplConfig serviceImplConfig) {
        this.serviceImplConfig = serviceImplConfig;
    }

    public void setActionConfig(ActionConfig actionConfig) {
        this.actionConfig = actionConfig;
    }

}

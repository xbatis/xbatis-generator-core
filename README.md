# 代码生成器
```
该模块从mybatis-mp独立而出，已经历多次迭代，可放心使用！！！
``` 

## maven引入

```xml
<dependency>
    <groupId>cn.mybatis-mp</groupId>
    <artifactId>mybatis-mp-generator-core</artifactId>
    <version>1.0.3</version>
</dependency>
```

## 添加数据库驱动 例如：

```xml

<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.0.32</version>
</dependency>
```

## 然后，编写一个任意带有 main 方法的类，如下所示

```java
// 根据数据库链接生成
new FastGenerator(new GeneratorConfig(
        "jdbc:mysql://xxx.xx.x:3306/数据库名字",
        "用户名",
        "密码")
        .basePackage("com.test")//根包路径
        ).create();

        or
        
//根据数据源生成
new FastGenerator(new GeneratorConfig(
        DbType.H2,//数据库类型
        dataSource)
        .basePackage("com.test")//根包路径
        ).create();

```

## 配置 GeneratorConfig

<table>
    <tr align="center">
        <th>属性</th>
        <th>默认值</th>
        <th align="left">说明</th>
    </tr>
    <tr align="center">
        <td>charset</td>
        <td>utf-8</td>
        <td align="left">生成文件的字符集</td>
    </tr>
    <tr align="center">
        <td>containerType</td>
        <td>SPRING</td>
        <td align="left">
            <p>容器类型，默认SPRING</p>
            <p>目前支持，SPRING、SOLON</p>
        </td>
    </tr>
    <tr align="center">
        <td>swaggerVersion</td>
        <td>3</td>
        <td align="left">swagger版本：2 代表2.x，3代表3.x</td>
    </tr>
    <tr align="center">
        <td>author</td>
        <td>""</td>
        <td align="left">作者</td>
    </tr>
    <tr align="center">
        <td>fileCover</td>
        <td>true</td>
        <td align="left">文件是否覆盖</td>
    </tr>
    <tr align="center">
        <td>ignoreView</td>
        <td>false</td>
        <td align="left">是否忽略视图</td>
    </tr>
    <tr align="center">
        <td>ignoreTable</td>
        <td>false</td>
        <td align="left">是否忽略表</td>
    </tr>
    <tr align="center">
        <td>baseFilePath</td>
        <td>System.getProperty("user.dir") + "/demo-generate"</td>
        <td align="left">根文件路径</td>
    </tr>
    <tr align="center">
        <td>basePackage</td>
        <td>NULL</td>
        <td align="left">根包路径</td>
    </tr>
    <tr align="center">
        <td>javaPath</td>
        <td>NULL</td>
        <td align="left">基于baseFilePath的java源码文件相对路径</td>
    </tr>
    <tr align="center">
        <td>resourcePath</td>
        <td>NULL</td>
        <td align="left">基于baseFilePath的resource文件相对路径</td>
    </tr>
    <tr align="center">
        <td>templateRootPath</td>
        <td>templates</td>
        <td align="left">模板根目录，默认即可</td>
    </tr>
    <tr align="center">
        <td>templateEngine</td>
        <td>new FreemarkerTemplateEngine()</td>
        <td align="left">模板引擎，默认Freemarker引擎，其他引擎需要自己实现</td>
    </tr>
    <tr align="center">
        <td>templateBuilders</td>
        <td>包含 实体类，mapper,mapper xml,dao,service,serviceImpl,action等模板生成构建器</td>
        <td align="left">模板生成构建器，继承AbstractTemplateBuilder，即可实现自己的生成器（生成自己的页面或其他类等）</td>
    </tr>
</table>

## 配置 TableConfig(表配置)

```java
new GeneratorConfig(...).tableConfig(tableConfig->{
    tableConfig.includeTables("table1","table2");
});
```

<table>
    <tr align="center">
        <th>属性</th>
        <th>默认值</th>
        <th align="left">说明</th>
    </tr>
    <tr align="center">
        <td>tablePrefixes</td>
        <td>空</td>
        <td align="left">表、视图的前缀，用于生成类名时忽略前缀</td>
    </tr>
    <tr align="center">
        <td>includeTables</td>
        <td>空</td>
        <td align="left">默认包含所有表、视图</td>
    </tr>
    <tr align="center">
        <td>excludeTables</td>
        <td>空</td>
        <td align="left">排除表，默认不排除</td>
    </tr>
</table>

## 配置 ColumnConfig(列配置)

```java
new GeneratorConfig(...).columnConfig(columnConfig->{
    
});
```

<table>
    <tr align="center">
        <th>属性</th>
        <th>默认值</th>
        <th align="left">说明</th>
    </tr>
    <tr align="center">
        <td>columnPrefixes</td>
        <td>空</td>
        <td align="left">列前缀，可进行列字段忽略前缀</td>
    </tr>
    <tr align="center">
        <td>versionColumn</td>
        <td>空</td>
        <td align="left">指定乐观锁列名</td>
    </tr>
    <tr align="center">
        <td>tenantIdColumn</td>
        <td>空</td>
        <td align="left">指定租户ID列名</td>
    </tr>
    <tr align="center">
        <td>logicDeleteColumn</td>
        <td>空</td>
        <td align="left">逻辑删除列名，配置实体类配置：logicDeleteCode 一起使用</td>
    </tr>
    <tr align="center">
        <td>disableUpdateColumns</td>
        <td>空</td>
        <td align="left">禁止更新的列,这样字段上会生成<strong>@TableField(update=false)</strong></td>
    </tr>
    <tr align="center">
        <td>disableSelectColumns</td>
        <td>空</td>
        <td align="left">禁止Select的列,这样字段上会生成<strong>@TableField(select=false)</strong></td>
    </tr>
    <tr align="center">
        <td>defaultValueConvert</td>
        <td>默认实现</td>
        <td align="left">可动态转换数据库的默认值（由静态值转成动态值）</td>
    </tr>
</table>

## 配置 EntityConfig(实体类配置)

```java
new GeneratorConfig(...).entityConfig(entityConfig->{
    entityConfig.lombok(true).excludeColumns("create_time","creater_id");;
});
```

<table>
    <tr align="center">
        <th>属性</th>
        <th>默认值</th>
        <th align="left">说明</th>
    </tr>
    <tr align="center">
        <td>excludeColumns</td>
        <td>空</td>
        <td align="left">排除列，默认不排除<strong>（在有公共实体类的时候很实用）</strong></td>
    </tr>
    <tr align="center">
        <td>swagger</td>
        <td>false</td>
        <td align="left">是否开启swagger</td>
    </tr>
    <tr align="center">
        <td>serial</td>
        <td>false</td>
        <td align="left">是否序列化，会implements Serializable</td>
    </tr>
    <tr align="center">
        <td>superClass</td>
        <td>NULL</td>
        <td align="left">实体类的父类，例如：com.xx.test.BaseEntity</td>
    </tr>
    <tr align="center">
        <td>lombok</td>
        <td>true</td>
        <td align="left">是否开启lombok，这样类上会生成<strong>@Data</strong></td>
    </tr>
    <tr align="center">
        <td>lombokBuilder</td>
        <td>false</td>
        <td align="left">是否开启lombok buidler，这样类上会生成<strong>@Buidler</strong></td>
    </tr>
    <tr align="center">
        <td>defaultValueEnable</td>
        <td>true</td>
        <td align="left">是否生成默认值</td>
    </tr>
    <tr align="center">
        <td>schema</td>
        <td>false</td>
        <td align="left">注解上是否加上schema信息</td>
    </tr>
    <tr align="center">
        <td>packageName</td>
        <td>DO</td>
        <td align="left">实体类包名</td>
    </tr>
    <tr align="center">
        <td>nameConvert</td>
        <td>NULL</td>
        <td align="left">实体类名转换器，可以自定义规则,默认大驼峰规则</td>
    </tr>
    <tr align="center">
        <td>fieldNamingStrategy</td>
        <td>NamingStrategy.UNDERLINE_TO_CAMEL</td>
        <td align="left">字段名策略，支持 NO_CHANGE ，UNDERLINE_TO_CAMEL </td>
    </tr>
    <tr align="center">
        <td>fieldNameConverter</td>
        <td>NULL</td>
        <td align="left">字段名转换器，优先级大于 fieldNamingStrategy</td>
    </tr>
    <tr align="center">
        <td>remarksConverter</td>
        <td>NULL</td>
        <td align="left">字段备注转换器，用于实现不一样的备注</td>
    </tr>
    <tr align="center">
        <td>defaultTableIdCode</td>
        <td>NULL</td>
        <td align="left">默认TableId代码，数据库非自增时生效,例如@TableId(...)</td>
    </tr>
    <tr align="center">
        <td>logicDeleteCode</td>
        <td>NULL</td>
        <td align="left">默认@LogicDelete代码，数据库非自增时生效,例如@LogicDelete(beforeValue="0",afterValue="1",deleteTimeField="create_time")</td>
    </tr>
    <tr align="center">
        <td>typeMapping</td>
        <td>内置包含各种列类型的java映射</td>
        <td align="left">数据库列类型映射，用于定制</td>
    </tr>
    <tr align="center">
        <td>alwaysAnnotation</td>
        <td>false</td>
        <td align="left">是否总是生成注解</td>
    </tr>
</table>

## 配置 MapperConfig(mapper类配置)

```java
new GeneratorConfig(...).mapperConfig(mapperConfig->{
    mapperConfig.mapperAnnotation(true);
});
```

<table>
    <tr align="center">
        <th>属性</th>
        <th>默认值</th>
        <th align="left">说明</th>
    </tr>
    <tr align="center">
        <td>superClass</td>
        <td>默认继承 MybatisMapper 接口</td>
        <td align="left">Mapper接口的父接口，例如：cn.mybatis.mp.core.mybatis.mapper.MybatisMapper</td>
    </tr>
    <tr align="center">
        <td>mapperAnnotation</td>
        <td>true</td>
        <td align="left">是否开启mybatis @Mapper注解，这样类上会生成<strong>@Mapper</strong></td>
    </tr>
    <tr align="center">
        <td>packageName</td>
        <td>mapper</td>
        <td align="left">mapper类的包名</td>
    </tr>
    <tr align="center">
        <td>suffix</td>
        <td>Mapper</td>
        <td align="left">mapper类的后缀</td>
    </tr>
</table>

## 配置 MapperXmlConfig(mapper xml配置)

```java
new GeneratorConfig(...).mapperXmlConfig(mapperXmlConfig->{
    mapperXmlConfig.enable(true);
});
```

<table>
    <tr align="center">
        <th>属性</th>
        <th>默认值</th>
        <th align="left">说明</th>
    </tr>
    <tr align="center">
        <td>enable</td>
        <td>false</td>
        <td align="left">是否生成mapper xml</td>
    </tr>
    <tr align="center">
        <td>resultMap</td>
        <td>false</td>
        <td align="left">是否生成resultMap</td>
    </tr>
    <tr align="center">
        <td>columnList</td>
        <td>false</td>
        <td align="left">是否生成列信息，用于select 列</td>
    </tr>
    <tr align="center">
        <td>packageName</td>
        <td>mappers</td>
        <td align="left">mapper xml的目录名字</td>
    </tr>
    <tr align="center">
        <td>suffix</td>
        <td>""</td>
        <td align="left">mapper xml文件的后缀</td>
    </tr>
</table>

## 配置 DaoConfig(dao接口配置)

```java
new GeneratorConfig(...).daoConfig(daoConfig->{
    daoConfig.enable(true);
});
```

<table>
    <tr align="center">
        <th>属性</th>
        <th>默认值</th>
        <th align="left">说明</th>
    </tr>
    <tr align="center">
        <td>enable</td>
        <td>true</td>
        <td align="left">是否生成 dao 接口</td>
    </tr>
    <tr align="center">
        <td>superClass</td>
        <td>默认继承 Dao 接口</td>
        <td align="left">dao接口的父接口，例如：cn.mybatis.mp.core.mvc.Dao</td>
    </tr>
    <tr align="center">
        <td>generic</td>
        <td>true</td>
        <td align="left">是否启用泛型，启用后会在superclass后面加泛型&gt;Entity,ID></td>
    </tr>
    <tr align="center">
        <td>packageName</td>
        <td>dao</td>
        <td align="left">dao接口的包名</td>
    </tr>
    <tr align="center">
        <td>suffix</td>
        <td>Dao</td>
        <td align="left">dao接口的后缀</td>
    </tr>
</table>

## 配置 DaoImplConfig(dao接口实现类的配置)

```java
new GeneratorConfig(...).daoImplConfig(daoImplConfig->{
    daoImplConfig.enable(true);
});
```

<table>
    <tr align="center">
        <th>属性</th>
        <th>默认值</th>
        <th align="left">说明</th>
    </tr>
    <tr align="center">
        <td>superClass</td>
        <td>默认继承 DaoImpl 实现类</td>
        <td align="left">dao接口的父接口，例如：cn.mybatis.mp.core.mvc.impl.DaoImpl</td>
    </tr>
   <tr align="center">
        <td>enable</td>
        <td>true</td>
        <td align="left">是否生成 dao impl接口</td>
    </tr>
    <tr align="center">
        <td>generic</td>
        <td>true</td>
        <td align="left">是否启用泛型，启用后会在superclass后面加泛型&gt;Entity,ID></td>
    </tr>
    <tr align="center">
        <td>packageName</td>
        <td>dao.impl</td>
        <td align="left">dao实现类的包名</td>
    </tr>
    <tr align="center">
        <td>suffix</td>
        <td>DaoImpl</td>
        <td align="left">dao实现类的后缀</td>
    </tr>
</table>

## 配置 ServiceConfig(service接口配置)

```java
new GeneratorConfig(...).serviceConfig(serviceConfig->{
    serviceConfig.enable(true);
});
```

<table>
    <tr align="center">
        <th>属性</th>
        <th>默认值</th>
        <th align="left">说明</th>
    </tr>
    <tr align="center">
        <td>enable</td>
        <td>true</td>
        <td align="left">是否生成 Service 接口</td>
    </tr>
    <tr align="center">
        <td>superClass</td>
        <td>默认继承 Service 接口</td>
        <td align="left">Service接口的父接口，例如：cn.mybatis.mp.core.mvc.Service</td>
    </tr>
    <tr align="center">
        <td>generic</td>
        <td>false</td>
        <td align="left">是否启用泛型，启用后会在superclass后面加泛型&gt;Entity,ID></td>
    </tr>
    <tr align="center">
        <td>packageName</td>
        <td>service</td>
        <td align="left">Service接口的包名</td>
    </tr>
    <tr align="center">
        <td>suffix</td>
        <td>Service</td>
        <td align="left">Service接口的后缀</td>
    </tr>
</table>

## 配置 ServiceImplConfig(service接口实现类的配置)

```java
new GeneratorConfig(...).serviceImplConfig(serviceImplConfig->{
    serviceImplConfig.injectDao(true);
});
```

<table>
    <tr align="center">
        <th>属性</th>
        <th>默认值</th>
        <th align="left">说明</th>
    </tr>
    <tr align="center">
        <td>enable</td>
        <td>true</td>
        <td align="left">是否生成 dao impl 接口</td>
    </tr>
    <tr align="center">
        <td>injectDao</td>
        <td>true</td>
        <td align="left">是否注入dao</td>
    </tr>
    <tr align="center">
        <td>injectMapper</td>
        <td>true</td>
        <td align="left">是否注入mapper</td>
    </tr>
    <tr align="center">
        <td>superClass</td>
        <td>默认继承 ServiceImpl 实现类</td>
        <td align="left">dao接口的父接口，例如：cn.mybatis.mp.core.mvc.impl.ServiceImpl</td>
    </tr>
    <tr align="center">
        <td>generic</td>
        <td>false</td>
        <td align="left">是否启用泛型，启用后会在superclass后面加泛型&gt;Entity,ID></td>
    </tr>
    <tr align="center">
        <td>packageName</td>
        <td>service.impl</td>
        <td align="left">service实现类的包名</td>
    </tr>
    <tr align="center">
        <td>suffix</td>
        <td>ServiceImpl</td>
        <td align="left">service实现类的后缀</td>
    </tr>
</table>

## 配置 ActionConfig(action实现类的配置)

```java
new GeneratorConfig(...).actionConfig(actionConfig->{
    actionConfig.enable(true);
});
```

<table>
    <tr align="center">
        <th>属性</th>
        <th>默认值</th>
        <th align="left">说明</th>
    </tr>
    <tr align="center">
        <td>enable</td>
        <td>true</td>
        <td align="left">是否生成控制器</td>
    </tr>
    <tr align="center">
        <td>swagger</td>
        <td>true</td>
        <td align="left">是否开启swagger</td>
    </tr>
    <tr align="center">
        <td>injectService</td>
        <td>true</td>
        <td align="left">是否注入service</td>
    </tr>
    <tr align="center">
        <td>superClass</td>
        <td>NULL</td>
        <td align="left">action父类，例如：cn.xxx.BaseAction</td>
    </tr>
    <tr align="center">
        <td>generic</td>
        <td>false</td>
        <td align="left">是否启用泛型，启用后会在superclass后面加泛型&gt;Entity,ID></td>
    </tr>
    <tr align="center">
        <td>packageName</td>
        <td>action</td>
        <td align="left">action实现类的包名</td>
    </tr>
    <tr align="center">
        <td>suffix</td>
        <td>Action</td>
        <td align="left">action实现类的后缀</td>
    </tr>
    <tr align="center">
        <td>returnClass</td>
        <td>Object</td>
        <td align="left">get save update delete find等返回的类型</td>
    </tr>
    <tr align="center">
        <td>enableSave</td>
        <td>true</td>
        <td align="left">是否生成save方法</td>
    </tr>
    <tr align="center">
        <td>enableUpdate</td>
        <td>true</td>
        <td align="left">是否生成update方法</td>
    </tr>
    <tr align="center">
        <td>enableDelete</td>
        <td>true</td>
        <td align="left">是否生成delete方法</td>
    </tr>
    <tr align="center">
        <td>enableGet</td>
        <td>true</td>
        <td align="left">是否生成get方法</td>
    </tr>
    <tr align="center">
        <td>enableFind</td>
        <td>true</td>
        <td align="left">是否生成find方法</td>
    </tr>
</table>
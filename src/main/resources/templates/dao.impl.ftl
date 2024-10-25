package ${entityInfo.daoImplPackage};

<#list imports as pkg>
import ${pkg};
</#list>

/**
 * <p>
 * ${entityInfo.tableInfo.remarks!} Dao 实现类
 * </p>
 *
 * @author ${author!}
 * @since ${date}
 */
@${repositoryAnnotationName}
public class ${entityInfo.buildDaoImplClassFullName(daoConfig , daoImplConfig)} {

<#if containerType.is("solon")>
    @${autowiredAnnotationName}
    private ${entityInfo.mapperName} ${util.firstToLower(entityInfo.mapperName)};

    @Init
    public void init(){
        this.setMapper(${util.firstToLower(entityInfo.mapperName)});
    }

    @Override
    protected ${entityInfo.mapperName} getMapper(){
        return this.${util.firstToLower(entityInfo.mapperName)};
    }
<#else>
    @${autowiredAnnotationName}
    public ${entityInfo.daoImplName} (${entityInfo.mapperName} ${util.firstToLower(entityInfo.mapperName)}){
        super(${util.firstToLower(entityInfo.mapperName)});
    }

    @Override
    protected ${entityInfo.mapperName} getMapper(){
        return (${entityInfo.mapperName}) this.mapper;
    }
</#if>

<#if entityInfo.hasMultiId()>
    @Override
    public ${entityInfo.name} getById(<#list entityInfo.idFieldInfoList as field>${field.typeName} ${field.name}<#if field_has_next>, </#if></#list>){
        return getMapper().getById(<#list entityInfo.idFieldInfoList as field>${field.name}<#if field_has_next>, </#if></#list>);
    }

    protected int deleteById(<#list entityInfo.idFieldInfoList as field>${field.typeName} ${field.name}<#if field_has_next>, </#if></#list>){
        return getMapper().deleteById(<#list entityInfo.idFieldInfoList as field>${field.name}<#if field_has_next>, </#if></#list>);
    }
</#if>
}

package ${entityInfo.serviceImplPackage};

<#list imports as pkg>
import ${pkg};
</#list>

/**
 * <p>
 * ${entityInfo.tableInfo.remarks!} Service 实现类
 * </p>
 *
 * @author ${author!}
 * @since ${date}
 */
@${serviceAnnotationName}
public class ${entityInfo.buildServiceImplClassFullName(serviceConfig,serviceImplConfig)} {

<#if serviceImplConfig.isInjectDao(generatorConfig)>
    @${autowiredAnnotationName}
    private ${serviceImplConfig.injectDaoClassName(generatorConfig,entityInfo)} ${util.firstToLower(serviceImplConfig.injectDaoClassName(generatorConfig,entityInfo))};

    protected ${serviceImplConfig.injectDaoClassName(generatorConfig,entityInfo)} getDao() {
        return ${util.firstToLower(serviceImplConfig.injectDaoClassName(generatorConfig,entityInfo))};
    }
    
</#if>
<#if !serviceImplConfig.isInjectDao(generatorConfig) && serviceImplConfig.isInjectMapper()>
    @${autowiredAnnotationName}
    private ${serviceImplConfig.mapperClassName(entityInfo)} ${util.firstToLower(serviceImplConfig.mapperClassName(entityInfo))};

    protected ${entityInfo.mapperName} getMapper(){
        return this.${util.firstToLower(entityInfo.mapperName)};
    }

    <#if serviceImplConfig.isGenChainMethod()>
    private QueryChain<${entityInfo.name}> queryChain() {
        return QueryChain.of(${util.firstToLower(serviceImplConfig.mapperClassName(entityInfo))}<#if !mapperConfig.isEnable()> , ${entityInfo.name}.class</#if>);
    }

    private UpdateChain updateChain() {
        return UpdateChain.of(${util.firstToLower(serviceImplConfig.mapperClassName(entityInfo))}<#if !mapperConfig.isEnable()> , ${entityInfo.name}.class</#if>);
    }

    private InsertChain insertChain(){
        return InsertChain.of(${util.firstToLower(serviceImplConfig.mapperClassName(entityInfo))}<#if !mapperConfig.isEnable()> , ${entityInfo.name}.class</#if>);
    }

    private DeleteChain deleteChain(){
        return DeleteChain.of(${util.firstToLower(serviceImplConfig.mapperClassName(entityInfo))}<#if !mapperConfig.isEnable()> , ${entityInfo.name}.class</#if>);
    }

    </#if>
</#if>
}

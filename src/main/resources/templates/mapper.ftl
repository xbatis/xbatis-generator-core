package ${entityInfo.mapperPackage};

<#list imports as pkg>
import ${pkg};
</#list>

/**
 * <p>
 * ${entityInfo.tableInfo.remarks!} Mapper 接口
 * </p>
 *
 * @author ${author!}
 * @since ${date}
 */
<#if mapperConfig.isMapperAnnotation()>
@Mapper
</#if>
public interface ${entityInfo.buildMapperClassFullName(mapperConfig)} {
<#if entityInfo.hasMultiId()>
    default ${entityInfo.name} getById(<#list entityInfo.idFieldInfoList as field>${field.typeName} ${field.name}<#if field_has_next>, </#if></#list>){
        return this.get(where -> where<#list entityInfo.idFieldInfoList as field>.eq(${entityInfo.name}::${field.getterMethodName()}, ${field.name})</#list>);
    }

    default int deleteById(<#list entityInfo.idFieldInfoList as field>${field.typeName} ${field.name}<#if field_has_next>, </#if></#list>){
        return this.delete(where -> where<#list entityInfo.idFieldInfoList as field>.eq(${entityInfo.name}::${field.getterMethodName()}, ${field.name})</#list>);
    }
</#if>
}

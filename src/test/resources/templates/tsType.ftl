export interface ${entityInfo.name} {
<#list entityInfo.fieldInfoList as field>
    ${field.name}?: ${javaToTsType.convert(field.typeName)}
</#list>
}
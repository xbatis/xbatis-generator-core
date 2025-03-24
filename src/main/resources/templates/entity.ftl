package ${entityInfo.entityPackage};

<#list imports as pkg>
import ${pkg};
</#list>
<#if entityConfig.isSwagger()>
    <#if generatorConfig.getSwaggerVersion() == 3>
import io.swagger.v3.oas.annotations.media.Schema;
    <#else>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
    </#if>
</#if>

<#if entityConfig.isComment()>
/**
<#if entityInfo.tableInfo.remarks?? && entityInfo.tableInfo.remarks != "">
 * <p>
 * ${entityInfo.tableInfo.remarks!}
 * </p>
</#if>
 *
 * @author ${author!}
 * @since ${date}
 */
</#if>
<#if entityConfig.isLombok()>
@Data
<#if entityConfig.isLombokBuilder()>
    <#if entityConfig.hasSuperClass()>
@SuperBuilder
    <#else>
@Builder
    </#if>
@NoArgsConstructor
@AllArgsConstructor
</#if>
<#if entityConfig.isCreateFieldClass()>
@FieldNameConstants
</#if>
<#if entityConfig.hasSuperClass()>
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
</#if>
</#if>
<#if entityConfig.isSwagger() && entityInfo.tableInfo.remarks?? && entityInfo.tableInfo.remarks != "">
<#if generatorConfig.getSwaggerVersion() == 3>
@Schema(name = "${entityInfo.tableInfo.remarks!}")
<#else>
@ApiModel("${entityInfo.tableInfo.remarks!}")
</#if>
</#if>
${entityInfo.buildTable(entityConfig)}
public class ${entityInfo.buildClassFullName(entityConfig)} {

<#if entityConfig.isSerial()>
    private static final long serialVersionUID = 1L;

</#if>
<#list entityInfo.fieldInfoList as field>
<#if entityConfig.isComment() && field.remarks?? && field.remarks != "">
    /**
     * ${field.remarks!}
     */
</#if>
<#if field.columnInfo.primaryKey>
<#if entityConfig.isSwagger() && field.remarks?? && field.remarks != "">
<#if generatorConfig.getSwaggerVersion() == 3>
    @Schema(description = "${field.remarks!}")
<#else>
    @ApiModelProperty("${field.remarks!}")
</#if>
</#if>
    ${field.buildTableIdCode()!}
    <#if field.isNeedTableField(entityConfig)>
    ${field.buildTableField(entityConfig)}
    private ${field.typeName} ${field.name};
    <#else>
    private ${field.typeName} ${field.name};
    </#if>
<#else>
<#if field.columnInfo.isVersion()>
    @Version
<#elseif  field.columnInfo.isTenantId()>
    @TenantId
<#elseif field.columnInfo.isLogicDelete()>
    ${entityConfig.getLogicDeleteCode()!}
</#if>
<#if entityConfig.isSwagger() && field.remarks?? && field.remarks != "">
<#if generatorConfig.getSwaggerVersion() == 3>
    @Schema(description = "${field.remarks!}")
<#else>
    @ApiModelProperty("${field.remarks!}")
</#if>
</#if>
    <#if field.isNeedTableField(entityConfig)>
    ${field.buildTableField(entityConfig)}
    private ${field.typeName} ${field.name};
<#else>
    private ${field.typeName} ${field.name};
</#if>
</#if>

</#list>
<#if entityConfig.isLombok() == false>
<#list entityInfo.fieldInfoList as field>
    public void ${field.setterMethodName()} (${field.typeName} ${field.name}) {
        this.${field.name} = ${field.name};
    }

    public ${field.typeName} ${field.getterMethodName()} () {
        return this.${field.name};
    }

</#list>
</#if>
<#if !entityConfig.isLombok()>
    @Override
    public String toString() {
        return "${entityInfo.name}{" +
    <#if entityConfig.hasSuperClass()>
        " super = " +super.toString() +
    </#if>
    <#list entityInfo.fieldInfoList as field>
        <#if field_index==0>
            <#if entityConfig.hasSuperClass()>", <#else >"</#if>${field.name} = " + ${field.name} +
        <#else>
            ", ${field.name} = " + ${field.name} +
        </#if>
    </#list>
        "}";
    }

</#if>
<#if !entityConfig.isLombok() && entityConfig.isCreateFieldClass()>
    public final static class Fields {
    <#list entityInfo.allFieldInfoList as field>
        public final static String ${field.name} = "${field.name}";
    </#list>
    }

</#if>
}

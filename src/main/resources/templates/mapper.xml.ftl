<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${entityInfo.mapperPackage}.${entityInfo.mapperName}">
<#if mapperXmlConfig.isResultMap()>

    <resultMap id="ResultMap" type="${entityInfo.entityPackage}.${entityInfo.name}">
        <!--@Table ${entityInfo.tableInfo.name} -->
<#list entityInfo.getAllFieldInfoList() as field>
    <#if field.columnInfo.primaryKey>
        <id column="${field.columnInfo.name}" property="${field.name}" />
    </#if>
</#list>
<#list entityInfo.getAllFieldInfoList() as field>
    <#if !field.columnInfo.primaryKey>
        <result column="${field.columnInfo.name}" property="${field.name}" />
    </#if>
</#list>
    </resultMap>
</#if>
<#if mapperXmlConfig.isColumnList()>

    <sql id="ColumnList">
        <!--@sql select -->
<#list entityInfo.getAllFieldInfoList() as field>
    <#if field_index==0>
        ${field.columnInfo.name}
    <#else>
        ,${field.columnInfo.name}
    </#if>
</#list>
        <!--@sql from  ${entityInfo.tableInfo.name}-->
    </sql>
</#if>
</mapper>

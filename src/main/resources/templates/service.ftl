package ${entityInfo.servicePackage};

<#list imports as pkg>
import ${pkg};
</#list>

/**
 * <p>
 * ${entityInfo.tableInfo.remarks!} Service 接口
 * </p>
 *
 * @author ${author!}
 * @since ${date}
 */
public interface ${entityInfo.buildServiceClassFullName(serviceConfig)} {

}

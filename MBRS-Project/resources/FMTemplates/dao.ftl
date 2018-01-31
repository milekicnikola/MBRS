package ${class.typePackage};
import framework.DaoGeneric;
<#list importedPackages as package>
import ${package}.*;
</#list>

${class.visibility} interface ${class.name}Dao extends DaoGeneric<${class.name}> {  

}
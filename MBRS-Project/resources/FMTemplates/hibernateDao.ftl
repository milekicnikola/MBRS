package ${class.typePackage};
import framework.HibernateDaoAbstract;
<#list importedPackages as package>
import ${package}.*;
</#list>

${class.visibility} class ${class.name}HibernateDao extends HibernateDaoAbstract<${class.name}> implements ${class.name}Dao{  

}

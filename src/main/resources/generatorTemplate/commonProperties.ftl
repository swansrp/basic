spring.profiles.active=dev
#=====================================  Common Config  ============================= 

<#list dbConfigMapPackageName?keys as db>
<#if db_index == 0> 
my.db.config.masterdb = ${r"${"}db.config.${dbConfigMapPackageName[db]}.name${r"}"}

</#if>
db.config.${dbConfigMapPackageName[db]}.name = ${db}
</#list>

${projectName}.portal.url = 


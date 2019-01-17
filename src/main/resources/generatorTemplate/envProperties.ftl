server.port = 8080
#===================================== Master RDS ==============================
<#list dbConfigMapPackageName?keys as db>
<#if db_index == 0>
my.db.config.username = ${r"${"}db.config.${dbConfigMapPackageName[db]}.username${r"}"}
my.db.config.password = ${r"${"}db.config.${dbConfigMapPackageName[db]}.password${r"}"}
my.db.config.driver = ${r"${"}db.config.${dbConfigMapPackageName[db]}.driver-class-name${r"}"}
my.db.config.url= ${r"${"}db.config.${dbConfigMapPackageName[db]}.ip${r"}"}
my.db.config.port = ${r"${"}db.config.${dbConfigMapPackageName[db]}.port${r"}"}
my.db.config.property = ${r"${"}db.config.${dbConfigMapPackageName[db]}.property${r"}"}

</#if>
#===================================== RDS (${db}) ==============================
<#if env == "-dev"> 
db.config.${dbConfigMapPackageName[db]}.ip = localhost
db.config.${dbConfigMapPackageName[db]}.port = 3306
db.config.${dbConfigMapPackageName[db]}.property = characterEncoding=utf8&useSSL=false

db.config.${dbConfigMapPackageName[db]}.url = jdbc:mysql://${r"${"}db.config.${dbConfigMapPackageName[db]}.ip${r"}"}:${r"${"}db.config.${dbConfigMapPackageName[db]}.port${r"}"}/${r"${"}db.config.${dbConfigMapPackageName[db]}.name${r"}"}?${r"${"}db.config.${dbConfigMapPackageName[db]}.property${r"}"}
db.config.${dbConfigMapPackageName[db]}.username = root
db.config.${dbConfigMapPackageName[db]}.password = 
db.config.${dbConfigMapPackageName[db]}.driver-class-name = com.mysql.jdbc.Driver

<#else>
db.config.${dbConfigMapPackageName[db]}.ip = 
db.config.${dbConfigMapPackageName[db]}.port = 3306
db.config.${dbConfigMapPackageName[db]}.property = characterEncoding=utf8&useSSL=false

db.config.${dbConfigMapPackageName[db]}.url = jdbc:mysql://${r"${"}db.config.${dbConfigMapPackageName[db]}.ip${r"}"}:${r"${"}db.config.${dbConfigMapPackageName[db]}.port${r"}"}/${r"${"}db.config.${dbConfigMapPackageName[db]}.name${r"}"}?${r"${"}db.config.${dbConfigMapPackageName[db]}.property${r"}"}
db.config.${dbConfigMapPackageName[db]}.username = 
db.config.${dbConfigMapPackageName[db]}.password = 
db.config.${dbConfigMapPackageName[db]}.driver-class-name = com.mysql.jdbc.Driver

</#if>
</#list>
#===================================== Redis ==============================
<#if env == "-dev"> 
spring.redis.host = Localhost
spring.redis.port = 6379
spring.redis.password = 
<#else>
spring.redis.host = 
spring.redis.port = 
spring.redis.password = 
</#if>


#=====================================  log  =============================  
<#if env != "-prod"> 
logging.config=classpath:logback-boot.xml
<#else>
logging.config=classpath:prd-logback-boot.xml
</#if>

#===================================  S2S config  ========================


<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.srct.service</groupId>
        <artifactId>${projectName}</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <artifactId>${projectName}${moduleName}</artifactId>
    <name>${projectName}${moduleName}</name>
    <description></description>

    <!--指定项目中公有的模块 -->
    <dependencies>
        <dependency>
            <groupId>${BASIC_PACKAGE}</groupId>
            <artifactId>SpringBootCommonLib</artifactId>
        </dependency>
        <dependency>
            <groupId>${BASIC_PACKAGE}</groupId>
            <artifactId>${projectName}Common</artifactId>
        </dependency>
    </dependencies>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <!--spring boot打包的话需要指定一个唯一的入门 -->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <!-- 指定该Main Class为全局的唯一入口 -->
                    <mainClass>${BASIC_PACKAGE}.${projectName}.${moduleName}.${applicationName}</mainClass>
                    <layout>ZIP</layout>
                    <classifier>exec</classifier>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal><!--可以把依赖的包都打包到生成的Jar包中 -->
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>build-info</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>pl.project13.maven</groupId>
                <artifactId>git-commit-id-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>revision</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <dotGitDirectory>${r"${project.basedir}"}/.git</dotGitDirectory>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>

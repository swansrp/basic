﻿<?xml version="1.0" encoding="UTF-8"?>
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
    </dependencies>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <!--spring boot打包的话需要指定一个唯一的入门 -->
    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
            </resource>
        </resources>
        <plugins>
            <plugin>
                <groupId>io.github.swagger2markup</groupId>
                <artifactId>swagger2markup-maven-plugin</artifactId>
                <version>1.3.1</version>
                <configuration>
                    <swaggerInput>http://localhost:${r"${project.port}"}/v2/api-docs</swaggerInput>
                    <outputDir>${r"${project.basedir}"}/docs/asciidoc/generated</outputDir>
                    <config>
                        <swagger2markup.markupLanguage>ASCIIDOC</swagger2markup.markupLanguage>
                    </config>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.asciidoctor</groupId>
                <artifactId>asciidoctor-maven-plugin</artifactId>
                <version>1.5.6</version>
                <configuration>
                    <sourceDirectory>${r"${project.basedir}"}/docs/asciidoc/generated</sourceDirectory>
                    <outputDirectory>${r"${project.basedir}"}/docs/asciidoc/html</outputDirectory>
                    <backend>html</backend>
                    <sourceHighlighter>coderay</sourceHighlighter>
                    <attributes>
                        <toc>left</toc>
                    </attributes>
                </configuration>
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

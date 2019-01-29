/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.plugin.mbg
 * @author: ruopeng.sha
 * @date: 2018-10-30 19:13
 */
package com.srct.plugin.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.srct.plugin.mbg.BaseData;
import com.srct.service.utils.StringUtil;
import com.srct.service.utils.log.Log;

/**
 * @ClassName: DatabaseUtil
 * @Description: TODO
 */
public class DatabaseUtil {

    public static final String DRIVER = "com.mysql.jdbc.Driver";

    public static final String BASEURL = "jdbc:mysql://localhost:3306/";

    public static final String PROFILE = "?characterEncoding=utf8&autoReconnect=true&useSSL=false";

    public static final String USERNAME = "root";

    public static final String PASSWORD = "";

    private static final String SQL = "SELECT * FROM ";
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            Log.e("can not load jdbc driver", e);
        }
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection(String dbName) {
        Connection conn = null;
        String URL = BASEURL + dbName + PROFILE;
        Properties props = new Properties();
        props.put("user", USERNAME);
        props.put("password", PASSWORD);
        props.put("useInformationSchema", "true"); // 表注释
        System.out.println("connect to " + URL);
        try {
            conn = DriverManager.getConnection(URL, props);
        } catch (SQLException e) {
            Log.e("get connection failure", e);
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     * 
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                Log.e("close connection failure", e);
            }
        }
    }

    /**
     * 获取表中字段名称
     * 
     * @param tableName
     *            表名
     * @return
     */
    public static List<String> getColumnNames(String db, String tableName) {
        List<String> columnNames = new ArrayList<>();
        // 与数据库的连接
        Connection conn = getConnection(db);
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        try {
            pStemt = conn.prepareStatement(tableSql);
            // 结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();
            // 表列数
            int size = rsmd.getColumnCount();
            for (int i = 0; i < size; i++) {
                columnNames.add(rsmd.getColumnName(i + 1));
            }
        } catch (SQLException e) {
            Log.e("getColumnNames failure", e);
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    Log.e("getColumnNames close pstem and connection failure", e);
                }
            }
        }
        return columnNames;
    }

    /**
     * 获取表中所有字段类型
     * 
     * @param tableName
     * @return
     */
    public static List<String> getColumnTypes(String db, String tableName) {
        List<String> columnTypes = new ArrayList<>();
        // 与数据库的连接
        Connection conn = getConnection(db);
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        try {
            pStemt = conn.prepareStatement(tableSql);
            // 结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();
            // 表列数
            int size = rsmd.getColumnCount();
            for (int i = 0; i < size; i++) {
                columnTypes.add(rsmd.getColumnTypeName(i + 1));
            }
        } catch (SQLException e) {
            Log.e("getColumnTypes failure", e);
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    Log.e("getColumnTypes close pstem and connection failure", e);
                }
            }
        }
        return columnTypes;
    }

    /**
     * 获取表中字段的注释
     * 
     * @param tableName
     * @return
     */
    public static List<String> getColumnComments(String db, String tableName) {
        List<String> columnTypes = new ArrayList<>();
        Connection conn = getConnection(db);
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        List<String> columnComments = new ArrayList<>();
        ResultSet rs = null;
        try {
            pStemt = conn.prepareStatement(tableSql);
            rs = pStemt.executeQuery("show full columns from " + tableName);
            while (rs.next()) {
                columnComments.add(rs.getString("Comment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    Log.e("getColumnComments close ResultSet and connection failure", e);
                }
            }
        }
        return columnComments;
    }

    /**
     * 获取表中字段的注释
     * 
     * @param tableName
     * @return
     */
    /**
     * 获取表中字段名称
     * 
     * @param tableName
     *            表名
     * @return
     */
    public static List<Map<String, Object>> getColumnData(String db, String tableName) {
        List<Map<String, Object>> columnData = new ArrayList<>();
        // 与数据库的连接
        Connection conn = getConnection(db);
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        try {
            pStemt = conn.prepareStatement(tableSql);
            ResultSet rs = pStemt.executeQuery(tableSql);
            while (rs.next()) {
            }
        } catch (SQLException e) {
            Log.e("getColumnNames failure", e);
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    Log.e("getColumnNames close pstem and connection failure", e);
                }
            }
        }
        return columnData;
    }

    public static List<String> getTableListFromDB(String dbName) {
        List<String> tableNameList = new ArrayList<String>();
        Connection conn = getConnection(dbName);
        try {
            DatabaseMetaData dbMetData = conn.getMetaData();
            // mysql convertDatabaseCharsetType null
            ResultSet rs = dbMetData.getTables(null, convertDatabaseCharsetType("root", "mysql"), null,
                new String[] {"TABLE", "VIEW"});
            while (rs.next()) {
                if (rs.getString(4) != null
                    && (rs.getString(4).equalsIgnoreCase("TABLE") || rs.getString(4).equalsIgnoreCase("VIEW"))) {
                    String tableName = rs.getString(3).toLowerCase();
                    tableNameList.add(tableName);
                }
            }
        } catch (SQLException e) {
            Log.e("getColumnNames failure", e);
        } finally {
            closeConnection(conn);
        }
        return tableNameList;
    }

    public static List<BaseData> getBaseDataListFromDB(String db, String dbName) {
        List<BaseData> list = new ArrayList<BaseData>();
        Connection conn = getConnection(db);
        try {
            DatabaseMetaData dbMetData = conn.getMetaData();
            // mysql convertDatabaseCharsetType null
            ResultSet rs = dbMetData.getTables(null, convertDatabaseCharsetType("root", "mysql"), null,
                new String[] {"TABLE", "VIEW"});
            while (rs.next()) {
                if (rs.getString(4) != null
                    && (rs.getString(4).equalsIgnoreCase("TABLE") || rs.getString(4).equalsIgnoreCase("VIEW"))) {
                    String tableName = rs.getString(3).toLowerCase();
                    System.out.print(tableName + "\t");
                    // 根据表名提前表里面信息：
                    ResultSet colRet = dbMetData.getColumns(null, "%", tableName, "%");
                    while (colRet.next()) {
                        String columnName = colRet.getString("COLUMN_NAME");
                        String columnType = colRet.getString("TYPE_NAME");
                        int datasize = colRet.getInt("COLUMN_SIZE");
                        int digits = colRet.getInt("DECIMAL_DIGITS");
                        int nullable = colRet.getInt("NULLABLE");
                        String comment = colRet.getString("REMARKS");
                        // System.out.println(columnName + " " + columnType + "
                        // " + datasize + " " +
                        // digits + " "
                        // + nullable + " " + comment);
                        BaseData baseData = new BaseData();
                        baseData.setTableName(StringUtil.firstUpperCamelCase(tableName));
                        baseData.setColumnComment(comment);
                        baseData.setColumnName(StringUtil.firstUpperCamelCase(columnName));
                        baseData.setColumnType(getJavaType(columnType));
                        list.add(baseData);
                    }
                }
            }
        } catch (SQLException e) {
            Log.e("getColumnNames failure", e);
        } finally {
            closeConnection(conn);
        }
        return list;
        // resultSet数据下标从1开始 ResultSet tableRet =
        // conn.getMetaData().getTables(null, null, "%", new String[] { "TABLE"
        // });
        // while (tableRet.next()) {
        // System.out.print(tableRet.getString(3) + "\t");
        // }
        // System.out.println();
    }

    public static String convertDatabaseCharsetType(String in, String type) {
        String dbUser;
        if (in != null) {
            if (type.equals("oracle")) {
                dbUser = in.toUpperCase();
            } else if (type.equals("postgresql")) {
                dbUser = "public";
            } else if (type.equals("mysql")) {
                dbUser = null;
            } else if (type.equals("mssqlserver")) {
                dbUser = null;
            } else if (type.equals("db2")) {
                dbUser = in.toUpperCase();
            } else {
                dbUser = in;
            }
        } else {
            dbUser = "public";
        }
        return dbUser;
    }

    public static List<BaseData> getBaseDataList(String db, String tableName) {
        List<String> columnNames = getColumnNames(db, tableName);
        List<String> columnTypes = getColumnTypes(db, tableName);
        List<String> columnComments = getColumnComments(db, tableName);
        List<BaseData> list = new ArrayList<BaseData>();
        for (int i = 0; i < columnNames.size(); i++) {
            BaseData baseData = new BaseData();
            baseData.setColumnName(StringUtil.firstLowerCamelCase(columnNames.get(i)));
            String columnType = getJavaType(columnTypes.get(i));
            baseData.setColumnType(columnType);
            baseData.setColumnComment(columnComments.get(i));
            list.add(baseData);
        }
        return list;
    }

    /**
     * @Title: getJavaType
     * @Description: TODO
     * @param columnTypes
     * @param i
     * @return String
     */
    private static String getJavaType(String sqlType) {
        String javaType = null;
        switch (sqlType) {
            case "VARCHAR":
            case "TEXT":
            case "CHAR":
            case "ENUM":
                javaType = "String";
                break;
            case "DATETIME":
            case "TIMESTAMP":
            case "TIME":
                javaType = "Date";
                break;
            case "DECIMAL":
                javaType = "BigDecimal";
                break;
            case "TINYINT UNSIGNED":
            case "TINYINT":
                javaType = "Byte";
                break;
            case "INT UNSIGNED":
            case "SMALLINT UNSIGNED":
            case "SMALLINT":
            case "INT":
                javaType = "Integer";
                break;
            case "BIGINT":
            case "BIGINT UNSIGNED":
                javaType = "Long";
                break;
            case "DOUBLE":
                javaType = "Double";
                break;
            default:
                javaType = "未知类型";
                System.out.println("==========存在不支持类型！请手写:" + sqlType);
                break;
        }
        return javaType;
    }
}

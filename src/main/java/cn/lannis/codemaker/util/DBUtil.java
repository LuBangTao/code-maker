package cn.lannis.codemaker.util;

import cn.lannis.codemaker.configure.CodeMakerConfig;
import cn.lannis.codemaker.vo.ColumnInfoVo;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>描述：数据库读取工具类</p>
 * <p>公司：Lannis©2021 All Rights Reserved</p>
 * <p>作者：鲁帮涛</p>
 * <p>日期：2020-12-02 13:52</p>
 * <p>版权：Lannis-2021</p>
 */
@Component
@Log4j2
public class DBUtil {
    private DBUtil(){}
    @Resource
    CodeMakerConfig codeMakerConfig;


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(codeMakerConfig.getUrl(), codeMakerConfig.getUsername(), codeMakerConfig.getPassword());
    }

    /**
     * <p>描述：获取表字段</p>
     * <p>公司：Lannis©2021 All Rights Reserved</p>
     * <p>作者：鲁帮涛</p>
     * <p>日期：2020-12-02 13:54</p>
     * <p>版权：Lannis-2021</p>
     */
    public List<ColumnInfoVo> readTableFields(String databaseName, String tableName) {
        List<ColumnInfoVo> columnInfoVos = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        try (Connection conn = this.getConnection(); Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            if ("mysql".equals(codeMakerConfig.getDatabaseType())) {
                sql.append("select COLUMN_NAME,DATA_TYPE,IS_NULLABLE,COLUMN_COMMENT,COLUMN_KEY,EXTRA,numeric_precision,numeric_scale from information_schema.columns where table_name = '")
                        .append(tableName).append("' and table_schema = '").append(databaseName).append("';");
            } else {
                return columnInfoVos;
            }
            ResultSet resultSet = statement.executeQuery(sql.toString());
            while (resultSet.next()) {
                ColumnInfoVo columnInfoVo = new ColumnInfoVo();
                columnInfoVo.setPrecision(TableConvert.getNullString(resultSet.getString(7)));
                columnInfoVo.setScale(TableConvert.getNullString(resultSet.getString(8)));
                columnInfoVo.setOriginColumnName(resultSet.getString(1).toLowerCase());
                if (codeMakerConfig.getUnderLineToCamel()) {
                    columnInfoVo.setColumnName(underLineToCamel(resultSet.getString(1).toLowerCase()));
                } else {
                    columnInfoVo.setColumnName(resultSet.getString(1).toLowerCase());
                }
                columnInfoVo.setDataDBType(underLineToCamel(resultSet.getString(2).toLowerCase()));
                columnInfoVo.setDataType(formatDataType(resultSet.getString(2).toLowerCase(), columnInfoVo.getPrecision(), columnInfoVo.getScale()));
                columnInfoVo.setNullable(resultSet.getString(3).toLowerCase());
                columnInfoVo.setColumnComment(resultSet.getString(4).toLowerCase());
                columnInfoVo.setColumnKey(resultSet.getString(5).toLowerCase());
                columnInfoVo.setExtra(resultSet.getString(6).toLowerCase());
                columnInfoVos.add(columnInfoVo);
            }
        } catch (Exception e) {
            log.error("查询表字段出错",e);
        }
        return columnInfoVos;
    }


    /**
     * <p>描述：下划线转驼峰</p>
     * <p>公司：Lannis©2021 All Rights Reserved</p>
     * <p>作者：鲁帮涛</p>
     * <p>日期：2020-12-02 15:16</p>
     * <p>版权：Lannis-2021</p>
     */
    private String underLineToCamel(String field){
        String[] strs = field.split("_");
        field = "";
        int m = 0;
        StringBuilder fieldBuilder = new StringBuilder(field);
        for (int length = strs.length; m < length; m++) {
            if (m > 0) {
                String tempStr = strs[m].toLowerCase();
                tempStr = tempStr.substring(0, 1).toUpperCase() + tempStr.substring(1);
                fieldBuilder.append(tempStr);
            } else {
                fieldBuilder.append(strs[m].toLowerCase());
            }
        }
        return fieldBuilder.toString();
    }
    /**
     * <p>描述：格式化数据类型</p>
     * <p>公司：Lannis©2021 All Rights Reserved</p>
     * <p>作者：鲁帮涛</p>
     * <p>日期：2021-01-04 14:31</p>
     * <p>版权：Lannis-2021</p>
     */
    private static String formatDataType(String dataType, String precision, String scale) {
        if (dataType.contains("char")) {
            dataType = "java.lang.String";
        } else if (dataType.contains("int")) {
            dataType = "java.lang.Integer";
        } else if (dataType.contains("float")) {
            dataType = "java.lang.Float";
        } else if (dataType.contains("double")) {
            dataType = "java.lang.Double";
        } else if (dataType.contains("number")) {
            if ((!StringUtils.isEmpty(scale)) && (Integer.parseInt(scale) > 0)) {
                dataType = "java.math.BigDecimal";
            } else if ((!StringUtils.isEmpty(precision)) && (Integer.parseInt(precision) > 10)) {
                dataType = "java.lang.Long";
            } else {
                dataType = "java.lang.Integer";
            }
        } else if (dataType.contains("decimal")) {
            dataType = "java.math.BigDecimal";
        } else if (dataType.contains("date")) {
            dataType = "java.util.Date";
        } else if (dataType.contains("time")) {
            dataType = "java.util.Date";
        } else if (dataType.contains("blob")) {
            dataType = "byte[]";
        } else if (dataType.contains("clob")) {
            dataType = "java.sql.Clob";
        } else if (dataType.contains("numeric")) {
            dataType = "java.math.BigDecimal";
        } else {
            dataType = "java.lang.Object";
        }
        return dataType;
    }

    /**首字母变大写*/
    public static String firstCharToUpperCase(String str) {
        if (StringUtils.isEmpty(str)){
            return str;
        }
        char[]chars = str.toCharArray();
        if ( chars[0] >= 'a' && chars[0] <= 'z'){
            chars[0] -= 32;
            return String.valueOf(chars);
        }else {
            return str;
        }
    }
    /**首字母变小写*/
    public static String firstCharToLowerCase(String str) {
        if (StringUtils.isEmpty(str)){
            return str;
        }
        char[]chars = str.toCharArray();
        if ( chars[0] >= 'A' && chars[0] <= 'Z') {
            chars[0] += 32;
            return String.valueOf(chars);
        }else {
            return str;
        }
    }

}

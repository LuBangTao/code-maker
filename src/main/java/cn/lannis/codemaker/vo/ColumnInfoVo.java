package cn.lannis.codemaker.vo;

import lombok.Data;

/**
 * <p>描述：表字段信息</p>
 * <p>公司：成都瑞华康源科技有限公司</p>
 * <p>作者：鲁帮涛</p>
 * <p>日期：2020-12-02 13:45</p>
 * <p>版权：RivaMed-2020</p>
 */
@Data
public class ColumnInfoVo {
    /**装换后的字段名称*/
    private String columnName;
    /**原始字段名称*/
    private String originColumnName;
    /**数据库类型*/
    private String dataDBType;
    /**Java中对应的类型*/
    private String dataType;
    private String nullable;
    private String columnComment;
    private String columnKey;
    private String extra;
    private String precision;
    private String scale;
}

package cn.lannis.codemaker.vo;

import lombok.Data;

/**
 * <p>描述：判断是否可以连接的参数</p>
 * <p>作者：鲁帮涛</p>
 * <p>日期：2020-11-30 16:39</p>
 */
@Data
public class CanConnectRequestVo {
    private String url;
    private String username;
    private String password;
    private String databaseName;
    private String tableName;
}

package cn.lannis.codemaker.configure;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * <p>描述：基本配置</p>
 * <p>公司：Lannis©2021 All Rights Reserved</p>
 * <p>作者：鲁帮涛</p>
 * <p>日期：2021-01-04 12:06</p>
 * <p>版权：Lannis-2021</p>
 */
@Component
@Data
public class BaseConfig {
    private String sourceEncoding = "UTF-8";
    /**maven项目代码所在路径*/
    private String mavenCodePath = "src.main.java";
}

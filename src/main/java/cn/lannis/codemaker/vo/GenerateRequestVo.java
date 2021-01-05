package cn.lannis.codemaker.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>描述：执行代码生成的请求参数</p>
 * <p>公司：成都瑞华康源科技有限公司</p>
 * <p>作者：鲁帮涛</p>
 * <p>日期：2021-01-04 13:35</p>
 * <p>版权：RivaMed-2021</p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GenerateRequestVo extends CanConnectRequestVo{
    /**基础包名*/
    private String basePackageName;
    /**包名*/
    private String packageName;
    /**实体名*/
    private String entityName;
}

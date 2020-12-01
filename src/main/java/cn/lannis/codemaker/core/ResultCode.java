package cn.lannis.codemaker.core;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>描述：返回代码</p>
 * <p>公司：成都瑞华康源科技有限公司</p>
 * <p>作者：鲁帮涛</p>
 * <p>日期：2020-12-01 11:46</p>
 * <p>版权：RivaMed-2020</p>
 */
public enum ResultCode {
    SUCCESS(1000,"操作成功"),
    FAIL(2000,"操作失败");

    @Getter
    @Setter
    private Integer code;
    @Getter
    @Setter
    private String message;
    ResultCode(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}

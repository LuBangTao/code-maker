package cn.lannis.codemaker.core;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>描述：统一返回对象</p>
 * <p>公司：Lannis©2021 All Rights Reserved</p>
 * <p>作者：鲁帮涛</p>
 * <p>日期：2020-12-01 11:38</p>
 * <p>版权：RivaMed-2021</p>
 */
@Data
public class Result<T> implements Serializable {
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应描述
     */
    private String msg;
    /**
     * 返回的数据
     */
    private T data;
}

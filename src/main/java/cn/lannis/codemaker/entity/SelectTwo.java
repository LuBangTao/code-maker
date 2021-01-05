package cn.lannis.codemaker.entity;

import lombok.Data;

/**
 * <p>描述：select2渲染参数</p>
 * <p>公司：成都瑞华康源科技有限公司</p>
 * <p>作者：鲁帮涛</p>
 * <p>日期：2020-12-01 13:29</p>
 * <p>版权：RivaMed-2021</p>
 */
@Data
public class SelectTwo {
    private Integer id;
    private String text;
    public SelectTwo(int id, String text) {
        this.id = id;
        this.text = text;
    }
}

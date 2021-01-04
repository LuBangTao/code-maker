package cn.lannis.codemaker.configure;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>描述：</p>
 * <p>公司：成都瑞华康源科技有限公司</p>
 * <p>作者：鲁帮涛</p>
 * <p>日期：2021-01-04 10:36</p>
 * <p>版权：RivaMed-2020</p>
 */
@Component
@Data
public class TempConfig {
    /**设置模板的数据*/
    private Map<String,Object> data = new HashMap<>();
    /**模板路径文件*/
    private List<File> templateRootDirs = new ArrayList<>();
}

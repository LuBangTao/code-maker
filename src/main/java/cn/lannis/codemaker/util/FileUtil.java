package cn.lannis.codemaker.util;

import cn.lannis.codemaker.configure.TempConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * <p>描述：文件操作相关工具类</p>
 * <p>公司：成都瑞华康源科技有限公司</p>
 * <p>作者：鲁帮涛</p>
 * <p>日期：2020-12-02 15:42</p>
 * <p>版权：RivaMed-2021</p>
 */
@Slf4j
@Component
public class FileUtil {
    @Resource
    TempConfig tempConfig;

    private FileUtil(){};
    public static boolean checkExist(){
        return true;
    }

    /**
     * <p>描述：初始化给定的文件路径</p>
     * <p>公司：成都瑞华康源科技有限公司</p>
     * <p>作者：鲁帮涛</p>
     * <p>日期：2020-12-02 15:46</p>
     * <p>版权：RivaMed-2021</p>
     */
    public void initFilePath(List<String> filePaths) {
        for (String path:filePaths){
            File file = new File(path);
            if(!file.exists() && !file.mkdirs()){
                log.warn("目录：{}，创建失败",path);
            }
        }
    }

    /**
     * <p>描述：获取指定路径下的模板文件</p>
     * <p>公司：成都瑞华康源科技有限公司</p>
     * <p>作者：鲁帮涛</p>
     * <p>日期：2020-12-02 16:15</p>
     * <p>版权：RivaMed-2021</p>
     */
    public void getTemplateRootDirs(String path){
        URL resource = getClass().getResource(path);
        String classpath = resource.getFile();
        classpath = classpath.replace("%20", " ");
        this.setTemplateRootDirs(new File(classpath));
    }
    /**
     * <p>描述：设置模板路径</p>
     * <p>公司：成都瑞华康源科技有限公司</p>
     * <p>作者：鲁帮涛</p>
     * <p>日期：2021-01-04 10:41</p>
     * <p>版权：RivaMed-2021</p>
     */
    private void setTemplateRootDirs(File... templateRootDirs) {
        tempConfig.setTemplateRootDirs(Arrays.asList(templateRootDirs));
    }
}

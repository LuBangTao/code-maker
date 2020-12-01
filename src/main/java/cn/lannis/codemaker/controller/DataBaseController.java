package cn.lannis.codemaker.controller;

import cn.lannis.codemaker.core.Result;
import cn.lannis.codemaker.service.DataBaseServiceImpl;
import cn.lannis.codemaker.vo.CanConnectRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>描述：数据库控制层</p>
 * <p>作者：鲁帮涛</p>
 * <p>日期：2020-11-30 16:33</p>
 */
@RestController
@RequestMapping("database")
@Slf4j
public class DataBaseController {
    @Resource
    DataBaseServiceImpl dataBaseService;

    @PostMapping("canConnect")
    public Result<Map<String, Object>> canConnect(@RequestBody CanConnectRequestVo canConnectRequestVo, Model model){
        return dataBaseService.canConnect(canConnectRequestVo,model);
    }
}

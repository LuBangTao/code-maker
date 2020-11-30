package cn.lannis.codemaker.service;

import cn.lannis.codemaker.core.CodeMakerConfig;
import cn.lannis.codemaker.vo.CanConnectRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * <p>描述：数据库操作业务层</p>
 * <p>作者：鲁帮涛</p>
 * <p>日期：2020-11-30 16:38</p>
 */
@Service
@Slf4j
public class DataBaseServiceImpl {
    @Resource
    CodeMakerConfig codeMakerConfig;

    public boolean canConnect(CanConnectRequestVo canConnectRequestVo){
        log.info(canConnectRequestVo.toString());
        Connection connection;
        try{
            connection= DriverManager.getConnection(canConnectRequestVo.getUrl(), canConnectRequestVo.getUsername(), canConnectRequestVo.getPassword());
            connection.close();
            return true;
        }catch (Exception e){
            log.error("数据库连接出错",e);
            return false;
        }
    }
}

package cn.lannis.codemaker.service;

import cn.lannis.codemaker.core.CodeMakerConfig;
import cn.lannis.codemaker.core.Result;
import cn.lannis.codemaker.core.ResultUtil;
import cn.lannis.codemaker.entity.SelectTwo;
import cn.lannis.codemaker.vo.CanConnectRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Result<Map<String, Object>> canConnect(CanConnectRequestVo canConnectRequestVo, Model model) {
        log.info(canConnectRequestVo.toString());
        try (Connection connection = DriverManager.getConnection(canConnectRequestVo.getUrl(), canConnectRequestVo.getUsername(), canConnectRequestVo.getPassword())) {
            Map<String, Object> databaseInfos = new HashMap<>();
            List<SelectTwo> allDataTables = getAllDataTables(connection);
            model.addAttribute("datatableNames", allDataTables);
            databaseInfos.put("datatableNames", allDataTables);
            databaseInfos.put("canConnect",true);
            return ResultUtil.success(databaseInfos);
        } catch (Exception e) {
            log.error("数据库连接出错", e);
            return ResultUtil.fail("数据库连接出错");
        }
    }

    /**
     * <p>描述：获取所有数据库名</p>
     * <p>公司：成都瑞华康源科技有限公司</p>
     * <p>作者：鲁帮涛</p>
     * <p>日期：2020-12-01 11:11</p>
     * <p>版权：RivaMed-2020</p>
     */
    private List<SelectTwo> getAllDataTables(Connection connection) throws SQLException {
        List<SelectTwo> databaseName = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if(codeMakerConfig.getDatabaseType().equals("mysql")){
                sql.append("SELECT SCHEMA_NAME AS 'Database' FROM INFORMATION_SCHEMA.SCHEMATA;");
            }else{
                return databaseName;
            }
            resultSet = statement.executeQuery(sql.toString());
            int i = 0;
            while(resultSet.next()){
                databaseName.add(
                        new SelectTwo(++i,resultSet.getString(1)));
            }
        }catch (Exception e){
            log.error("获取所有数据库名出错",e);
        }finally {
            if (statement!=null){
                statement.close();
            }
            if(resultSet!=null){
                resultSet.close();
            }
        }
        return databaseName;
    }
}

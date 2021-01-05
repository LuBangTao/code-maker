package cn.lannis.codemaker.service;

import cn.lannis.codemaker.configure.BaseConfig;
import cn.lannis.codemaker.configure.CodeMakerConfig;
import cn.lannis.codemaker.configure.TempConfig;
import cn.lannis.codemaker.core.Result;
import cn.lannis.codemaker.core.ResultUtil;
import cn.lannis.codemaker.entity.SelectTwo;
import cn.lannis.codemaker.util.DBUtil;
import cn.lannis.codemaker.util.FileHelper;
import cn.lannis.codemaker.util.FileUtil;
import cn.lannis.codemaker.util.FreemarkerHelper;
import cn.lannis.codemaker.vo.CanConnectRequestVo;
import cn.lannis.codemaker.vo.ColumnInfoVo;
import cn.lannis.codemaker.vo.GenerateRequestVo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.*;
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
    BaseConfig baseConfig;
    @Resource
    FileUtil fileUtil;
    @Resource
    DBUtil dbUtil;
    @Resource
    CodeMakerConfig codeMakerConfig;
    @Resource
    TempConfig tempConfig;

    /**
     * <p>描述：判断连接是否可用</p>
     * <p>公司：成都瑞华康源科技有限公司</p>
     * <p>作者：鲁帮涛</p>
     * <p>日期：2020-12-02 13:24</p>
     * <p>版权：RivaMed-2021</p>
     */
    public Result<Map<String, Object>> canConnect(CanConnectRequestVo canConnectRequestVo, Model model) {
        try (Connection connection = DriverManager.getConnection(canConnectRequestVo.getUrl(), canConnectRequestVo.getUsername(), canConnectRequestVo.getPassword())) {
            Map<String, Object> databaseInfos = new HashMap<>();
            List<SelectTwo> allDataTables = getAllDatabaseNames(connection);
            model.addAttribute("datatableNames", allDataTables);
            databaseInfos.put("datatableNames", allDataTables);
            databaseInfos.put("canConnect",true);
            codeMakerConfig.setUrl(canConnectRequestVo.getUrl());
            codeMakerConfig.setUsername(canConnectRequestVo.getUsername());
            codeMakerConfig.setPassword(canConnectRequestVo.getPassword());
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
     * <p>版权：RivaMed-2021</p>
     */
    private List<SelectTwo> getAllDatabaseNames(Connection connection) throws SQLException {
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
    public Result<Map<String, Object>> getTables(CanConnectRequestVo canConnectRequestVo, Model model) {
        try{
            Map<String, Object> tableInfos = new HashMap<>();
            List<SelectTwo> allDataTables = getAllTables(canConnectRequestVo.getDatabaseName());
            model.addAttribute("tableNames", allDataTables);
            tableInfos.put("tableNames", allDataTables);
            return ResultUtil.success(tableInfos);
        } catch (Exception e) {
            log.error("数据库连接出错", e);
            return ResultUtil.fail("数据库连接出错");
        }
    }
    /**
     * <p>描述：获取指定数据库下面的所有表名</p>
     * <p>公司：成都瑞华康源科技有限公司</p>
     * <p>作者：鲁帮涛</p>
     * <p>日期：2020-12-02 13:24</p>
     * <p>版权：RivaMed-2021</p>
     */
    private List<SelectTwo> getAllTables(String databaseName) throws SQLException {
        List<SelectTwo> tableNames = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = dbUtil.getConnection();
            if(codeMakerConfig.getDatabaseType().equals("mysql")){
                sql.append("select table_name from information_schema.TABLES where TABLE_SCHEMA='").append(databaseName).append("' and TABLE_TYPE = 'BASE TABLE';");
            }else{
                return tableNames;
            }
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(sql.toString());
            int i = 0;
            while(resultSet.next()){
                tableNames.add(
                        new SelectTwo(++i,resultSet.getString(1)));
            }
        }catch (Exception e){
            log.error("获取所有数据库名出错",e);
        }finally {
            if(connection!=null){
                connection.close();
            }
            if (statement!=null){
                statement.close();
            }
            if(resultSet!=null){
                resultSet.close();
            }
        }
        return tableNames;
    }

    /**
     * <p>描述：代码生成</p>
     * <p>公司：成都瑞华康源科技有限公司</p>
     * <p>作者：鲁帮涛</p>
     * <p>日期：2021-01-04 10:25</p>
     * <p>版权：RivaMed-2021</p>
     */
    public Result<String> generate(GenerateRequestVo generateRequestVo){
        try{
            //预填充模板数据
            if(!initTempData(generateRequestVo)){
                return ResultUtil.fail("代码生成失败，表字段信息处理失败");
            }
//            //文件路径检查
//            List<String> filePaths = new ArrayList<>();
//            String applicationPath = codeMakerConfig.getProjectPath()+codeMakerConfig.getApplicationPath();
//            filePaths.add(applicationPath);
//            filePaths.add(applicationPath+codeMakerConfig.getControllerPath());
//            filePaths.add(applicationPath+codeMakerConfig.getEntityPath());
//            filePaths.add(applicationPath+codeMakerConfig.getMapperPath());
//            filePaths.add(applicationPath+codeMakerConfig.getServiceImplPath());
//            filePaths.add(applicationPath+codeMakerConfig.getServicePath());
//            filePaths.add(applicationPath+codeMakerConfig.getMapperXmlPath());
//            filePaths.add(applicationPath+codeMakerConfig.getVoPath());
//            filePaths.add(applicationPath+codeMakerConfig.getDtoPath());
//            fileUtil.initFilePath(filePaths);
            //获取模板文件
            fileUtil.getTemplateRootDirs(codeMakerConfig.getTemplatePath());
            log.info("模板配置：{}",tempConfig);
            for (int i = 0; i < tempConfig.getTemplateRootDirs().size(); i++) {
                File templateRootDir = tempConfig.getTemplateRootDirs().get(i);
                scanTemplatesAndProcess(templateRootDir);
            }
            return ResultUtil.success("代码生成成功");
        }catch (Exception e){
            log.error(e.getMessage());
            return ResultUtil.fail(e.getMessage());
        }
    }

    private boolean initTempData(GenerateRequestVo generateRequestVo) {
        tempConfig.getData().put("basePackageName",StringUtils.isEmpty(generateRequestVo.getBasePackageName())?"cn.lannis.server.one":generateRequestVo.getBasePackageName().toLowerCase());
        tempConfig.getData().put("packageName", StringUtils.isEmpty(generateRequestVo.getPackageName())?"sys":generateRequestVo.getPackageName().toLowerCase());
        tempConfig.getData().put("entityName",StringUtils.isEmpty(generateRequestVo.getEntityName())?"account":generateRequestVo.getEntityName().toLowerCase());
        tempConfig.getData().put("entityNameUpperCaseFirst",StringUtils.isEmpty(generateRequestVo.getEntityName())?"Account":DBUtil.firstCharToUpperCase(generateRequestVo.getEntityName()));
        tempConfig.getData().put("tableName",StringUtils.isEmpty(generateRequestVo.getTableName())?"emptyTableName":generateRequestVo.getTableName().toLowerCase());
        //填充表字段信息
        return fillTableFieldInfo(generateRequestVo);

    }

    /**
     * <p>描述：填充表字段信息</p>
     * <p>公司：成都瑞华康源科技有限公司</p>
     * <p>作者：鲁帮涛</p>
     * <p>日期：2021-01-04 13:51</p>
     * <p>版权：RivaMed-2021</p>
     */
    private boolean fillTableFieldInfo(GenerateRequestVo generateRequestVo) {
        List<ColumnInfoVo> columnInfoVos = dbUtil.readTableFields(generateRequestVo.getDatabaseName(), generateRequestVo.getTableName());
        if(columnInfoVos.isEmpty()){
            return false;
        }else {
            tempConfig.getData().put("columnInfos",columnInfoVos);
            return true;
        }
    }

    private void scanTemplatesAndProcess(File templateRootDir) {
        log.info("加载模板中...");
        try {
            List<?> srcFiles = FileHelper.searchAllNotIgnoreFile(templateRootDir);
            for (Object file : srcFiles) {
                File srcFile = (File) file;
                executeGenerate(templateRootDir, srcFile);
            }
        }catch (Exception e){
            log.error("加载模板出错",e);
        }

    }

    /**
     * <p>描述：执行模板代码生成</p>
     * <p>公司：成都瑞华康源科技有限公司</p>
     * <p>作者：鲁帮涛</p>
     * <p>日期：2021-01-04 11:10</p>
     * <p>版权：RivaMed-2021</p>
     */
    private void executeGenerate(File templateRootDir, File srcFile) {
        log.info("【当前文件临时根路径】:{}" , templateRootDir.getPath());
        log.info("【当前文件临时路径】:{}" ,srcFile.getPath());
        String templateFile = FileHelper.getRelativePath(templateRootDir, srcFile);
        try {
            String outputFilepath = processForOutputFilepath(templateFile);
            if (outputFilepath.startsWith("java")) {
                String soure = codeMakerConfig.getProjectPath() + File.separator + baseConfig.getMavenCodePath().replace(".", File.separator);
                outputFilepath = outputFilepath.substring("java".length());
                outputFilepath = soure + outputFilepath;
                log.debug("【当前文件输出完整路径】：{}", outputFilepath);
                generateNewFileOrInsertIntoFile(templateFile, outputFilepath,tempConfig.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        }
    }

    private void generateNewFileOrInsertIntoFile(String templateFile, String outputFilepath, Map<String, Object> data) {
        try {
            if (outputFilepath.endsWith("i")) {
                outputFilepath = outputFilepath.substring(0, outputFilepath.length() - 1);
            }
            Template template = getFreeMarkerTemplate(templateFile);
            template.setOutputEncoding(baseConfig.getSourceEncoding());
            File absoluteOutputFilePath = FileHelper.parentMkdir(outputFilepath);
            log.info("[最终]\t template:" + templateFile + " ==> " + outputFilepath);
            FreemarkerHelper.processTemplate(template, data, absoluteOutputFilePath, baseConfig.getSourceEncoding());
            if (isCutFile(absoluteOutputFilePath)) {
                splitFile(absoluteOutputFilePath, "#segment#");
            }
        }catch (Exception e){
            log.error("出现错误",e);
        }

    }

    private Template getFreeMarkerTemplate(String templateFile) throws IOException {
        return FreemarkerHelper.newFreeMarkerConfiguration(tempConfig.getTemplateRootDirs(), baseConfig.getSourceEncoding(), templateFile).getTemplate(templateFile);
    }

    /**
     * <p>描述：分割文件</p>
     * <p>公司：成都瑞华康源科技有限公司</p>
     * <p>作者：鲁帮涛</p>
     * <p>日期：2021-01-04 12:22</p>
     * <p>版权：RivaMed-2021</p>
     */
    private void splitFile(File file, String splitStr) {
        InputStreamReader isr = null;
        BufferedReader br = null;
        List<Writer> flist = new ArrayList<>();
        try {
            isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
            br = new BufferedReader(isr);

            boolean start = false;
            Writer targetFile = null;
            String row;
            while ((row = br.readLine()) != null) {
                if ((row.trim().length() > 0) && (row.startsWith(splitStr))) {
                    String fileName = row.substring(splitStr.length());
                    String parent = file.getParentFile().getAbsolutePath();
                    fileName = parent + File.separator + fileName;
                    log.info("[generate]\t split file:" + file.getAbsolutePath() + " ==> " + fileName);
                    targetFile = new OutputStreamWriter(new FileOutputStream(fileName), baseConfig.getSourceEncoding());
                    flist.add(targetFile);
                    start = true;
                } else if (start) {
                    log.debug("row : " + row);
                    targetFile.append(row).append("\r\n");
                }
            }
            for (int i = 0; i < flist.size(); i++) {
                flist.get(i).close();
            }
            br.close();
            isr.close();
            log.info("[generate]\t delete file:" + file.getAbsolutePath());
            forceDelete(file);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (!flist.isEmpty()) {
                    for (Writer writer : flist) {
                        if (writer != null) {
                            writer.close();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean forceDelete(File file) {
        boolean result = false;
        int tryCount = 0;
        while ((!result) && (tryCount++ < 10)) {
            System.gc();
            result = file.delete();
        }
        return result;

    }

    private boolean isCutFile(File file) {
        return file.getName().startsWith("[1-n]");
    }

    //TODO 在这处理最终文件生成的位置
    private String processForOutputFilepath(String templateFile) {
        String outputFilePath = templateFile;
        try {
            Configuration conf = FreemarkerHelper.newFreeMarkerConfiguration(tempConfig.getTemplateRootDirs(), baseConfig.getSourceEncoding(), "/");
            outputFilePath = FreemarkerHelper.processTemplateString(outputFilePath, tempConfig.getData(), conf);
            String extName = outputFilePath.substring(outputFilePath.lastIndexOf("."));
            String fileName = outputFilePath.substring(0, outputFilePath.lastIndexOf(".")).replace(".", File.separator);
            outputFilePath = fileName + extName;
        }catch (Exception e){
            log.error("processForOutputFilepath方法出错",e);
        }
        return outputFilePath;
    }
}

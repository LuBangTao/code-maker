package cn.lannis.codemaker.configure;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>描述：配置类</p>
 * <p>作者：鲁帮涛</p>
 * <p>日期：2020-11-30 14:59</p>
 */
@Component
@PropertySource(value = "classpath:code-maker.yml")
@ConfigurationProperties(prefix = "config")
@Getter
@Setter
@ToString
public class CodeMakerConfig {
    /**文件输出路径*/
    @Value("${optPutPath:D:\\}")
    private String optPutPath;
    /**数据库类型，默认MySql*/
    @Value("${databaseType:mysql}")
    private String databaseType;
    /**驱动名称，默认使用Mysql驱动*/
    @Value("${driverName:com.mysql.cj.jdbc.Driver}")
    private String driverName;
    @Value("${ip:localhost}")
    private String ip;
    @Value("${port:3306}")
    private String port;
    @Value("${extraParams:?characterEncoding=UTF-8&useUnicode=true&useSSL=false}")
    private String extraParams;
    /**连接URL*/
    @Value("${url:jdbc:mysql://localhost:3306/sys?characterEncoding=UTF-8&useUnicode=true&useSSL=false}")
    private String url;
    /**当前数据库用户名*/
    @Value("${username:root}")
    private String username;
    /**当前数据库密码*/
    @Value("${password:123456}")
    private String password;
    /**项目所在的文件路径，相对于src目录*/
    @Value("${projectPath:D://Space/demos/one}")
    private String projectPath;
    @Value("${applicationPath:/src/main/java/cn/lannis/codemaker}")
    private String applicationPath;
    @Value("${controllerPath:/controller}")
    private String controllerPath;
    @Value("${entityPath:/entity}")
    private String entityPath;
    /**VO路径*/
    @Value("${voPath:/vo}")
    private String voPath;
    /**DTO路径*/
    @Value("${dtoPath:/dto}")
    private String dtoPath;
    /**service路径*/
    @Value("${servicePath:/service}")
    private String servicePath;
    /**serviceImpl路径*/
    @Value("${serviceImplPath:/service/impl}")
    private String serviceImplPath;
    @Value("${mapperPath:/mapper}")
    private String mapperPath;
    @Value("${mapperXmlPath:/mapper/xml}")
    private String mapperXmlPath;
    @Value("${underLineToCamel:true}")
    private Boolean underLineToCamel;
    @Value("${templatePath:/lannis}")
    private String templatePath;

}

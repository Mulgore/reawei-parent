package com.reawei.server.commom.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

/**
 * 致终于来到这里的勇敢的人：
 * <p>
 * 天将降大任于是人也，必先苦其心志，劳其筋骨，饿其体肤，空乏其身，行拂乱其所为，所以动心忍性，曾益其所不能。
 * <p>
 * 嗯！好了这个类的作用是:
 * 创建数据源
 *
 * @author xingwu
 * @date 2017/6/8
 */
@Configuration
@PropertySource("classpath:properties/jdbc.properties")
public class DbConfiguration {
    private static Logger log = LoggerFactory.getLogger(DbConfiguration.class);

    @Value("${mysql.datasource.type}")
    private Class<? extends DataSource> mysqlDataSourceType;

    /**
     * master DataSource
     *
     * @return data source
     * @Primary 注解用于标识默认使用的 DataSource Bean，因为有5个 DataSource Bean，该注解可用于 master
     * 或 slave DataSource Bean, 但不能用于 dynamicDataSource Bean, 否则会产生循环调用
     * @ConfigurationProperties 注解用于从 application.properties 文件中读取配置，为 Bean 设置属性
     */
    @Bean("masterDataSource")
    @Primary
    @ConfigurationProperties(prefix = "master.datasource")
    public DataSource masterDataSource() {
        log.info("-------------------- master DataSource init ---------------------");
        return DataSourceBuilder.create().type(mysqlDataSourceType).build();
    }

    /**
     * 有多少个从库就要配置多少个
     *
     * @return DataSource
     */
    @Bean("clusterDataSource")
    @ConfigurationProperties(prefix = "cluster.datasource")
    public DataSource clusterDataSource() {
        log.info("-------------------- cluster DataSource init ---------------------");
        return DataSourceBuilder.create().type(mysqlDataSourceType).build();
    }
}

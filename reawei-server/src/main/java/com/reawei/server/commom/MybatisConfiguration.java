package com.reawei.server.commom;

import com.reawei.server.commom.datasource.*;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 致终于来到这里的勇敢的人：
 * <p>
 * 天将降大任于是人也，必先苦其心志，劳其筋骨，饿其体肤，空乏其身，行拂乱其所为，所以动心忍性，曾益其所不能。
 * <p>
 * 嗯！好了这个类的作用是:
 * 1）springboot集成mybatis的基本入口
 * 1）创建数据源
 * 2）创建SqlSessionFactory
 *
 * @author xingwu
 * @date 2017/6/8
 */
@Configuration
@MapperScan(basePackages = "com.reawei.server.mapper")
public class MybatisConfiguration {

    @Resource
    private DataSource masterDataSource;

    @Resource
    private DataSource clusterDataSource;


    @Value("${mybatis.typeAliasesPackage}")
    String typeAliasesPackage;

    @Value("${mybatis.mapperLocations}")
    String mapperLocations;

    @Bean("dynamicDataSource")
    public DynamicDataSource dynamicDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DbType.WRITE, masterDataSource);
        targetDataSources.put(DbType.READ, clusterDataSource);
        DynamicDataSource dataSource = new DynamicDataSource();
        // 该方法是AbstractRoutingDataSource的方法
        dataSource.setTargetDataSources(targetDataSources);
        // 默认的datasource设置为myTestDbDataSource
        dataSource.setDefaultTargetDataSource(masterDataSource);
        return dataSource;

    }

    /**
     * 配置 SqlSessionFactoryBean
     *
     * @return the sql session factory bean
     * @ConfigurationProperties 在这里是为了将 MyBatis 的 mapper 位置和持久层接口的别名设置到
     * Bean 的属性中，如果没有使用 *.xml 则可以不用该配置，否则将会产生 invalid bond statement 异常
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("dynamicDataSource") DynamicDataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        DynamicPlugin dynamicPlugin = new DynamicPlugin();
        Interceptor[] plugins = new Interceptor[]{dynamicPlugin};
        sqlSessionFactoryBean.setPlugins(plugins);
        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource 作为数据源则不能实现切换
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        return sqlSessionFactoryBean;
    }

    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dynamicDataSource") DynamicDataSource dynamicDataSource) {
        DynamicDataSourceTransactionManager transactionManager = new DynamicDataSourceTransactionManager();
        transactionManager.setDataSource(dynamicDataSource);
        return transactionManager;
    }

}

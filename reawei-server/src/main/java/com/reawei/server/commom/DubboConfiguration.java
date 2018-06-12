package com.reawei.server.commom;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created in 2018/6/11 14:54
 * Dubbo 服务配置
 *
 * @author qigong
 */
@Configuration
@PropertySource("classpath:properties/dubbo.properties")
@ImportResource("classpath:spring/dubbo-*.xml")
public class DubboConfiguration {

    @Autowired
    private Environment env;

    /**
     * 提供方应用信息，用于计算依赖关系
     *
     * @return ApplicationConfig
     */
    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(env.getProperty("application.name").trim());
        return applicationConfig;
    }

    /**
     * 注册中心暴露服务地址
     *
     * @return RegistryConfig
     */
    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol(env.getProperty("registry.protocol").trim());
        registryConfig.setAddress(env.getProperty("registry.address").trim());
        return registryConfig;
    }

    /**
     * 暴露服务
     *
     * @return ProtocolConfig
     */
    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setPort(Integer.parseInt(env.getProperty("protocol.port").trim()));
        protocolConfig.setName(env.getProperty("protocol.name").trim());
        protocolConfig.setThreads(200);
        return protocolConfig;
    }

    /**
     * 注册服务监控
     *
     * @return MonitorConfig
     */
    @Bean
    public MonitorConfig monitorConfig() {
        MonitorConfig monitorConfig = new MonitorConfig();
        monitorConfig.setProtocol("registry");
        return monitorConfig;
    }

}

package com.reawei.server.commom;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
//@DubboComponentScan(basePackages = "com.reawei.server.service.impl")
public class DubboConfiguration {

    @Value("${dubbo.application.name}")
    private String applicationName;

    @Value("${dubbo.registry.protocol}")
    private String registryProtocol;

    @Value("${dubbo.registry.address}")
    private String registryAddress;

    @Value("${dubbo.protocol.port}")
    private Integer protocolPort;

    @Value("${dubbo.protocol.name}")
    private String protocolName;

    /**
     * 提供方应用信息，用于计算依赖关系
     *
     * @return ApplicationConfig
     */
    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(applicationName);
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
        registryConfig.setProtocol(registryProtocol);
        registryConfig.setAddress(registryAddress);
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
        protocolConfig.setPort(protocolPort);
        protocolConfig.setName(protocolName);
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

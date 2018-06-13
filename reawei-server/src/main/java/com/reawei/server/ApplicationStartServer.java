package com.reawei.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author qigong
 */
@SpringBootApplication
@EnableTransactionManagement
public class ApplicationStartServer {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStartServer.class, args);
    }
}

package com.webank.ddcms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableTransactionManagement
@MapperScan(value = "com.webank.ddcms.dao.mapper")
public class DDCMSApplication {
  public static void main(String[] args) {
    SpringApplication.run(DDCMSApplication.class, args);
  }
}

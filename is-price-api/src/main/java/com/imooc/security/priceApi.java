package com.imooc.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.dao.DataAccessResourceFailureException;

@SpringBootApplication
public class priceApi{

    public static void main(String[] args) {
        SpringApplication.run(priceApi.class, args);
    }

}

package com.cola.concurrentstock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cola.concurrentstock.base.mapper")
public class ConcurrentStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConcurrentStockApplication.class, args);
    }

}

package com.libmis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 二木
 * @date 2024-12-17 21:42
 */
@MapperScan("com.libmis.mapper")
@SpringBootApplication
public class LibMisApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibMisApplication.class, args);
    }
}

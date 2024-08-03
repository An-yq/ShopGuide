package com.aaqqs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.aaqqs.dao.mapper")
public class ShopGuideApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopGuideApplication.class,args);
    }
}

package com.zerob.my_rnts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MyRntsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyRntsApplication.class, args);
    }

}

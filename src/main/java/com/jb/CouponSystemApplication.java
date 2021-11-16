package com.jb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
//@EnableAspectJAutoProxy
public class CouponSystemApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(CouponSystemApplication.class, args);
    }
}

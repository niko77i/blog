package com.niko.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync  //开启异步注解功能
@MapperScan("com.niko.blog.mapper")
public class BlogApplication {

    public static void main(String[] args) {

        // 解决elasticsearch启动保存问题
//        System.setProperty("es.set.netty.runtime.available.processors", "false");

        SpringApplication.run(BlogApplication.class, args);
        System.out.println("http://localhost:8080");
    }

}

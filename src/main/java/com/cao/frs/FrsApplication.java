package com.cao.frs;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSwaggerBootstrapUI
public class FrsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrsApplication.class, args);
        System.out.println("启动成功");
    }

}

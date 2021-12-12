package com.cao.frs;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwaggerBootstrapUI
@SpringBootApplication
public class FrsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrsApplication.class, args);
    }

}

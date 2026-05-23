package com.study.EasyOrder.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI easyOrderOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EasyOrder API")
                        .description("EasyOrder 상품 및 주문 관리 API 문서")
                        .version("v1.0"));
    }
}

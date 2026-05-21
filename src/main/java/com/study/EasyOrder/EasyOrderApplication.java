package com.study.EasyOrder;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;


// JPA/DB 의존성이 포함되어 있으나, 로컬 테스트를 위해 자동 구성을 일시적으로 제외함
// TODO: 실제 MariaDB 연결 시 아래 exclude 옵션 제거 및 application.properties 설정 필요
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication

public class EasyOrderApplication {

	@Value("${spring.datasource.url}") private String url;
	@Value("${spring.datasource.username}") private String username;
	@Value("${spring.datasource.password}") private String password;

	public static void main(String[] args) {
		SpringApplication.run(EasyOrderApplication.class, args);


		// 서버 실행 확인을 위한 로그
		System.out.println("======================================");
		System.out.println("   EasyOrder 서버가 성공적으로 떴습니다!   ");
		System.out.println("======================================");
	}

	// properties 연결정보
	@Bean
    CommandLineRunner run() {
		return args -> {
			System.out.println("DB URL = " + url);
			System.out.println("DB USER = " + username);
			System.out.println("DB PASSWORD = " + password);
		};
	}
}

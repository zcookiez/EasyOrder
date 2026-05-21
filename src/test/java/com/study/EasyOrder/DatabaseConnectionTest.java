package com.study.EasyOrder;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // JPA 관련 설정만 로드
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 내장 DB가 아닌 실제 MariaDB 사용 설정
class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate; // 쿼리 실행 확인용

    @Test
    void db_test() {
        try (Connection connection = dataSource.getConnection()) {
            // 1. 물리적 연결 확인
            assertThat(connection).isNotNull();
            System.out.println("✅ MariaDB 연결 성공!");

            // 2. 간단한 쿼리 실행 확인 (현재 시간 조회)
            String now = jdbcTemplate.queryForObject("SELECT NOW()", String.class);
            System.out.println("🕒 DB 현재 시간: " + now);

        } catch (Exception e) {
            System.out.println("❌ 접속 실패 사유: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
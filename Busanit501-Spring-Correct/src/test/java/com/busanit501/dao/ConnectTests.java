package com.busanit501.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectTests {
    @Test
    public void test1() {
        int v1 = 10;
        int v2 = 10;
// 같은지 확인하는 메서드
        // 같으면 : 성공, 다르면 실패.
        Assertions.assertEquals(v1, v2);
    }

    // 2번째 테스트, 디비 연결 테스트
    @Test
    public void testConnection() throws Exception{
        // 1 드라이버 연결.
        Class.forName("org.mariadb.jdbc.Driver");
        
        // 2 연결 커넥션 객체 도구 이용. 
        // 접근 하는 디비 서버 1) 주소 2) 계정 3) 패스워드 
        Connection connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/webdb", 
                "webuser", 
                "webuser"
        );
        
        // 3. 연결 도구의 객체가 유효한지, 체크 
        Assertions.assertNotNull(connection);
        
        // 4. 확인 후, 자원 반납
        connection.close();
        
        
    }


    //3번째, Hikari CP 이용한 접근 테스트
    @Test
    public void testHikariCP() throws Exception{
        // 1, 히카리 이용하기 위한 객체 도구 필요함.
        HikariConfig config = new HikariConfig();
        // 2, 어느 디비 서버에 연결 할지, 정보 등록,
        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl("jdbc:mariadb://localhost:3306/webdb");
        config.setUsername("webuser");
        config.setPassword("webuser");
        //3. 옵션 설정, 캐시, 크기 , 제한량,
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        //4. 데이터 소스 연결, 위의 설정을 모아두기, 불러오기,
        HikariDataSource dataSource = new HikariDataSource(config);

        //5. 연결 객체 만들기.
        Connection connection = dataSource.getConnection();

        // 6. 연결 객체 확인,
        System.out.println(connection);

        //7. 자원 반납.
        connection.close();

    }

}










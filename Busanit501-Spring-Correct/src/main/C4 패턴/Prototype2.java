package com.busanit501.hello_project._3jdbc.dao;

import com.busanit501.hello_project._3jdbc.domain.TodoVO;
import lombok.Cleanup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// 🏛️ 클래스(Class)   🛠️ 메서드(Method)   📦 패키지(Package)   📨 VO(Value Object)   🧰 내장함수(Built-in)   🔑 키워드(Keyword)
// ➡️ 호출(Calls)   ⬅️ 호출됨(Called by)   🔄 반환(Return)
// 🟡 연관(Related)   🟢 독립(Standalone)   🔴 강한의존(Strong Dependency)
// ^ 가독성(Readability)   >> 관례(Convention)   V 정상작동(Valid)   + 이익(Benefit)   - 방지(Prevention)

// [C] 🏛️TodoDAO: Todo 관련 DB 작업을 담당하는 DAO 클래스를 정의하라.
// [P] +🟡 DB 접근 로직 분리(비즈니스 로직과 데이터 접근의 분리, 유지보수성↑) (F: 계층 분리, 재사용성)
// [F] (계층 분리/재사용성, +🟡) DB 작업을 별도 클래스로 분리함으로써, 서비스/컨트롤러와의 결합도를 낮추고 재사용성을 높인다.
// [T] 🏛️클래스, 이름/구조 변경 가능
// [R]
// ┌─────────────┬──────────────┬──────────────────────────────────────────────┐
// │ 🟡 연관     │ ⬅️ 호출됨    │ 🛎️TodoService.java, 🎮Controller 계층         │
// └─────────────┴──────────────┴──────────────────────────────────────────────┘
// 레이어: [DAO][Service][Controller]
// 설명: 서비스/컨트롤러에서 DAO의 메서드를 호출하여 DB 작업을 위임함
public class TodoDAO {
    // [C] 🛠️getTime: DB 서버의 현재 시간을 조회하는 메서드를 구현하라.
    // [P] +🟡 DB 연결 및 시간 동기화(서버-클라이언트 시간 일치) (F: 데이터 동기화)
    // [F] (데이터 동기화, +🟡) DB 서버의 현재 시간을 조회하여, 서버와 클라이언트 간 시간 차이로 인한 문제를 예방한다.
    // [T] 🛠️메서드, String, 반환타입/로직 변경 가능
    // [R]
    // ┌─────────────┬──────────────┬──────────────────────────────────────────────┐
    // │ 🟡 연관     │ ⬅️ 호출됨    │ 🛎️TodoService.java, 🎮Controller 계층         │
    // └─────────────┴──────────────┴──────────────────────────────────────────────┘
    // 레이어: [DAO][Service][Controller]
    // 설명: 서비스/컨트롤러에서 getTime()을 호출하여 DB 서버의 현재 시간을 가져감
    public String getTime() {
        // [C] DB로부터 전달받은 시간을 담을 임시 변수를 선언하라.
        // [P] + 데이터 임시 저장(가독성↑, 유지보수성↑) (F: 임시 저장, 가독성)
        // [F] (임시 저장/가독성, +) 쿼리 결과를 임시 변수에 저장하여 코드의 명확성과 유지보수성을 높인다.
        // [T] 변수, String, 이름/타입 변경 가능
        // [R] * 독립
        String now = null;
        // [C] DB 연결 및 쿼리 실행, 자원 자동 반납을 구현하라.
        // [P] - 자원 누수 방지(try-with-resources, @Cleanup 등) (F: 자원 관리, 안정성)
        // [F] (자원 관리/안정성, -) try-with-resources를 사용해 DB 연결/쿼리/결과셋 자원을 자동 반납하여, 자원 누수로 인한 장애를 예방한다.
        // [T] 🧰내장함수, try-with-resources, 변경 가능
        // [R] * 독립
        try(Connection connection = ConnectionUtil.INSTANCE.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("select now()");
            ResultSet rs = pstmt.executeQuery();){
            rs.next();
            now = rs.getString(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    } //getTime

    // [C] 🛠️getTime2: @Cleanup을 이용한 DB 시간 조회 메서드를 구현하라.
    // [P] >> 롬복 관례 활용(코드 간결성↑, 자원 관리↑) (F: 관례, 간결성)
    // [F] (관례/간결성, >>) @Cleanup을 사용해 try-with-resources를 대체, 코드가 간결해지고 자원 관리가 쉬워진다.
    // [T] 🛠️메서드, String, 반환타입/로직 변경 가능
    // [R]
    // ┌─────────────┬──────────────┬──────────────────────────────────────────────┐
    // │ 🟡 연관     │ ⬅️ 호출됨    │ 🛎️TodoService.java, 🎮Controller 계층         │
    // └─────────────┴──────────────┴──────────────────────────────────────────────┘
    // 레이어: [DAO][Service][Controller]
    // 설명: 서비스/컨트롤러에서 getTime2()를 호출하여 DB 서버의 현재 시간을 가져감
    public String getTime2() throws Exception {
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement pstmt = connection.prepareStatement("select now()");
        @Cleanup ResultSet rs = pstmt.executeQuery();
        rs.next();
        String now = rs.getString(1);
        return now;
    } //getTime2

    // [C] 🛠️insert: Todo 등록 기능을 구현하라.
    // [P] +🟡 신규 Todo 데이터 DB 저장(데이터 영속성↑) (F: 데이터 입력, 영속성)
    // [F] (데이터 입력/영속성, +🟡) 화면에서 전달받은 📨TodoVO를 DB에 저장하여, 데이터의 영속성과 신뢰성을 확보한다.
    // [T] 🛠️메서드, void, 파라미터/로직 변경 가능
    // [R]
    // ┌─────────────┬──────────────┬──────────────────────────────────────────────┐
    // │ 🟡 연관     │ ⬅️ 호출됨    │ 🛎️TodoService.java#register(), 🎮Controller 계층 │
    // └─────────────┴──────────────┴──────────────────────────────────────────────┘
    // 레이어: [DAO][Service][Controller]
    // 설명: 서비스/컨트롤러에서 insert()를 호출하여 Todo 등록 로직을 위임함
    public void insert(TodoVO todoVO) throws Exception{
        String sql ="insert into tbl_todo (title, dueDate, finished) values (?,?,?)";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, todoVO.getTitle());
        pstmt.setDate(2, Date.valueOf(todoVO.getDueDate()));
        pstmt.setBoolean(3, todoVO.isFinished());
        pstmt.executeUpdate();
    } // insert

    // [C] 🛠️selectAll: 전체 Todo 목록을 조회하는 기능을 구현하라.
    // [P] +🟡 전체 데이터 조회(사용자에게 전체 목록 제공) (F: 데이터 조회, 사용자 편의)
    // [F] (데이터 조회/사용자 편의, +🟡) DB에서 전체 Todo 데이터를 리스트로 반환하여, 사용자가 모든 Todo를 한눈에 볼 수 있게 한다. 결과적으로 업무 파악과 관리가 용이해진다.
    // [T] 🛠️메서드, List<📨TodoVO>, 반환타입/로직 변경 가능
    // [R]
    // ┌─────────────┬──────────────┬──────────────────────────────────────────────┐
    // │ 🟡 연관     │ ⬅️ 호출됨    │ 🛎️TodoService.java#getList(), 🎮Controller 계층 │
    // └─────────────┴──────────────┴──────────────────────────────────────────────┘
    // 레이어: [DAO][Service][Controller]
    // 설명: 서비스/컨트롤러에서 selectAll()을 호출하여 전체 Todo 목록을 가져감
    public List<TodoVO> selectAll() throws Exception{
        String sql ="select * from tbl_todo";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement pstmt = connection.prepareStatement(sql);
        @Cleanup ResultSet rs = pstmt.executeQuery();
        List<TodoVO> list = new ArrayList<>();
        while(rs.next()){
            TodoVO vo = TodoVO.builder()
                    .tno(rs.getLong("tno"))
                    .title(rs.getString("title"))
                    .dueDate(rs.getDate("dueDate").toLocalDate())
                    .finished(rs.getBoolean("finished"))
                    .build();
            list.add(vo);
        }
        return list;
    }

    // [C] 🛠️selectOne: tno로 Todo를 하나 조회하는 기능을 구현하라.
    // [P] +🟡 특정 데이터 조회(상세 정보 제공) (F: 데이터 조회, 상세 정보)
    // [F] (데이터 조회/상세 정보, +🟡) tno 값을 받아 해당 Todo의 상세 정보를 반환한다. 결과적으로 사용자는 원하는 Todo의 세부 정보를 확인할 수 있다.
    // [T] 🛠️메서드, 📨TodoVO, 반환타입/로직 변경 가능
    // [R]
    // ┌─────────────┬──────────────┬──────────────────────────────────────────────┐
    // │ 🟡 연관     │ ⬅️ 호출됨    │ 🛎️TodoService.java#getTodoByTno(), 🎮Controller 계층 │
    // └─────────────┴──────────────┴──────────────────────────────────────────────┘
    // 레이어: [DAO][Service][Controller]
    // 설명: 서비스/컨트롤러에서 selectOne()을 호출하여 특정 Todo의 상세 정보를 가져감
    public TodoVO selectOne(Long tno) throws Exception{
        String sql = "select * from tbl_todo where tno=?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setLong(1, tno);
        @Cleanup ResultSet rs = pstmt.executeQuery();
        rs.next();
        TodoVO vo = TodoVO.builder()
                .tno(rs.getLong("tno"))
                .title(rs.getString("title"))
                .dueDate(rs.getDate("dueDate").toLocalDate())
                .build();
        return vo;
    }

    // [C] 🛠️deleteOne: Todo 삭제 기능을 구현하라.
    // [P] -🟡 불필요한 데이터 방지(데이터 정합성↑) (F: 데이터 정합성, 방지)
    // [F] (데이터 정합성/방지, -🟡) tno로 특정 Todo를 삭제하여, 불필요한 데이터가 쌓이는 것을 방지하고 데이터 정합성을 유지한다.
    // [T] 🛠️메서드, void, 파라미터/로직 변경 가능
    // [R]
    // ┌─────────────┬──────────────┬──────────────────────────────────────────────┐
    // │ 🟡 연관     │ ⬅️ 호출됨    │ 🛎️TodoService.java#deleteOne(), 🎮Controller 계층 │
    // └─────────────┴──────────────┴──────────────────────────────────────────────┘
    // 레이어: [DAO][Service][Controller]
    // 설명: 서비스/컨트롤러에서 deleteOne()을 호출하여 Todo 삭제 로직을 위임함
    public void deleteOne(Long tno) throws Exception{
        String sql = "delete from tbl_todo where tno=?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setLong(1, tno);
        pstmt.executeUpdate();
    }

    // [C] 🛠️updateOne: Todo 수정 기능을 구현하라.
    // [P] +🟡 데이터 최신화(정확한 정보 제공) (F: 데이터 최신화, 신뢰성)
    // [F] (데이터 최신화/신뢰성, +🟡) 화면에서 전달받은 📨TodoVO로 DB의 Todo 정보를 최신 상태로 갱신하여, 사용자에게 정확한 정보를 제공한다.
    // [T] 🛠️메서드, void, 파라미터/로직 변경 가능
    // [R]
    // ┌─────────────┬──────────────┬──────────────────────────────────────────────┐
    // │ 🟡 연관     │ ⬅️ 호출됨    │ 🛎️TodoService.java#updateOne(), 🎮Controller 계층 │
    // └─────────────┴──────────────┴──────────────────────────────────────────────┘
    // 레이어: [DAO][Service][Controller]
    // 설명: 서비스/컨트롤러에서 updateOne()을 호출하여 Todo 수정 로직을 위임함
    public void updateOne(TodoVO todoVO) throws Exception{
        String sql = "update tbl_todo set title=?, dueDate=?, finished=? where tno=?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, todoVO.getTitle());
        pstmt.setDate(2, Date.valueOf(todoVO.getDueDate()));
        pstmt.setBoolean(3, todoVO.isFinished());
        pstmt.setLong(4, todoVO.getTno());
        pstmt.executeUpdate();
    }

}

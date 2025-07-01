package com.busanit501.dao;

import com.busanit501.hello_project._3jdbc.dao.TodoDAO;
import com.busanit501.hello_project._3jdbc.domain.TodoVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TodoDAOTests {
    // 1, TodoDAO 기능 사용하기, 가져오기.
    private TodoDAO todoDAO;

    // 각각 @Test 메서드가 실행 되기전에 실해되는 메서드.
    @BeforeEach
    public void ready() {
        todoDAO = new TodoDAO();
    }

    @Test
    public void testTime() throws Exception {
        System.out.println("testTime : " + todoDAO.getTime());
    }

    @Test
    public void testTime2() throws Exception {
        System.out.println("testTime : " + todoDAO.getTime2());
    }

    @Test
    public void testInsert() throws Exception {
        // 임시 데이터로 구성, 나중에 화면에서 입력 받은 데이터를 사용할 예정.

        // 기본 인스턴스 생성, 빌더 패턴 사용 전
//        TodoVO todoVO = new TodoVO();
//        todoVO.setTitle("샘플 제목");
//        todoVO.setDueDate(LocalDate.now());

        // 빌더 패턴 사용 후
        TodoVO todoVO = TodoVO.builder()
                .title("샘플 제목")
                .dueDate(LocalDate.now())
                .build();

        // TodoDAO 기능 이용해서, insert 해보기.
        todoDAO.insert(todoVO);
    }

    // 전체 조회 테스트
    @Test
    public void testList() throws Exception {
        // 디비로 부터 전달 받은 리스트를 담을 임시 리스트 만들고, 확인.
        List<TodoVO> list = todoDAO.selectAll();
        // 임시로 콘솔에 출력 해보기.
        list.forEach(vo -> System.out.println(vo));
    }

    //하나 조회 테스트
    @Test
    public void testSelectOne() throws Exception {
        // 현재 디비 내용에 따라서, 다름. 3개의 더미 데이터 있음.
        Long tno = 1L; // 디비에 존재해야함.
        TodoVO todoVO = todoDAO.selectOne(tno);
        System.out.println(todoVO);
    }

    // 삭제 테스트
    @Test
    public void testDeleteOne() throws Exception {
        // 현재 디비 내용에 따라서, 다름. 3개의 더미 데이터 있음.
        Long tno = 1L; // 디비에 존재해야함.
        todoDAO.deleteOne(tno);
        // 실제 디비를 확인.
        System.out.println("삭제 완료");
    }

    // 수정테스트
    @Test
    public void testUpdateOne() throws Exception {
        // 변경할 임시 데이터를, 화면에서 받아왔다고 가정하고,
        // 받아온 데이터를 -> 모델에 담아서,
        // dao 로 업데이트 기능 수행.
        TodoVO vo = TodoVO.builder()
                .tno(2L)
                .title("수정 제목")
                .dueDate(LocalDate.now())
                .finished(true)
                .build();
        // 업데이트 로직 처리 요청.
        todoDAO.updateOne(vo);
    }

}

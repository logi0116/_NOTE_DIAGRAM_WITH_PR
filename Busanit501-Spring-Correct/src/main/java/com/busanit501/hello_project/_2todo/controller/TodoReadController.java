package com.busanit501.hello_project._2todo.controller;

import com.busanit501.hello_project._2todo.dto.TodoDTO;
import com.busanit501.hello_project._2todo.service.TodoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "todoReadController", urlPatterns = "/todo/read")
public class TodoReadController extends HttpServlet {
    // **BEFORE**: 화면 제공 목적, + 데이터를 담은 결과 화면.
    // Todo 상세 조회 기능: GET 요청을 받아서 쿼리스트링의 tno 파라미터로 특정 Todo를 조회하여 상세 화면에 전달하는 컨트롤러

@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    System.out.println("TodoReadController : 하나 조회 ");
    
    // **BEFORE**: 임의의  todo 번호, 100
    // **BEFORE**: 더미 데이터 가져오기, 현재는 메모리 작업,
    // **BEFORE**: /todo/read?tno=100, 쿼리 스트링 형식으로 , 파라미터 전달 받기.
    // **BEFORE**: 문자열을 받아와서, 타입 변환, 롱 타입으로
    // 파라미터 추출 기능: 쿼리스트링 형식(/todo/read?tno=100)으로 전달된 tno 값을 문자열로 받아와서 Long 타입으로 변환한다
    Long tno = Long.parseLong(req.getParameter("tno"));
    
    // **BEFORE**: TodoService 를 이용해서, 하나 조회하는 기능을 이용함.
    // 데이터 조회 기능: TodoService의 getTodoByTno() 메서드를 호출하여 특정 tno에 해당하는 Todo 정보를 DTO 형태로 반환받는다
    TodoDTO dto  = TodoService.INSTANCE.getTodoByTno(tno);
    
    // **BEFORE**: 화면에 데이터 탑재하기.
    // 화면 데이터 전달 기능: 조회된 Todo 정보를 "dto"라는 키로 request 객체에 저장하여 JSP 화면에서 접근할 수 있도록 한다
    req.setAttribute("dto",dto);
    
    // 화면 전달 기능: RequestDispatcher를 사용하여 /WEB-INF/todo/todoRead.jsp로 포워딩하여 상세 조회 화면을 렌더링한다
    req.getRequestDispatcher("/WEB-INF/todo/todoRead.jsp").forward(req,resp);

}
}

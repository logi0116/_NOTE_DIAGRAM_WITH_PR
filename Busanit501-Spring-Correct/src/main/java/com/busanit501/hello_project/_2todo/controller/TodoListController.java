package com.busanit501.hello_project._2todo.controller;

import com.busanit501.hello_project._2todo.dto.TodoDTO;
import com.busanit501.hello_project._2todo.service.TodoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "todoListController" , urlPatterns = "/todo/list")
public class TodoListController extends HttpServlet {
    // Todo 목록 조회 기능: GET 요청을 받아서 TodoService에서 전체 목록을 가져와 화면에 전달하는 컨트롤러
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("todoListController.doGet 호출, 목록 화면제공. ");

        // 데이터 조회 기능: TodoService의 getList() 메서드를 호출하여 전체 Todo 목록을 DTO 리스트 형태로 반환받는다
        List< TodoDTO> dtoList = TodoService.INSTANCE.getList();

        // 화면 데이터 전달 기능: 조회된 Todo 목록을 "list"라는 키로 request 객체에 저장하여 JSP 화면에서 접근할 수 있도록 한다
        req.setAttribute("list", dtoList);

        // 화면 전달 기능: RequestDispatcher를 사용하여 /WEB-INF/todo/todoList.jsp로 포워딩하여 실제 화면을 렌더링한다
        req.getRequestDispatcher("/WEB-INF/todo/todoList.jsp").forward(req, resp);
        // 아직 화면은 미구현.
    }

}


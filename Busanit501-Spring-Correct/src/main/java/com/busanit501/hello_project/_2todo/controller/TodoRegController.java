package com.busanit501.hello_project._2todo.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "todoRegController" , urlPatterns = "/todo/register")
public class TodoRegController extends HttpServlet {
    // **BEFORE**: 등록 화면 제공.
    // Todo 등록 화면 제공 기능: GET 요청을 받아서 Todo 등록 폼 화면을 제공하는 컨트롤러
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("TodoRegController.doGet 호출, 입력 화면제공. ");
        
        // **BEFORE**: 빌드 패턴으로 해당 객체에서, 사용하는 메서드를 연속적으로 사용하는 디자인 패턴형식
        // 화면 전달 기능: RequestDispatcher를 사용하여 /WEB-INF/todo/todoReg.jsp로 포워딩하여 등록 폼 화면을 렌더링한다
        req.getRequestDispatcher("/WEB-INF/todo/todoReg.jsp").forward(req, resp);
        // **BEFORE**: 아직 화면은 미구현.
    }
    
    // **BEFORE**: 등록 로직 처리,
    // Todo 등록 처리 기능: POST 요청을 받아서 등록 로직을 처리하고 목록 화면으로 리다이렉트하는 컨트롤러
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("TodoRegController.doPost 호출, 로직 처리 ");

        // **BEFORE**: 단순 화면 전환, 리다이렉트
        // 화면 전환 기능: PRG 패턴을 적용하여 등록 처리 후 /todo/list로 리다이렉트하여 목록 화면으로 이동한다
        resp.sendRedirect("/todo/list");
    }
    
}

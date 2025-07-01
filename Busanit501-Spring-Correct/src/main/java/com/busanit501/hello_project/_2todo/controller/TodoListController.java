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

// [C] 🏛️TodoListController: Todo 목록 조회를 담당하는 컨트롤러 클래스
// [T] 🏛️클래스, HttpServlet 상속, @WebServlet 어노테이션, 이름/구조 변경 가능
// [F] (MVC 패턴, +🟡) 서블릿 기반 컨트롤러로 HTTP 요청을 받아 비즈니스 로직과 뷰를 연결한다. 결과적으로 사용자 요청을 적절한 서비스로 위임하고 응답을 뷰로 전달하는 중재자 역할을 수행한다.
// [P] +🟡 사용자 요청 처리(Todo 목록 조회 요청을 받아 적절한 서비스로 위임) (F: 요청 처리, 사용자 편의)
// [R]
// ┌─────────────┬──────────────┬──────────────────────────────────────────────┐
// │ 🟡 연관     │ ➡️ 호출      │ 🛎️TodoService#getList(), 📨TodoDTO, 🎨todoList.jsp │
// │ 🟡 연관     │ ⬅️ 호출됨    │ 🌐브라우저(GET /todo/list)                      │
// └─────────────┴──────────────┴──────────────────────────────────────────────┘
// 레이어: [Controller][Service][DAO]
// 설명: 브라우저의 GET 요청을 받아 Todo 목록을 조회하고 JSP 화면으로 전달
@WebServlet(name = "todoListController" , urlPatterns = "/todo/list")
public class TodoListController extends HttpServlet {
    
    // [C] 🛠️doGet: HTTP GET 요청을 처리하는 메서드
    // [T] 🛠️메서드, void, 파라미터/로직 변경 가능
    // [F] (HTTP 메서드 처리, +🟡) HttpServlet의 doGet을 오버라이드하여 GET 요청만 처리한다. 결과적으로 RESTful한 설계로 GET 요청에 대한 명확한 응답을 제공한다.
    // [P] +🟡 GET 요청 처리(Todo 목록 조회 요청을 받아 서비스 호출 후 뷰 전달) (F: 요청 처리, 사용자 편의)
    // [R]
    // ┌─────────────┬──────────────┬──────────────────────────────────────────────┐
    // │ 🟡 연관     │ ➡️ 호출      │ 🛎️TodoService#getList(), 🎨RequestDispatcher │
    // │ 🟡 연관     │ ⬅️ 호출됨    │ 🌐브라우저(GET /todo/list)                      │
    // └─────────────┴──────────────┴──────────────────────────────────────────────┘
    // 레이어: [Controller][Service][View]
    // 설명: GET 요청을 받아 Todo 목록을 조회하고 JSP로 포워딩
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("todoListController.doGet 호출, 목록 화면제공. ");

        // [C] 📦dtoList: Todo 목록을 담는 DTO 리스트 변수
        // [T] 📦변수, List<TodoDTO>, 이름/타입 변경 가능
        // [F] (데이터 전달 객체, +🟡) 서비스에서 받은 Todo 데이터를 DTO 형태로 변환하여 뷰로 전달한다. 결과적으로 계층 간 데이터 전달 시 타입 안정성과 명확성을 보장한다.
        // [P] +🟡 데이터 임시 저장(서비스에서 받은 Todo 목록을 뷰로 전달하기 위한 임시 저장소) (F: 데이터 전달, 임시 저장)
        // [R]
        // ┌─────────────┬──────────────┬──────────────────────────────────────────────┐
        // │ 🟡 연관     │ ⬅️ 호출됨    │ 🛎️TodoService#getList()                      │
        // │ 🟡 연관     │ ➡️ 호출      │ 🎨todoList.jsp (request attribute로 전달)     │
        // └─────────────┴──────────────┴──────────────────────────────────────────────┘
        // 레이어: [Controller][Service][View]
        // 설명: 서비스에서 받은 Todo 목록을 JSP로 전달하기 위한 임시 저장소
        List< TodoDTO> dtoList = TodoService.INSTANCE.getList();

        // [C] 🛠️setAttribute: request 객체에 데이터를 저장하는 메서드 호출
        // [T] 🛠️메서드, void, 파라미터/로직 변경 가능
        // [F] (데이터 바인딩, +🟡) request 객체에 데이터를 키-값 쌍으로 저장하여 뷰에서 접근할 수 있게 한다. 결과적으로 컨트롤러와 뷰 간의 데이터 전달이 안전하고 명확해진다.
        // [P] +🟡 뷰 데이터 전달(조회된 Todo 목록을 "list" 키로 request에 저장하여 JSP에서 접근 가능하게 함) (F: 데이터 전달, 뷰 연결)
        // [R]
        // ┌─────────────┬──────────────┬──────────────────────────────────────────────┐
        // │ 🟡 연관     │ ➡️ 호출      │ 🎨todoList.jsp (EL ${list}로 접근)           │
        // └─────────────┴──────────────┴──────────────────────────────────────────────┘
        // 레이어: [Controller][View]
        // 설명: 조회된 Todo 목록을 "list" 키로 request에 저장하여 JSP에서 접근 가능하게 함
        req.setAttribute("list", dtoList);

        // [C] 🛠️forward: RequestDispatcher를 통한 화면 전달 메서드 호출
        // [T] 🛠️메서드, void, 파라미터/로직 변경 가능
        // [F] (서버 내 포워딩, +🟡) RequestDispatcher를 사용하여 서버 내에서 다른 리소스로 요청을 전달한다. 결과적으로 클라이언트에게 URL 변경 없이 다른 페이지를 보여줄 수 있다.
        // [P] +🟡 화면 렌더링(RequestDispatcher를 사용하여 todoList.jsp로 포워딩하여 화면 렌더링) (F: 화면 전달, 사용자 경험)
        // [R]
        // ┌─────────────┬──────────────┬──────────────────────────────────────────────┐
        // │ 🟡 연관     │ ➡️ 호출      │ 🎨/WEB-INF/todo/todoList.jsp                 │
        // └─────────────┴──────────────┴──────────────────────────────────────────────┘
        // 레이어: [Controller][View]
        // 설명: RequestDispatcher를 사용하여 todoList.jsp로 포워딩하여 화면 렌더링
        req.getRequestDispatcher("/WEB-INF/todo/todoList.jsp").forward(req, resp);
        // 아직 화면은 미구현.
    }

}


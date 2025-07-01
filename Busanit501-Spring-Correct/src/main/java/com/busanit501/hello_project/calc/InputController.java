package com.busanit501.hello_project.calc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "inputController", urlPatterns = "/calc/input")
public class InputController extends HttpServlet {
    // 화면을 제공하는 목적의 컨트롤러, doGet 이용하기.
    /*
    * 파라미터:
    HttpServletRequest req: 클라이언트 요청 정보를 담고 있는 객체
    HttpServletResponse resp: 클라이언트에게 응답을 보내기 위한 객체
    예외:
    ServletException: 서블릿 처리 중 발생할 수 있는 예외
    IOException: 입출력 처리 중 발생할 수 있는 예외
    * */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("inputController..doGet 처리함, 화면 제공용");
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/calc/input.jsp");
        rd.forward(req, resp);

    }
}




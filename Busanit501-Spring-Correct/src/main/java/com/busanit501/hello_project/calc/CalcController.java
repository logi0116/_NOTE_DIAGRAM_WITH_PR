package com.busanit501.hello_project.calc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "calcController", urlPatterns = "/calc/makeResult")
public class CalcController extends HttpServlet {
    // post 처리반, 로직처리 하기.
    // 사용자로부터 전달받은 숫자 2개를 단순 출력만하기.
    @Override
    protected  void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 웹, 파라미터로 전달 되는 모든 타입은 문자열이다. 100 와도, 문자열임.
        // req : 클라이언트 -> 서버에게 요청할 때, 사용하는 박스,
        // 여기에 숫자를 담아서 전달하니., 서버는 여기서 숫자를 꺼내고 있음.
        String num1 = req.getParameter("num1");
        String num2 = req.getParameter("num2");

        System.out.println("num1: " + num1);
        System.out.println("num2: " + num2);

        // 화면 제공 없음. 단순 , 콘솔 출력 밖에 없음. 일단.
        // 리다이렉트 기능을 통해서, 특정 화면으로 안내.
        // 1) 화면에 직접 안내  x 2) 서블릿을 통해서 화면을 안내.
        resp.sendRedirect("/index");
    }
}

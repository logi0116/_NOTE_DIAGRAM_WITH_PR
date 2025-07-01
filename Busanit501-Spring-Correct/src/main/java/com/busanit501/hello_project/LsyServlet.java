package com.busanit501.hello_project;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "lsyServlet", urlPatterns = "/lsy")
public class LsyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {
        // 클라이언트에게, html 문서를 전달하기.
        // 전달하는 형식,  PrintWriter 도구를 이용해서, 전달 할 예정.
        // 서버 -> 클라이언트에게 전달시, 응답 객체에 담아서 전달.
        resp.setContentType("text/html;charset=UTF-8");
        // 출력 도구, : html 문법을 문자열로 전달 할 예정,
        // 웹 브라우저(클라이언트) : 받아서, 자바스크립트 엔진을 그림을 그려주기.
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>이상용 서블릿 테스트</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>서버에서, 서블릿이라는 자바클래스, html 화면으로 변환해서 전달하기</h1>");
        out.println("<h2>나만의 서블릿 자바 클래스 만들어서, 테스트 연습 해보기.</h2>");
        out.println("</body>");
        out.println("</html>");
    }

}

package com.busanit501.hello_project.index;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "indexController", urlPatterns = "/index")
public class IndexController extends HttpServlet {
    // 화면 제공. ->
    @Override
    protected  void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("indexController- doGet 호출중, 화면 담당, 나의 구역은: /index");
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/index.jsp");
        rd.forward(req, resp);

    }
}

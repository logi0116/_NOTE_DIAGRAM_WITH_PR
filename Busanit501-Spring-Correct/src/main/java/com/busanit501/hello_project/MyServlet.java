package com.busanit501.hello_project;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;

// 서블릿을 정의하는 어노테이션
// name: 서블릿의 이름을 "myServlet"으로 지정
// urlPatterns: 이 서블릿에 접근할 수 있는 URL 경로를 "/my"로 지정
@WebServlet(name = "myServlet", urlPatterns = "/my")
public class MyServlet extends HttpServlet {  // HttpServlet 클래스를 상속받아 서블릿 클래스 생성

    // HTTP GET 요청을 처리하는 메서드 오버라이드
    // HttpServletRequest: 클라이언트의 요청 정보를 담고 있는 객체
    // HttpServletResponse: 클라이언트에게 응답을 보내기 위한 객체
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServerException, IOException {

        // 아래에 코드를 묶었는데,
        // 결론, 서버가 -> 클라이언트에게 html 이라는 문번의 템플릿 문자열로 전송해서,
        // -> 화면에 html 그리게 하기.
        // ==================================================================

        // 응답의 콘텐츠 타입을 HTML로 설정하고, 문자 인코딩을 UTF-8로 지정
        // 이 설정은 한글이 올바르게 표시되도록 하기 위해 중요함
        resp.setContentType("text/html;charset=UTF-8");

        // 응답 출력 스트림을 얻어옴
        // 이 스트림을 통해 클라이언트에게 HTML 콘텐츠를 전송
        PrintWriter out = resp.getWriter();

        // HTML 문서 생성 시작
        out.println("<html><body>");

        // H1 태그를 사용하여 "Hello World! 마이서블릿!!" 메시지를 출력
        // UTF-8 인코딩 설정으로 한글도 정상적으로 표시됨
        out.println("<h1>Hello World! 마이서블릿!!</h1>");

        // HTML 문서 종료 태그
        out.println("</body></html>");

        // PrintWriter는 자동으로 닫히므로 명시적으로 close()를 호출할 필요 없음
        // ==================================================================
    }
}

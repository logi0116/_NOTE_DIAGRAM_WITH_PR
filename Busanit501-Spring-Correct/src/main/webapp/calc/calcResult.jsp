<%--
  Created by IntelliJ IDEA.
  User: it
  Date: 25. 6. 20.
  Time: 오전 11:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>input.jsp에서 전달 받은 데이터를 출력 해보기</h1>
<h2>num1 : ${param.num1}</h2>
<h2>num1 : ${param.num2}</h2>
<p>웹의 파라미터는 모두 문자열로 구성,
jsp가 브라우저에서 전달하는 데이터를 구성은 쉽지만,  코드가 복잡하면, 부적합.
num1, num2, 문자열로 처리가 되어서,
    자바코드의 Wrapper 클래스등을 이용해서, 문자열을 숫자 형태로 변환을 해야함.</p>

<h1> 100 + 200</h1>
<h1> 문자열 합 : ${param.num1} + ${param.num2}</h1>
<h1> 문자열-> 숫자 변환 합 : ${Integer.parseInt(param.num1) + Integer.parseInt(param.num2)}</h1>
</body>
</html>

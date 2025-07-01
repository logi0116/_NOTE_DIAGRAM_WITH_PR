<%--
  Created by IntelliJ IDEA.
  User: it
  Date: 25. 6. 20.
  Time: 오전 11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%--action, 폼에서 입력한 데이터를 전달 할 곳 도착지,--%>
<%--method 전달 방식을 post, 데이터를 body 담아서 보내고, --%>
<%--get 과 비교하면, 공개되지 않는 데이터이다.--%>
<form action="calcResult.jsp" method="post">
    <input type="number" name="num1">
    <input type="number" name="num2">
    <button type="submit">SEND</button>
</form>
</body>
</html>

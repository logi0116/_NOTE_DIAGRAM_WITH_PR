<%--
  Created by IntelliJ IDEA.
  User: it
  Date: 25. 6. 23.
  Time: 오후 4:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>임시 하나 조회 화면</h1>
결과 확인 하는 방법 : http://localhost:8080/todo/read?tno=100
<div>tno : ${dto.tno}</div>
<div>title : ${dto.title}</div>
<div>dueDate : ${dto.dueDate}</div>
<div>finished : ${dto.finished}</div>
</body>
</html>

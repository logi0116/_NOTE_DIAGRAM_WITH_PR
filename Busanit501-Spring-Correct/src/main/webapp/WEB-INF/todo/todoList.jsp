<%--
  Created by IntelliJ IDEA.
  User: it
  Date: 25. 6. 23.
  Time: 오전 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--JSTL 설정 적용하기, 메타 태그 붙여넣기--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Todo List 임시 화면</h1>
<h2>서비스에서 만든 더미 데이터10개를, 컨트롤에서, 화면에 탑재했고,</h2>
<h2>탑재된 데이터를 , 화면에서 가져오기 연습. </h2>
<h2>이때, 사용하는 언어 EL 표기법을 이용하기.</h2>
<h2>컨트롤러에서, 탑재시 사용했던 키:값, 키로 이용하기. </h2>
${list}
<h2>JSTL 문법이용해서, html 화면 파일에,
    자바 코드를 조금 더 간결히 표현해서, 반복, 조건, 변수, 출력등 사용하기 위한 도구</h2>
<h2>메타 태그로, JSTL 설정 적용후, 위에 복붙했음. , 이용하기. </h2>
<ul>
    <c:forEach var="dto" items="${list}">
        <li>${dto}</li>
    </c:forEach>
</ul>

<h2>숫자 1 ~ 10으로 반복문 이용해서 출력해보기. JSTL 이용</h2>
<ul>
    <c:forEach var="num" begin="1" end="10">
        <li>${num}</li>
    </c:forEach>
</ul>

<h2> 리스트의 크기를 2로 나눈 나머지가 0이면, 짝수, 아니면 홀수 </h2>
<ul>
    <c:forEach var="dto" items="${list}">
        <li>${dto}</li>
        <c:if test="${dto.tno % 2 ==0}">
            짝수
        </c:if>
        <c:if test="${dto.tno % 2 !=0}">
            홀수
        </c:if>
    </c:forEach>
</ul>
<h3> choose 이용해서, if랑 else 효과 </h3>
<ul>
    <c:forEach var="dto" items="${list}">
        <c:choose>
            <c:when test="${dto.tno % 2 == 0}">
                <li> 짝수 , ${dto}</li>
            </c:when>
            <c:otherwise>
                <li>홀수, ${dto}</li>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</ul>

<h3>JSTL 변수 설정하고 사용하는 방법. </h3>
<c:set var="todoDTO" value="${list[0]}"/>

<c:forEach var="dto" items="${list}">
    <c:if test="${dto.tno == todoDTO.tno}">
        ${dto}
    </c:if>
</c:forEach>

</body>
</html>

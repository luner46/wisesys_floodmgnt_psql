<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>시스템 템플릿 정보</title>
</head>
<body>
<h2>템플릿 정보</h2>
<table border="1" cellpadding="8">
    <c:forEach var="entry" items="${info}">
        <tr>
            <th>${entry.key}</th>
            <td>${entry.value}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

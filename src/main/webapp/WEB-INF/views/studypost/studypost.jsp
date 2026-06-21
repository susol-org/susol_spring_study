<%--
  Created by IntelliJ IDEA.
  User: solmi
  Date: 2026-06-21
  Time: 오후 2:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>스터디 게시판</title>
</head>
<body>
    <h1>study post</h1>
    <hr>
    <c:choose>
        <c:when test="${not empty studyList}">
            <c:forEach var="study" items="${studyList}">
                <a href="<c:url value='/study/post/${study.study_id}' /> ">${study.study_id}번 스터디</a><br>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <h1>참여한 스터디가 없습니다.</h1>
        </c:otherwise>
    </c:choose>
</body>
</html>

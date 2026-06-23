<%--
  Created by IntelliJ IDEA.
  User: solmi
  Date: 2026-06-23
  Time: 오후 5:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>내 스터디노트~</title>
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    <h2>study note!</h2>
    <hr>
    <c:choose>
        <c:when test="${not empty studyNotes}">
            <table>
                <thead>
                    <tr>
                        <th>번호</th>
                        <th>작성자 아이디</th>
                        <th>제목</th>
                        <th>작성일자</th>
                        <th>스터디명</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="note" items="${studyNotes}">
                        <tr>
                            <td>${note.studyNoteId}</td>
                            <td>${note.writerEmailId}</td>
                            <td>${note.studyNoteTitle}</td>
                            <td>${note.studyNoteCreatedAt}</td>
                            <td>${note.studyTitle}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <h1>스터디 노트가 없어요.</h1>
        </c:otherwise>
    </c:choose>
</body>
</html>

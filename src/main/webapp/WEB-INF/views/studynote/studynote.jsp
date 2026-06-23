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
    <link rel="stylesheet" href="/css/studynote/studynote.css" />
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
                            <td><a href="/note/${note.studyNoteId}">${note.studyNoteTitle}</a></td>
                            <td>${note.studyNoteCreatedAt}</td>
                            <td>${note.studyTitle}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p class="empty">스터디 노트가 없어요.</p>
        </c:otherwise>
    </c:choose>

    <c:if test="${totalPages > 1}">
        <c:set var="groupStart" value="${currentPage - (currentPage % 5)}" />
        <c:set var="groupEnd" value="${groupStart + 4 < totalPages - 1 ? groupStart + 4 : totalPages - 1}" />

        <div class="pagination">
            <c:if test="${groupStart > 0}">
                <a href="/note?page=${groupStart - 1}">이전</a>
            </c:if>

            <c:forEach begin="${groupStart}" end="${groupEnd}" var="i">
                <c:choose>
                    <c:when test="${i == currentPage}">
                        <strong>${i + 1}</strong>
                    </c:when>
                    <c:otherwise>
                        <a href="/note?page=${i}">${i + 1}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${groupEnd < totalPages - 1}">
                <a href="/note?page=${groupEnd + 1}">다음</a>
            </c:if>
        </div>
    </c:if>
</body>
</html>

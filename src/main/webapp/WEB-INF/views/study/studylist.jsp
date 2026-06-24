<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>스터디 목록</title>
    <link rel="stylesheet" href="/css/studypost/studypostlist.css" />
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    <h1>스터디 목록</h1>
    <hr>
    <c:choose>
        <c:when test="${not empty studyList}">
            <table>
                <thead>
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>신청인원/모집인원</th>
                        <th>모집상태</th>
                        <th>등록일</th>
                        <th>모집마감일</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="study" items="${studyList}">
                        <tr>
                            <td>${study.studyId}</td>
                            <td><a href="<c:url value='/studies/${study.studyId}' />">${study.studyTitle}</a></td>
                            <td>${study.currentMemberCount} / ${study.maxMemberCount}</td>
                            <td>${study.recruitStatus}</td>
                            <td>${study.studyCreatedAt}</td>
                            <td>${study.studyEndDate}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p class="empty">등록된 게시물이 없습니다.</p>
        </c:otherwise>
    </c:choose>
    <div>
        <button onclick="location.assign('<c:url value="/studies/new" />');">스터디 만들기</button>
    </div>
</body>
</html>
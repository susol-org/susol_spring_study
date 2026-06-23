<%--
  Created by IntelliJ IDEA.
  User: solmi
  Date: 2026-06-18
  Time: 오후 4:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>스터디~</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/index.css">
</head>
<body>
    <header>
        <div class="header-left-container">
            <h1>SUSOL STUDY</h1>
        </div>
        <div class="header-right-container">
            <sec:authorize access="isAuthenticated()">
                <a href="/mypage" class="header-btn">마이페이지~</a>
                <form action="<c:url value='/auth/logout' />" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <button type="submit" class="header-btn">로그아웃~</button>
                </form>
            </sec:authorize>
            <sec:authorize access="isAnonymous()">
                <a href="<c:url value='/auth/login'/>" class="header-btn">로그인하러 가기</a>
            </sec:authorize>
        </div>
    </header>
    <section>
        <ul class="content-ul">
            <li>
                <a href="#">스터디 목록</a>
            </li>
            <li>
                <a href="<c:url value='/study/post' />">스터디 게시판</a>
            </li>
            <li>
                <a href="#">스터디 목록</a>
            </li>
            <li>
                <a href="#">스터디 목록</a>
            </li>
        </ul>
    </section>
    <footer>
        <p>권한은 <b>susol</b>에게 있어용</p>
        <p>maerong~</p>
    </footer>
</body>
</html>

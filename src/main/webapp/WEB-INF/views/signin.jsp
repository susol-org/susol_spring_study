<%--
  Created by IntelliJ IDEA.
  User: solmi
  Date: 2026-06-19
  Time: 오후 5:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>로그인</title>
</head>
<body>
    <div>
        <form action="<%= request.getContextPath() %>/auth/login" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <div>
                <label for="usernameInput">아이디</label>
                <input type="text" name="username" id="usernameInput" placeholder="아이디"><br>
            </div>
            <div>
                <label for="userPasswordInput">비밀번호</label>
                <input type="password" name="userPassword" id="userPasswordInput" placeholder="비밀번호"><br>
            </div>
            <button type="submit">로그인</button>
        </form>
    </div>
    <div>
        <a href="<%= request.getContextPath() %>/auth/signup">회원가입</a>
        <a href="<%= request.getContextPath() %>/auth/findId">아이디찾기</a>
        <a href="<%= request.getContextPath() %>/auth/findPassword">비밀번호찾기</a>
    </div>
</body>
</html>

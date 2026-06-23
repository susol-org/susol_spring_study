<%--
  Created by IntelliJ IDEA.
  User: solmi
  Date: 2026-06-19
  Time: 오후 4:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>마이페이지</title>
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    <ul>
        <li>아이디 : ${userInfo.userEmailId}</li>
        <li>나이 : ${userInfo.userAge}</li>
        <li>성별 : ${userInfo.userGender}</li>
        <li>한줄 소개 : ${userInfo.userShortBio}</li>
    </ul>
    <a href="<c:url value='/note' />">내 스터디 노트</a>
</body>
</html>

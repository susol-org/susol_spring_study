<%--
  Created by IntelliJ IDEA.
  User: solmi
  Date: 2026-06-21
  Time: 오후 4:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>스터디 게시물</title>
</head>
<h1>스터디 게시물</h1>
<hr>
<body>
    <c:choose>
        <c:when test="${not empty postList}">
            <table>
                <thead>
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>작성일</th>
                        <th>조회수</th>
                        <th>게시물 타입</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="post" items="${postList}">
                        <tr>
                            <td>${post.postId}</td>
                            <td>
                                <a href="<c:url value='/study/post/${post.postId}' />">${post.postTitle}</a>
                            </td>
                            <td>${post.postWriter}</td>
                            <td>${post.postCreatedAt}</td>
                            <td>${post.postViewCount}</td>
                            <td>${post.postType}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <h1>해당 스터디에 등록된 게시물이 없습니다.</h1>
        </c:otherwise>
    </c:choose>
</body>
</html>

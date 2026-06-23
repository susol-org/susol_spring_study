<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>스터디 게시물</title>
    <link rel="stylesheet" href="/css/studypost/studypostlist.css" />
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    <h1>스터디 게시물</h1>
    <hr>
    <c:choose>
        <c:when test="${not empty postList}">
            <table>
                <thead>
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>타입</th>
                        <th>작성자</th>
                        <th>작성일</th>
                        <th>조회수</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="post" items="${postList}">
                        <tr>
                            <td>${post.postId}</td>
                            <td><a href="<c:url value='/study/${studyId}/post/${post.postId}' />">${post.postTitle}</a></td>
                            <td>${post.postType}</td>
                            <td>${post.postWriter}</td>
                            <td>${post.postCreatedAt}</td>
                            <td>${post.postViewCount}</td>
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
        <button onclick="goPostWritePage();">게시글작성</button>
    </div>
    <script>
        const goPostWritePage = () => {
            location.assign("<c:url value='/study/${studyId}/post/write' /> ")
        }
    </script>
</body>
</html>
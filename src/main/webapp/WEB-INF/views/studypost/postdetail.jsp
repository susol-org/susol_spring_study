<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>게시물 상세</title>
</head>
<body>
    <h2>${postDetail.studyTitle}</h2>
    <hr>
    <h1>[${postDetail.postType}] ${postDetail.postTitle}</h1>
    <p>작성자: ${postDetail.writerEmailId} | 작성일: ${postDetail.postCreatedAt} | 조회수: ${postDetail.postViewCount}</p>
    <hr>
    <div>${postDetail.postContent}</div>
    <hr>
    <p>최종 수정일: ${postDetail.postUpdatedAt}</p>
    <a href="<c:url value='/study/${studyId}/post' />">목록으로</a>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>게시물 상세</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/github-dark.min.css">
</head>
<body>
    <h2>${postDetail.studyTitle}</h2>
    <hr>
    <h1>[${postDetail.postType}] ${postDetail.postTitle}</h1>
    <p>작성자: ${postDetail.writerEmailId} | 작성일: ${postDetail.postCreatedAt} | 조회수: ${postDetail.postViewCount}</p>
    <hr>
    <script id="contentData" type="application/json">${postDetail.postContent}</script>
    <div id="postContent"></div>
    <hr>
    <c:if test="${not empty postDetail.postUpdatedAt}">
        <p>최종 수정일: ${postDetail.postUpdatedAt}</p>
    </c:if>
    <a href="<c:url value='/study/${studyId}/post' />">목록으로</a>

    <script src="https://cdn.jsdelivr.net/npm/editorjs-html@3.4.3/build/edjsHTML.browser.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js"></script>
    <script>
        const data = JSON.parse(document.getElementById('contentData').textContent);
        const parser = edjsHTML();
        const html = parser.parse(data);
        document.getElementById('postContent').innerHTML = html.join('');
        hljs.highlightAll();
    </script>
</body>
</html>

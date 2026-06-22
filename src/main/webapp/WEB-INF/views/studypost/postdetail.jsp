<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>게시물 상세</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/github-dark.min.css">
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
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
    <c:if test="${not empty postDetail.postFiles}">
        <h3>첨부파일</h3>
        <ul>
            <c:forEach items="${postDetail.postFiles}" var="file">
                <li>
                    <a href="#" onclick="downloadFile('${file.renamedFileName}', '${file.originalFileName}'); return false;">
                        ${file.originalFileName}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${not empty postDetail.postUpdatedAt}">
        <p>최종 수정일: ${postDetail.postUpdatedAt}</p>
    </c:if>
    <a href="<c:url value='/study/${studyId}/post' />">목록으로</a>
    <c:if test="${updateAuth eq true}">
        <a href="<c:url value='/study/${studyId}/post/${postId}/update' />">수정하기</a>
        <a href="#" onclick="deletePost(event)">삭제하기</a>
    </c:if>

    <script src="https://cdn.jsdelivr.net/npm/editorjs-html@3.4.3/build/edjsHTML.browser.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js"></script>
    <script>
        const data = JSON.parse(document.getElementById('contentData').textContent);
        const parser = edjsHTML();
        const html = parser.parse(data);
        document.getElementById('postContent').innerHTML = html.join('');
        hljs.highlightAll();


        const downloadFile = async (renamedFileName, originalFileName) => {
            const response = await fetch('/file/' + renamedFileName);
            if (!response.ok) {
                alert('파일을 다운로드할 수 없습니다.');
                return;
            }
            const blob = await response.blob();
            const url = URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = originalFileName;
            a.click();
            URL.revokeObjectURL(url);
        };

        const deletePost = (e) => {
            e.preventDefault();

            const isDelete = confirm("삭제하시겠습니까?");

            if(isDelete === false) return;

            const url = "<c:url value='/study/${studyId}/post/${postId}' />";
            const header = document.querySelector("meta[name='_csrf_header']").getAttribute('content');
            const token = document.querySelector("meta[name='_csrf']").getAttribute('content');
            const redirectURL = "<c:url value='/study/${studyId}/post' />";

            console.log("header : " + header + " token : " + token);

            fetch(url, {
                method : "DELETE",
                headers : {
                    [header] : token
                }
            })
            .then(response => {
                if(!response.ok) {
                    alert("해당 게시물을 삭제할 수 없습니다.");
                } else {
                    location.assign(redirectURL);
                }
            })
            .catch(error => console.log(error))

        }
    </script>
</body>
</html>

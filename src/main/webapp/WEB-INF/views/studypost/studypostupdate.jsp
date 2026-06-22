<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>게시물 수정</title>
    <link rel="stylesheet" href="/css/studypost/studypostwrite.css">
</head>
<body>
    <h1>게시물 수정</h1>
    <hr>
    <form id="postForm" action="<c:url value='/study/${studyId}/post/${postId}/update' />" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <input type="hidden" name="postContent" id="postContent">

        <div class="form-row">
            <label for="postType">카테고리</label>
            <select name="postType" id="postType">
                <option value="NOTICE">공지</option>
                <option value="GENERAL">일반</option>
                <option value="QUESTION">질문</option>
            </select>
        </div>

        <div class="form-row">
            <label for="postTitle">제목</label>
            <input type="text" name="postTitle" id="postTitle" value="${updatingPost.postTitle}">
        </div>

        <div class="form-row">
            <label>내용</label>
            <div id="editor"></div>
        </div>

        <div class="btn-row">
            <button type="button" class="btn-cancel" onclick="history.back()">취소</button>
            <button type="submit" class="btn-submit">수정</button>
        </div>
    </form>

    <script id="contentData" type="application/json">${updatingPost.postContent}</script>
    <script src="https://cdn.jsdelivr.net/npm/@editorjs/editorjs@2.30.2"></script>
    <script src="https://cdn.jsdelivr.net/npm/@editorjs/header@2.8.1"></script>
    <script src="https://cdn.jsdelivr.net/npm/@editorjs/list@1.9.0"></script>
    <script src="https://cdn.jsdelivr.net/npm/@editorjs/code@2.9.0"></script>
    <script src="https://cdn.jsdelivr.net/npm/@editorjs/quote@2.6.0"></script>
    <script>
        document.getElementById('postType').value = '${updatingPost.postType}';

        const existingData = JSON.parse(document.getElementById('contentData').textContent);

        const editor = new EditorJS({
            holder: 'editor',
            data: existingData,
            tools: {
                header: {
                    class: Header,
                    config: { levels: [1, 2, 3], defaultLevel: 2 }
                },
                list: { class: List, inlineToolbar: true },
                code: { class: CodeTool },
                quote: { class: Quote, inlineToolbar: true }
            }
        });

        document.getElementById('postForm').addEventListener('submit', async function (e) {
            e.preventDefault();
            const data = await editor.save();
            document.getElementById('postContent').value = JSON.stringify(data);
            HTMLFormElement.prototype.submit.call(this);
        });
    </script>
</body>
</html>
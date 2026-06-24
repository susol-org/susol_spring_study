<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>스터디 노트 작성</title>
    <link rel="stylesheet" href="/css/studynote/studynotewrite.css">
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    <h1>스터디 노트 작성</h1>
    <hr>
    <form id="noteForm" action="/note/write" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <input type="hidden" name="studyNoteContent" id="studyNoteContent">

        <div class="form-row">
            <label for="studyId">스터디</label>
            <select name="studyId" id="studyId">
                <c:forEach var="study" items="${joinStudy}">
                    <option value="${study.studyId}">${study.studyTitle}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-row">
            <label for="studyNoteTitle">제목</label>
            <input type="text" name="studyNoteTitle" id="studyNoteTitle" placeholder="제목을 입력하세요">
        </div>

        <div class="form-row">
            <label>내용</label>
            <div id="editor"></div>
        </div>

        <div class="form-row">
            <label for="studyNoteVisibility">공개 범위</label>
            <select name="studyNoteVisibility" id="studyNoteVisibility">
                <option value="MEMBER">스터디 멤버 공개</option>
                <option value="PRIVATE">나만 보기</option>
            </select>
        </div>

        <div class="btn-row">
            <button type="button" class="btn-cancel" onclick="history.back()">취소</button>
            <button type="submit" class="btn-submit">작성</button>
        </div>
    </form>

    <script src="https://cdn.jsdelivr.net/npm/@editorjs/editorjs@2.30.2"></script>
    <script src="https://cdn.jsdelivr.net/npm/@editorjs/header@2.8.1"></script>
    <script src="https://cdn.jsdelivr.net/npm/@editorjs/list@1.9.0"></script>
    <script src="https://cdn.jsdelivr.net/npm/@editorjs/code@2.9.0"></script>
    <script src="https://cdn.jsdelivr.net/npm/@editorjs/quote@2.6.0"></script>
    <script>
        const editor = new EditorJS({
            holder: 'editor',
            tools: {
                header: {
                    class: Header,
                    config: { levels: [1, 2, 3], defaultLevel: 2 }
                },
                list: { class: List, inlineToolbar: true },
                code: { class: CodeTool },
                quote: { class: Quote, inlineToolbar: true }
            },
            placeholder: '내용을 입력하세요'
        });

        document.getElementById('noteForm').addEventListener('submit', async function (e) {
            e.preventDefault();
            const data = await editor.save();
            document.getElementById('studyNoteContent').value = JSON.stringify(data);

            await fetch(this.action, {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: new URLSearchParams(new FormData(this))
            });
            window.location.href = '/note';
        });
    </script>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>${studyNote.studyNoteTitle}</title>
    <link rel="stylesheet" href="/css/studynote/studynotedetail.css" />
</head>
<body>
    <%@ include file="../common/header.jsp" %>

    <div class="note-wrapper">
        <div class="note-meta">
            <span class="study-name">${studyNote.studyName}</span>
            <span class="visibility">${studyNote.studyNoteVisibility}</span>
        </div>

        <h1 class="note-title">${studyNote.studyNoteTitle}</h1>

        <div class="note-info">
            <span>작성자: ${studyNote.studyNoteWriter}</span>
            <span>작성일: ${studyNote.studyNoteCreatedAt}</span>
            <c:if test="${not empty studyNote.studyNoteUpdatedAt}">
                <span>수정일: ${studyNote.studyNoteUpdatedAt}</span>
            </c:if>
        </div>

        <hr>

        <script id="contentData" type="application/json">${studyNote.studyNoteContent}</script>
        <div id="noteContent" class="note-content"></div>

        <hr>

        <div class="note-actions">
            <a href="/note">목록으로</a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/editorjs-html@3.4.3/build/edjsHTML.browser.js"></script>
    <script>
        const raw = document.getElementById('contentData').textContent.trim();
        const container = document.getElementById('noteContent');

        try {
            const data = JSON.parse(raw);
            const parser = edjsHTML();
            container.innerHTML = parser.parse(data).join('');
        } catch (e) {
            container.innerText = raw;
        }
    </script>
</body>
</html>

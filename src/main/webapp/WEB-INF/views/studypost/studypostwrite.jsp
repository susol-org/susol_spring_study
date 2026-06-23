<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>게시물 작성</title>
    <link rel="stylesheet" href="/css/studypost/studypostwrite.css">
</head>
<body>
    <h1>게시물 작성</h1>
    <hr>
    <form id="postForm" action="<c:url value='/study/${studyId}/post' />" method="post" enctype="multipart/form-data">
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
            <input type="text" name="postTitle" id="postTitle" placeholder="제목을 입력하세요">
        </div>

        <div class="form-row">
            <label>내용</label>
            <div id="editor"></div>
        </div>

        <div class="form-row">
            <label>첨부파일</label>
            <div class="drop-zone" id="dropZone">
                <span class="drop-zone__prompt">파일을 드래그하거나 클릭하여 추가</span>
                <input type="file" name="uploadFiles" id="fileInput" multiple hidden>
            </div>
            <ul class="file-list" id="fileList"></ul>
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

        const dropZone = document.getElementById('dropZone');
        const fileInput = document.getElementById('fileInput');
        const fileList = document.getElementById('fileList');
        let selectedFiles = [];

        dropZone.addEventListener('click', () => fileInput.click());

        dropZone.addEventListener('dragover', (e) => {
            e.preventDefault();
            dropZone.classList.add('dragover');
        });

        dropZone.addEventListener('dragleave', () => dropZone.classList.remove('dragover'));

        dropZone.addEventListener('drop', (e) => {
            e.preventDefault();
            dropZone.classList.remove('dragover');
            addFiles(e.dataTransfer.files);
        });

        fileInput.addEventListener('change', () => addFiles(fileInput.files));

        function addFiles(files) {
            Array.from(files).forEach(file => {
                if (!selectedFiles.some(f => f.name === file.name && f.size === file.size)) {
                    selectedFiles.push(file);
                }
            });
            renderFileList();
        }

        function renderFileList() {
            fileList.innerHTML = '';
            selectedFiles.forEach((file, idx) => {
                const li = document.createElement('li');
                li.innerHTML = '<span>' + file.name + ' (' + (file.size / 1024).toFixed(1) + ' KB)</span>'
                            + '<button type="button" data-idx="' + idx + '">삭제</button>';
                fileList.appendChild(li);
            });
            fileList.querySelectorAll('button').forEach(btn => {
                btn.addEventListener('click', () => {
                    selectedFiles.splice(Number(btn.dataset.idx), 1);
                    renderFileList();
                });
            });
        }

        document.getElementById('postForm').addEventListener('submit', async function (e) {
            e.preventDefault();
            const data = await editor.save();
            document.getElementById('postContent').value = JSON.stringify(data);

            const formData = new FormData(this);
            formData.delete('uploadFiles');
            selectedFiles.forEach(file => formData.append('uploadFiles', file));

            await fetch(this.action, { method: 'POST', body: formData });
            window.location.href = '/study/${studyId}/post';
        });
    </script>
</body>
</html>
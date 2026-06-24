<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>스터디 개설</title>
</head>
<body>
    <h1>스터디 개설</h1>
    <hr>

    <form action="<c:url value='/studies' />" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <div>
            <label>제목</label>
            <input type="text" name="studyTitle" placeholder="스터디 제목" required>
        </div>
        <div>
            <label>소개</label>
            <textarea name="studyDescription" placeholder="스터디 소개" required></textarea>
        </div>
        <div>
            <label>카테고리</label>
            <select name="category">
                <option value="LANGUAGE">어학</option>
                <option value="CERTIFICATE">자격증</option>
                <option value="EMPLOYMENT">취업</option>
                <option value="PROGRAMMING">프로그래밍</option>
                <option value="HOBBY">취미</option>
                <option value="ETC">기타</option>
            </select>
        </div>
        <div>
            <label>모집 인원</label>
            <input type="number" name="maxMemberCount" min="1" required>
        </div>
        <div>
            <label>모집 마감일</label>
            <input type="datetime-local" name="studyEndDate" required>
        </div>
        <div>
            <button type="submit">개설하기</button>
        </div>
    </form>
</body>
</html>

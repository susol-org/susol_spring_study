<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>스터디 수정</title>
</head>
<body>
    <h1>스터디 수정</h1>
    <hr>

    <form action="<c:url value='/studies/${study.studyId}/update' />" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <div>
            <label>제목</label>
            <input type="text" name="studyTitle" value="${study.studyTitle}" required>
        </div>
        <div>
            <label>소개</label>
            <textarea name="studyDescription" required>${study.studyDescription}</textarea>
        </div>
        <div>
            <label>카테고리</label>
            <select name="category">
                <option value="LANGUAGE" ${study.category == 'LANGUAGE' ? 'selected' : ''}>어학</option>
                <option value="CERTIFICATE" ${study.category == 'CERTIFICATE' ? 'selected' : ''}>자격증</option>
                <option value="EMPLOYMENT" ${study.category == 'EMPLOYMENT' ? 'selected' : ''}>취업</option>
                <option value="PROGRAMMING" ${study.category == 'PROGRAMMING' ? 'selected' : ''}>프로그래밍</option>
                <option value="HOBBY" ${study.category == 'HOBBY' ? 'selected' : ''}>취미</option>
                <option value="ETC" ${study.category == 'ETC' ? 'selected' : ''}>기타</option>
            </select>
        </div>
        <div>
            <label>모집 인원</label>
            <input type="number" name="maxMemberCount" value="${study.maxMemberCount}" min="1" required>
        </div>
        <div>
            <label>모집 마감일</label>
            <input type="datetime-local" name="studyEndDate" value="${study.studyEndDate}" required>
        </div>
        <div>
            <button type="submit">수정하기</button>
        </div>
    </form>
</body>
</html>

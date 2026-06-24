<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>스터디 상세</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<body>
    <h1>스터디 상세</h1>
    <hr>

    <h2>${study.studyTitle}</h2>
    <p>카테고리: ${study.category}</p>
    <p>소개: ${study.studyDescription}</p>
    <p>신청인원/모집인원: ${study.currentMemberCount} / ${study.maxMemberCount}</p>
    <p>모집상태: ${study.recruitStatus}</p>
    <p>모집마감일: ${study.studyEndDate}</p>

    <%-- 리더만: 수정/삭제/마감/지원자 관리 --%>
    <c:if test="${isLeader}">
        <div>
            <button onclick="location.assign('<c:url value="/studies/${study.studyId}/update" />');">수정</button>
            <button onclick="closeStudy();">모집마감</button>
            <button onclick="deleteStudy();">삭제</button>
            <button onclick="location.assign('<c:url value="/studies/${study.studyId}/applications" />');">지원자 관리</button>
        </div>
    </c:if>

    <%-- 멤버(리더 포함): 멤버 관리(강퇴/위임/탈퇴) --%>
    <c:if test="${isMember}">
        <div>
            <button onclick="location.assign('<c:url value="/studies/${study.studyId}/members" />');">멤버 관리</button>
        </div>
    </c:if>

    <%-- 로그인했지만 멤버가 아닌 사람만: 지원 폼 --%>
    <c:if test="${loggedIn and not isMember}">
        <hr>
        <h3>스터디 지원</h3>
        <form action="<c:url value='/studies/${study.studyId}/applications' />" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <textarea name="message" placeholder="지원 동기를 작성하세요" required></textarea>
            <button type="submit">지원하기</button>
        </form>
    </c:if>

    <hr>
    <h3>멤버 목록</h3>
    <c:choose>
        <c:when test="${not empty study.members}">
            <table border="1" style="text-align: center;">
                <thead>
                    <tr>
                        <th>멤버번호</th>
                        <th>이름</th>
                        <th>역할</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="member" items="${study.members}">
                        <tr>
                            <td>${member.studyMemberId}</td>
                            <td>${member.memberName}</td>
                            <td>${member.role}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>멤버가 없습니다.</p>
        </c:otherwise>
    </c:choose>

    <div>
        <button onclick="location.assign('<c:url value="/studies" />');">목록으로</button>
    </div>

    <script>
        const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
        const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
        const studyId = "${study.studyId}";

        const deleteStudy = () => {
            if (!confirm("정말 삭제하시겠습니까?")) return;
            fetch("/studies/" + studyId, {
                method: "DELETE",
                headers: { [csrfHeader]: csrfToken }
            }).then(res => {
                if (res.ok) {
                    alert("삭제되었습니다.");
                    location.assign("/studies");
                } else {
                    alert("삭제 권한이 없습니다.");
                }
            });
        };

        const closeStudy = () => {
            fetch("/studies/" + studyId + "/close", {
                method: "PATCH",
                headers: { [csrfHeader]: csrfToken }
            }).then(res => {
                if (res.ok) {
                    alert("모집이 마감되었습니다.");
                    location.reload();
                } else {
                    alert("권한이 없습니다.");
                }
            });
        };
    </script>
</body>
</html>

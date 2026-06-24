<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>멤버 관리</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<body>
    <h1>멤버 관리</h1>
    <hr>

    <c:choose>
        <c:when test="${not empty memberList}">
            <table border="1" style="text-align: center;">
                <thead>
                    <tr>
                        <th>멤버번호</th>
                        <th>이름</th>
                        <th>역할</th>
                        <th>관리</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="member" items="${memberList}">
                        <tr>
                            <td>${member.studyMemberId}</td>
                            <td>${member.memberName}</td>
                            <td>${member.role}</td>
                            <td>
                                <button onclick="kick(${member.studyMemberId});">강퇴</button>
                                <button onclick="delegate(${member.studyMemberId});">리더위임</button>
                            </td>
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
        <button onclick="leave();">스터디 탈퇴</button>
        <button onclick="location.assign('<c:url value="/studies/${studyId}" />');">스터디로 돌아가기</button>
    </div>

    <script>
        const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
        const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
        const studyId = "${studyId}";

        const kick = (memberId) => {
            if (!confirm("정말 강퇴하시겠습니까?")) return;
            fetch("/studies/" + studyId + "/members/" + memberId, {
                method: "DELETE",
                headers: { [csrfHeader]: csrfToken }
            }).then(res => {
                if (res.ok) { alert("강퇴되었습니다."); location.reload(); }
                else { alert("권한이 없습니다."); }
            });
        };

        const delegate = (memberId) => {
            if (!confirm("이 멤버에게 리더를 위임하시겠습니까?")) return;
            fetch("/studies/" + studyId + "/members/" + memberId + "/delegate", {
                method: "PATCH",
                headers: { [csrfHeader]: csrfToken }
            }).then(res => {
                if (res.ok) { alert("리더를 위임했습니다."); location.reload(); }
                else { alert("권한이 없습니다."); }
            });
        };

        const leave = () => {
            if (!confirm("정말 탈퇴하시겠습니까?")) return;
            fetch("/studies/" + studyId + "/members/me", {
                method: "DELETE",
                headers: { [csrfHeader]: csrfToken }
            }).then(res => {
                if (res.ok) { alert("탈퇴했습니다."); location.assign("/studies"); }
                else { alert("탈퇴할 수 없습니다. (리더는 위임 후 탈퇴 가능)"); }
            });
        };
    </script>
</body>
</html>

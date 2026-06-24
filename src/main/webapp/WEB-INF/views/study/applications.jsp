<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>지원자 관리</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<body>
    <h1>지원자 관리</h1>
    <hr>

    <c:choose>
        <c:when test="${not empty applicationList}">
            <table border="1" style="text-align: center;">
                <thead>
                    <tr>
                        <th>지원번호</th>
                        <th>지원자</th>
                        <th>지원동기</th>
                        <th>상태</th>
                        <th>관리</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="app" items="${applicationList}">
                        <tr>
                            <td>${app.applicationId}</td>
                            <td>${app.username}</td>
                            <td>${app.message}</td>
                            <td>${app.applicationStatus}</td>
                            <td>
                                <button onclick="approve(${app.applicationId});">승인</button>
                                <button onclick="reject(${app.applicationId});">거절</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>지원자가 없습니다.</p>
        </c:otherwise>
    </c:choose>

    <div>
        <button onclick="location.assign('<c:url value="/studies/${studyId}" />');">스터디로 돌아가기</button>
    </div>

    <script>
        const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
        const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
        const studyId = "${studyId}";

        const approve = (applicationId) => {
            fetch("/studies/" + studyId + "/applications/" + applicationId + "/approve", {
                method: "PATCH",
                headers: { [csrfHeader]: csrfToken }
            }).then(res => {
                if (res.ok) { alert("승인되었습니다."); location.reload(); }
                else { alert("권한이 없습니다."); }
            });
        };

        const reject = (applicationId) => {
            fetch("/studies/" + studyId + "/applications/" + applicationId + "/reject", {
                method: "PATCH",
                headers: { [csrfHeader]: csrfToken }
            }).then(res => {
                if (res.ok) { alert("거절되었습니다."); location.reload(); }
                else { alert("권한이 없습니다."); }
            });
        };
    </script>
</body>
</html>

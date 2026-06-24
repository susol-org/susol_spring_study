<%--
  Created by IntelliJ IDEA.
  User: solmi
  Date: 2026-06-23
  Time: 오전 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>비밀번호 찾기</title>
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    <h1>비밀번호 찾기</h1>
    <hr>
    <label for="userEmailId">아이디 입력</label>
    <input type="text" name="userEmailId" id="userEmailId" placeholder="아이디입력"><br>
    <button onclick="checkExistingUser()">찾기</button>
    <script>
        const checkExistingUser = () => {
            const userEmailId = document.getElementById("userEmailId").value;

            if(!userEmailId) {
                alert("입력좀 해줘요");
                return;
            }

            const params = new URLSearchParams({
                emailId : userEmailId
            });

            fetch("<c:url value='/auth/password/exist?' />" + params.toString())
            .then(response => {
                if(!response.ok) {
                    alert("존재하지 않는 아이디입니다.")
                } else {
                    history.replaceState(null, "", "<c:url value='/auth/password/change' />");
                    window.location.href = "<c:url value='/auth/password/change' />";
                }
            })
            .catch(error => console.log(error));
        }
    </script>
</body>
</html>

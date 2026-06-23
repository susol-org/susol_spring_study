<%--
  Created by IntelliJ IDEA.
  User: solmi
  Date: 2026-06-23
  Time: 오전 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>비밀번호변경</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<body>
    <h1>비밀번호 변경</h1>
    <hr>
    <h2>변경할 아이디 : ${userEmailId}</h2>
    <label for="userPassword">변경할 비밀번호 : </label>
    <input type="password" name="userPassword" id="userPassword" placeholder="변경할 비밀번호"><br>
    <label for="userPasswordCheck">비밀번호 재입력 : </label>
    <input type="password" name="userPasswordCheck" id="userPasswordCheck" placeholder="비밀번호 확인">
    <label for="showPassword">비밀번호 볼랭</label>
    <input type="checkbox" id="showPassword"><br>
    <button onclick="changePassword()">변경하기</button>

    <script>
        const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
        const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");

        const changePassword = () => {
            const changePassword = document.getElementById("userPassword");
            const changePasswordCheck = document.getElementById("userPasswordCheck");

            if(!changePassword.value || !changePasswordCheck.value) {
                alert("입력되지 않은곳이 있습니다.");
                return;
            }

            if(changePassword.value !== changePasswordCheck.value) {
                alert("비밀번호를 확인해주세요 일치하지 않습니다.");
                return;
            }

            console.log(csrfHeader + " " + csrfToken);
            console.log(changePassword.value);

            fetch("<c:url value='/auth/password/change' />", {
                method : "PATCH",
                headers : {
                    "Content-Type" : "application/json",
                    [csrfHeader] : csrfToken
                },
                body : JSON.stringify({
                    password : changePassword.value
                })
            });
        }

        document.getElementById("showPassword").addEventListener("click", e => {
            const changePassword = document.getElementById("userPassword");
            const changePasswordCheck = document.getElementById("userPasswordCheck");

            if(e.target.checked) {
                changePassword.type = "text";
                changePasswordCheck.type = "text";
            } else {
                changePassword.type = "password";
                changePasswordCheck.type = "password";
            }
        });
    </script>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: solmi
  Date: 2026-06-23
  Time: 오전 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>아이디찾기</title>
</head>
<body>
    <p>이름, 나이, 성별로 찾기</p>
    <label for="usernameInput">이름</label>
    <input type="text" name="userName" id="usernameInput" placeholder="이름"><br>

    <label for="userageInput">나이</label>
    <input type="number" name="userAge" id="userageInput" placeholder="나이"><br>

    <label for="userGenderInputMale">남</label>
    <input type="radio" name="userGender" id="userGenderInputMale" value="MALE">
    <label for="userGenderInputFemale">여</label>
    <input type="radio" name="userGender" id="userGenderInputFemale" value="FEMALE"><br>
    <button onclick="findId()">찾기</button>

    <div id="result-container">

    </div>
    <script>
        const findId = () => {
            const name = document.getElementById('usernameInput').value;
            const age = document.getElementById('userageInput').value;
            const gender = document.querySelector("input[name='userGender']:checked")?.value;

            if(!gender || !age || !name) {
                alert("다 입력안함..");
                return;
            }

            const params = new URLSearchParams({
               userName : name,
               userAge : age,
               userGender : gender
            });

            fetch("<c:url value='/auth/user/findid' />?" + params.toString())
            .then(response => {
                if(!response.ok) {
                    alert("일치하는 회원정보가 없습니다.");
                } else {
                    return response.text();
                }
            })
            .then(data => {
                document.getElementById("result-container").innerHTML = data.toString();
            })
            .catch(error => console.log(error));
        }
    </script>
</body>
</html>

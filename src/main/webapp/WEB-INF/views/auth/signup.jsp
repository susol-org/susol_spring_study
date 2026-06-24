<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>회원가입</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/auth/signup.css">
</head>
<body>
<%@ include file="../common/header.jsp" %>
<div class="signup-card">
    <h2>회원가입</h2>
    <form id="signUpForm" action="<%= request.getContextPath() %>/auth/signup" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

        <div class="form-group">
            <label for="userEmailId">이메일</label>
            <input type="email" id="userEmailId" name="userEmailId" placeholder="example@email.com" />
            <span class="error-msg" id="emailError">올바른 이메일을 입력해주세요.</span>
            <span class="error-msg" id="duplicateError">중복체크를 완료해주세요.</span>
            <button type="button" onclick="duplicateCheckEmail()">중복체크</button>
        </div>

        <div class="form-group">
            <label for="userPassword">비밀번호</label>
            <input type="password" id="userPassword" name="userPassword" placeholder="8자 이상 입력" />
            <span class="error-msg" id="pwError">비밀번호는 8자 이상이어야 합니다.</span>
        </div>

        <div class="form-group">
            <label for="userName">이름</label>
            <input type="text" id="userName" name="userName" placeholder="이름을 입력하세요" />
            <span class="error-msg" id="nameError">이름을 입력해주세요.</span>
        </div>

        <div class="form-group">
            <label for="userAge">나이</label>
            <input type="number" id="userAge" name="userAge" placeholder="나이를 입력하세요" min="1" max="120" />
            <span class="error-msg" id="ageError">유효한 나이를 입력해주세요.</span>
        </div>

        <div class="form-group">
            <label>성별</label>
            <div class="gender-group">
                <label><input type="radio" name="gender" value="MALE" /> 남성</label>
                <label><input type="radio" name="gender" value="FEMALE" /> 여성</label>
            </div>
            <span class="error-msg" id="genderError">성별을 선택해주세요.</span>
        </div>

        <div class="form-group">
            <label for="userShortBio">자기소개 <span class="optional">(선택)</span></label>
            <textarea id="userShortBio" name="userShortBio" placeholder="간단한 자기소개를 입력하세요"></textarea>
        </div>

        <button type="submit" class="submit-btn">가입하기</button>
    </form>

    <div class="login-link">
        이미 계정이 있으신가요? <a href="<%= request.getContextPath() %>/auth/login">로그인</a>
    </div>
</div>

<script>
    let emailChecked = false;
    let emailAvailable = false;

    document.getElementById('userEmailId').addEventListener('input', function () {
        emailChecked = false;
        emailAvailable = false;
    });

    document.getElementById('signUpForm').addEventListener('submit', function (e) {
        const email    = document.getElementById('userEmailId').value.trim();
        const password = document.getElementById('userPassword').value;
        const name     = document.getElementById('userName').value.trim();
        const age      = parseInt(document.getElementById('userAge').value);
        const gender   = document.querySelector('input[name="gender"]:checked');

        let valid = true;

        function show(id, condition) {
            const el = document.getElementById(id);
            el.style.display = condition ? 'block' : 'none';
            if (condition) valid = false;
        }

        show('emailError',     !email || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email));
        show('duplicateError', !emailChecked || !emailAvailable);
        show('pwError',        password.length < 8);
        show('nameError',      !name);
        show('ageError',       !age || age < 1 || age > 120);
        show('genderError',    !gender);

        if (!valid) e.preventDefault();
    });

    const duplicateCheckEmail = () => {
        const userEmailId = document.getElementById('userEmailId').value;

        fetch("<c:url value='/auth/id/duplicate' />?userEmailId=" + userEmailId)
        .then(response => response.json())
        .then(data => {
            emailChecked = true;
            if (data.checkResult === true) {
                emailAvailable = false;
                alert("이미 사용 중인 이메일입니다.");
            } else {
                emailAvailable = true;
                alert("사용 가능한 이메일입니다.");
            }
        })
        .catch(error => console.log(error));
    }
</script>
</body>
</html>

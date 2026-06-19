<%--
  Created by IntelliJ IDEA.
  User: solmi
  Date: 2026-06-18
  Time: 오후 4:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<html>
<head>
    <title>스터디~</title>
    <style>
        /*body * {*/
        /*    border: 1px solid red;*/
        /*}*/
        header {
            display: flex;
        }
        section {
            height: 70%;
        }
        .header-left-container, .header-right-container {
            width: 100%;
            height: 100%;
        }
        .header-right-container {
            display: grid;
        }
        .align-right{
            text-align: right;
        }
        .mypage-a {
            text-align: right;
            color: magenta;
        }
        .content-ul {
            display: flex;
            justify-content: space-between;
            padding-left: 30px;
            padding-right: 30px;
        }
        a {
            text-decoration: none;
            color: black;
        }

        li {
            list-style: none;
        }
    </style>
</head>
<body>
    <header>
        <div class="header-left-container">
            <h1>SUSOL STUDY</h1>
        </div>
        <div class="header-right-container">
            <a href="/mypage" class="mypage-a">마이페이지~</a>
            <p class="align-right">사용자 : ${user.username}</p>
        </div>
    </header>
    <section>
        <ul class="content-ul">
            <li>
                <a href="#">스터디 목록</a>
            </li>
            <li>
                <a href="#">스터디 목록</a>
            </li>
            <li>
                <a href="#">스터디 목록</a>
            </li>
            <li>
                <a href="#">스터디 목록</a>
            </li>
        </ul>
    </section>
    <footer>
        <p>권한은 <b>susol</b>에게 있어용</p>
        <p>maerong~</p>
    </footer>
</body>
</html>

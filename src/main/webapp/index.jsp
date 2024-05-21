<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>삐뚤어진 오목게임</title>
<link rel="stylesheet" type="text/css" href="css/index.css">
<script src="js/index.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
    <div id="container">
        <div id="loginContainer">
            <h1>오목은 재밌을까?</h1>
            <form id="loginForm" action="login.do" method="post" onsubmit="return validateLoginForm()">
                <h2>로그인</h2>
                <input type="text" id="id" name="id" placeholder="ID"><br>
                <input type="password" id="pw" name="pw" placeholder="PW"><br>
                <a href="#" id="signupLink">회원가입</a>
                <button id="loginButton">로그인</button>
            </form>
            <form id="signupForm" action="signup.do" method="post" onsubmit="return validateLoginForm()" style="display: none;">
                <h2>회원가입</h2>
                <!-- 회원가입 폼 요소들을 추가합니다. -->
                <div class="inputGroup">
                    <input type="text" name="signUp_id" placeholder="ID" class="inputField" id="userId">
                    <button type="button" class="checkButton" id="checkButton">중복확인</button>
                </div>
                <div class="inputGroup">
                    <input type="password" name="signUp_pw" placeholder="PW" id="pw1">
                </div>
                <div class="inputGroup">
                    <input type="password" name="signUp_pw" placeholder="PW확인" id="pw2">
                </div>
                <span id="pwConfirm">비밀번호를 입력하세요</span><br>
                <div class="inputGroup">
                    <input type="text" name="signUp_name" placeholder="NAME">
                </div>
                <a href="#" id="signinLink">로그인</a>
                <!-- 회원가입 버튼 -->
                <button id="signupButton">가입하기</button>
            </form>
        </div>
    </div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h2>방 만들기</h2>
	<form id="createRoomForm" action="create.do">
	    <label for="roomName">방 이름:</label><br>
	    <input type="text" id="roomName" name="roomName"><br>
	    <label for="password">비밀번호:</label><br>
	    <input type="password" id="password" name="password"><br><br>
	    <input type="submit" value="방 만들기">
	    <input type="button" value="취소" onclick="cancelCreation()">
	</form>
</body>
</html>
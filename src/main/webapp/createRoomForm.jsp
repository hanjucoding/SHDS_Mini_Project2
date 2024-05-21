<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
	<h2>방 만들기</h2>
        <form id="createRoomForm">
            <label for="roomName">방 이름:</label><br>
            <input type="text" id="roomName" name="roomName"><br>
            <label for="password">비밀번호:</label><br>
            <input type="password" id="password" name="password"><br><br>
            <input type="submit" value="방 만들기" onclick="addRoomToList()">
            <input type="button" value="취소" onclick="cancelCreation()">
        </form>
</body>
</html>
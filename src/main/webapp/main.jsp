<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="omok.UserVO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>삐뚤어진 오목게임</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
<script src="js/main.js"></script>
</head>
<body>
<%
    HttpSession existingSession = request.getSession(false); // 기존에 존재하는 세션 반환, 없으면 null 반환
    if (existingSession == null || existingSession.getAttribute("loginSession") == null) {
        // 세션이 없으면 로그인 페이지로 리디렉션
        response.sendRedirect("index.jsp");
        return; // 이후 코드 실행 방지
    }
    
    UserVO user = (UserVO) existingSession.getAttribute("loginSession"); // 세션에서 유저 정보 가져오기
%>
	<div class="container">
        <div class="sidebar-left">
            <!-- 여기에 오목 방 목록 표출 -->
            <h2>오목 방 목록</h2><br>
            <ul id="roomList">
                <!-- 오목 방 목록이 동적으로 생성될 예정 -->
            </ul>
            <button id="createRoomButton">방만들기</button>
            <button id="enterRoomButton">방입장</button>
        </div>
        <div class="sidebar-right">
        	<div>
	            <!-- 여기에 접속한 유저들과 현재 사용자 정보 표출 -->
	            <h2>접속한 유저들</h2><br>
	            <ul id="userList">
	                <!-- 접속한 유저 목록이 동적으로 생성될 예정 -->
	            </ul><br>
	            <button id="userReset">새로고침</button>
	        </div>
            <div id="userInfo">
            	<h2>나의 정보</h2>
                <!-- 현재 사용자 정보가 동적으로 업데이트될 예정 -->
                <p id="id">아이디: <%= user.getId() %></p>
                <p id ="myInfo">
					닉네임:
					<span id="userName">
						<%=user.getName()%>
					</span>
				</p>
                <button id="logoutButton">로그아웃</button>
            </div>
        </div>
    </div>
    <script type="text/javascript">
		$(document).ready(function() {
			$("#userList").on("click", "div", function() {
				var id = $(this).text();
				console.log("선택된 id: " + id);
				
				 
				var url = "popUp.do?userId=" + encodeURIComponent(id);
				window.open(url, "pop", "width=400,height=260");
				
			});
			
			$("#myInfo").on("click", function() {
				var name = document.getElementById("userName").innerText;
				console.log("선택된 name: " + name);
					 
				var url = "popUp.do?userName=" + encodeURIComponent(name);
				window.open(url, "pop", "width=400,height=260");
				
			});
		});
	</script>
</body>
</html>
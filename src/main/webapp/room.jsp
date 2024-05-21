<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="omok.UserVO"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>삐뚤어진 오목게임</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/game.css">
<script src="js/game.js"></script>
<script type="text/javascript">
	window.onload = function() {
		stroke_board(); // 보드 그리기
		// 이벤트 핸들러 등록
		document.getElementById("mycanvas").onclick = put_stone; // 캔버스 클릭 핸들러 등록
		document.getElementById("start_button").onclick = start_game; // 시작 버튼 클릭 핸들러 등록
		document.getElementById("retire_button").onclick = retire_game;// 기권 버튼 클릭 핸들러 등록
		document.getElementById("back_button").onclick = delete_stone; // 무르기 버튼 클릭 핸들러 등록
		document.getElementById("exit_button").onclick = exit_game; // 종료 버튼 클릭 핸들러 등록
		// 선수 변경 라디오 버튼 핸들러 등록
		document.querySelectorAll("input[type=radio][name=who_first]")[0].onchange = function() {
			change_first();
		};
		// 선수 변경 라디오 버튼 핸들러 등록
		document.querySelectorAll("input[type=radio][name=who_first]")[1].onchange = function() {
			change_first();
		};

		// 엘리먼트 초기 상태 지정
		//document.getElementById("back_button").disabled = true; // 무르기 버튼 비활성화
		//document.getElementById("retire_button").disabled = true; // 기권 버튼 비활성화
		
		var roomInfo = document.getElementById("roomInfo").value;
		console.log(roomInfo);

	}
</script>
</head>
<body>
	<%
	HttpSession existingSession = request.getSession(false); // 기존에 존재하는 세션 반환, 없으면 null 반환
	String roomId = request.getParameter("roomId");
	String roomName = request.getParameter("roomName");
	String roomInfo = roomId + roomName;
	%>
	<div class="layout">
		<div class="left">
			<div style="margin: 30px;">
				<!-- 	  게임판 출력용 HTML5 Canvas 선언 -->
				<canvas id="mycanvas" width="640" height="640"></canvas>
			</div>
		</div>
		<div class="right">
			<div class="boxframe">
				<div class=box>
					<input id="blackfirst" type="radio" name="who_first" value="black"
						checked /> 흑 선수<br><br> <input id="whitefirst" type="radio"
						name="who_first" value="white" /> 백 선수 
				</div>
				<div class=box2>
					<input id="start_button" type="button" class="btn blue" value="시 작" />
					<input id="back_button" type="button"
						class="btn yellow small text-color-reverse" value="무르기" />
				</div>
				<div class=box3>
					<input id="retire_button" type="button" class="btn gray" onclick="loseButton()"
						value="기 권" />
				</div>
				<div class=box4>
					<!-- 	    게임 로그 출력용 textarea -->
					<textarea name="gamelog" rows="19" cols="35" readonly></textarea>
				</div>
				<div class=box5>
					<input id="exit_button" type="button" class="btn red" value="종 료" onclick="loseButton()"/>
				</div>
				<input type="hidden" id="roomInfo" value="<%= roomInfo %>">
			</div>
		</div>
	</div>
	 <script type="text/javascript">
	 $(document).ready(function(){
         // 버튼 클릭 이벤트 처리
         $("#loseButton").click(function(){
             alert("Button clicked!");
         });
     });
    </script>
</body>
</html>
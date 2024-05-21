$(document).ready(function() {
var webSocket = new WebSocket("ws://localhost:8090/omok/websocket");

webSocket.onopen = function(event) {
console.log("Connected to WebSocket.");
// 연결이 성공적으로 열린 후 10초마다 서버에 사용자 목록 요청
setInterval(function() {
webSocket.send("requestUserList");
webSocket.send("requestRoomList");
}, 1000); // 10초 마다 실행
};

webSocket.onmessage = function(event) {
console.log("Message from server: ", event.data);
var flag = event.data.substring(0, 4);
if (flag == "user") {
	updateUserList(event.data.substring(4));
} else if (flag == "room") {
	updateRoomList(event.data.substring(4))
}

};

webSocket.onclose = function(event) {
console.log("Disconnected from WebSocket.");
};

webSocket.onerror = function(event) {
console.error("WebSocket error: ", event);
};

function updateUserList(data) {
	console.log(data);
	var users = JSON.parse(data);
	var userListHtml = '';
	users.forEach(function(user) {
	userListHtml += '<div class="users">' + user + '</div>';
	});
	$('#userList').html(userListHtml);
}

function updateRoomList(data) {
	console.log(data);
	var rooms = JSON.parse(data);
	var roomListHtml = '';
	rooms.forEach(function(room) {
		var roomId = room.substring(0, 7); // 맨 앞 7자리 추출
        var roomName = room.substring(7); // 나머지 부분 추출
        roomListHtml += '<div class="rooms"><a href="enter.do?roomId=' + roomId + '&roomName=' + roomName + '">' + roomName + '</a></div>';
	});
	$('#roomList').html(roomListHtml);
}

// 로그아웃 버튼 이벤트 핸들러
$("#logoutButton").click(function() {
console.log("로그아웃");
webSocket.close();  // 웹소켓 연결을 먼저 닫습니다.

var userIdText = $("#id").text(); // "아이디: yjh"와 같은 형태의 텍스트를 가져옵니다.
var userId = userIdText.replace("아이디: ", "").trim(); // "아이디: " 문자열을 제거하고 공백을 정리합니다.

window.location.href = "logout.do?userId=" + encodeURIComponent(userId);  // 사용자 ID를 쿼리 스트링에 포함
});

$("#createRoomButton").click(function() {
	// Ajax를 이용하여 새로운 HTML 파일을 가져오는 요청 생성
	const xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState === XMLHttpRequest.DONE) {
			if (xhr.status === 200) {
			// 성공적인 응답을 받으면 HTML 파일의 내용을 기존 요소에 추가
			const sidebarRight = document.querySelector('.sidebar-left');
			sidebarRight.innerHTML = xhr.responseText;
			} else {
			// 오류 처리
			console.error('새로운 HTML 파일 가져오기 실패:', xhr.status);
			}
		}
	};
	
	// GET 요청으로 새로운 HTML 파일의 내용을 가져오는 요청 전송
	xhr.open('GET', 'createRoomJsp.jsp', true);
	xhr.send();
	});
});


// 취소 버튼 클릭 시 실행되는 함수
function cancelCreation() {
// 현재 페이지를 다시 로드하여 원래 페이지로 돌아갑니다.
location.reload();
}
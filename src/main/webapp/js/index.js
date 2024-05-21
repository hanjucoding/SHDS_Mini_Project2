window.onload = function() {
    // 회원가입 및 로그인 폼 전환 이벤트
    document.getElementById("signupLink").addEventListener("click", function(event) {
        event.preventDefault();
        document.getElementById("loginForm").style.display = "none";
        document.getElementById("signupForm").style.display = "flex";
    });

    document.getElementById("signinLink").addEventListener("click", function(event) {
        event.preventDefault();
        document.getElementById("loginForm").style.display = "flex";
        document.getElementById("signupForm").style.display = "none";
    });

    // ID 중복 확인
    document.getElementById('checkButton').addEventListener("click", checkUserId);

    // 비밀번호 일치 확인
    $('#pw1, #pw2').on('keyup', pwCheck);
    
    connectWebSocket();
    
    
};

function connectWebSocket() {
    // 서버의 웹소켓 엔드포인트 URL
    var webSocket = new WebSocket("ws://localhost:8090/omok/websocket");

    // 연결이 열리면 호출되는 이벤트
    webSocket.onopen = function(event) {
        console.log("Connected to WebSocket server.");
        // 여기서 추가적인 초기화나 메시지 전송 등의 작업을 수행할 수 있습니다.
    };

    // 메시지를 받았을 때 호출되는 이벤트
    webSocket.onmessage = function(event) {
        console.log("Message from server: " + event.data);
    };

    // 연결이 닫혔을 때 호출되는 이벤트
    webSocket.onclose = function(event) {
        console.log("Disconnected from WebSocket server.");
    };

    // 에러가 발생했을 때 호출되는 이벤트
    webSocket.onerror = function(event) {
        console.error("WebSocket error observed:", event);
    // 추가적으로 에러 이벤트의 세부 속성을 로깅할 수 있습니다.
    if (event instanceof ErrorEvent) {
        console.error("WebSocket error message:", event.message);
    }
    };
}

// ID 중복 확인 함수
function checkUserId() {
    var userId = document.getElementById('userId').value;
    $.ajax({
        type: "POST",
        url: "checkout",
        data: {id: userId},
        success: function(response) {
            if (response == '0') {
                alert('사용가능한 아이디 입니다.');
            } else {
                alert('중복된 아이디 입니다.');
                document.getElementById('userId').value = '';
            }
        },
        error: function(e) {
            console.log(e);
        }
    });
}

// 비밀번호 검증 함수
function pwCheck(){
    if($('#pw1').val() == $('#pw2').val()){
        $('#pwConfirm').text('비밀번호 일치').css('color', 'blue');
    } else {
        $('#pwConfirm').text('비밀번호 불일치').css('color', 'red');
    }
}

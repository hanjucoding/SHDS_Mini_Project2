window.onload = function () {
    canvas = document.getElementById('canvas');
    ctx = canvas.getContext('2d');
    const margin = 30;
    const cw = (ch = canvas.width = canvas.height = 600 + margin * 2);
    console.log(cw);
    const row = 18; // 바둑판 선 개수
    const rowSize = 600 / row; // 바둑판 한 칸의 너비
    const dolSize = 13;  // 바둑돌 크기
    let count = 0;
    let board = new Array(Math.pow(row + 1, 2)).fill(-1); // 144개의 배열을 생성해서 -1로 채움
    let checkDirection = [ //승리조건
      [1, -1],
      [1, 0],
      [1, 1],
      [0, 1],
      [-1, 1],
      [-1, 0],
      [-1, -1],
      [0, -1],
    ];
  
    draw(); // 시작하면서 빈 바둑판 그리기
  
    // 배열을 콘솔창에 grid로 보여주는 함수.
    // 코딩하면서 바둑판이 어떻게 그려지는지 콘솔창에서 확인하려는 목적이고, 게임과는 관계 없음.
    function indexView(m) {
      let s = '\n';
      let c = 0;
      for (let e of m) {
        s += `${e} `;
        if (c % (row + 1) === row) s += '\n'; //줄바꿈 문자 삽입 
        c++;
      }
      return s;
    }
  
    // x,y 좌표를 배열의 index값으로 변환
    let xyToIndex = (x, y) => {
      return x + y * (row + 1);
    };
  
    // 배열 index값을 x,y좌표로 변환
    let indexToXy = (i) => {
      w = Math.sqrt(board.length);
      x = i % w;
      y = Math.floor(i / w);
      return [x, y];
    };
  
    // 바둑판 그리기 함수
    function draw() {
      ctx.fillStyle = '#e38d00';
      ctx.fillRect(0, 0, cw, ch);
      for (let x = 0; x < row; x++) {
        for (let y = 0; y < row; y++) {
          let w = (cw - margin * 2) / row;
          ctx.strokeStyle = 'black';
          ctx.lineWidth = 1;
          ctx.strokeRect(w * x + margin, w * y + margin, w, w);
        }
      }
  
      // 화점에 점 찍기
      for (let a = 0; a < 3; a++) {
        for (let b = 0; b < 3; b++) {
          ctx.fillStyle = 'black';
          ctx.lineWidth = 1;
          ctx.beginPath();
          ctx.arc(
            (3 + a) * rowSize + margin + a * 5 * rowSize,
            (3 + b) * rowSize + margin + b * 5 * rowSize,
            dolSize / 3,
            0,
            Math.PI * 2
          );
          ctx.fill();
        }
      }
    }
    winShow = (x) => {
		alert(x + "승리!");
	};
  
    // 방금 둔 바둑돌에 사각 표시
    drawRect = (x, y) => {
      let w = rowSize/2;
      ctx.strokeStyle = 'red';
      ctx.lineWidth = 3;
      ctx.strokeRect(
        x * rowSize + margin - w,
        y * rowSize + margin - w,
        w + rowSize/2,
        w + rowSize/2
      );
    };
  
    //바둑알 그리기. 실제로는 바둑판까지 매번 통째로 그려줌
    drawCircle = (x, y) => {
      draw();
      drawRect(x, y);
      for (i = 0; i < board.length; i++) {
        // 모든 눈금의 돌의 유무,색깔 알아내기
        let a = indexToXy(i)[0];
        let b = indexToXy(i)[1];
  
        if (board[xyToIndex(a, b)] == 1) {
          ctx.fillStyle = 'black';
          ctx.beginPath();
          ctx.arc(
            a * rowSize + margin,
            b * rowSize + margin,
            dolSize,
            0,
            Math.PI * 2
          );
          ctx.fill();
        }
        if (board[xyToIndex(a, b)] == 2) {
          ctx.fillStyle = 'white';
          ctx.beginPath();
          ctx.arc(
            a * rowSize + margin,
            b * rowSize + margin,
            dolSize,
            0,
            Math.PI * 2
          );
          ctx.fill();
        }
      }

  
      checkWin(x, y); // 돌이 5개 연속 놓였는지 확인 함수 실행
    };
  
    // 승패 판정 함수
    function checkWin(x, y) {
      let thisColor = board[xyToIndex(x, y)]; // 마지막 둔 돌의 색깔이 1(흑),2(백)인지...
      //가로,세로와 대각선 두 방향, 총 네 방향 체크
      for (k = 0; k < 4; k++) {
        winBlack = 1;   winWhite = 1;
        // 놓여진 돌의 양쪽 방향으로
        for (j = 0; j < 2; j++) {
          // 5개씩의 돌의 색깔을 확인
          for (i = 1; i < 5; i++) {
            let a = x + checkDirection[k + 4 * j][0] * i;
            let b = y + checkDirection[k + 4 * j][1] * i;
            if (board[xyToIndex(a, b)] == thisColor) {
              // 색깔에 따라서 흑,백의 숫자를 하나씩 증가
              switch (thisColor) {
                case 1: winBlack++; break;
                case 2: winWhite++; break;
              }
            } else { break; }
          }
        }
        // 연속 돌이 5개이면 승리
        if (winBlack == 5) {winShow(1);}
        if (winWhite == 5) {winShow(2);}
      }
    } // 승리확인 함수 끝
  
    // 마우스 클릭한 위치를 정확한 눈금 위치로 보정
    document.addEventListener('mouseup', (e) => {
	      if (e.target.id == 'canvas') {
	        // 마진을 고려하여 클릭 좌표를 바둑판 그리드에 맞게 조정
	        let x = Math.floor((e.offsetX - margin) / rowSize * 0.75);
	        let y = Math.floor((e.offsetY - margin) / rowSize * 1.1);
	
	        console.log(e.offsetX, e.offsetY, x, y); // 디버깅을 위해 좌표 출력
	
	        // 캔버스의 유효 영역 내에서만 돌을 놓도록 검사
	        if (x >= 0 && x < row && y >= 0 && y < row) {
	            if (board[xyToIndex(x, y)] != -1) {
	                alert('그곳엔 둘 수 없습니다.');
	            } else {
	                // 턴에 따라 흑돌 또는 백돌 놓기
	                board[xyToIndex(x, y)] = count % 2 == 0 ? 1 : 2;
	                count++;
	                drawCircle(x, y); // 돌을 그리고 게임 상태를 업데이트
	            }
	        }
		}
    });
  };;
  
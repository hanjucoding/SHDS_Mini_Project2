package omok;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import socket.WebSocketServer;

@WebServlet("/create.do")
public class CreateRoom extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sess = request.getSession();
		String sessionId = sess.getId();
		String roomName = request.getParameter("roomName");
		UserVO vo = (UserVO) sess.getAttribute("loginSession");
		String roomId = sessionId.substring(sessionId.length() - 7);
		request.setAttribute("roomId", roomId); // 세션 아이디 뒤 7자리만 잘라서 저장
		
		System.out.println(roomId + roomName);
		WebSocketServer.createRoom(roomId + roomName, vo, sess);
		response.sendRedirect("room.jsp?roomId=" + roomId + "&roomName=" + roomName);
	}

}

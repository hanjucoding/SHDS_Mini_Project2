package omok;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import socket.WebSocketServer;

@WebServlet("/enter.do")
public class EnterRoom extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		HttpSession sess = request.getSession();
		String roomId = (String) request.getParameter("roomId");
		String roomName = (String) request.getParameter("roomName");
		UserVO vo = (UserVO) sess.getAttribute("loginSession"); // 세션에서 유저 정보 가져오기
		System.out.println(roomId);
		boolean flag = WebSocketServer.enterRoom(roomId + roomName, vo, sess);
		
		if (flag) {
			response.sendRedirect("room.jsp?roomId=" + roomId + "&roomName=" + roomName);
		} else {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('방에 자리가 없습니다.');");
			out.println("location.href='main.jsp';");
			out.println("</script>");
		}
	}

}
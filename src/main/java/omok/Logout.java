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

@WebServlet("/logout.do")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sess = request.getSession();
		
		//웹소켓에서 로그아웃처리를 해야함
		WebSocketServer.delUser(sess, request.getParameter("userId"));
		sess.invalidate(); //세션 삭제
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('로그아웃 되었습니다.')");
		out.println("location.href='index.jsp';");
		out.println("</script>");
	}

}

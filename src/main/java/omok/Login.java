package omok;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import socket.WebSocketServer;


@WebServlet("/login.do")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		UserDAO dao = new UserDAO();
		UserVO vo = new UserVO();
		vo.setId(id);
		vo.setPw(pw);
		UserVO loginVO = dao.login(vo);
		
		if (loginVO == null) {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('아이디, 비밀번호가 올바르지 않습니다.');");
			out.println("location.href='index.jsp';");
			out.println("</script>");
		} else if (!WebSocketServer.checkLogged(id)) {
			//로그인 리스트에 존재함 (로그아웃처리가 되지 않음)
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('이미 로그인 중 입니다.');");
			out.println("location.href='index.jsp';");
			out.println("</script>");
		} else {
			//세션에 객체 저장
			PrintWriter out = response.getWriter();
			HttpSession httpSession = request.getSession();
            httpSession.setAttribute("loginSession", loginVO);
            WebSocketServer.registerUser(httpSession.getId(), loginVO);
            request.getSession().setAttribute("loginSession", loginVO);
			response.sendRedirect("main.jsp");
		}
		
		
	}

}

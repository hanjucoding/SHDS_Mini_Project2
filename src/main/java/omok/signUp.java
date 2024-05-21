package omok;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signup.do")
public class signUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String id = request.getParameter("signUp_id");
		String pw = request.getParameter("signUp_pw");
		String name = request.getParameter("signUp_name");
		
		UserDAO dao = new UserDAO();
		UserVO vo = new UserVO();
		if ("".equals(id) || "".equals(pw) || "".equals(name)) {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('모든 항목은 필수입니다.');");
			out.println("location.href='index.jsp';");
			out.println("</script>");
		}
		System.out.println(id + " " + pw + " " + name);
		vo.setId(id);
		vo.setPw(pw);
		vo.setName(name);
		System.out.println(vo.getId() + " " + vo.getPw() + " " + vo.getName());
		
		boolean check = dao.addUser(vo);
		if (check) {
			PrintWriter out = response.getWriter();
			System.out.println("중복");
			out.println("<script>");
			out.println("alert('이미 사용중인 아이디 입니다.');");
			out.println("</script>");
		} else {
			response.sendRedirect("index.jsp");
		}
	}

}

package omok;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/checkout")
public class CheckOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("id");
        
        System.out.println("checkout servlet아이디 :"+userId);
        
        UserDAO dao = new UserDAO();
        int isAvailable = dao.checkUserId(userId);
        System.out.println(isAvailable);
        
        response.setContentType("text/plain;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        out.print(isAvailable);
	}

}

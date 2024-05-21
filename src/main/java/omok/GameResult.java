package omok;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/GameResult.do")
public class GameResult extends HttpServlet {
private static final long serialVersionUID = 1L;

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
EndGame(request, response);
}

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

EndGame(request, response);
}
protected void EndGame(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
response.setContentType("text/html;charset=utf-8");

UserVO vo = (UserVO) request.getSession().getAttribute("loginSession");
String id = vo.getId();
System.out.println(id);
String result = request.getParameter("result");

RecordDAO dao = new RecordDAO();
if(result.equals("win")) {
dao.Add_Win(id);
}else if(result.equals("lose")){
dao.Add_Lose(id);
}else {
dao.Add_Draw(id);
}

PrintWriter out = response.getWriter();
out.println("<script>");
out.println("alert('게임 종료! 메인화면으로 돌아갑니다');");
out.println("location.href='main.jsp';");
out.println("</script>");

}

}
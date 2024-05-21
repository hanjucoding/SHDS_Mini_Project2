package omok;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/popUp.do")
public class PopupInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String userId = null;
		String userName = null;
		userId = request.getParameter("userId");
        userName = request.getParameter("userName");
        
        RecordDAO dao = new RecordDAO();
        RecordVO vo = new RecordVO();
        if(userId==null && userName!=null) {
        	System.out.println("searchbyname");
        	vo = dao.getRecordInfoByName(userName);
        }else if(userId!=null && userName==null) {
        	System.out.println("searchbyid");
        	UserDAO Udao = new UserDAO();
        	vo = dao.getRecordInfoById(userId);
        	userName = Udao.getNameById(userId);
        }
        
        
        
        
        int Ranking = dao.Get_Ranking(vo.getId());
        String bodyStyle = "body { background-color:  rgba(132, 206, 255, 0.747);  \n"
        				 +"text-align: center; }";
        String h1Style="h1 {padding: 5px 15px; \n"
        				+"background: #eee;\n"
        				+"border-radius: 10px;\n"
        				+"font-size: 1.1rem;}";
        
        String pStyle="p {padding: 5px 15px; \n"
						+"background: #eee;\n"
						+"border-radius: 10px;\n"
						+"font-size: 1.1rem;}";
        
        String buttonStyle="button{  width: 100px; \n"
        		+ "height:30px;}";
        
        double rate = Math.round(vo.getWin()/(double)(vo.getGames()-vo.getDraw())*100.0);
   
        
      
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>User Info</title>");
        out.println("<style>");
        out.println(bodyStyle);
        out.println(h1Style);
        out.println(pStyle);
        out.println(buttonStyle);
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
		out.println("<h1>"+userName+"</h1>");
		out.println("<p>"+vo.getGames()+"전 "+ vo.getWin() +"승 "+
					vo.getDraw() +"무 "+ vo.getLose() +"패</p>");
		out.println("<p>승률 "+rate+"%</p>");
		out.println("<p>");
		out.println("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-trophy\" viewBox=\"0 0 16 16\">");
		out.println("<path d=\"M2.5.5A.5.5 0 0 1 3 0h10a.5.5 0 0 1 .5.5q0 .807-.034 1.536a3 3 0 1 1-1.133 5.89c-.79 1.865-1.878 2.777-2.833 3.011v2.173l1.425.356c.194.048.377.135.537.255L13.3 15.1a.5.5 0 0 1-.3.9H3a.5.5 0 0 1-.3-.9l1.838-1.379c.16-.12.343-.207.537-.255L6.5 13.11v-2.173c-.955-.234-2.043-1.146-2.833-3.012a3 3 0 1 1-1.132-5.89A33 33 0 0 1 2.5.5m.099 2.54a2 2 0 0 0 .72 3.935c-.333-1.05-.588-2.346-.72-3.935m10.083 3.935a2 2 0 0 0 .72-3.935c-.133 1.59-.388 2.885-.72 3.935M3.504 1q.01.775.056 1.469c.13 2.028.457 3.546.87 4.667C5.294 9.48 6.484 10 7 10a.5.5 0 0 1 .5.5v2.61a1 1 0 0 1-.757.97l-1.426.356a.5.5 0 0 0-.179.085L4.5 15h7l-.638-.479a.5.5 0 0 0-.18-.085l-1.425-.356a1 1 0 0 1-.757-.97V10.5A.5.5 0 0 1 9 10c.516 0 1.706-.52 2.57-2.864.413-1.12.74-2.64.87-4.667q.045-.694.056-1.469z\"/>");
		out.println("</svg>");
		out.println("Ranking "+Ranking+"위</p>");
		out.println("<button onclick='window.close()'>창 닫기</button>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}



}

package omok;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

@WebServlet("/logged")
public class Logged extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // 세션에서 로그인 정보 가져오기
	    HttpSession session = request.getSession();
	    String username = (String) session.getAttribute("username");
	    
	    // 유저 목록 데이터를 가져오는 로직
	    List<String> userList = getActiveUsers(request);
	    
	    // 데이터를 JSON 형태로 변환하여 응답
	    JSONObject responseObject = new JSONObject();
	    responseObject.put("loggedInUser", username);
	    responseObject.put("userList", userList);
	    
	    response.setContentType("application/json");
	    response.getWriter().write(responseObject.toString());
	}

    // 현재 활성화된 세션에서 사용자 목록을 가져오는 메소드
    public List<String> getActiveUsers(HttpServletRequest request) {
        List<String> activeUsers = new ArrayList<>();
        Enumeration<String> sessionNames = request.getSession().getAttributeNames();
        while (sessionNames.hasMoreElements()) {
            String attributeName = sessionNames.nextElement();
            if (attributeName.startsWith("user_")) {
                activeUsers.add(attributeName.substring(5)); // "user_"를 제외한 부분을 사용자 이름으로 추가
            }
        }
        return activeUsers;
	}


}

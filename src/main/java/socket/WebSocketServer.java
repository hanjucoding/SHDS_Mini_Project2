package socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import omok.UserVO;

@ServerEndpoint("/websocket")
public class WebSocketServer {
	
	//테스트
    private static Map<String, Session> sessionMap = new ConcurrentHashMap<>();
    private static Map<String, String> userSessionMap = new ConcurrentHashMap<>();
    //유저목록
    private static List<String> userList = new ArrayList<>();
    //방목록
    private static Map<String, ArrayList<UserVO>> rooms = new ConcurrentHashMap<>();
    private static Map<String, ArrayList<Session>> forOmok = new ConcurrentHashMap<>();
    //룸에 세션id 넣으면
    private static Map<String, String> currentPlayerMap = new ConcurrentHashMap<>();
    private static Map<String, String[][]> boardMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("New connection: " + session.getId());
        sessionMap.put(session.getId(), session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("Message received: " + message);
        // 메시지가 좌표인지, 명령인지 구분
        switch (message) {
            case "requestUserList":
                sendUserList(session);
                break;
            case "requestRoomList":
                sendRoomList(session);
                break;
            default:
                // 추가 명령 처리
            	//1. 게임시작
            	if (message.contains("Game Start At : ")) {
            		String roomId = message.substring(16);
            		Session blackP = forOmok.get(roomId).get(0);
            		Session whiteP = forOmok.get(roomId).get(1);
            		System.out.println(roomId);
            		System.out.println(rooms.get(roomId));
            		blackP.getBasicRemote().sendText("Set Stone : black");
            		blackP.getBasicRemote().sendText("Set Current : black");
            		whiteP.getBasicRemote().sendText("Set Stone : white");
            		whiteP.getBasicRemote().sendText("Set Current : black");
            	} else if (message.contains("Set Current : ")) {
            		String str = message.replace("Set Current : ", ",");
            		String[] array = str.split(",");
            		System.out.println(array[0] + " " + array[1]);
            		Session blackP = forOmok.get(array[0]).get(0);
            		Session whiteP = forOmok.get(array[0]).get(1);
            		blackP.getBasicRemote().sendText("Set Current : " + array[1]);
            		whiteP.getBasicRemote().sendText("Set Current : " + array[1]);
            	} else if (message.contains("Chat : ")) {
            		
            	} else if (message.contains("StonePlace : ")) {
            		System.out.println("소켓에서 받은 요청 : " + message);
            		Session oppnsess = null;
            		String roomInfo = "";
            		String str = message.replace("StonePlace : ", ",");
            		String[] data = str.replace(roomInfo, "").split(",");
            		roomInfo = data[0];
            		//상대 유저에게 보내야함
            		ArrayList<Session> list = forOmok.get(roomInfo);
            		for(int i = 0; i < forOmok.get(roomInfo).size(); i++) {
            			if (!session.getId().equals(list.get(i).getId())) {
            				oppnsess = list.get(i);
            			}
            		}
            		oppnsess.getBasicRemote().sendText(message);
            		
            	} else if (message.contains("Send Session : ")) {
            		//상대에게 내 세션 보내기
            	} else if (message.contains("Add : ")) {
            		
            		String roomInfo = message.replace("Add : ", "");
            		forOmok.get(roomInfo).add(session);
            	} else if (message.contains("Game End At : ")) {
            		String str = message.replace("Game End At : ", "");
            		rooms.remove(str);
            	}
                break;
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Connection closed: " + session.getId());
        sessionMap.remove(session.getId());
    }
    
    public static void registerUser(String sessionId, UserVO user) {
        userSessionMap.put(sessionId, user.getId());
        if (!userList.contains(user.getId())) {
        	userList.add(user.getId());
        }
        // Update all connected clients
    }
    
    public static void delUser(HttpSession sess, String id) {
    	System.out.println("나가기실행" + id);
        userSessionMap.remove(sess, id);
        if (userList.contains(id)) {
        	userList.remove(id);
        }
        // Update all connected clients
    }
    
    public static boolean checkLogged(String id) {
    	if (userList.contains(id)) {
    		return false;
    	} else {
    		return true;
    	}
    }
    
    public static void createRoom(String roomId, UserVO vo, HttpSession sess) {
    	ArrayList<UserVO> list = new ArrayList<>();
    	
    	list.add(vo);
    	rooms.put(roomId, list);
    	forOmok.put(roomId, new ArrayList<Session>());
    	System.out.println(roomId + "방 생성");
    	System.out.println("방 내의 유저" + rooms.get(roomId));
    	boardMap.put(roomId, new String[19][19]); // 19x19 게임판 초기화
    }
    
    //enterUser
    public static boolean enterRoom(String roomId, UserVO vo, HttpSession sess) {
    	if (rooms.get(roomId).size() > 1) {
    		//풀방시
    		return false;
    	} else {
    		System.out.println("방 내의 유저 입장 전" + rooms.get(roomId));
    		rooms.get(roomId).add(vo);
    		System.out.println("방 내의 유저 입장 후" + rooms.get(roomId));
    		System.out.println(rooms.get(roomId));
    		return true;
    	}
    }
    
    private void sendUserList(Session session) throws IOException {
        JSONArray jsonArray = new JSONArray();
        for (String user : userList) {
            jsonArray.add(user);
        }
        session.getBasicRemote().sendText("user" + jsonArray.toString());
    }

    private void sendRoomList(Session session) throws IOException {
        JSONArray roomKeysArray = new JSONArray();
        for (String key : rooms.keySet()) {
            roomKeysArray.add(key);
        }
        session.getBasicRemote().sendText("room" + roomKeysArray.toString());
    }
    
}
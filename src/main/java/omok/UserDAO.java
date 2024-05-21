package omok;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserDAO {
	private Connection con;
	private Statement stmt;
	private PreparedStatement pstmt;
	
	private DataSource dataFactory;
	
	public UserDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context)ctx.lookup("java:/comp/env");
			dataFactory = (DataSource)envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<UserVO> listUsers(String searchType, String searchWord) {
		List<UserVO> list = new ArrayList<>();
		ResultSet rs = null;
		try {
			con = dataFactory.getConnection();
			String query = "select * from users";
			String whereQuery = "";
			if (searchWord != null && !"".equals(searchWord)) {
				if ("all".equals(searchType)) {
					whereQuery = " where id like '%"+searchWord+"%' or name like '%"+searchWord+"%' or email like '%"+searchWord+"%'";
				} else {
					whereQuery = " where "+searchType+" like '%"+searchWord+"%'";
				}
			}
			query += whereQuery;
			// PreparedStatement
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String name = rs.getString("name");
				UserVO vo = new UserVO();
				vo.setId(id);
				vo.setPw(pw);
				vo.setName(name);
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();}catch(Exception e) {}
			try {stmt.close();}catch(Exception e) {}
			try {pstmt.close();}catch(Exception e) {}
			try {con.close();}catch(Exception e) {}
		}
		
		return list;
	}
	
	public boolean addUser(UserVO vo) {
        RecordDAO dao = new RecordDAO();
        dao.createRecord(vo.getId());
        try {
            con = dataFactory.getConnection();
            String query = "INSERT INTO users (id,pw,name) VALUES (?,?,?)";
            pstmt = con.prepareStatement(query);
            System.out.println(vo.getId() + " " + vo.getPw() + " " + vo.getName());
            pstmt.setString(1, vo.getId());
            pstmt.setString(2, vo.getPw());
            pstmt.setString(3, vo.getName());
            pstmt.executeUpdate();
            return false;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("ID 중복 오류 발생");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {pstmt.close();}catch(Exception e) {}
            try {con.close();}catch(Exception e) {}
        }
        return true;
    }
	
	public void delUser(String id) {
		try {
			con = dataFactory.getConnection();
			String query = "DELETE FROM users WHERE id=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();}catch(Exception e) {}
			try {con.close();}catch(Exception e) {}
		}
	}
	
	public UserVO login(UserVO userVO) {
		UserVO vo = null;
		String id = userVO.getId();
		String pw = userVO.getPw();
		try {
			con = dataFactory.getConnection();
			String query = "SELECT * FROM users";
			query += " WHERE id=? and pw=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = new UserVO();
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setName(rs.getString("name"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}
	
	public int checkUserId(String userId) {
        // DB 연결 코드
		int cnt = 0;
        
        try {
        	
        	con = dataFactory.getConnection();
        	String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            
            pstmt.setString(1, userId);
            System.out.println("DAO servlet아이디 :"+userId); //이걸못받옴
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
            	cnt = rs.getInt(1);//COUNT(*)의 결과        	
            }
         // 아이디가 없으면 true, 있으면 false 반환
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cnt;
    }
	//id

	public String getNameById(String userId) {
		String name= null;
try {
        	
        	con = dataFactory.getConnection();
        	String sql = "SELECT name FROM users WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            
            pstmt.setString(1, userId);
            System.out.println("DAO servlet아이디 :"+userId); //이걸못받옴
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
            	name = rs.getString("name");//COUNT(*)의 결과        	
            }
         // 아이디가 없으면 true, 있으면 false 반환
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return name;
	}
}

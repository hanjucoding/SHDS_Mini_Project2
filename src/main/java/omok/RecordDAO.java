package omok;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class RecordDAO {
	private Connection con;
	private PreparedStatement pstmt;

	private DataSource dataFactory;

	public RecordDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RecordVO getRecordInfoByName(String name){
		RecordVO vo = null;
		try {
        	con = dataFactory.getConnection();
        	String sql = "SELECT * FROM RECORD WHERE ID = (SELECT ID FROM USERS WHERE NAME=?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()) {
            	vo = new RecordVO();
            	vo.setId(rs.getString("ID"));	
            	vo.setGames(rs.getInt("GAMES"));
            	vo.setWin(rs.getInt("WIN"));
            	vo.setLose(rs.getInt("LOSE"));
            	vo.setDraw(rs.getInt("DRAW"));
            	vo.setRank(rs.getInt("RANK"));
            }
            rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return vo;
	}

	public RecordVO getRecordInfoById(String userId) {
		RecordVO vo = null;
		try {
        	con = dataFactory.getConnection();
        	String sql = "SELECT * FROM RECORD WHERE ID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,userId);
            
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()) {
            	vo = new RecordVO();
            	vo.setId(rs.getString("ID"));	
            	vo.setGames(rs.getInt("GAMES"));
            	vo.setWin(rs.getInt("WIN"));
            	vo.setLose(rs.getInt("LOSE"));
            	vo.setDraw(rs.getInt("DRAW"));
            	vo.setRank(rs.getInt("RANK"));
            }
            rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return vo;
	}
	
	public void createRecord(String id) {
        try {
            con = dataFactory.getConnection();
            String query = "INSERT INTO RECORD (ID,GAMES,WIN,LOSE,DRAW,RANK) VALUES (?,0,0,0,0,0)";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("!!!RECORD 추가 오류!!!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {pstmt.close();}catch(Exception e) {}
            try {con.close();}catch(Exception e) {}
        }
    }
	
	private void Add_Games(String id) {
		try {
			con = dataFactory.getConnection();
			String sql = "UPDATE RECORD SET GAMES = GAMES + 1 WHERE ID = ?";
			pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
	}
	
	public void Add_Win(String id) {
		try {
			con = dataFactory.getConnection();
			String sql = "UPDATE RECORD SET WIN = WIN + 1, RANK = RANK + 3 WHERE ID = ?";
			pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            
            Add_Games(id);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
	}
	
	
	public void Add_Lose(String id) {
		try {
			con = dataFactory.getConnection();
			String sql = "UPDATE RECORD SET LOSE = LOSE + 1 WHERE ID = ?";
			pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            
            Add_Games(id);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
	}

	public void Add_Draw(String id) {
		try {
			con = dataFactory.getConnection();
			String sql = "UPDATE RECORD SET DRAW = DRAW + 1, RANK = RANK + 1 WHERE ID = ?";
			pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            
            Add_Games(id);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
	}
	
	public int Get_Ranking(String id) {
		int result = 0;
		try {
        	con = dataFactory.getConnection();
        	String sql = "SELECT ID,ROW_NUMBER() OVER (ORDER BY win DESC) AS RANKING FROM RECORD";
            pstmt = con.prepareStatement(sql);
            
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()) {
            	System.out.println(rs.getString("ID"));
            	if(id.equals(rs.getString("ID"))){
            		System.out.println(rs.getString("ID"));
            		System.out.println(rs.getInt("RANKING"));
            		return rs.getInt("RANKING");
            		
            	}
            }
            rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return result;
		
		
	}

	
	
}

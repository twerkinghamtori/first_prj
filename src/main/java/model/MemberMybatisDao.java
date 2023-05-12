package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.mapper.MemberMapper;

public class MemberMybatisDao {
	private Class<MemberMapper> cls = MemberMapper.class;
	private Map<String, Object> map = new HashMap<>();
	
	public boolean insert(Member mem) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			int cnt = session.getMapper(cls).insert(mem);
	   		 if(cnt > 0) return true; 
	   		 else return false;
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }	
	   	 return false;
	}

	public Member selectOneNick(String nickname) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).selectOneNick(nickname);			
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }
	   	 return null;
	}
	
	//로그인
	public Member selectOneEmail(String email) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).selectOneEmail(email);				
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }
	   	 return null;
	}

	public boolean update(Member mem) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			int cnt = session.getMapper(cls).update(mem);
	   		 if(cnt > 0) return true; 
	   		 else return false;
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }	
	   	 return false;
	}

	public boolean delete(String email) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			int cnt = session.getMapper(cls).delete(email);
	   		 if(cnt > 0) return true; 
	   		 else return false;
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }	
	   	 return false;
	}

	public boolean updatePass(String email, String pass) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			int cnt = session.getMapper(cls).updatePass(email, pass);
	   		 if(cnt > 0) return true; 
	   		 else return false;
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }	
	   	 return false;
	}

	public List<Member> list(int pageNum, int limit, String nickname) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			map.clear();
			map.put("start", (pageNum-1)*limit);
			map.put("limit", limit);
			map.put("nickname", nickname);
			return session.getMapper(cls).list(map);				
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }
	   	 return null;
	}

	public int memberCount(String nickname) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			int count = session.getMapper(cls).memberCount(nickname);
			return count;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return 0;
	}

	public int myBoardCnt(String nickname) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			int count = session.getMapper(cls).myBoardCnt(nickname);
			return count;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return 0;
	}

	public int myCommCnt(String nickname) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			int count = session.getMapper(cls).myCommCnt(nickname);
			return count;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return 0;
	}

	public List<Board> boardList(int pageNum, int limit, String nickname) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			map.clear();
			map.put("start", (pageNum-1)*limit);
			map.put("limit", limit);
			map.put("nickname", nickname);
			return session.getMapper(cls).boardList(map);				
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }
	   	 return null;
	}

	public List<Board> commList(int pageNum, int limit, String nickname) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			map.clear();
			map.put("start", (pageNum-1)*limit);
			map.put("limit", limit);
			map.put("nickname", nickname);
			return session.getMapper(cls).commList(map);				
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }
	   	 return null;
	}

	public void updateImg(String img, String login) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			session.getMapper(cls).updateImg(img, login);
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }	
	}
}

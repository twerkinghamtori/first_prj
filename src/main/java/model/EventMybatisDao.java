package model;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.mapper.EventMapper;
import model.mapper.MemberMapper;

public class EventMybatisDao {
	private Class<EventMapper> cls = EventMapper.class;
	private Map<String, Object> map = new HashMap<>();
	
	public boolean insert(Event event) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			int cnt = session.getMapper(cls).insert(event);
	   		 if(cnt > 0) return true; 
	   		 else return false;
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }	
	   	 return false;
	}

	public Event selectLatest() {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).selectLatest();
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }	
	   	 return null;
	}

	public void update(String winner, int no) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			session.getMapper(cls).update(winner,no);
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }		
	}

	public int countNo() {
		SqlSession session = MybatisConnection.getConnection();
		int cnt = 0;
		try {
			cnt = session.getMapper(cls).counNo();
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }
		return cnt;
	}	
	
}

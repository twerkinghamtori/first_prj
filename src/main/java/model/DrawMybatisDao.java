package model;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.mapper.DrawMapper;
import model.mapper.MemberMapper;

public class DrawMybatisDao {
	private Class<DrawMapper> cls = DrawMapper.class;
	private Map<String, Object> map = new HashMap<>();
	public boolean insert(String email, int no) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).insert(email,no);
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }	
	   	 return false;
	}
	public Draw selectWinner(int latestEvent) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).selectWinner(latestEvent);
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }	
	   	 return null;
	}
	public Draw selectOne(int no) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).selectOne(no);
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }	
	   	 return null;
	}
	public void delete() {
		SqlSession session = MybatisConnection.getConnection();
		try {
			session.getMapper(cls).delete();
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }	
		
	}
}

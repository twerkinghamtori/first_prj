package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.mapper.MemberMapper;
import model.mapper.MessengerMapper;

public class MessengerMybatisDao {
	private Class<MessengerMapper> cls = MessengerMapper.class;
	private Map<String, Object> map = new HashMap<>();
	
	public void insert(Messenger messenger) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			session.getMapper(cls).insert(messenger);
			session.getMapper(cls).insert2(messenger);
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }	
	}

	public Messenger selectLatest(String nickname) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).selectLatest(nickname);
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }	
	   	 return null;
	}

	public List<Messenger> selectMsgs(String receiver, String nickname) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).selectMsgs(receiver,nickname);				
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }
	   	 return null;
	}
	
	public List<String> selectSenders(String nickname) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).selectSenders(nickname);				
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }
	   	 return null;
	}

	public void read(String nickname, String receiver) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			session.getMapper(cls).read(nickname, receiver);				
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }
	}

	public int notReadCnt(String nickname) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).notReadCnt(nickname);				
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }
		return 0;
	}

	public int notReadCntSep(String nickname, String receiver) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).notReadCntSep(nickname, receiver);				
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }
	   	 return 0;
	}

	public void delete(String receiver, String nickname) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			session.getMapper(cls).delete(receiver, nickname);				
		} catch(Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 MybatisConnection.close(session);
	   	 }
		
	}
}

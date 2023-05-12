package model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import model.mapper.CommRecommendMapper;

public class CommRecommendMybatisDao {
	private static Class<CommRecommendMapper> cls = CommRecommendMapper.class;
	
	//추천
	public boolean insert(int no, int seq, String emailaddress) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			int cnt = session.getMapper(cls).insert(no, seq,emailaddress);
			return cnt>0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		
		return false;
	}

	public List<String> list(int no, int seq) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).list(no, seq);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		
		return null;
	}
}

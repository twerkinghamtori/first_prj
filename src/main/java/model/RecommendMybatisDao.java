package model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import model.mapper.RecommendMapper;

public class RecommendMybatisDao {
	private static Class<RecommendMapper> cls = RecommendMapper.class;
	
	//추천
	public boolean insert(int no, String emailaddress) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			int cnt = session.getMapper(cls).insert(no, emailaddress);
			return cnt>0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		
		return false;
	}

	public List<String> list(int no) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).list(no);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		
		return null;
	}
}

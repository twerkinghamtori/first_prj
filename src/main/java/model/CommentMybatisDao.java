package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.mapper.CommentMapper;

public class CommentMybatisDao {
	private static Class<CommentMapper> cls = CommentMapper.class;
	private static Map<String, Object> map = new HashMap<>();
	
	public int maxSeq(int no) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			int cnt = session.getMapper(cls).maxSeq(no);
			return cnt;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		
		return 0;
	}

	public boolean insert(Comment comm) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			int cnt = session.getMapper(cls).insert(comm);
			return cnt>0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		
		return false;
	}
	
	public List<CommentListView> list(int no){
		return list(no, 1, 10);
	}

	public List<CommentListView> list(int no, int pageNum, int limit) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			map.clear();
			map.put("no", no);
			map.put("start", (pageNum -1) * limit);
			map.put("limit", limit);
			
			return session.getMapper(cls).list(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		
		return null;
	}

	public int commCnt(int no) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).commCnt(no);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return 0;
	}

	public void grpStepAdd(int grp, int grpStep) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			 session.getMapper(cls).grpStepAdd(grp, grpStep);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
	}

	public Comment selectOne(int no, int seq) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			 return session.getMapper(cls).selectOne(no, seq);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return null;
	}

	public boolean delete(int no, int seq) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			 int cnt = session.getMapper(cls).delete(no, seq);
			 cnt+= session.getMapper(cls).deleteGrp(no, seq);
			 return cnt>0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return false;
	}

	public boolean plusRecomm(int no, int seq) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			int cnt = session.getMapper(cls).plusRecomm(no, seq);
			return cnt>0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return false;
	}

}

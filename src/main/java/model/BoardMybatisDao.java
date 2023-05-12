package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.mapper.BoardMapper;

public class BoardMybatisDao {
	private static Class<BoardMapper> cls = BoardMapper.class;
	private static Map<String, Object> map = new HashMap<>();
	
	//게시물 등록
	public boolean insert(Board b) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			int cnt = session.getMapper(cls).insert(b);
			return cnt>0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		
		return false;
	}

	public int boardCount(String boardType, String field, String query, boolean adminList, String excep_mode) {
		SqlSession session = MybatisConnection.getConnection();
		
		try {
			map.clear();
			map.put("boardType", boardType);
			String[] fields = field.split("\\+");
			if(fields.length == 2) {
				map.put("field1", fields[0]);
				map.put("field2", fields[1]);
			}else {
				map.put("field1", field);
				map.put("field2", null);
			}
			map.put("query",query);
			
			if(!adminList)
				map.put("pub", 1);
			if(!excep_mode.equals(""))
				map.put("recomm", 5);
			
			return session.getMapper(cls).boardCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		
		return 0;
	}

	public List<BoardListView> list(String boardType, int pageNum, int limit, String field, String query, boolean adminList, String excep_mode) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			map.clear();
			map.put("boardType", boardType);
			String[] fields = field.split("\\+");
			if(fields.length > 1) {
				map.put("field1", fields[0]);
				map.put("field2", fields[1]);
			}else {
				map.put("field1", field);
				map.put("field2", null);
			}
			map.put("query", query);
			map.put("start", (pageNum -1) * limit);
			map.put("limit", limit);
			
			if(!adminList)
				map.put("pub", 1);
			if(!excep_mode.equals(""))
				map.put("recomm", 5);
			
			return session.getMapper(cls).list(map);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
		}
	
		return null;
	}
	
	public List<Board> nList() {
		SqlSession session = MybatisConnection.getConnection();
		try {
			List<Board> list = new ArrayList<>();
			list.add(session.getMapper(cls).getOldestBoard());
			list.add(session.getMapper(cls).getRecentBoard());
			
			// 공지사항이 1개일 경우
			if(list.size() == 2 && list.get(0).getNo() == list.get(1).getNo()) list.remove(1);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
		}
	
		return null;
	}

	public boolean pubBoardAll(List<String> oids, List<String> cids) {
		SqlSession session = MybatisConnection.getConnection();
		
		int result = 0;
		String sqlOpen = String.format("(%s)", String.join(",", oids));
		String sqlClose = String.format("(%s)",String.join(",", cids));
		
		try {
			if(!sqlOpen.equals("()")) 
				result += session.getMapper(cls).updateOpen(sqlOpen);
			if(!sqlClose.equals("()")) 
				result += session.getMapper(cls).updateClose(sqlClose);
			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
		}
		
		return false;
	}

	// 조회수 증가
	public void HitAdd(int no) {
		SqlSession session = MybatisConnection.getConnection();
		
		try {
			session.getMapper(cls).HitAdd(no);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
		}
	}

	// 상세보기 조회
	public BoardDetailView selectOne(int no) {
		SqlSession session = MybatisConnection.getConnection();
		
		try {
			return session.getMapper(cls).selectOne(no);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
		}
		
		return null;
	}
	
	// 다음글 조회
	public BoardDetailView selectNext(BoardDetailView b) {
		SqlSession session = MybatisConnection.getConnection();
		
		try {
			return session.getMapper(cls).selectNext(b);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
		}
		
		return null;
	}
	
	// 이전글 조회
	public BoardDetailView selectPrevious(BoardDetailView b) {
		SqlSession session = MybatisConnection.getConnection();
		
		try {
			return session.getMapper(cls).selectPrevious(b);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
		}
		
		return null;
	}
	
	// 게시물 수정
	public boolean update(Board b) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			int cnt = session.getMapper(cls).update(b);
			return cnt>0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return false;
	}

	public boolean delete(int no) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			int cnt = session.getMapper(cls).delete(no);
			return cnt>0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return false;
	}

	public boolean plusRecomm(int no) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			int cnt = session.getMapper(cls).plusRecomm(no);
			return cnt>0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return false;
	}

}		
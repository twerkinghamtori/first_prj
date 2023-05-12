package model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.Board;
import model.BoardDetailView;
import model.BoardListView;

public interface BoardMapper {
	
	@Insert("insert into board (title, nickname, content, boardType) values (#{title}, #{nickname}, #{content}, #{boardType})")
	int insert(Board b);

	String sqlField = " <if test='pub != null'> and pub=#{pub} </if> " +
							  " <if test='recomm != null'> and recommend>=#{recomm} </if> " +
							  " and ( ${field1} like '%${query}%' " + 
							  " <if test='field2 != null'> or ${field2} like '%${query}%' </if> ";
	@Select("<script>"
			+ " SELECT * FROM boardListView "
			+ " WHERE BOARDTYPE=#{boardType} "
			+ sqlField
			+ " ) ORDER BY regdate DESC LIMIT #{start}, #{limit} "
			+ " </script>")
	List<BoardListView> list(Map<String, Object> map); 

	@Select("<script>"
			+ " SELECT COUNT(*) FROM boardListView "
			+ " WHERE BOARDTYPE=#{boardType} "
			+ sqlField
			+ " ) </script>")
	int boardCount(Map<String, Object> map);

	@Select("SELECT * FROM board WHERE BOARDTYPE=4 and PUB=1 ORDER BY regdate DESC LIMIT 1")
	Board getRecentBoard();
	@Select("SELECT * FROM board WHERE boardType=4 and PUB=1 ORDER BY regdate ASC LIMIT 1")
	Board getOldestBoard();

	@Update("update board set pub=1 where no in ${value}")
	int updateOpen(String sqlOpen);
	@Update("update board set pub=0 where no in ${value}")
	int updateClose(String sqlClose);

	@Update("update board set hit=hit+1 where no=#{value}")
	void HitAdd(int no);

	@Select("select * from boardDetailView where no=#{value}")
	BoardDetailView selectOne(int no);

	@Select("SELECT * "
			+ " FROM boardDetailView "
			+ " WHERE NO = (SELECT NO FROM boardDetailView "
			+ "				 WHERE boardType=#{boardType} AND pub=#{pub} AND regdate > (SELECT regdate FROM boardDetailView WHERE no=#{no} ) LIMIT 1)")
	BoardDetailView selectNext(BoardDetailView b);

	@Select("SELECT * "
			+ " FROM boardDetailView "
			+ " WHERE NO = (SELECT NO FROM boardDetailView  "
			+ "				 WHERE boardType=#{boardType} AND pub=#{pub} AND regdate < (SELECT regdate FROM boardDetailView WHERE NO=#{no}) order by regdate desc LIMIT 1)")
	BoardDetailView selectPrevious(BoardDetailView b);

	@Update("update board set title=#{title}, content=#{content} where no=#{no}")
	int update(Board b);

	@Delete("delete from board where no=#{value}")
	int delete(int no);

	@Update("update board set recommend=recommend+1 where no=#{value}")
	int plusRecomm(int no);
}

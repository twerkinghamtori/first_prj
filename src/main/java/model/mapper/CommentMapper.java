package model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.Comment;
import model.CommentListView;

public interface CommentMapper {

	@Select("SELECT ifnull(MAX(seq),0) cnt FROM comment where no=#{value}")
	int maxSeq(int no);

	@Insert("insert into comment "
			+ " (no, seq, nickname, content, grp, grpLevel, grpStep) values "
			+ " (#{no}, #{seq}, #{nickname}, #{content}, #{grp}, #{grpLevel}, #{grpStep})")
	int insert(Comment comm);

	@Select("select * from commentListView where no=#{no} ORDER BY GRP asc, GRPLEVEL asc, GRPSTEP desc  LIMIT #{start}, #{limit}")
	List<CommentListView> list(Map<String, Object> map);

	@Select("select count(*) from comment where no=#{value}")
	int commCnt(int no);

	@Update("update comment set grpStep = grpStep +1 where grp=#{grp} and grpStep >#{grpStep}")
	void grpStepAdd(@Param("grp")int grp,@Param("grpStep") int grpStep);

	@Select("select * from comment where no=#{no} and seq=#{seq}")
	Comment selectOne(@Param("no")int no, @Param("seq")int seq);

	@Delete("delete from comment where no=#{no} and seq=#{seq}")
	int delete(@Param("no")int no, @Param("seq")int seq);

	@Delete("delete from comment where no=#{no} and grp=#{seq}")
	int deleteGrp(@Param("no")int no, @Param("seq")int seq);
	
	@Update("update comment set recommend=recommend+1 where no=#{no} and seq=#{seq}")
	int plusRecomm(@Param("no")int no, @Param("seq")int seq);

	
}

package model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CommRecommendMapper {
	
	@Insert("insert into commRecommend (no, seq, emailaddress) values (#{no}, #{seq}, #{emailaddress})")
	int insert(@Param("no")int no, @Param("seq")int seq,@Param("emailaddress")String emailaddress);

	@Select("select emailaddress from commRecommend where no=#{no} and seq=#{seq}")
	List<String> list(@Param("no")int no, @Param("seq")int seq);
	
}

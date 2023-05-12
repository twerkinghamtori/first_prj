package model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RecommendMapper {
	
	@Insert("insert into recommend (no, emailaddress) values (#{no}, #{emailaddress})")
	int insert(@Param("no")int no, @Param("emailaddress")String emailaddress);

	@Select("select emailaddress from recommend where no=#{value}")
	List<String> list(int no);
	
}

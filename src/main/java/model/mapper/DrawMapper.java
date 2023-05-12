package model.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import model.Draw;

public interface DrawMapper {

	@Insert("insert into draw (emailaddress, no) values (#{param1}, #{param2})")
	boolean insert(String email, int no);

	@Select("SELECT * FROM draw where no=#{value} ORDER BY RAND() LIMIT 1")
	Draw selectWinner(int latestEvent);

	@Select("select * from draw where no=#{value}")
	Draw selectOne(int no);

	@Delete("delete from draw")
	void delete();	
}

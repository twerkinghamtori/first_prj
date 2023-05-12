package model.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.Event;

public interface EventMapper {

	@Insert("insert into event_ (product, startdate, enddate, picture) "
			+ "values (#{product}, #{startdate}, #{enddate}, #{picture})")
	int insert(Event event);

	@Select("SELECT * FROM event_ ORDER BY no DESC LIMIT 1;")
	Event selectLatest();

	@Update("UPDATE event_ SET winner = #{param1} where no=#{param2}")
	void update(String winner, int no);

	@Select("select distinct count(no) from event_ where winner is null")
	int counNo();
}

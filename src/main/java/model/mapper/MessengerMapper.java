package model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.Messenger;

public interface MessengerMapper {

	@Insert("insert into msg (sender, receiver, content, mine) values (#{sender}, #{receiver}, #{content}, #{sender})")
	void insert(Messenger messenger);
	
	@Insert("insert into msg (sender, receiver, content, mine) values (#{sender}, #{receiver}, #{content}, #{receiver})")
	void insert2(Messenger messenger);

	@Select("SELECT * FROM msg where receiver=#{value} ORDER BY no DESC LIMIT 1;")
	Messenger selectLatest(String nickname);

	@Select("SELECT * FROM msg"
			+ " WHERE (receiver = #{receiver} AND sender = #{nickname} and mine=#{nickname})"
			+ " OR (receiver = #{nickname} AND sender = #{receiver} and mine=#{nickname})"
			+ " ORDER BY regdate ASC")
	List<Messenger> selectMsgs(@Param("receiver")String receiver, @Param("nickname")String nickname);
	
	@Select("SELECT DISTINCT"
			+ " CASE WHEN sender=#{value} THEN receiver"
			+ " ELSE sender"
			+ " END AS correspondent"
			+ " FROM msg"
			+ " WHERE (receiver=#{value} and mine=#{value}) OR (sender=#{value} and mine=#{value})"
			+ " ORDER BY regdate desc")
	List<String> selectSenders(String nickname);

	@Update("update msg set isRead='1' where (receiver=#{nickname} and sender=#{receiver}) and mine=#{nickname}")
	void read(@Param("nickname")String nickname, @Param("receiver")String receiver);

	@Select("select count(*) from msg where (receiver=#{value} and mine=#{value}) and isRead='0'")
	int notReadCnt(String nickname);

	@Select("select count(*) from msg where (receiver=#{nickname} and sender=#{receiver} and mine=#{nickname}) and isRead='0'")
	int notReadCntSep(@Param("nickname")String nickname, @Param("receiver")String receiver);

	@Delete("delete from msg"
			+ " where (receiver=#{nickname} and sender=#{receiver} and mine=#{nickname})"
			+ " or (receiver=#{receiver} and sender=#{nickname} and mine=#{nickname})")
	void delete(@Param("receiver")String receiver, @Param("nickname")String nickname);

}

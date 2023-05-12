package model;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisConnection {
	private MybatisConnection() {}
	private static final SqlSessionFactory sqlMap;
	
	static {
		String url = "model/mapper/mybatis-config.xml";
		InputStream input = null;
		try {
			input = Resources.getResourceAsStream(url);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		sqlMap = new SqlSessionFactoryBuilder().build(input);
	}
	
	public static SqlSession getConnection() {
		return sqlMap.openSession();
	}
	public static void close(SqlSession session) {
		session.commit();
		session.close();
	}
}

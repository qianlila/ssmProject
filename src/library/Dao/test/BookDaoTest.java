package library.Dao.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.plaf.synth.SynthScrollBarUI;

import org.junit.jupiter.api.Test;

import library.Dao.BookDao;
import library.Dao.IBookDao;
import library.book.Book;

class BookDaoTest {
	IBookDao book = new BookDao();
	Book b = new Book("20191106","伊索寓言",21,34,true);
	@Test
	void testInsert() {
		System.out.println(book.insert(null));
	}

	@Test
	void testDelete() {
		book.delete("20191105");
	}

	@Test
	void testUpdate() {
		book.update("20191104", b);
	}
	
	@Test
	void testSelete() {
		System.out.println(book.seleteById("20191104"));
	}
	
	@Test
	void testSeleteAllInfo() {
		System.out.println(book.seleteAllInfo());
	}
	
	@Test
	void testIsExistBookById() {
		//大学英语
		System.out.println(book.seleteById("20180911"));
	}

	@Test
	void testIsExistBookByName() {
		System.out.println(book.isExistBookByName("大学英语"));
		System.out.println(book.seleteByName("大学英语"));
	}
@Test
public void test() throws Exception {
	Class.forName("com.mysql.jdbc.Driver");
	final String URL = "jdbc:mysql://localhost:3306/java_test";
	final String USER = "root";
	final String PASSWORD = "lqx2877354362";
	Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
	String sql = "select * from bank;";
	Statement st = con.createStatement();
//	st.execute(sql);
	ResultSet rs = st.executeQuery(sql);
	int count = 1;
	while(rs.next()) {
		System.out.println(rs.getObject(count));
		count++;
	}
	rs.close();
	st.close();
	con.close();
}
}

package library.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import library.book.Book;
import library.util.JDBCUtil;

public class BookDao implements IBookDao {
	private ResultSet rs;

	@Override
	public boolean insert(Book book) {
		String sql = "insert into books(bId,bName,bNum,limitDay,existReturn) values(?,?,?,?,?)";
		return JDBCUtil.executeUpdate(sql, book.getbId(), book.getbName(), book.getbNum(), book.getlimitDay(),
				book.existReturn() ? 1 : 0);
	}

	@Override
	public boolean delete(String bId) {
		String sql = "delete from books where bId = ?";
		return JDBCUtil.executeUpdate(sql, bId);
	}

	@Override
	public boolean update(String bId, Book book) {
		String sql = "update books set bName = ?,bNum = ?,limitDay = ?,existReturn = ? where bId = ?";
		return JDBCUtil.executeUpdate(sql, book.getbName(), book.getbNum(), book.getlimitDay(),
				book.existReturn() ? 1 : 0, bId);

	}

	@Override
	public Book seleteById(String bId) {
		Book b = null;
		try {
			String sql = "select * from books where bId = ?";
			rs = JDBCUtil.createResultSet(sql, bId);
			if (rs.next()) {
				b = new Book(rs.getString("bId"), rs.getString("bName"), rs.getInt("bNum"), rs.getInt("limitDay"),
						rs.getBoolean("existReturn"));
			}
			return b;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Book seleteByName(String bName) {
		Book b = null;
		try {
			String sql = "select * from books where bName = ?";
			// 调用JDBCUtil类同时进行语句的创建及执行
			rs = JDBCUtil.createResultSet(sql, bName);
			if (rs.next()) {
				b = new Book(rs.getString("bId"), rs.getString("bName"), rs.getInt("bNum"), rs.getInt("limitDay"),
						rs.getBoolean("existReturn"));
			}
			return b;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Book> seleteAllInfo() {
		List<Book> bookList = new ArrayList<>();
		try {
			String sql = "select * from books";
			// 无参数(?)时则传入的参数只为sql语句
			rs = JDBCUtil.createResultSet(sql);
			while (rs.next()) {
				Book b = new Book(rs.getString("bId"), rs.getString("bName"), rs.getInt("bNum"), rs.getInt("limitDay"),
						rs.getBoolean("existReturn"));
				bookList.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return bookList;
	}

	@Override
	public boolean isExistBookById(String bId) {
		if (seleteById(bId) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isExistBookByName(String bName) {
		if (seleteByName(bName) != null) {
			return true;
		}
		return false;
	}
}

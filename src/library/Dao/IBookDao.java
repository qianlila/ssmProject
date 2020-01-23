package library.Dao;

import java.util.List;

import library.book.Book;

public interface IBookDao {
	/**
	 * 实现图书添加功能
	 * 
	 * @param b
	 * @return 返回是否添加成功
	 */

	boolean insert(Book b);

	/**
	 * 通过图书编号查找图书
	 * 
	 * @param bId 图书编号
	 * @return 是否查找成功
	 */
	boolean delete(String bId);

	/**
	 * 根据图书编号实现修改
	 * 
	 * @param bId  书的编号
	 * @param book 修改后的book
	 */
	boolean update(String bId, Book book);

	/**
	 * 选择查询
	 * 
	 * @param bId 根据图书编号查找
	 * @return 查询后获取的图书
	 */
	Book seleteById(String bId);

	/**
	 * 查询
	 * 
	 * @param bName 根据图书名查找
	 * @return 查询后获取的图书
	 */
	Book seleteByName(String bName);

	/**
	 * 根据编号查询是否存在
	 * 
	 * @param bId
	 * @return 是否存在
	 */
	boolean isExistBookById(String bId);

	/**
	 * 根据书名查询是否存在
	 * 
	 * @param bName
	 * @return 是否存在
	 */
	boolean isExistBookByName(String bName);

	/**
	 * 查询所有图书
	 * 
	 * @return
	 */
	List<Book> seleteAllInfo();
}

package library.service;

import java.util.List;

import library.book.Book;

public interface IBookService {
	/**
	 * 获取错误信息
	 * 
	 * @return
	 */
	String getError();

	/**
	 * 设置错误信息
	 * 
	 * @param error
	 */
	void setError(String error);

	/**
	 * 查询所有图书
	 * 
	 * @return
	 */
	List<Book> seleteAllInfo();

	/**
	 * 是否插入成功
	 * 
	 * @param b
	 * @return
	 */
	boolean isInsertBook(Book b);

	/**
	 * 是否更改成功
	 * 
	 * @param bId
	 * @param b
	 * @return
	 */
	boolean isUpdate(String bId, Book b);

	/**
	 * 是否删除成功
	 * 
	 * @param bId
	 * @return
	 */
	boolean isdelete(String bId);

	/**
	 * 根据编号或图书名进行查询Book对象
	 * 
	 * @param bId
	 * @param bName
	 * @return
	 */
	Book seleteVal(String bId, String bName);

}

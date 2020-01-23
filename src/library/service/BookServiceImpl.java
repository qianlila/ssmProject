package library.service;

import java.util.List;

import com.sun.org.apache.regexp.internal.RE;

import library.Dao.BookDao;
import library.Dao.IBookDao;
import library.book.Book;

public class BookServiceImpl implements IBookService {
	private String error = null;
	private IBookDao bookDao = new BookDao();

	@Override
	public String getError() {
		return error;
	}

	@Override
	public void setError(String error) {
		this.error = error;
	}

	@Override
	public List<Book> seleteAllInfo() {
		return bookDao.seleteAllInfo();
	}

	@Override
	public boolean isInsertBook(Book b) {
//		try {
		if (bookDao.seleteById(b.getbId()) != null || bookDao.seleteByName(b.getbName()) != null) {
			error = "该图书已存在！请检查你输入的图书编号及图书名！";
			return false;
		}
		if (bookDao.insert(b)) {
			return true;
		}
		error = "插入失败";
		return false;
	}

	@Override
	public boolean isdelete(String bId) {
		return bookDao.delete(bId);
	}

	@Override
	public boolean isUpdate(String bId, Book b) {
		if (!bookDao.update(bId, b)) {
			error = "更改失败！";
			return false;
		}
		return true;
	}

	@Override
	public Book seleteVal(String bId, String bName) {
		if (bId == null && bName != null) {
			if (bookDao.isExistBookByName(bName)) {
				return bookDao.seleteByName(bName);
			}
			error = "未查询到该图书";
		}

		if (bId != null && bName == null) {
			if (bookDao.isExistBookById(bId)) {
				return bookDao.seleteById(bId);
			}
			error = "未查询到该图书";
		}
		return null;
	};

}

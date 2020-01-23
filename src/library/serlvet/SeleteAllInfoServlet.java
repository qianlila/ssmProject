package library.serlvet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import library.book.Book;
import library.service.BookServiceImpl;
import library.service.IBookService;

@WebServlet("/SeleteAllInfoServlet")
public class SeleteAllInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SeleteAllInfoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		IBookService bookService = new BookServiceImpl();
		List<Book> books = bookService.seleteAllInfo();
		// 设置books属性，值为List<Book>,存储再request中
//		System.out.println(books);
		// String error = BookDao.error;
		request.setAttribute("books", books);
		// request.setAttribute("error", error);
		// 请求转发，重定向会失去request存储的值
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

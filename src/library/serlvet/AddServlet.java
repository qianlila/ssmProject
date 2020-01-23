package library.serlvet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import library.Dao.BookDao;
import library.Dao.IBookDao;
import library.book.Book;
import library.service.BookServiceImpl;
import library.service.IBookService;

/**
 * Servlet implementation class AddServlet
 */
@WebServlet("/AddServlet")
public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		IBookService bookService = new BookServiceImpl();
		String error = null;
		try {
			request.setCharacterEncoding("utf-8");
			String bId = request.getParameter("bId");
			String bName = request.getParameter("bName");
			int bNum = Integer.parseInt(request.getParameter("bNum"));
			int limitDay = Integer.parseInt(request.getParameter("limitDay"));
			String bool = (String) request.getParameter("existReturn");
			// 数据库中的boolean类型与Java中的Boolean原理，除true，都为false，故进行筛选
			if (bool.equals("false")) {
				Book b = new Book(bId, bName, bNum, limitDay, false);
				bookService.isInsertBook(b);
			} else if (bool.equals("true")) {
				Book b = new Book(bId, bName, bNum, limitDay, true);
				bookService.isInsertBook(b);
			} else {
				bookService.setError("添加失败！！");
			}
			error = bookService.getError();
			request.setAttribute("error", error);
			if (error == null) {
				request.getRequestDispatcher("SeleteAllInfoServlet").forward(request, response);
			} else {
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			error = "添加失败！！";
			request.setAttribute("error", error);
			// 增加后进行转发，重新显示，防止存储的数据丢失
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

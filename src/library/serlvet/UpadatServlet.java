package library.serlvet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import library.Dao.BookDao;
import library.book.Book;
import library.service.BookServiceImpl;
import library.service.IBookService;

/**
 * Servlet implementation class UpadatServlet
 */
@WebServlet("/UpadatServlet")
public class UpadatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpadatServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NumberFormatException {
		String error = null;
		try {
			request.setCharacterEncoding("utf-8");
			IBookService bookService = new BookServiceImpl();
			String bId = request.getParameter("bId");
			String bName = request.getParameter("bName");
			int bNum = Integer.parseInt(request.getParameter("bNum"));
			int limitDay = Integer.parseInt(request.getParameter("limitDay"));
			String bool = (String) request.getParameter("existReturn");
			Book b = null;
			if (bool.equals("false")) {
				b = new Book(bId, bName, bNum, limitDay, false);
			} else if (bool.equals("true")) {
				b = new Book(bId, bName, bNum, limitDay, true);
			}
			bookService.isUpdate(bId, b);
			error = bookService.getError();
			if (error == null) {
				request.getRequestDispatcher("SeleteAllInfoServlet").forward(request, response);
			} else {
				request.setAttribute("error", error);
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("error", "发生修改异常！！修改失败");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

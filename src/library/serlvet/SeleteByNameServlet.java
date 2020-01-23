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
 * Servlet implementation class SeleteByNameServlet
 */
@WebServlet("/SeleteByNameServlet")
public class SeleteByNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SeleteByNameServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String error = null;
		String seleteName = request.getParameter("seleteName");
		IBookService bookService = new BookServiceImpl();
		Book book = bookService.seleteVal(null, seleteName);
		error = bookService.getError();
		if (book != null) {
			request.setAttribute("book", book);
			request.getRequestDispatcher("SeleteByName.jsp").forward(request, response);
			;
		} else {
			request.setAttribute("error", error);
			request.getRequestDispatcher("error.jsp").forward(request, response);
			;
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

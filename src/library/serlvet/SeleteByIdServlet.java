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
 * Servlet implementation class SeleteByIdServlet
 */
@WebServlet("/SeleteByIdServlet")
public class SeleteByIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SeleteByIdServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String error = null;
		request.setCharacterEncoding("utf-8");
		String seleteId = request.getParameter("seleteId");
		IBookService bookService = new BookServiceImpl();
		Book book = bookService.seleteVal(seleteId, null);
		//
		error = bookService.getError();
		if (error == null) {
			request.setAttribute("book", book);
			request.getRequestDispatcher("SeleteById.jsp").forward(request, response);
		} else {
			request.setAttribute("error", bookService.getError());
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

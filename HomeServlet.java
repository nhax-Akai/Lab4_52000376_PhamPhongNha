package test;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class HomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // lấy thông tin tên JSP view từ query string
        String viewName = request.getParameter("page");

        if (viewName == null) {
            // nếu không có tên view được chỉ định, hiển thị trang chủ
            response.getWriter().append("Welcome to our website");
        } else {
            // điều phối request đến JSP view tương ứng
            request.getRequestDispatcher("/" + viewName + ".jsp").forward(request, response);
        }
    }

}


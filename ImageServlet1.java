package Ex2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Ex2/image1")
public class ImageServlet1 extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// thiết lập content type để trình duyệt hiển thị ảnh
		resp.setContentType("image/jpeg");

		 // lấy InputStream của file ảnh từ thư mục images
		InputStream inputStream = getServletContext().getResourceAsStream("/Ex2/resources/image1.jpg");

		// lấy OutputStream của client để gửi dữ liệu ảnh cho client
		OutputStream outputStream = resp.getOutputStream();

		// copy dữ liệu từ InputStream vào OutputStream để gửi dữ liệu đến client
		byte[] buffer = new byte[1024];
		int bytesRead = 0;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}

		// đóng luồng nhập và xuất.
		inputStream.close();
		outputStream.close();
	}
}
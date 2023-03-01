package Ex2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Ex2/download")
public class DownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// đường dẫn tới thư mục chứa file của bạn
		String filePath = getServletContext().getRealPath("/") + "Ex2/resources/";

		// Lấy đường dẫn file từ tham số "file" trong URL.
		String fileName = req.getParameter("file");

		// Kiểm tra xem tên file có được cung cấp trong URL không.
		if (fileName == null) {
			resp.getWriter().write("File not found");
			resp.getWriter().close();
			return;
		}

		// Tạo đối tượng file dựa trên tên file và đường dẫn file.
		File file = new File(filePath, fileName);

		// Kiểm tra xem file có tồn tại trong hệ thống file không.
		if (!file.exists()) {
			resp.getWriter().write("File not found");
			resp.getWriter().close();
			return;
		}

		// Thiết lập kiểu nội dung và tiêu đề đính kèm.
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");

		// Thiết lập tiêu đề chiều dài nội dung.
		resp.setContentLength((int) file.length());

		// lấy InputStream của file ảnh từ thư mục file
		InputStream inputStream = new FileInputStream(file);
		
		// lấy OutputStream của client để gửi dữ liệu ảnh cho client
		OutputStream outputStream = resp.getOutputStream();

		// copy dữ liệu từ InputStream vào OutputStream để gửi dữ liệu đến client
		byte[] buffer = new byte[10240];
		int bytesRead = -1;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}

		// Đóng luồng nhập và xuất.
		inputStream.close();
		outputStream.close();
	}
}

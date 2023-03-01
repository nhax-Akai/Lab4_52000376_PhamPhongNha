package test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // List of accepted file extensions
    private static final List<String> ACCEPTED_EXTENSIONS = Arrays.asList("txt", "doc", "docx", "img", "pdf", "rar", "zip");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the filename from the request
        String fileName = request.getParameter("filename");
        // Get the uploaded file from the request
        Part filePart = request.getPart("file");

        // Check if the file extension is supported
        String extension = getExtension(fileName);
        if (!ACCEPTED_EXTENSIONS.contains(extension)) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h2>Unsupported file extension</h2>");
            out.println("</body></html>");
            out.close();
            return;
        }

        // Get the override flag from the request
        boolean override = Boolean.parseBoolean(request.getParameter("override"));

        // Check if a file with the same name already exists
        String savePath = getServletContext().getRealPath("/") + "uploads/";
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        File file = new File(savePath + fileName);
        if (file.exists() && !override) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h2>File already exists</h2>");
            out.println("</body></html>");
            out.close();
            return;
        }

        // Save the file to the "uploads" folder
        filePart.write(savePath + fileName);

        // Display a success message
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>File has been uploaded</h2>");
        out.println("<p><a href=\"" + request.getContextPath() + "/uploads/" + fileName + "\">Click here to visit file</a></p>");
        out.println("</body></html>");
        out.close();
    }

    // Helper method to get the file extension from a filename
    private String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
            return "";
        } else {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
    }
}

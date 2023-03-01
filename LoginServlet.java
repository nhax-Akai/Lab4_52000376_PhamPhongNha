package test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    private HashMap<String, String> accounts;

    public void init() throws ServletException {
        accounts = new HashMap<>();
        accounts.put("user", "password");
        accounts.put("admin", "admin");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        // get the username and password from the request
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        response.setContentType("text/html");
        // check if the username and password match
        if (accounts.containsKey(username) && accounts.get(username).equals(password)) {
            out.println("Name/Password match");
        } else {
            out.println("Name/Password does not match");
        }
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }
}

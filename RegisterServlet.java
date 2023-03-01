package test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/registerr")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String birthday = request.getParameter("birthday");
        String birthtime = request.getParameter("birthtime");
        String gender = request.getParameter("gender");
        String country = request.getParameter("country");
        String[] favoriteIde = request.getParameterValues("favorite_ide[]");
        String toeic = request.getParameter("toeic");
        String message = request.getParameter("message");

        // Validate form data
        if (name == null || name.isEmpty()) {
            response.getWriter().println("Name is required");
            return;
        }
        if (email == null || email.isEmpty()) {
            response.getWriter().println("Email is required");
            return;
        }
        if (birthday == null || birthday.isEmpty()) {
            response.getWriter().println("Birthday is required");
            return;
        }
        if (birthtime == null || birthtime.isEmpty()) {
            response.getWriter().println("Birthtime is required");
            return;
        }
        if (gender == null || gender.isEmpty()) {
            response.getWriter().println("Gender is required");
            return;
        }
        if (country == null || country.isEmpty()) {
            response.getWriter().println("Country is required");
            return;
        }
        if (favoriteIde == null || favoriteIde.length == 0) {
            response.getWriter().println("Favorite IDE is required");
            return;
        }
        if (toeic == null || toeic.isEmpty()) {
            response.getWriter().println("TOEIC Score is required");
            return;
        }
        if (message == null || message.isEmpty()) {
            response.getWriter().println("Message is required");
            return;
        }

        // Display form data
        response.setContentType("text/html");
        response.getWriter().println("<h1>Register Information</h1>");
        response.getWriter().println("<p>Name: " + name + "</p>");
        response.getWriter().println("<p>Email: " + email + "</p>");
        response.getWriter().println("<p>Birthday: " + birthday + "</p>");
        response.getWriter().println("<p>Birthtime: " + birthtime + "</p>");
        response.getWriter().println("<p>Gender: " + gender + "</p>");
        response.getWriter().println("<p>Country: " + country + "</p>");
        response.getWriter().println("<p>Favorite IDE: ");
        for (String ide : favoriteIde) {
            response.getWriter().println(ide + " ");
        }
        response.getWriter().println("</p>");
        response.getWriter().println("<p>TOEIC Score: " + toeic + "</p>");
        response.getWriter().println("<p>Message: " + message + "</p>");
    }

}

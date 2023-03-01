package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private List<Product> productList;

    @Override
    public void init() throws ServletException {
        productList = new ArrayList<>();
        productList.add(new Product(1, "Product 1", 10.5));
        productList.add(new Product(2, "Product 2", 20.5));
        productList.add(new Product(3, "Product 3", 30.5));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // When the request parameter is not used, all products are returned
        // When using the request parameter id, only information about the product
        // corresponding to that id is returned. If the id does not exist, return the
        // appropriate error message.
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String idString = request.getParameter("id");
        String result = "";
        if (idString == null || idString.isEmpty()) {
            result = "{\"id\": 200, \"message\": \"OK\", \"data\": " + productList + "}";
        } else {
            int id;
            try {
                id = Integer.parseInt(idString);
            } catch (NumberFormatException e) {
                result = "{\"id\": 400, \"message\": \"Invalid product id format\", \"data\": null}";
                out.print(result);
                return;
            }
            int index = -1;
            for (int i = 0; i < productList.size(); i++) {
                Product product = productList.get(i);
                if (product.getId() == id) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                result = "{\"id\": 404, \"message\": \"Product not found\", \"data\": null}";
            } else {
                result = "{\"id\": 200, \"message\": \"OK\", \"data\": " + productList.get(index) + "}";
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // The doPost() method handles adding a new product:
        // -When adding a new product, the user needs to provide: id, name and price
        // -id and price must be numeric values, id cannot be the same as an existing
        // product
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String idString = request.getParameter("id");
        String name = request.getParameter("name");
        String priceString = request.getParameter("price");
        String result = "";
        if (idString == null || idString.isEmpty() || name == null || name.isEmpty() || priceString == null
                || priceString.isEmpty()) {
            result = "{\"id\": 400, \"message\": \"Missing product information\", \"data\": null}";
            out.print(result);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            result = "{\"id\": 400, \"message\": \"Invalid product id format\", \"data\": null}";
            out.print(result);
            return;
        }
        double price;
        try {
            price = Double.parseDouble(priceString);
        } catch (NumberFormatException e) {
            result = "{\"id\": 400, \"message\": \"Invalid product price format\", \"data\": null}";
            out.print(result);
            return;
        }
        int index = -1;
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            if (product.getId() == id) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            result = "{\"id\": 400, \"message\": \"Product id already exists\", \"data\": null}";
        } else {
            productList.add(new Product(id, name, price));
            result = "{\"id\": 200, \"message\": \"OK\", \"data\": " + productList.get(productList.size() - 1) + "}";
        }
        out.print(result);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // • The doPut() method handles updating a product's information
        // -When updating a product, the user needs to pass: id, name and price
        // -If the id does not exist on the server, the update will fail

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        String idString = req.getParameter("id");
        String name = req.getParameter("name");
        String priceString = req.getParameter("price");
        String result = "";
        if (idString == null || idString.isEmpty() || name == null || name.isEmpty() || priceString == null
                || priceString.isEmpty()) {
            result = "{\"id\": 400, \"message\": \"Missing product information\", \"data\": null}";
            out.print(result);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            result = "{\"id\": 400, \"message\": \"Invalid product id format\", \"data\": null}";
            out.print(result);
            return;
        }
        double price;
        try {
            price = Double.parseDouble(priceString);
        } catch (NumberFormatException e) {
            result = "{\"id\": 400, \"message\": \"Invalid product price format\", \"data\": null}";
            out.print(result);
            return;
        }
        int index = -1;
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            if (product.getId() == id) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            result = "{\"id\": 400, \"message\": \"Product id does not exist\", \"data\": null}";
        } else {
            productList.set(index, new Product(id, name, price));
            result = "{\"id\": 200, \"message\": \"OK\", \"data\": " + productList.get(index) + "}";
        }
        out.print(result);

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // get product id from request parameter
        String idString = request.getParameter("id");

        // validate product id
        if (idString == null || idString.isEmpty()) {
            String errorMessage = "{\"id\": 400, \"message\": \"Missing product id\", \"data\": null}";
            out.print(errorMessage);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            String errorMessage = "{\"id\": 400, \"message\": \"Invalid product id format\", \"data\": null}";
            out.print(errorMessage);
            return;
        }

        // find product with given id in product list
        int index = -1;
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            if (product.getId() == id) {
                index = i;
                break;
            }
        }

        // if product not found, return error message
        if (index == -1) {
            String errorMessage = "{\"id\": 404, \"message\": \"Product not found\", \"data\": null}";
            out.print(errorMessage);
            return;
        }

        // remove product from product list
        productList.remove(index);

        // return success message
        String successMessage = "{\"id\": 200, \"message\": \"Product deleted successfully\", \"data\": null}";
        out.print(successMessage);
    }

}

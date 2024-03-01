package com.vansisto.servlet;

import com.vansisto.model.Product;
import com.vansisto.service.ProductService;
import com.vansisto.service.impl.ProductServiceImpl;
import com.vansisto.util.RestUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    private final ProductService productService = new ProductServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Product product = RestUtil.getFromJson(req, Product.class);
        int status = productService.create(product);
        resp.setStatus(status);
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Product product = RestUtil.getFromJson(req, Product.class);

        if (product != null) {
            int status = productService.update(product);

            resp.setStatus(status);
        } else {
            resp.setStatus(400);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("ID parameter is required");
            return;
        }

        try {
            long id = Long.parseLong(idParam);
            Product product = productService.getById(id);
            if (product != null) {
                resp.setContentType("application/json");
                resp.getWriter().write(RestUtil.toJson(product));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Product with ID " + id + " not found");
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid ID parameter");
        }
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = RestUtil.getIdFromJson(req);
        if (id != -1) {
            int status = productService.deleteById(id);
            resp.setStatus(status);
        } else {
            resp.setStatus(400);
        }
    }
}

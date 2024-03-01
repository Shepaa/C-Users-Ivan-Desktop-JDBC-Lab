package com.vansisto.service.impl;

import com.vansisto.dao.ProductDAO;
import com.vansisto.dao.impl.ProductDAOImpl;
import com.vansisto.model.Product;
import com.vansisto.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductDAO productDAO = new ProductDAOImpl();

    @Override
    public int create(Product product) {
        return productDAO.create(product);
    }

    @Override
    public Product getById(long id) {
        return productDAO.getById(id);
    }

    @Override
    public int update(Product product) {
        return productDAO.update(product);
    }

    @Override
    public int deleteById(long id) {
        return productDAO.deleteById(id);
    }

    @Override
    public List<Product> getAll() {
        return productDAO.getAll();
    }
}

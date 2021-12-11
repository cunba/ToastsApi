package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.Product;
import com.sanvalero.toastsapi.model.ProductType;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.dto.ProductDTO;
import com.sanvalero.toastsapi.repository.MenuRepository;
import com.sanvalero.toastsapi.repository.ProductRepository;
import com.sanvalero.toastsapi.repository.ProductTypeRepository;
import com.sanvalero.toastsapi.repository.PublicationRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductTypeRepository ctr;
    @Autowired
    private MenuRepository mr;
    @Autowired
    private PublicationRepository pr;

    @Override
    public List<Product> findByType(ProductType productType) {
        return productRepository.findByType(productType);
    }

    @Override
    public List<Product> findByTypes(List<ProductType> productTypeList) {
        List<Product> products = new LinkedList<>();
        for (ProductType productType : productTypeList) {
            List<Product> lista = productRepository.findByType(productType);
            for (Product product : lista) {
                products.add(product);
            }
        }
        return products;
    }

    @Override
    public List<Product> findByDate(LocalDate date) {
        return productRepository.findByDate(date);
    }

    @Override
    public List<Product> findByDateBetween(LocalDate minDate, LocalDate maxDate) {
        return productRepository.findByDateBetween(minDate, maxDate);
    }

    @Override
    public List<Product> findByPrice(float price) {
        return productRepository.findByPrice(price);
    }

    @Override
    public List<Product> findByPriceBetween(float minPrice, float maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Product> findByPunctuation(float punctuation) {
        return productRepository.findByPunctuation(punctuation);
    }

    @Override
    public List<Product> findByPunctuationBetween(float minPunctuation, float maxPunctuation) {
        return productRepository.findByPunctuationBetween(minPunctuation, maxPunctuation);
    }

    @Override
    public List<Product> findByInMenu(boolean inMenu) {
        return productRepository.findByInMenu(inMenu);
    }

    @Override
    public List<Product> findByMenu(Menu menu) {
        return productRepository.findByMenu(menu);
    }

    @Override
    public List<Product> findByPublication(Publication publication) {
        return productRepository.findByPublication(publication);
    }

    @Override
    public Product findById(int id) throws NotFoundException {
        return productRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(ProductDTO productDTO) throws NotFoundException {
        ProductType type = ctr.findById(productDTO.getTypeId())
                .orElseThrow(NotFoundException::new);
        Menu menu = mr.findById(productDTO.getMenuId())
                .orElseThrow(NotFoundException::new);
        Publication publication = pr.findById(productDTO.getPublicationId())
                .orElseThrow(NotFoundException::new);

        ModelMapper mapper = new ModelMapper();
        Product product = mapper.map(productDTO, Product.class);
        product.setType(type);
        product.setMenu(menu);
        product.setPublication(publication);

        return productRepository.save(product);
    }

    @Override
    public Product deleteProduct(int id) throws NotFoundException {
        Product product = productRepository.findById(id).orElseThrow(NotFoundException::new);
        productRepository.delete(product);
        return product;
    }

    @Override
    public Product modifyProduct(Product product) {
        return productRepository.save(product);
    }

}

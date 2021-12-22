package com.sanvalero.toastsapi.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.sanvalero.toastsapi.exception.ErrorResponse;
import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.Product;
import com.sanvalero.toastsapi.model.ProductType;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.dto.ProductDTO;
import com.sanvalero.toastsapi.service.MenuService;
import com.sanvalero.toastsapi.service.ProductService;
import com.sanvalero.toastsapi.service.ProductTypeService;
import com.sanvalero.toastsapi.service.PublicationService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    private ProductService ps;
    @Autowired
    private ProductTypeService pts;
    @Autowired
    private MenuService ms;
    @Autowired
    private PublicationService publicationService;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(ps.findAll(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getById(@PathVariable int id) throws NotFoundException {
        return new ResponseEntity<>(ps.findById(id), HttpStatus.OK);
    }

    @GetMapping("/products/{dateString}")
    public ResponseEntity<List<Product>> getByDate(@PathVariable String dateString) {
        LocalDate date = LocalDate.parse(dateString, formatter);

        return new ResponseEntity<>(ps.findByDate(date), HttpStatus.OK);
    }

    @GetMapping("/products/{minDateString}-{maxDateString}")
    public ResponseEntity<List<Product>> getByDateBetween(@PathVariable String minDateString,
            @PathVariable String maxDateString) {

        LocalDate minDate = LocalDate.parse(minDateString, formatter);
        LocalDate maxDate = LocalDate.parse(maxDateString, formatter);

        LocalDate changerDate = LocalDate.now();
        if (minDate.isAfter(maxDate)) {
            changerDate = minDate;
            minDate = maxDate;
            maxDate = changerDate;
        }

        return new ResponseEntity<>(ps.findByDateBetween(minDate, maxDate), HttpStatus.OK);
    }

    @GetMapping("/products/{inMenu}")
    public ResponseEntity<List<Product>> getByInMenu(@PathVariable boolean inMenu) {
        return new ResponseEntity<>(ps.findByInMenu(inMenu), HttpStatus.OK);
    }

    @GetMapping("/products/{price}")
    public ResponseEntity<List<Product>> getByPrice(@PathVariable float price) {
        return new ResponseEntity<>(ps.findByPrice(price), HttpStatus.OK);
    }

    @GetMapping("/products/{minPrice}-{maxPrice}")
    public ResponseEntity<List<Product>> getByPriceBetween(@PathVariable float minPrice,
            @PathVariable float maxPrice) {

        float templatePrice = 0;
        if (minPrice > maxPrice) {
            templatePrice = minPrice;
            minPrice = maxPrice;
            maxPrice = templatePrice;
        }

        return new ResponseEntity<>(ps.findByPriceBetween(minPrice, maxPrice), HttpStatus.OK);
    }

    @GetMapping("/products/{punctuation}")
    public ResponseEntity<List<Product>> getByPunctuation(@PathVariable float punctuation) {
        return new ResponseEntity<>(ps.findByPunctuation(punctuation), HttpStatus.OK);
    }

    @GetMapping("/products/{minPunctuation}-{maxPunctuation}")
    public ResponseEntity<List<Product>> getByPunctuationBetween(@PathVariable float minPunctuation,
            @PathVariable float maxPunctuation) {

        float templatePunctuation = 0;
        if (minPunctuation > maxPunctuation) {
            templatePunctuation = minPunctuation;
            minPunctuation = maxPunctuation;
            maxPunctuation = templatePunctuation;
        }

        return new ResponseEntity<>(ps.findByPunctuationBetween(minPunctuation, maxPunctuation), HttpStatus.OK);
    }

    @GetMapping("products/type")
    public ResponseEntity<List<Product>> getByTypeId(@RequestParam(value = "id") int typeId) throws NotFoundException {
        ProductType type = pts.findById(typeId);
        return new ResponseEntity<>(ps.findByType(type), HttpStatus.OK);
    }

    @GetMapping("/products/menu")
    public ResponseEntity<List<Product>> getByMenu(@RequestParam(value = "id") int id) throws NotFoundException {
        Menu menu = ms.findById(id);

        return new ResponseEntity<>(ps.findByMenu(menu), HttpStatus.OK);
    }

    @GetMapping("/products/publication")
    public ResponseEntity<List<Product>> getByPublication(@RequestParam(value = "id") int id) throws NotFoundException {
        Publication publication = publicationService.findById(id);

        return new ResponseEntity<>(ps.findByPublication(publication), HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> create(@RequestBody ProductDTO productDTO) throws NotFoundException {
        logger.info("begin create product");
        ProductType type = pts.findById(productDTO.getTypeId());
        Publication publication = publicationService.findById(productDTO.getPublicationId());

        logger.info("Product type found: " + type.getId());
        logger.info("Publication found: " + publication.getId());

        ModelMapper mapper = new ModelMapper();
        Product product = mapper.map(productDTO, Product.class);
        product.setDate(LocalDate.now());
        product.setType(type);
        product.setPublication(publication);

        logger.info("Product mapped");

        Menu menu = null;
        if (productDTO.isInMenu()) {
            menu = ms.findById(productDTO.getMenuId());
            product.setPrice(0);
            logger.info("Menu found: " + menu.getId());
        }
        product.setMenu(menu);
        logger.info("Product created");
        logger.info("end create product");

        return new ResponseEntity<>(ps.addProduct(product), HttpStatus.OK);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> update(@RequestBody ProductDTO productDTO, @PathVariable int id) throws NotFoundException {
        logger.info("begin update product");
        Product product = ps.findById(id);

        logger.info("Product found: " + product.getId());

        ProductType type = pts.findById(productDTO.getTypeId());
        Publication publication = publicationService.findById(productDTO.getPublicationId());

        logger.info("Product type found: " + type.getId());
        logger.info("Publication found: " + publication.getId());

        product.setInMenu(productDTO.isInMenu());
        product.setPrice(productDTO.getPrice());
        product.setPublication(publication);
        product.setType(type);

        logger.info("Prodcut new properties set");

        Menu menu = null;
        if (productDTO.isInMenu()) {
            menu = ms.findById(productDTO.getMenuId());
            logger.info("Menu found: " + menu.getId());
        }
        product.setMenu(menu);
        logger.info("Product properties updated");

        logger.info("end update product");

        return new ResponseEntity<>(ps.updateProduct(product), HttpStatus.OK);
    }

    @PatchMapping("/product/update-price")
    public ResponseEntity<String> updatePrice(@RequestParam(value = "id") int id,
            @RequestBody Map<Float, Object> price) throws NotFoundException {

        logger.info("begin update price of product");
        Product product = ps.findById(id);

        logger.info("Product found: " + product.getId());

        ModelMapper mapper = new ModelMapper();
        Product productPrice = mapper.map(price, Product.class);

        logger.info("Product mapped");

        product.setPrice(productPrice.getPrice());
        ps.updatePrice(product);

        logger.info("Product price updated");
        logger.info("end update price of product");

        return new ResponseEntity<>("Price updated.", HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws NotFoundException {
        logger.info("begin delete product");
        Product product = ps.findById(id);
        logger.info("Product found: " + product.getId());
        ps.deleteProduct(product);
        logger.info("Product deleted");
        logger.info("end delete product");

        return new ResponseEntity<>("Product deleted.", HttpStatus.OK);
    }

    @DeleteMapping("/products")
    public ResponseEntity<String> deleteAll() {
        ps.deleteAll();

        return new ResponseEntity<>("All products deleted.", HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException nfe) {
        ErrorResponse errorResponse = new ErrorResponse("404", nfe.getMessage());
        logger.error(nfe.getMessage(), nfe);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}

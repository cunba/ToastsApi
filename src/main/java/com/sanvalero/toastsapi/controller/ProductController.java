package com.sanvalero.toastsapi.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import com.sanvalero.toastsapi.exception.BadRequestException;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    private long dateFrom = 1640995200000L;
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(ps.findAll(), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getById(@PathVariable int id) throws NotFoundException {
        try {
            return new ResponseEntity<>(ps.findById(id), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Product not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Product with ID " + id + " does not exists.");
        }
    }

    @GetMapping("/products/date/{date}")
    public ResponseEntity<List<Product>> getByDate(@PathVariable long date) throws BadRequestException {
        if (date < dateFrom) {
            logger.error("Product get by date error.", new BadRequestException());
            throw new BadRequestException(
                    "The date must be in timestamp and more than " + dateFrom + " (01-01-2022 00:00:00).");
        }
        Timestamp timestamp = new Timestamp(date);
        LocalDate dateLocal = timestamp.toLocalDateTime().toLocalDate();

        return new ResponseEntity<>(ps.findByDate(dateLocal), HttpStatus.OK);
    }

    @GetMapping("/products/date/between")
    public ResponseEntity<List<Product>> getByDateBetween(@RequestParam(value = "minDate") long minDate,
            @RequestParam(value = "maxDate") long maxDate) throws BadRequestException {

        if (minDate < dateFrom || maxDate < dateFrom) {
            logger.error("Product get by date between error.", new BadRequestException());
            throw new BadRequestException(
                    "The dates must be in timestamp and more than " + dateFrom + " (01-01-2022 00:00:00).");
        }
        Timestamp minTimestamp = new Timestamp(minDate);
        LocalDate minDateLocal = minTimestamp.toLocalDateTime().toLocalDate();
        Timestamp maxTimestamp = new Timestamp(maxDate);
        LocalDate maxDateLocal = maxTimestamp.toLocalDateTime().toLocalDate();

        LocalDate changerDate = LocalDate.now();
        if (minDateLocal.isAfter(maxDateLocal)) {
            changerDate = minDateLocal;
            minDateLocal = maxDateLocal;
            maxDateLocal = changerDate;
        }

        return new ResponseEntity<>(ps.findByDateBetween(minDateLocal, maxDateLocal), HttpStatus.OK);
    }

    @GetMapping("/products/inMenu/{inMenu}")
    public ResponseEntity<List<Product>> getByInMenu(@PathVariable boolean inMenu) {
        return new ResponseEntity<>(ps.findByInMenu(inMenu), HttpStatus.OK);
    }

    @GetMapping("/products/price/{price}")
    public ResponseEntity<List<Product>> getByPrice(@PathVariable float price) throws BadRequestException {
        if (price < 0) {
            logger.error("Product get by price error.", new BadRequestException());
            throw new BadRequestException("The price must be 0 or more.");
        }
        return new ResponseEntity<>(ps.findByPrice(price), HttpStatus.OK);
    }

    @GetMapping("/products/price/between")
    public ResponseEntity<List<Product>> getByPriceBetween(@PathVariable float minPrice,
            @PathVariable float maxPrice) throws BadRequestException {

        if (minPrice < 0 || maxPrice < 0) {
            logger.error("Product get by price error.", new BadRequestException());
            throw new BadRequestException("The price must be 0 or more.");
        }
        float templatePrice = 0;
        if (minPrice > maxPrice) {
            templatePrice = minPrice;
            minPrice = maxPrice;
            maxPrice = templatePrice;
        }

        return new ResponseEntity<>(ps.findByPriceBetween(minPrice, maxPrice), HttpStatus.OK);
    }

    @GetMapping("/products/punctuation/{punctuation}")
    public ResponseEntity<List<Product>> getByPunctuation(@PathVariable float punctuation) throws BadRequestException {
        if (punctuation < 0 || punctuation > 5) {
            logger.error("Product get by puntuation error.", new BadRequestException());
            throw new BadRequestException("The punctuation must be between 0 and 5.");
        }
        return new ResponseEntity<>(ps.findByPunctuation(punctuation), HttpStatus.OK);
    }

    @GetMapping("/products/punctuation/between")
    public ResponseEntity<List<Product>> getByPunctuationBetween(@PathVariable float minPunctuation,
            @PathVariable float maxPunctuation) throws BadRequestException {

        if (minPunctuation < 0 || minPunctuation > 5 || maxPunctuation < 0 || maxPunctuation > 5) {
            logger.error("Product get by puntuation between error.", new BadRequestException());
            throw new BadRequestException("The punctuation must be between 0 and 5.");
        }
        float templatePunctuation = 0;
        if (minPunctuation > maxPunctuation) {
            templatePunctuation = minPunctuation;
            minPunctuation = maxPunctuation;
            maxPunctuation = templatePunctuation;
        }

        return new ResponseEntity<>(ps.findByPunctuationBetween(minPunctuation, maxPunctuation), HttpStatus.OK);
    }

    @GetMapping("/products/type/{id}")
    public ResponseEntity<List<Product>> getByTypeId(@PathVariable int id) throws NotFoundException {
        try {
            ProductType type = pts.findById(id);
            return new ResponseEntity<>(ps.findByType(type), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Type not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Type with ID " + id + " does not exists.");
        }
    }

    @GetMapping("/products/menu/{id}")
    public ResponseEntity<List<Product>> getByMenu(@PathVariable int id) throws NotFoundException {
        try {
            Menu menu = ms.findById(id);
            return new ResponseEntity<>(ps.findByMenu(menu), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Menu not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Menu with ID " + id + " does not exists.");
        }
    }

    @GetMapping("/products/publication/{id}")
    public ResponseEntity<List<Product>> getByPublication(@PathVariable int id) throws NotFoundException {
        try {
            Publication publication = publicationService.findById(id);
            return new ResponseEntity<>(ps.findByPublication(publication), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Publication not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Publication with ID " + id + " does not exists.");
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> create(@RequestBody ProductDTO productDTO)
            throws NotFoundException, BadRequestException {

        logger.info("begin create product");
        ProductType type = null;
        try {
            type = pts.findById(productDTO.getTypeId());
        } catch (NotFoundException nfe) {
            logger.error("Type not found exception with id " + productDTO.getTypeId() + ".", nfe);
            throw new NotFoundException("Type with ID " + productDTO.getTypeId() + " does not exists.");
        }
        Publication publication = null;

        try {
            publication = publicationService.findById(productDTO.getPublicationId());
        } catch (NotFoundException nfe) {
            logger.error("Publication not found exception with id " + productDTO.getPublicationId() + ".", nfe);
            throw new NotFoundException("Publication with ID " + productDTO.getPublicationId() + " does not exists.");
        }

        logger.info("Product type found: " + type.getId());
        logger.info("Publication found: " + publication.getId());

        // if (productDTO.getPrice() < 0) {
        // logger.error("Product price error.", new BadRequestException());
        // throw new BadRequestException("The price must be 0 or more.");
        // }
        // if (productDTO.getPunctuation() < 0 || productDTO.getPunctuation() > 5) {
        // logger.error("Product punctuation error.", new BadRequestException());
        // throw new BadRequestException("The punctuation must be 0 or more and 5 or
        // less.");
        // }

        Product product = new Product();
        product.setPunctuation(productDTO.getPunctuation());
        product.setDate(LocalDate.now());
        product.setType(type);
        product.setPublication(publication);

        logger.info("Product mapped");

        Menu menu = null;
        if (productDTO.isInMenu()) {
            try {
                menu = ms.findById(productDTO.getMenuId());
            } catch (NotFoundException nfe) {
                logger.error("Menu not found exception with id " + productDTO.getMenuId() + ".", nfe);
                throw new NotFoundException(
                        "Menu with ID " + productDTO.getMenuId() + " does not exists.");
            }
            product.setPrice(0);
            logger.info("Menu found: " + menu.getId());
        } else {
            product.setPrice(productDTO.getPrice());
        }
        product.setInMenu(productDTO.isInMenu());
        product.setMenu(menu);
        logger.info("Product created");
        logger.info("end create product");

        return new ResponseEntity<>(ps.addProduct(product), HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> update(@RequestBody ProductDTO productDTO, @PathVariable int id)
            throws NotFoundException, BadRequestException {
        logger.info("begin update product");
        Product product = null;
        try {
            product = ps.findById(id);
        } catch (NotFoundException nfe) {
            logger.error("Product not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Product with ID " + id + " does not exists.");
        }

        logger.info("Product found: " + product.getId());

        ProductType type = null;
        try {
            type = pts.findById(productDTO.getTypeId());
        } catch (NotFoundException nfe) {
            logger.error("Type not found exception with id " + productDTO.getTypeId() + ".", nfe);
            throw new NotFoundException("Type with ID " + productDTO.getTypeId() + " does not exists.");
        }

        logger.info("Product type found: " + type.getId());

        if (productDTO.getPrice() < 0) {
            logger.error("Product price error.", new BadRequestException());
            throw new BadRequestException("The price must be 0 or more.");
        }
        if (productDTO.getPunctuation() < 0 || productDTO.getPunctuation() > 5) {
            logger.error("Product punctuation error.", new BadRequestException());
            throw new BadRequestException("The punctuation must be 0 or more and 5 or less.");
        }

        product.setInMenu(productDTO.isInMenu());
        product.setPrice(productDTO.getPrice());
        product.setPunctuation(productDTO.getPunctuation());
        product.setType(type);

        logger.info("Prodcut new properties set");

        Menu menu = null;
        if (productDTO.isInMenu()) {
            try {
                menu = ms.findById(productDTO.getMenuId());
            } catch (NotFoundException nfe) {
                logger.error("Menu not found exception with id " + productDTO.getMenuId() + ".", nfe);
                throw new NotFoundException(
                        "Menu with ID " + productDTO.getMenuId() + " does not exists.");
            }
            logger.info("Menu found: " + menu.getId());
        }
        product.setMenu(menu);
        logger.info("Product properties updated");

        logger.info("end update product");

        return new ResponseEntity<>(ps.updateProduct(product), HttpStatus.OK);
    }

    @PatchMapping("/products/{id}/price/{price}")
    public ResponseEntity<String> updatePrice(@PathVariable int id,
            @PathVariable float price) throws NotFoundException, BadRequestException {

        logger.info("begin update price of product");
        try {
            Product product = ps.findById(id);

            logger.info("Product found: " + product.getId());

            if (price < 0) {
                logger.error("Product price error.", new BadRequestException());
                throw new BadRequestException("The price must be 0 or more.");
            }

            product.setPrice(price);
            ps.updatePrice(product);

            logger.info("Product price updated");
            logger.info("end update price of product");

            return new ResponseEntity<>("Price updated.", HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Product not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Product with ID " + id + " does not exists.");
        }
    }

    @PatchMapping("/products/{id}/punctuation/{punctuation}")
    public ResponseEntity<String> updatePunctuation(@PathVariable int id,
            @PathVariable float punctuation) throws NotFoundException, BadRequestException {

        logger.info("begin update punctuation of product");
        try {
            Product product = ps.findById(id);

            logger.info("Product found: " + product.getId());

            if (punctuation < 0) {
                logger.error("Product punctuation error.", new BadRequestException());
                throw new BadRequestException("The punctuation must be 0 or more and 5 or less.");
            }

            product.setPunctuation(punctuation);
            ps.updatePunctuation(product);

            logger.info("Product punctuation updated");
            logger.info("end update punctuation of product");

            return new ResponseEntity<>("Punctuation updated.", HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Product not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Product with ID " + id + " does not exists.");
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws NotFoundException {
        logger.info("begin delete product");
        try {
            Product product = ps.findById(id);
            logger.info("Product found: " + product.getId());
            ps.deleteProduct(product);
            logger.info("Product deleted");
            logger.info("end delete product");

            return new ResponseEntity<>("Product deleted.", HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Product not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Product with ID " + id + " does not exists.");
        }
    }

    @DeleteMapping("/products")
    public ResponseEntity<String> deleteAll() {
        ps.deleteAll();

        return new ResponseEntity<>("All products deleted.", HttpStatus.OK);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException br) {
        ErrorResponse errorResponse = new ErrorResponse("400", "Bad request exception", br.getMessage());
        logger.error(br.getMessage(), br);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException nfe) {
        ErrorResponse errorResponse = new ErrorResponse("404", "Not found exception", nfe.getMessage());
        logger.error(nfe.getMessage(), nfe);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse("500", "Internal server error", exception.getMessage());
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("401", "Acceso denegado",
                "Este usuario no tiene permisos suficientes para realizar esta operación.");
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleArgumentNotValidException(MethodArgumentNotValidException manve) {
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException cve) {
        Map<String, String> errors = new HashMap<>();
        cve.getConstraintViolations().forEach(error -> {
            String fieldName = error.getPropertyPath().toString();
            String message = error.getMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}

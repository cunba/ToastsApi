package com.sanvalero.toteco.controller.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sanvalero.toteco.controller.ProductApi;
import com.sanvalero.toteco.exception.BadRequestException;
import com.sanvalero.toteco.exception.ErrorResponse;
import com.sanvalero.toteco.exception.NotFoundException;
import com.sanvalero.toteco.model.Menu;
import com.sanvalero.toteco.model.Product;
import com.sanvalero.toteco.model.Publication;
import com.sanvalero.toteco.model.dto.ProductDTO;
import com.sanvalero.toteco.model.utils.HandledResponse;
import com.sanvalero.toteco.service.MenuService;
import com.sanvalero.toteco.service.ProductService;
import com.sanvalero.toteco.service.PublicationService;

@RestController
public class ProductController implements ProductApi {
    @Autowired
    private ProductService ps;
    @Autowired
    private MenuService ms;
    @Autowired
    private PublicationService publicationService;

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Override
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(ps.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> getById(@PathVariable UUID id) throws NotFoundException {
        try {
            return new ResponseEntity<>(ps.findById(id), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Product not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Product with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Product>> getByMenu(@PathVariable UUID id) throws NotFoundException {
        try {
            Menu menu = ms.findById(id);
            return new ResponseEntity<>(ps.findByMenu(menu), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Menu not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Menu with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Product>> getByPublication(@PathVariable UUID id) throws NotFoundException {
        try {
            Publication publication = publicationService.findById(id);
            return new ResponseEntity<>(ps.findByPublication(publication), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Publication not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Publication with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<Product> save(@RequestBody ProductDTO productDTO)
            throws NotFoundException, BadRequestException {

        logger.info("begin create product");
        Publication publication = null;

        try {
            publication = publicationService.findById(productDTO.getPublicationId());
        } catch (NotFoundException nfe) {
            logger.error("Publication not found exception with id " + productDTO.getPublicationId() + ".", nfe);
            throw new NotFoundException("Publication with ID " + productDTO.getPublicationId() + " does not exists.");
        }

        logger.info("Publication found: " + publication.getId());

        Product product = new Product();
        product.setScore(productDTO.getScore());
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

        return new ResponseEntity<>(ps.save(product), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HandledResponse> update(@PathVariable UUID id, @RequestBody ProductDTO productDTO)
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

        if (productDTO.getPrice() < 0) {
            logger.error("Product price error.", new BadRequestException());
            throw new BadRequestException("The price must be 0 or more.");
        }
        if (productDTO.getScore() < 0 || productDTO.getScore() > 5) {
            logger.error("Product score error.", new BadRequestException());
            throw new BadRequestException("The score must be 0 or more and 5 or less.");
        }

        product.setInMenu(productDTO.isInMenu());
        product.setPrice(productDTO.getPrice());
        product.setScore(productDTO.getScore());

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
        ps.update(product);
        logger.info("end update product");

        return new ResponseEntity<>(new HandledResponse("Product updated", 1), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> delete(@PathVariable UUID id) throws NotFoundException {
        logger.info("begin delete product");
        try {
            Product product = ps.findById(id);
            logger.info("Product found: " + product.getId());
            ps.delete(product);
            logger.info("Product deleted");
            logger.info("end delete product");

            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Product not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Product with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<String> deleteAll() {
        ps.deleteAll();

        return new ResponseEntity<>("All products deleted.", HttpStatus.OK);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException br) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Bad request exception");
        ErrorResponse errorResponse = new ErrorResponse("400", error, br.getMessage());
        logger.error(br.getMessage(), br);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException nfe) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Not found exception");
        ErrorResponse errorResponse = new ErrorResponse("404", error, nfe.getMessage());
        logger.error(nfe.getMessage(), nfe);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal server error");
        ErrorResponse errorResponse = new ErrorResponse("500", error, exception.getMessage());
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(Exception e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Acceso denegado");
        ErrorResponse errorResponse = new ErrorResponse("401", error,
                "Este usuario no tiene permisos suficientes para realizar esta operación.");
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleArgumentNotValidException(MethodArgumentNotValidException manve) {
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        ErrorResponse errorResponse = new ErrorResponse("400", errors, "Validation error");
        logger.error(manve.getMessage(), manve);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException cve) {
        Map<String, String> errors = new HashMap<>();
        cve.getConstraintViolations().forEach(error -> {
            String fieldName = error.getPropertyPath().toString();
            String message = error.getMessage();
            errors.put(fieldName, message);
        });
        ErrorResponse errorResponse = new ErrorResponse("400", errors, "Validation error");
        logger.error(cve.getMessage(), cve);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

package com.sanvalero.toastsapi.controller;

import java.util.List;

import com.sanvalero.toastsapi.exception.ErrorResponse;
import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.ProductType;
import com.sanvalero.toastsapi.model.dto.ProductTypeDTO;
import com.sanvalero.toastsapi.service.ProductTypeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductTypeController {

    @Autowired
    private ProductTypeService pts;

    private final Logger logger = LoggerFactory.getLogger(ProductTypeController.class);

    @GetMapping("/types")
    public ResponseEntity<List<ProductType>> getAllTypes() {
        return new ResponseEntity<>(pts.findAll(), HttpStatus.OK);
    }

    @GetMapping("/types/{id}")
    public ResponseEntity<ProductType> getById(@PathVariable int id) throws NotFoundException {
        logger.info("begin getting types by id");
        try {
            ProductType toPrint = pts.findById(id);
            logger.info("Type found");
            logger.info("end getting types by id");
            return new ResponseEntity<>(toPrint, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Type not found exception wiwth id " + id + ".", nfe);
            throw new NotFoundException("Type with ID " + id + "does not exists.");
        }
    }

    @GetMapping("/types/name/{name}")
    public ResponseEntity<List<ProductType>> getByName(@PathVariable String name) {
        return new ResponseEntity<>(pts.findByProductName(name), HttpStatus.OK);
    }

    @GetMapping("/types/type/{type}")
    public ResponseEntity<ProductType> getByType(@PathVariable String type) {
        return new ResponseEntity<>(pts.findByType(type), HttpStatus.OK);
    }

    @GetMapping("/types/type/{type}/name/{name}")
    public ResponseEntity<ProductType> getByNameAndType(@PathVariable String type,
            @PathVariable String name) {

        return new ResponseEntity<>(pts.findByProductNameAndType(name, type), HttpStatus.OK);
    }

    @PostMapping("/types")
    public ResponseEntity<ProductType> create(@RequestBody ProductTypeDTO typeDTO) {
        ProductType type = new ProductType();
        type.setProductName(typeDTO.getProduct_name());
        type.setType(typeDTO.getType());
        return new ResponseEntity<>(pts.addType(type), HttpStatus.CREATED);
    }

    @PutMapping("/types/{id}")
    public ResponseEntity<ProductType> update(@PathVariable int id, @RequestBody ProductType type)
            throws NotFoundException {

        logger.info("begin update type");
        try {
            ProductType typeToUpdate = pts.findById(id);
            logger.info("Type found: " + typeToUpdate.getId());
            typeToUpdate.setProductName(type.getProductName());
            typeToUpdate.setType(type.getType());
            logger.info("Type properties updated");
            logger.info("end update type");

            return new ResponseEntity<>(pts.updateType(typeToUpdate), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Type not found exception wiwth id " + id + ".", nfe);
            throw new NotFoundException("Type with ID " + id + "does not exists.");
        }
    }

    @DeleteMapping("/types/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws NotFoundException {
        try {
            logger.info("begin delete type");
            ProductType type = pts.findById(id);
            logger.info("Type found: " + type.getId());
            pts.deleteType(type);
            logger.info("Type deleted");
            logger.info("end delete type");

            return new ResponseEntity<>("Product type deleted.", HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Type not found exception wiwth id " + id + ".", nfe);
            throw new NotFoundException("Type with ID " + id + "does not exists.");
        }
    }

    @DeleteMapping("/types")
    public ResponseEntity<String> deleteAll() {
        pts.deleteAll();

        return new ResponseEntity<>("All types deleted.", HttpStatus.OK);
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
}

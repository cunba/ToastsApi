package com.sanvalero.toastsapi.controller;

import java.util.List;

import com.sanvalero.toastsapi.exception.ErrorResponse;
import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.ProductType;
import com.sanvalero.toastsapi.service.ProductTypeService;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductTypeController {

    @Autowired
    private ProductTypeService pts;

    @GetMapping("/types")
    public List<ProductType> getAllTypes() {
        return pts.findAll();
    }

    @GetMapping("/type/{id}")
    public ProductType getById(@PathVariable int id) throws NotFoundException {
        return pts.findById(id);
    }

    @GetMapping("/type/{name}")
    public List<ProductType> getByName(@PathVariable String name) {
        return pts.findByProductName(name);
    }

    @GetMapping("/type/{type}")
    public ProductType getByType(@PathVariable String type) {
        return pts.findByType(type);
    }

    @GetMapping("/type")
    public ProductType getByNameAndType(@RequestParam(value = "name") String name,
            @RequestParam(value = "type") String type) {

        return pts.findByProductNameAndType(name, type);
    }

    @PostMapping("/type")
    public ProductType create(@RequestBody ProductType type) {
        return pts.addType(type);
    }

    @PutMapping("/type/{id}")
    public ProductType update(@PathVariable int id, @RequestBody ProductType type) throws NotFoundException {
        ProductType typeToUpdate = pts.findById(id);
        typeToUpdate.setProductName(type.getProductName());
        typeToUpdate.setType(type.getType());

        return pts.updateType(typeToUpdate);
    }

    @DeleteMapping("/type/{id}")
    public ProductType delete(@PathVariable int id) throws NotFoundException {
        ProductType type = pts.findById(id);
        return pts.deleteType(type);
    }

    @DeleteMapping("/types")
    public String deleteAll() {
        pts.deleteAll();

        return "All types deleted";
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException nfe) {
        ErrorResponse errorResponse = new ErrorResponse("404", nfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}

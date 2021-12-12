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

    @GetMapping("/type/id={id}")
    public ProductType getTypeById(@PathVariable int id) throws NotFoundException {
        return pts.findById(id);
    }

    @GetMapping("/type/name={name}")
    public List<ProductType> getTypeByName(@PathVariable String name) {
        return pts.findByProductName(name);
    }

    @GetMapping("/type/type={type}")
    public ProductType getTypeByType(@PathVariable String type) {
        return pts.findByType(type);
    }

    @GetMapping("/type")
    public ProductType getTypeByNameAndType(@RequestParam(value = "name") String name,
            @RequestParam(value = "type") String type) {
        return pts.findByProductNameAndType(name, type);
    }

    @PostMapping("/type")
    public ProductType createType(@RequestBody ProductType type) {
        return pts.addType(type);
    }

    @DeleteMapping("/type/id={id}")
    public ProductType deleteType(@PathVariable int id) throws NotFoundException {
        ProductType type = pts.findById(id);
        return pts.deleteType(type);
    }

    @PutMapping("/type/id={id}")
    public ProductType modifyType(@PathVariable int id, @RequestBody ProductType type) throws NotFoundException {
        ProductType typeToModify = pts.findById(id);
        typeToModify.setProductName(type.getProductName());
        typeToModify.setType(type.getType());

        return pts.modifyType(typeToModify);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException bnfe) {
        ErrorResponse errorResponse = new ErrorResponse("404", bnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}

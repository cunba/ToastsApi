package com.sanvalero.toastsapi.controller;

import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.ProductType;
import com.sanvalero.toastsapi.service.ProductTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ProductType getTypeById(@PathVariable int id) throws NotFoundException {
        return pts.findById(id);
    }

    @GetMapping("/type/{name}")
    public ProductType getTypeByName(@PathVariable String name) {
        return pts.findByName(name);
    }

    @GetMapping("/type/{type}")
    public List<ProductType> getTypeByType(@PathVariable String type) {
        return pts.findByType(type);
    }

    @GetMapping("/type")
    public ProductType getTypeByNameAndType(@RequestParam(value = "name") String name, 
                                            @RequestParam(value = "type") String type) {
        return pts.findByNameAndType(name, type);
    }

    @PostMapping("/type")
    public ProductType createType(@RequestBody ProductType type) {
        return pts.addType(type);
    }

}

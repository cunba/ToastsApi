package com.sanvalero.toteco.controller.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.ConstraintViolationException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sanvalero.toteco.controller.MenuApi;
import com.sanvalero.toteco.exception.BadRequestException;
import com.sanvalero.toteco.exception.ErrorResponse;
import com.sanvalero.toteco.exception.NotFoundException;
import com.sanvalero.toteco.model.Menu;
import com.sanvalero.toteco.model.dto.MenuDTO;
import com.sanvalero.toteco.model.utils.HandledResponse;
import com.sanvalero.toteco.service.MenuService;

@RestController
public class MenuController implements MenuApi {
    @Autowired
    private MenuService ms;

    private final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Override
    public ResponseEntity<Menu> getById(@PathVariable UUID id) throws NotFoundException {
        try {
            Menu menu = ms.findById(id);
            return new ResponseEntity<>(menu, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Menu not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Menu with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Menu>> getAll() {
        return new ResponseEntity<>(ms.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Menu> save(@RequestBody MenuDTO menuDTO) {
        logger.info("begin create menu");
        ModelMapper mapper = new ModelMapper();
        Menu menu = mapper.map(menuDTO, Menu.class);
        logger.info("Menu mapped");
        Menu toPrint = ms.save(menu);
        logger.info("Menu created");
        logger.info("end create establishment");

        return new ResponseEntity<>(toPrint, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HandledResponse> update(@PathVariable UUID id, @RequestBody MenuDTO menuDTO)
            throws NotFoundException {
        logger.info("begin update menu");
        try {
            Menu menuToUpdate = ms.findById(id);
            logger.info("Menu found: " + id);
            menuToUpdate.setPrice(menuDTO.getPrice());
            menuToUpdate.setScore(menuDTO.getScore());
            ms.update(menuToUpdate);
            logger.info("Menu properties updated");
            logger.info("end update menu");

            return new ResponseEntity<>(new HandledResponse("Menu updated", 1), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Menu not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Menu with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<Menu> delete(@PathVariable UUID id) throws NotFoundException {
        logger.info("begin delete menu");
        try {
            Menu menu = ms.findById(id);
            logger.info("Menu found: " + menu.getId());
            ms.delete(menu);
            logger.info("Menu deleted");
            logger.info("end delete menu");

            return new ResponseEntity<>(menu, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Menu not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Menu with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<String> deleteAll() {
        ms.deleteAll();

        return new ResponseEntity<>("All menus deleted", HttpStatus.OK);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException br) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Bad Request Exception");
        ErrorResponse errorResponse = new ErrorResponse("400", error, br.getMessage());
        logger.error(br.getMessage(), br);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException nfe) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Not Found Exception");
        ErrorResponse errorResponse = new ErrorResponse("404", error, nfe.getMessage());
        logger.error(nfe.getMessage(), nfe);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
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

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal server error");
        ErrorResponse errorResponse = new ErrorResponse("500", error, exception.getMessage());
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

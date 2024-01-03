package com.sanvalero.toteco.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanvalero.toteco.exception.BadRequestException;
import com.sanvalero.toteco.exception.ErrorResponse;
import com.sanvalero.toteco.exception.NotFoundException;
import com.sanvalero.toteco.model.Product;
import com.sanvalero.toteco.model.dto.ProductDTO;
import com.sanvalero.toteco.model.utils.HandledResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Validated
@RestController
@RequestMapping(value = "/products", produces = { MediaType.APPLICATION_JSON_VALUE })
@SecurityScheme(name = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public interface ProductApi {

        @Operation(summary = "Get products by menu", operationId = "getByMenu")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Menu not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @GetMapping("/menu/{id}")
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<List<Product>> getByMenu(
                        @Parameter(description = "Menu ID", required = true) @PathVariable("id") UUID id)
                        throws NotFoundException;

        @Operation(summary = "Get products by publication", operationId = "getByPublication")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Publication not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @GetMapping("/publication/{id}")
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<List<Product>> getByPublication(
                        @Parameter(description = "Menu ID", required = true) @PathVariable("id") UUID id)
                        throws NotFoundException;

        @Operation(summary = "Get product by ID", operationId = "getById")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @GetMapping("/{id}")
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<Product> getById(
                        @Parameter(description = "Product ID", required = true) @PathVariable("id") UUID id)
                        throws NotFoundException;

        @Operation(summary = "Get all products", operationId = "getAll")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping
        public ResponseEntity<List<Product>> getAll();

        @Secured({ "ROLE_ADMIN" })
        @Operation(summary = "Save product", operationId = "save")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "CREATED"),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @PostMapping
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<Product> save(
                        @Parameter(description = "Product transfer object", required = true) @RequestBody ProductDTO productDTO)
                        throws NotFoundException, BadRequestException;

        @Secured({ "ROLE_ADMIN" })
        @Operation(summary = "Update product", operationId = "update")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @PutMapping("/{id}")
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<HandledResponse> update(
                        @Parameter(description = "Product ID", required = true) @PathVariable UUID id,
                        @Parameter(description = "Product transfer object", required = true) @RequestBody ProductDTO productDTO)
                        throws NotFoundException, BadRequestException;

        @Secured({ "ROLE_ADMIN" })
        @Operation(summary = "Delete product", operationId = "delete")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @DeleteMapping("/{id}")
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<Product> delete(
                        @Parameter(description = "Product ID", required = true) @PathVariable UUID id)
                        throws NotFoundException;

        @Secured({ "ROLE_ADMIN" })
        @Operation(summary = "Delete all products", operationId = "deleteAll")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @DeleteMapping
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<String> deleteAll();

}

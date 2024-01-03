package com.sanvalero.toteco.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanvalero.toteco.exception.BadRequestException;
import com.sanvalero.toteco.exception.ErrorResponse;
import com.sanvalero.toteco.exception.NotFoundException;
import com.sanvalero.toteco.model.UserModel;
import com.sanvalero.toteco.model.dto.PasswordChangeDTO;
import com.sanvalero.toteco.model.dto.UserDTO;
import com.sanvalero.toteco.model.utils.HandledResponse;
import com.sanvalero.toteco.security.JwtRequest;
import com.sanvalero.toteco.security.JwtResponse;

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
@RequestMapping(value = "/users", produces = { MediaType.APPLICATION_JSON_VALUE })
@SecurityScheme(name = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public interface UserApi {

        @Operation(summary = "Get user logged", operationId = "getUserLogged")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK"),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @GetMapping("/logged")
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<UserModel> getUserLogged(@Context HttpServletRequest headers)
                        throws NotFoundException;

        @Operation(summary = "Get user by username", operationId = "getByUsername")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @GetMapping("/username")
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<List<UserModel>> getByUsername(
                        @Parameter(description = "Username", required = true) @RequestParam("username") String username);

        @Operation(summary = "Get user by email", operationId = "getByEmail")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @GetMapping("/email")
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<List<UserModel>> getByEmail(
                        @Parameter(description = "Email", required = true) @RequestParam("email") String email);

        @Operation(summary = "Get user recovery code", operationId = "getRecoveryCode")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @GetMapping("/recovery-code/{id}")
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<Integer> getRecoveryCode(
                        @Parameter(description = "User ID", required = true) @PathVariable("id") UUID id);

        @Operation(summary = "Get user by ID", operationId = "getById")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @GetMapping("/{id}")
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<UserModel> getById(
                        @Parameter(description = "User ID", required = true) @PathVariable("id") UUID id)
                        throws NotFoundException;

        @Operation(summary = "Get all users", operationId = "getAll")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping
        public ResponseEntity<List<UserModel>> getAll();

        @Secured({ "ROLE_ADMIN" })
        @Operation(summary = "Save user", operationId = "save")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "CREATED"),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @PostMapping
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<UserModel> save(
                        @Parameter(description = "User transfer object", required = true) @RequestBody UserDTO userDTO)
                        throws NotFoundException, BadRequestException;

        @Secured({ "ROLE_ADMIN" })
        @Operation(summary = "Update user", operationId = "update")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @PutMapping("/{id}")
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<HandledResponse> update(
                        @Parameter(description = "User ID", required = true) @PathVariable UUID id,
                        @Parameter(description = "User transfer object", required = true) @RequestBody UserDTO userDTO)
                        throws NotFoundException;

        @Operation(summary = "Update user's number of publications", operationId = "updatePublicationsNumber")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @PatchMapping(value = "/users/{id}/publications-number")
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<String> updatePublicationsNumber(
                        @Parameter(description = "User ID", required = true) @PathVariable UUID id)
                        throws NotFoundException;

        @Operation(summary = "Update user's money spent", operationId = "updateMoneySpent")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @PatchMapping(value = "/users/{id}/money-spent")
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<String> updateMoneySpent(
                        @Parameter(description = "User ID", required = true) @PathVariable UUID id)
                        throws NotFoundException;

        @Operation(summary = "Update user's password", operationId = "updatePassword")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @PatchMapping(value = "/users/{id}/password")
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<String> updatePassword(
                        @Parameter(description = "User ID", required = true) @PathVariable UUID id,
                        @Parameter(description = "Password change body", required = true) @RequestBody PasswordChangeDTO password)
                        throws NotFoundException;

        @Operation(summary = "Disable user", operationId = "disable")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @PatchMapping(value = "/users/{id}/disable")
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<String> disable(
                        @Parameter(description = "User ID", required = true) @PathVariable UUID id)
                        throws NotFoundException;

        @Operation(summary = "Activate user", operationId = "activate")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @PatchMapping(value = "/users/{id}/activate")
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<String> activate(
                        @Parameter(description = "User ID", required = true) @PathVariable UUID id)
                        throws NotFoundException;

        @Secured({ "ROLE_ADMIN" })
        @Operation(summary = "Delete user", operationId = "delete")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @DeleteMapping("/{id}")
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<UserModel> delete(
                        @Parameter(description = "User ID", required = true) @PathVariable UUID id)
                        throws NotFoundException;

        @Secured({ "ROLE_ADMIN" })
        @Operation(summary = "Delete all users", operationId = "deleteAll")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @DeleteMapping
        @SecurityRequirements
        @SecurityRequirement(name = "bearer")
        public ResponseEntity<String> deleteAll();

        @Operation(summary = "Login", operationId = "login")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK"),
                        @ApiResponse(responseCode = "400", description = "Bad request exception", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
        @PostMapping("/login")
        public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) throws Exception;

}

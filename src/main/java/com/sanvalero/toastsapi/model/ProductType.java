package com.sanvalero.toastsapi.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "products_types")
public class ProductType {
    @Id
    private UUID _id;
    @Field(name = "product_name")
    @NotNull
    private String productName;
    @Field
    @NotNull
    private String type;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "type")
    @JsonBackReference(value = "product_type-products")
    private List<Product> products;
}

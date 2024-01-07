package com.medicare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @Column(name = "Name", nullable = false, length = 100)

    private String name;

    @Column(name = "Price", nullable = false)
    private Double price;

    @Column(name = "Seller", nullable = false, length = 100)

    private String seller;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "OfferPrice")
    private Double offer;

    @Column(name = "Quantity", nullable = false)
    private Integer stock;

    @Column(name = "Status", nullable = false)
    private Boolean status=true;



}

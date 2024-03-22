package com.example.websildebag.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proDetailId;
    @Column
    private String origin;
    @Column
    private Integer quantity;
    @Column
    private String imagePro;
    @Column
    private String descriptions;
    @ManyToOne
    @JoinColumn(name = "brandId",referencedColumnName = "brandId")
    private Brands brands;
    @ManyToOne
    @JoinColumn(name = "cateId",referencedColumnName = "cateId")
    private Categories categories;
    @ManyToOne
    @JoinColumn(name = "materialId",referencedColumnName = "materialId")
    private Material materials;
    @ManyToOne
    @JoinColumn(name = "sizeId",referencedColumnName = "sizeId")
    private Sizes sizes;
    @ManyToOne
    @JoinColumn(name = "colorId",referencedColumnName = "colorId")
    private Colors colors;
    @ManyToOne
    @JoinColumn(name = "imageId",referencedColumnName = "imageId")
    private ImageDetails imageDetails;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Products products;
}

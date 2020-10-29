package com.poly.petcare.domain.entites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
        (name = "dbo_product")
public class Product extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

//    @Column(name = "discount")
//    private Double discounts;
//
//    @Column(name = "states")
//    private Boolean states;

//    @Column(name = "price")
//    private Double price;

//    @Column(name = "quantity")
//    private Integer quantity;

    @Column(name = "description_Short")
    private String descriptionShort;

    @Column(name = "description_Long")
    private String descriptionLong;

//    @Column(name = "image")
//    private String mainImage;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<CartProduct> cartProductList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<ProductImage> productImageList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonIgnoreProperties({"productList","categoryAttribute"})
    @JoinColumn(name = "category_id")
    private Category category;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "discount_id")
//    private Discount discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    private Brand brand;


    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "dbo_product_attribute_value",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_attribute_value_id")}

    )
    private List<CategoryAttributeValue> categoryAttributeValues;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @OneToMany(mappedBy = "product")
    private List<InputDetail> inputDetails;

    @OneToMany(mappedBy = "product")
    private List<OutputDetail> outputDetails;

    @OneToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @OneToMany(mappedBy = "products")
    private List<Product_Warehouse> productWarehouses;

    @OneToMany(mappedBy = "products")
    private List<Product_Shop> productShop;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

}

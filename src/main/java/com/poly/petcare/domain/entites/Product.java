package com.poly.petcare.domain.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "code",unique = true)
    private String code;

    @Column(name = "description_Short")
    private String descriptionShort;

    @Column(name = "description_Long")
    private String descriptionLong;

    @Column(name = "main_image")
    private String mainImage;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "price")
    private BigDecimal price;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<CartProduct> cartProductList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<ProductImage> productImageList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;


    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "dbo_product_attribute_value",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_attribute_value_id")}
    )
    private List<CategoryAttributeValue> categoryAttributeValues;

    @OneToMany(mappedBy = "product")
    private List<InputDetail> inputDetails;

    @OneToMany(mappedBy = "product")
    private List<OutputDetail> outputDetails;

    @OneToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @OneToMany(mappedBy = "products")
    private List<ProductWarehouse> productWarehouses;

    @OneToMany(mappedBy = "products")
    private List<ProductStore> productStore;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product")
    private List<ServiceRequestDetail> serviceRequestDetails;

}

package com.poly.petcare.domain.services;

import com.poly.petcare.app.contant.StatesConstant;
import com.poly.petcare.app.dtos.ProductDTO;
import com.poly.petcare.app.dtos.ProductImageDTO;
import com.poly.petcare.app.responses.ProductResponses;
import com.poly.petcare.app.result.DataApiResult;
import com.poly.petcare.domain.entites.*;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;

import com.poly.petcare.domain.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ProductService extends BaseServices {
    private static final Logger logger = LogManager.getLogger(ProductService.class);
    private Long codeMax;
    private ProductMapper productMapper;
    private ProductStoreMapper productStoreMapper;
    private ProductRepository productRepository;
    private CategoryAttributeValueMapper categoryAttributeValueMapper;

//    @Autowired
//    private ProductRepository productRepository1;

    @Autowired
    public ProductService(ProductMapper productMapper, ProductStoreMapper productStoreMapper, ProductRepository productRepository,
                          CategoryAttributeValueMapper categoryAttributeValueMapper
    ) {
        try {
            this.productMapper = productMapper;
            this.productStoreMapper = productStoreMapper;
            this.productRepository = productRepository;
            this.categoryAttributeValueMapper = categoryAttributeValueMapper;
        } finally {
            codeMax = productRepository.getCodeMax();
            System.out.println(codeMax);
            if (codeMax == null) {
                codeMax = 0L;
            }
        }
    }

    public ResponseEntity<?> listProduct(Specification conditions, Pageable pageable) {
        DataApiResult result = new DataApiResult();
        List<ProductStoreResponse> responsesList = new ArrayList<>();
        Page<ProductStore> productStoreList = productStoreRepository.findAll(conditions,pageable);
        if (productStoreList.getTotalElements() == 0) {
            throw new ResourceNotFoundException("List Empty");
        }
        for (ProductStore productStore : productStoreList) {
            ProductStoreResponse productStoreResponse=productStoreMapper.convertToDTO(productStore);
            ProductResponse productResponse=new ProductResponse();
            productResponse=productMapper.convertToDTO(productStore.getProducts());
            List<CategoryAttributeValueResponses> listC=new ArrayList<>();
            for (CategoryAttributeValue c:productStore.getProducts().getCategoryAttributeValues()) {
                CategoryAttributeValueResponses categoryAttributeValueResponses=categoryAttributeValueMapper.convertToDTO(c);
                listC.add(categoryAttributeValueResponses);
            }
            productResponse.setCategoryAttributeValueResponses(listC);
            productStoreResponse.setProduct(productResponse);
            responsesList.add(productStoreResponse);
        }
        result.setMessage("Total item product : "+productStoreList.getTotalElements());
        result.setData(responsesList);
        result.setSuccess(true);
        return ResponseEntity.ok(result);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity createProduct(ProductDTO dto) {
        Category category = categoryRepository.getOne(dto.getCategoryID());
        Brand brand = brandRepository.getOne(dto.getBrandID());
        Unit unit = unitRepository.getOne(dto.getUnitID());
        Warehouse warehouse = warehouseRepository.getOne(dto.getWarehouseID());
        List<CategoryAttributeValue> categoryAttributeValues = new ArrayList<>();
        for (Long id : dto.getCategoryAttributeValueID()) {
            CategoryAttributeValue Value = new CategoryAttributeValue();
            Value.setId(id);
            categoryAttributeValues.add(Value);
        }
        if (ConverCode.convertCode(codeMax, dto.getCode(), "SP").startsWith("SP")) {
            codeMax += 1;
        }
        Product product = Product.builder()
                .code(ConverCode.convertCode(codeMax, dto.getCode(), "SP"))
                .name(dto.getName())
                .mainImage(dto.getMainImage())
                .descriptionShort(dto.getDescriptionShort())
                .descriptionLong(dto.getDescriptionLong())
                .category(category)
                .brand(brand)
                .unit(unit)
                .categoryAttributeValues(categoryAttributeValues)
                .build();
        productRepository.save(product);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> listProduct(int page, int limit) {
        DataApiResult result = new DataApiResult();
        Specification conditions = Specification.where(ProductStoreSpecification.hasQuantity(1, ">").
                and(ProductStoreSpecification.hasExpiryDate()));
        Pageable pageable = PageRequest.of(page, limit);
        listProduct(conditions,pageable);
        return listProduct(conditions,pageable);

//        DataApiResult result = new DataApiResult();
//        List<ProductResponse> responsesList = productRepository1.listProduct(page,limit);
//        result.setMessage("Total item product : "+productRepository.totalItem());
//        result.setData(responsesList);
//        return ResponseEntity.ok(result);
    }

    public ResponseEntity<?> detail(long id) {
        DataApiResult result = new DataApiResult();
        Specification conditions = Specification.where(ProductStoreSpecification.hasProduct("id", String.valueOf(id), "="));
        List<ProductStoreResponse> responsesList = new ArrayList<>();
        List<ProductStore> productStoreList = productStoreRepository.findAll(conditions);
        for (ProductStore productStore : productStoreList) {
            ProductStoreResponse productStoreResponse = productStoreMapper.convertToDTO(productStore);
            ProductResponse productResponse = new ProductResponse();
            productResponse = productMapper.convertToDTO(productStore.getProducts());
            List<CategoryAttributeValueResponses> listC = new ArrayList<>();
            for (CategoryAttributeValue c : productStore.getProducts().getCategoryAttributeValues()) {
                CategoryAttributeValueResponses categoryAttributeValueResponses = categoryAttributeValueMapper.convertToDTO(c);
                listC.add(categoryAttributeValueResponses);
            }
            productResponse.setCategoryAttributeValueResponses(listC);
            productStoreResponse.setProduct(productResponse);
            responsesList.add(productStoreResponse);
        }
        result.setData(responsesList);
        result.setSuccess(true);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<?> edit(Long productID, ProductDTO productDTO) {
        Category category = categoryRepository.getOne(productDTO.getCategoryID());
        Brand brand = brandRepository.getOne(productDTO.getBrandID());
        Unit unit = unitRepository.getOne(productDTO.getUnitID());
        Warehouse warehouse = warehouseRepository.getOne(productDTO.getWarehouseID());
        Product product = productRepository.findById(productID).orElse(null);
        if (Objects.isNull(product)) {
            throw new ResourceNotFoundException("Not found productID:" + productID);
        }
        List<CategoryAttributeValue> categoryAttributeValues = new ArrayList<>();
        for (Long id : productDTO.getCategoryAttributeValueID()) {
            CategoryAttributeValue categoryAttributeValue = new CategoryAttributeValue();
            categoryAttributeValue.setId(id);
            categoryAttributeValues.add(categoryAttributeValue);
        }
        product.setCategory(category);
        product.setBrand(brand);
        product.setUnit(unit);
        product.setName(productDTO.getName());
        product.setMainImage(productDTO.getMainImage());
        product.setDescriptionShort(productDTO.getDescriptionShort());
        product.setDescriptionLong(productDTO.getDescriptionLong());
        product.setCategoryAttributeValues(categoryAttributeValues);
        productRepository.saveAndFlush(product);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> searchByName(int page, int limit, String content) {
        DataApiResult result = new DataApiResult();
        Pageable pageable = PageRequest.of(page, limit);
        Specification conditions = Specification.where(ProductStoreSpecification.hasProduct("name", content, ":"));
        listProduct(conditions,pageable);
        return listProduct(conditions,pageable);
    }

    public ResponseEntity<?> searchByDesc(int page, int limit, String content) {
        DataApiResult result = new DataApiResult();
        Pageable pageable = PageRequest.of(page, limit);
        Specification conditions = Specification.where(ProductStoreSpecification.hasProduct("descriptionShort", content, ":"));
        listProduct(conditions,pageable);
        return listProduct(conditions,pageable);
    }


    public ResponseEntity<?> findByQuantity(int page,int limit,int quantity1, int quantity2) {
        DataApiResult result = new DataApiResult();
        Specification conditions = Specification.where(ProductStoreSpecification.hasQuantity(quantity1,">").
                and(ProductStoreSpecification.hasQuantity(quantity2,"<"))
        );
        Pageable pageable = PageRequest.of(page, limit);
        listProduct(conditions,pageable);
        return listProduct(conditions,pageable);
    }

    public ResponseEntity<?> findByPrice(int page, int limit, Integer price1, Integer price2) {
        DataApiResult result = new DataApiResult();
        Specification conditions = Specification.where(ProductStoreSpecification.hasPrice(price1,">").
                and(ProductStoreSpecification.hasPrice(price2,"<"))
        );
        Pageable pageable = PageRequest.of(page, limit);
        listProduct(conditions,pageable);
        return listProduct(conditions,pageable);
    }

//    public ResponseEntity<?> findByCategoryAttributeValue(int page,int limit,long id) {
//        DataApiResult result = new DataApiResult();
//        Specification conditions = Specification.where(ProductStoreSpecification.hasQuantity(1,">").
//                and(ProductStoreSpecification.hasProductCategoryAttributeValue(id))
//        );
//        Pageable pageable = PageRequest.of(page, limit);
//        listProduct(conditions,pageable);
//        return listProduct(conditions,pageable);
//    }

}

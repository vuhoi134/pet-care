package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.ProductDTO;
import com.poly.petcare.app.responses.*;
import com.poly.petcare.app.result.DataApiResult;
import com.poly.petcare.domain.entites.*;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;

import com.poly.petcare.domain.mapper.CategoryAttributeValueMapper;
import com.poly.petcare.domain.mapper.ProductMapper;
import com.poly.petcare.domain.mapper.ProductStoreMapper;
import com.poly.petcare.domain.repository.OrderDetailRepository;
import com.poly.petcare.domain.repository.ProductRepository;
import com.poly.petcare.domain.specification.CategorySpecification;
import com.poly.petcare.domain.specification.ProductSpecification;
import com.poly.petcare.domain.specification.ProductStoreSpecification;
import com.poly.petcare.domain.specification.ProductWarehouseSpecification;
import com.poly.petcare.domain.utils.ConverCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class ProductService extends BaseServices {
    private static final Logger logger = LogManager.getLogger(ProductService.class);
    private Long codeMax;
    private ProductMapper productMapper;
    private ProductStoreMapper productStoreMapper;
    private ProductRepository productRepository;
    private CategoryAttributeValueMapper categoryAttributeValueMapper;

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
            if (codeMax == null) {
                codeMax = 0L;
            }
        }
    }

    public ResponseEntity<?> listProduct(Specification conditions,Pageable pageable) {
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
        result.setMessage("List Product : ");
        result.setTotalItem(productStoreList.getTotalElements());
        result.setData(responsesList);
        result.setSuccess(true);
        return ResponseEntity.ok(result);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity createProduct(ProductDTO dto) {
        Category category = categoryRepository.getOne(dto.getCategoryId());
        Brand brand = brandRepository.getOne(dto.getBrandId());
        List<CategoryAttributeValue> categoryAttributeValues = new ArrayList<>();
        if(dto.getCategoryAttributeValueID().size()>0) {
            for (Long id : dto.getCategoryAttributeValueID()) {
                CategoryAttributeValue Value = new CategoryAttributeValue();
                Value.setId(id);
                categoryAttributeValues.add(Value);
            }
        }else{
            categoryAttributeValues=null;
        }

        if (ConverCode.convertCode(codeMax, dto.getCode(), "SP").startsWith("SP")) {
            codeMax += 1;
        }
        Product product = Product.builder()
                .code(ConverCode.convertCode(codeMax, dto.getCode(), "SP"))
                .name(dto.getName())
                .mainImage(dto.getImages().get(0))
                .descriptionShort(dto.getDescriptionShort())
                .descriptionLong(dto.getDescriptionLong())
                .category(category)
                .brand(brand)
                .price(dto.getPrice())
                .status(true)
                .unit(dto.getUnit())
                .categoryAttributeValues(categoryAttributeValues)
                .build();
        try {
            Product p=productRepository.save(product);
            if(dto.getImages().size()>0){
                for (String link:dto.getImages()) {
                    ProductImage productImage=new ProductImage();
                    productImage.setLink(link);
                    productImage.setProduct(p);
                    productImageRepository.save(productImage);
                }
            }
            return ResponseEntity.ok(true);
        }catch(Exception e){
            System.out.println(e);
            return ResponseEntity.badRequest().body("FALSE "+e);
        }
    }

    public ResponseEntity<?> listProduct(int page, int limit) {
        Specification conditions = Specification.where((ProductStoreSpecification.hasExpiryDate()).and(ProductStoreSpecification.hasStatus()).
                or(ProductStoreSpecification.hasNoExpiryDate()));
        Pageable pageable = PageRequest.of(page, limit);
        listProduct(conditions,pageable);
        return listProduct(conditions,pageable);
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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<?> edit(Long productID, ProductDTO productDTO) {
        Category category = categoryRepository.getOne(productDTO.getCategoryId());
        Brand brand = brandRepository.getOne(productDTO.getBrandId());
        Product product = productRepository.findById(productID).orElse(null);
        if (Objects.isNull(product)) {
            throw new ResourceNotFoundException("Not found productID:" + productID);
        }
        List<ProductImage> productImageList=productImageRepository.findAllByProduct_Id(productID);
        if(Objects.isNull(productImageList)) {
            throw new ResourceNotFoundException("Not found productImageList:" + productID);
        }
        for (ProductImage i:productImageList) {
            System.out.println(i.getId()+" ở đây");
            productImageRepository.delete(i);
        }
        List<CategoryAttributeValue> categoryAttributeValues = new ArrayList<>();
        for (Long id : productDTO.getCategoryAttributeValueID()) {
            ProductAttributeValue productAttributeValue = new ProductAttributeValue();
            productAttributeValue.setCategoryAttributeValueId(id);
            productAttributeValue.setProductId(productID);
            productAttributeValueRepository.save(productAttributeValue);
//            categoryAttributeValues.add(categoryAttributeValue);
        }
        product.setCategory(category);
        product.setBrand(brand);
        product.setUnit(productDTO.getUnit());
        product.setName(productDTO.getName());
        product.setMainImage(productDTO.getMainImage());
        product.setDescriptionShort(productDTO.getDescriptionShort());
        product.setDescriptionLong(productDTO.getDescriptionLong());
//        product.setCategoryAttributeValues(categoryAttributeValues);
        if(productDTO.getImages().size()>0) {
            for (String link : productDTO.getImages()) {
                ProductImage productImage = new ProductImage();
                productImage.setLink(link);
                productImage.setProduct(product);
                productImageRepository.save(productImage);
            }
        }
        try {
            productRepository.saveAndFlush(product);
            return ResponseEntity.ok(true);
        }catch(Exception e){
            System.out.println(e);
            return ResponseEntity.badRequest().body("FALSE "+e);
        }

    }

    public ResponseEntity<?> searchByName(int page, int limit, String content) {
        DataApiResult result = new DataApiResult();
        Pageable pageable = PageRequest.of(page, limit);
        Specification conditions = Specification.where(ProductStoreSpecification.hasProduct("name", content, ":"));
        listProduct(conditions,pageable);
        return listProduct(conditions,pageable);
    }
    public List<ProductSearchResponse> searchByNameAdmin(String productName) {
        Specification conditions = Specification.where(ProductStoreSpecification.hasProductByName(productName));
        List<ProductSearchResponse> list=new ArrayList<>();
        List<ProductStore> productStoreList=productStoreRepository.findAll(conditions);
        for (ProductStore p:productStoreList) {
            ProductSearchResponse productSearchResponse=new ProductSearchResponse();
            productSearchResponse.setId(p.getProducts().getId());
            productSearchResponse.setName(p.getProducts().getName());
            productSearchResponse.setQuantity(p.getQuantityStore());
            list.add(productSearchResponse);
        }
        return list;
    }

    public List<ProductSearchOutputResponse> searchProductOutput(String productName) {
        Specification conditions = Specification.where(ProductWarehouseSpecification.hasProductByName(productName));
        List<ProductSearchOutputResponse> list=new ArrayList<>();
        List<ProductWarehouse> productStoreList=productWarehouseRepository.findAll(conditions);
        for (ProductWarehouse p:productStoreList) {
            ProductSearchOutputResponse response=new ProductSearchOutputResponse();
            response.setId(p.getProducts().getId());
            response.setName(p.getProducts().getName());
            response.setQuantity(p.getQuantityWarehouse());
            response.setCodeTag(p.getCodeTag());
            response.setUnit(p.getProducts().getUnit());
            response.setPrice(p.getProducts().getPrice());
            for (CategoryAttributeValue v:p.getProducts().getCategoryAttributeValues()) {
                response.setAttributeValue(v.getValue());
            }
            list.add(response);
        }
        return list;
    }

    public List<ProductSearchResponse> searchProductInput(String productName) {
        Specification conditions = Specification.where(ProductSpecification.hasProductName(productName));
        List<ProductSearchResponse> list=new ArrayList<>();
        List<Product> productList=productRepository.findAll(conditions);
        for (Product p:productList) {
            ProductSearchResponse productSearchResponse=new ProductSearchResponse();
            productSearchResponse.setId(p.getId());
            productSearchResponse.setName(p.getName());
            productSearchResponse.setCode(p.getCode());
            for (CategoryAttributeValue v:p.getCategoryAttributeValues()) {
                productSearchResponse.setAttributeValue(v.getValue());
            }
            list.add(productSearchResponse);
        }
        return list;
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

    public ResponseEntity<?> findByPrice(int page, int limit, BigDecimal price1, BigDecimal price2) {
        DataApiResult result = new DataApiResult();
        Specification conditions = Specification.where(ProductStoreSpecification.hasPrice(price1,">").
                and(ProductStoreSpecification.hasPrice(price2,"<"))
        );
        Pageable pageable = PageRequest.of(page, limit);
        listProduct(conditions,pageable);
        return listProduct(conditions,pageable);
    }

    public ResponseEntity<?> findByPriceAndCategory(int page, int limit, BigDecimal price1,BigDecimal price2,Long categoryId,Long brandId) {
        DataApiResult result = new DataApiResult();
        if (price2.intValue()>price1.intValue() && brandId > 0 && categoryId > 0) {
            Specification conditions = Specification.where(ProductStoreSpecification.hasPrice(price1, ">").
                    and(ProductStoreSpecification.hasPrice(price2, "<")).
                    and(ProductStoreSpecification.hasProductBrand(brandId)).
                    and(ProductStoreSpecification.hasProductCategory(categoryId))
            );
            Pageable pageable = PageRequest.of(page, limit);
            listProduct(conditions, pageable);
            return listProduct(conditions, pageable);
        }else if(price2.intValue()>price1.intValue() && brandId == 0 && categoryId > 0){
            Specification conditions = Specification.where(ProductStoreSpecification.hasPrice(price1, ">").
                    and(ProductStoreSpecification.hasPrice(price2, "<")).
                    and(ProductStoreSpecification.hasProductCategory(categoryId))
            );
            Pageable pageable = PageRequest.of(page, limit);
            listProduct(conditions, pageable);
            return listProduct(conditions, pageable);
        }else if(price2.intValue()>price1.intValue() && brandId > 0 && categoryId == 0){
            Specification conditions = Specification.where(ProductStoreSpecification.hasPrice(price1, ">").
                    and(ProductStoreSpecification.hasPrice(price2, "<")).
                    and(ProductStoreSpecification.hasProductBrand(brandId))
            );
            Pageable pageable = PageRequest.of(page, limit);
            listProduct(conditions, pageable);
            return listProduct(conditions, pageable);
        }else if(price2.intValue()==0 && price2.intValue()==0 && brandId > 0 && categoryId > 0){
            Specification conditions = Specification.where(ProductStoreSpecification.hasProductCategory(categoryId).
                    and(ProductStoreSpecification.hasProductBrand(brandId))
            );
            Pageable pageable = PageRequest.of(page, limit);
            listProduct(conditions, pageable);
            return listProduct(conditions, pageable);
        }else if(price1.intValue()==0 && price2.intValue()==0 &&  brandId == 0 && categoryId > 0){
            Specification conditions = Specification.where(ProductStoreSpecification.hasProductCategory(categoryId));
            Pageable pageable = PageRequest.of(page, limit);
            listProduct(conditions, pageable);
            return listProduct(conditions, pageable);
        }else if(price1.intValue()==0 && price2.intValue()==0 &&  brandId > 0 && categoryId == 0){
            Specification conditions = Specification.where(ProductStoreSpecification.hasProductBrand(brandId));
            Pageable pageable = PageRequest.of(page, limit);
            listProduct(conditions, pageable);
            return listProduct(conditions, pageable);
        }else if(price2.intValue()>price1.intValue()&&  brandId == 0 && categoryId == 0) {
            Specification conditions = Specification.where(ProductStoreSpecification.hasPrice(price1, ">").
                    and(ProductStoreSpecification.hasPrice(price2, "<"))
            );
            Pageable pageable = PageRequest.of(page, limit);
            listProduct(conditions, pageable);
            return listProduct(conditions, pageable);
        }else{
            return null;
        }
    }

    public DataApiResult listProductAdmin(Integer page,Integer limit) {
        DataApiResult result=new DataApiResult();
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(page, limit,sort);
        List<ProductResponse> list=new ArrayList<>();
        Page<Product> productList=productRepository.findAll(pageable);
        for (Product p:productList) {
            ProductResponse productResponse=productMapper.convertToDTO(p);
            list.add(productResponse);
        }
        result.setSuccess(true);
        result.setData(list);
        result.setTotalItem(productList.getTotalElements());
        result.setMessage("All list product");
        return result;
    }

    public DataApiResult topProduct(Long categoryId){
        DataApiResult result=new DataApiResult();
        List<OrderDetailRepository.ODD> detailList=orderDetailRepository.topProduct(categoryId);
        List<ProductStoreResponse> responseList=new ArrayList<>();
        for (OrderDetailRepository.ODD item:detailList) {
            ProductStore productStore=productStoreRepository.findByProducts_Id(item.getProductId());
            ProductStoreResponse productStoreResponse=productStoreMapper.convertToDTO(productStore);
            ProductResponse productResponse=new ProductResponse();
            productResponse=productMapper.convertToDTO(productStore.getProducts());
            List<CategoryAttributeValueResponses> listC=new ArrayList<>();
            for (CategoryAttributeValue c:productStore.getProducts().getCategoryAttributeValues()) {
                CategoryAttributeValueResponses categoryAttributeValueResponses=categoryAttributeValueMapper.convertToDTO(c);
                listC.add(categoryAttributeValueResponses);
            }
            productResponse.setCategoryAttributeValueResponses(listC);
            productStoreResponse.setSoldQuantity(item.getAmount());
            productStoreResponse.setProduct(productResponse);
            responseList.add(productStoreResponse);
        }

        result.setSuccess(true);
        result.setData(responseList);
        result.setMessage("All list product");
        return result;
    }


}

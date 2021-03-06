package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.CategoryDTO;
import com.poly.petcare.app.responses.CategoryAttributeValueResponses;
import com.poly.petcare.app.responses.ProductResponse;
import com.poly.petcare.app.responses.CategoryResponses;
import com.poly.petcare.app.responses.ProductStoreResponse;
import com.poly.petcare.app.result.DataApiResult;
import com.poly.petcare.domain.entites.Category;
import com.poly.petcare.domain.entites.CategoryAttributeValue;
import com.poly.petcare.domain.entites.ProductStore;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;
import com.poly.petcare.domain.mapper.CategoryAttributeValueMapper;
import com.poly.petcare.domain.mapper.ProductMapper;
import com.poly.petcare.domain.mapper.ProductStoreMapper;
import com.poly.petcare.domain.specification.CategorySpecification;
import com.poly.petcare.domain.specification.ProductStoreSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServices extends BaseServices {

    private ProductMapper productMapper;
    private ProductStoreMapper productStoreMapper;
    private CategoryAttributeValueMapper categoryAttributeValueMapper;

    @Autowired
    public CategoryServices(ProductMapper productMapper, ProductStoreMapper productStoreMapper,
                          CategoryAttributeValueMapper categoryAttributeValueMapper
    ) {
            this.productMapper = productMapper;
            this.productStoreMapper = productStoreMapper;
            this.productRepository = productRepository;
            this.categoryAttributeValueMapper = categoryAttributeValueMapper;
    }

    public ResponseEntity<?> create(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setLevel(categoryDTO.getLevel());
        category.setStates(categoryDTO.getStates());
        if (Objects.nonNull(categoryDTO.getParentId())) {
            Category parent = categoryRepository.findById(categoryDTO.getParentId()).orElse(null);
            if (parent != null) {
                category.setParentId(parent);
            }
        }
        categoryRepository.save(category);
        return ResponseEntity.ok("Success!");
    }

    public ResponseEntity<?> searchByCategory(Integer level) {
        List<Category> list = categoryRepository.findAllByLevel(level);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Empty");
        }
        List<CategoryResponses> dtoList = new ArrayList<>();
        for (Category category : list) {
            CategoryResponses responses = modelMapper.categoryToResponse(category);
            dtoList.add(responses);
        }
        return ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<?> getCategory(Long id) {
        Specification conditions = Specification.where(CategorySpecification.hasCategory("id",id));
        List<Category> list = categoryRepository.findAll(conditions);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Empty");
        }
        List<CategoryResponses> dtoList = new ArrayList<>();
        for (Category category : list) {
            CategoryResponses responses = modelMapper.categoryToResponse(category);
            dtoList.add(responses);
        }
        return ResponseEntity.ok(dtoList);
    }

    public ResponseEntity listCategory() {
        DataApiResult result = new DataApiResult();
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponses> dtoList = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryResponses responses = modelMapper.categoryToResponse(category);
            if (responses.getLevel().equals(0)) {
                dtoList.add(responses);
            }
        }
        result.setTotalItem(Long.valueOf(dtoList.size()));
        result.setData(categoryList);
        result.setSuccess(true);
        result.setMessage("List Category");
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<?> findByCategory(int page,int limit,long id) {
        DataApiResult result = new DataApiResult();
        Specification conditions = Specification.where(ProductStoreSpecification.hasQuantity(1,">").
                and(CategorySpecification.hasProductCategory(id))
        );
        Pageable pageable = PageRequest.of(page, limit);
        listProduct(conditions,pageable);
        return listProduct(conditions,pageable);
    }

    public ResponseEntity<?> findByListCategory(int page,int limit,List<Long> id) {
        DataApiResult result = new DataApiResult();
        Specification conditions = Specification.where(ProductStoreSpecification.hasQuantity(1,">")
        );
        for (int i=0;i<id.size();i++){
            if(i>0){
                conditions = conditions.or(ProductStoreSpecification.hasProductCategory(id.get(i)));
            }else {
                conditions = conditions.and(ProductStoreSpecification.hasProductCategory(id.get(i)));
            }
        }
        Pageable pageable = PageRequest.of(page, limit);
        listProduct(conditions,pageable);
        return listProduct(conditions,pageable);
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
        result.setMessage("List product find by Category : ");
        result.setTotalItem(productStoreList.getTotalElements());
        result.setData(responsesList);
        result.setSuccess(true);
        return ResponseEntity.ok(result);
    }


}
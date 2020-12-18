package com.poly.petcare.domain.services;

import com.poly.petcare.app.responses.ProductResponse;
import com.poly.petcare.app.responses.ProductWarehouseResponse;
import com.poly.petcare.app.result.BaseApiResult;
import com.poly.petcare.app.result.DataApiResult;
import com.poly.petcare.domain.entites.ProductWarehouse;
import com.poly.petcare.domain.mapper.ProductMapper;
import com.poly.petcare.domain.mapper.ProductWarehouseMapper;
import com.poly.petcare.domain.specification.ProductWarehouseSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductWarehouseService extends BaseServices{

    @Autowired
    private ProductWarehouseMapper productWarehouseMapper;

    @Autowired
    private ProductMapper productMapper;

    public DataApiResult listProductWarehouse(Integer page, Integer limit,String productName){
        DataApiResult result=new DataApiResult();
        Sort sort = Sort.by("id").descending();
        Pageable pageable= PageRequest.of(page,limit,sort);
        Specification conditions=Specification.where(ProductWarehouseSpecification.hasProductByName(productName));
        Page<ProductWarehouse> productWarehouses=productWarehouseRepository.findAll(conditions,pageable);
        List<ProductWarehouseResponse> responseList=new ArrayList<>();
        for (ProductWarehouse p:productWarehouses) {
            ProductWarehouseResponse response=productWarehouseMapper.convertToDTO(p);
            ProductResponse productResponse=productMapper.convertToDTO(p.getProducts());
            productResponse.setCategoryAttributeValueResponses(null);
            response.setProduct(productResponse);
            responseList.add(response);
        }
        result.setMessage("List ProductWarehouse : ");
        result.setTotalItem(productWarehouses.getTotalElements());
        result.setData(responseList);
        result.setSuccess(true);
        return  result;
    }
}

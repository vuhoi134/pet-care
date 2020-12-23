package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.OutputDTO;
import com.poly.petcare.app.dtos.OutputDetailDTO;
import com.poly.petcare.app.responses.OutputDetailResponse;
import com.poly.petcare.app.responses.OutputResponse;
import com.poly.petcare.app.result.BaseApiResult;
import com.poly.petcare.app.result.DataApiResult;
import com.poly.petcare.domain.entites.*;
import com.poly.petcare.domain.repository.InputRepository;
import com.poly.petcare.domain.repository.OutputRepository;
import com.poly.petcare.domain.repository.ProductWarehouseRepository;
import com.poly.petcare.domain.specification.OutputSpecification;
import com.poly.petcare.domain.utils.ConverCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OutputService extends BaseServices{
    private Long codeMax;
    private OutputRepository outputRepository;
    private ProductWarehouseRepository productWarehouseRepository;
    @Autowired
    public OutputService(OutputRepository outputRepository,ProductWarehouseRepository productWarehouseRepository) {
        try {
            this.outputRepository=outputRepository;
            this.productWarehouseRepository=productWarehouseRepository;
        } finally {
            codeMax = outputRepository.getCodeMax();
            if (codeMax == null) {
                codeMax = 0L;
            }
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BaseApiResult createOutput(OutputDTO outputDTO) {
        BaseApiResult result = new BaseApiResult();
        try {
            // tạo mới phiếu xuất
            codeMax += 1;
            Warehouse warehouse = warehouseRepository.getOne(outputDTO.getWarehouseId());
            Output output = new Output();
            output.setCode(ConverCode.convertCode(codeMax, "", "OP"));
            output.setExport_date(new Date());
            output.setUser(userRepository.getOne(outputDTO.getUserId()));
            output.setWarehouse(warehouse);
            Output output1=outputRepository.save(output);
            // tạo phiếu xuất chi tiết
            for (OutputDetailDTO item : outputDTO.getOutputDetailDTOS()) {
                Product product = productRepository.getOne(item.getProductId());
                OutputDetail outputDetail = new OutputDetail();
                outputDetail.setActualAmount(item.getActualAmount());
                outputDetail.setOutput(output1);
                outputDetail.setCodeTag(item.getCodeTag());
                outputDetail.setProduct(product);
                OutputDetail outputDetail1 = outputDetailRepository.save(outputDetail);
                // trừ số lượng trong kho sản phẩm
                ProductWarehouse productWarehouse = productWarehouseRepository.findByCodeTag(outputDetail1.getCodeTag());
                productWarehouse.setQuantityWarehouse(productWarehouse.getQuantityWarehouse() - outputDetail1.getActualAmount());
                productWarehouseRepository.saveAndFlush(productWarehouse);
                // công số lượng vào kho ở cửa hàng
                ProductStore productStore=productStoreRepository.findByCodeTag(outputDetail1.getCodeTag());
                if(productStore==null){
                    ProductStore productStore1=new ProductStore();
                    productStore1.setQuantityStore(outputDetail1.getActualAmount());
                    productStore1.setProducts(product);
                    productStore1.setCodeTag(outputDetail1.getCodeTag());
                    productStore1.setExpiryDate(productWarehouse.getExpiryDate());
                    productStoreRepository.save(productStore1);
                }else {
                    productStore.setQuantityStore(productStore.getQuantityStore() + outputDetail1.getActualAmount());
                    productStoreRepository.saveAndFlush(productStore);
                }
            }
            result.setMessage("Create output success!!");
            result.setSuccess(true);
            return result;
        } catch (Exception e) {
            result.setMessage("False!!");
            result.setSuccess(false);
            return result;
        }
    }

    public DataApiResult listOutput(Integer page,Integer limit,String name){
        DataApiResult result=new DataApiResult();
        Sort sort=Sort.by("id").descending();
        Pageable pageable= PageRequest.of(page,limit,sort);
        Specification specification=Specification.where(OutputSpecification.hasOutputByUserName(name));
        Page<Output> outputs=outputRepository.findAll(specification,pageable);
        List<OutputResponse> responseList=new ArrayList<>();
        for (Output op:outputs) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = formatter.format(op.getExport_date());
            OutputResponse response=new OutputResponse();
            response.setCode(op.getCode());
            response.setUserName(op.getUser().getProfile().getFullName());
            response.setWarehouseAddress(op.getWarehouse().getAddress());
            response.setExportDate(strDate);
            List<OutputDetailResponse> list=new ArrayList<>();
            for (OutputDetail opd:op.getOutputDetails()) {
                OutputDetailResponse detailResponse=new OutputDetailResponse();
                detailResponse.setActualAmount(opd.getActualAmount());
                detailResponse.setCodeTag(opd.getCodeTag());
                if(opd.getProduct().getCategoryAttributeValues().size() >0) {
                    detailResponse.setProductName(opd.getProduct().getName() + " (" + opd.getProduct().getCategoryAttributeValues().get(0).getValue() + ")");
                }else{
                    detailResponse.setProductName(opd.getProduct().getName());
                }
                detailResponse.setImage(opd.getProduct().getMainImage());
                list.add(detailResponse);
            }
            response.setOutputDetailResponses(list);
            responseList.add(response);
        }
        result.setSuccess(true);
        result.setData(responseList);
        result.setTotalItem(outputs.getTotalElements());
        result.setMessage("List Output");
        return result;
    }


}

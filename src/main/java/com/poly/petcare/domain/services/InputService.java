package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.InputDTO;
import com.poly.petcare.app.dtos.InputDetailDTO;
import com.poly.petcare.app.responses.InputDetailResponse;
import com.poly.petcare.app.responses.InputResponse;
import com.poly.petcare.app.result.BaseApiResult;
import com.poly.petcare.app.result.DataApiResult;
import com.poly.petcare.domain.entites.*;
import com.poly.petcare.domain.mapper.CategoryAttributeValueMapper;
import com.poly.petcare.domain.mapper.ProductMapper;
import com.poly.petcare.domain.mapper.ProductStoreMapper;
import com.poly.petcare.domain.repository.InputRepository;
import com.poly.petcare.domain.repository.ProductRepository;
import com.poly.petcare.domain.specification.InputSpecification;
import com.poly.petcare.domain.utils.ConverCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InputService extends BaseServices{

    private Long codeMax;
    private InputRepository inputRepository;
    @Autowired
    public InputService(InputRepository inputRepository) {
        try {
            this.inputRepository=inputRepository;
        } finally {
            codeMax = inputRepository.getCodeMax();
            if (codeMax == null) {
                codeMax = 0L;
            }
        }
    }

    public BaseApiResult createInput(InputDTO inputDTO){
        BaseApiResult result = new BaseApiResult();
        try {
            // Tạo mới phiếu nhập
            codeMax += 1;
            Warehouse warehouse=warehouseRepository.getOne(inputDTO.getWarehouseId());
            Input input = new Input();
            input.setCode(ConverCode.convertCode(codeMax, "", "IP"));
            input.setImport_date(new Date());
            input.setUser(userRepository.getOne(inputDTO.getUserId()));
            input.setWarehouse(warehouse);
            input.setTransporter(inputDTO.getTransporter());
            input.setPhoneTransporter(inputDTO.getPhoneTransporter());
            input.setStatus(inputDTO.getStatus());
            input.setMoney(inputDTO.getMoney());
            Input input1 = inputRepository.save(input);

            // Tạo phiêu nhập chi tiết
            for (InputDetailDTO item:inputDTO.getInputDetailDTOS()) {
                Product product = productRepository.getOne(item.getProductId());
                Supplier supplier = supplierRepository.getOne(item.getSupplierId());
                String codeTag=product.getCode() + new Date().getTime();
                InputDetail inputDetail = new InputDetail();
                inputDetail.setActualAmount(item.getActualAmount());
                inputDetail.setTheoreticalAmount(item.getTheoreticalAmount());
                inputDetail.setImport_price(item.getImportPrice());
                inputDetail.setStatus(1);
                inputDetail.setProduct(product);
                inputDetail.setSupplier(supplier);
                inputDetail.setInput(input1);
                inputDetail.setCodeTag(codeTag);

                if(item.getExpiryDate()==null){
                    ProductWarehouse pW=productWarehouseRepository.findByProducts_Id(product.getId());
                    if(pW != null){
                        inputDetail.setExpiryDate(0L);
                        pW.setQuantityWarehouse(pW.getQuantityWarehouse()+item.getActualAmount());
                        pW.setWarehouse(warehouse);
                        inputDetailRepository.save(inputDetail);
                        productWarehouseRepository.save(pW);
                    }else {
                        ProductWarehouse productWarehouse = new ProductWarehouse();
                        productWarehouse.setCodeTag(codeTag);
                        productWarehouse.setProducts(product);
                        productWarehouse.setExpiryDate(0L);
                        inputDetail.setExpiryDate(0L);
                        productWarehouse.setQuantityWarehouse(item.getActualAmount());
                        productWarehouse.setWarehouse(warehouse);
                        inputDetailRepository.save(inputDetail);
                        productWarehouseRepository.save(productWarehouse);
                    }
                }else{
//                    ProductWarehouse pW=productWarehouseRepository.findByExpiryDate(item.getExpiryDate().getTime());
                    ProductWarehouse pW=productWarehouseRepository.findByProducts_IdAndExpiryDate(product.getId(),item.getExpiryDate().getTime());
                    if(pW != null) {
                        inputDetail.setExpiryDate(item.getExpiryDate().getTime());
                        pW.setQuantityWarehouse(pW.getQuantityWarehouse() + item.getActualAmount());
                        pW.setWarehouse(warehouse);
                        inputDetailRepository.save(inputDetail);
                        productWarehouseRepository.save(pW);
                    }else{
                        ProductWarehouse productWarehouse = new ProductWarehouse();
                        productWarehouse.setCodeTag(codeTag);
                        productWarehouse.setProducts(product);
                        productWarehouse.setExpiryDate(item.getExpiryDate().getTime());
                        inputDetail.setExpiryDate(item.getExpiryDate().getTime());
                        productWarehouse.setQuantityWarehouse(item.getActualAmount());
                        productWarehouse.setWarehouse(warehouse);
                        inputDetailRepository.save(inputDetail);
                        productWarehouseRepository.save(productWarehouse);
                    }
                }
            }

            result.setMessage("Create input success!!");
            result.setSuccess(true);
            return result;
        }catch (Exception e){
            result.setMessage("False!!");
            result.setSuccess(false);
            return result;
        }
    }

    public DataApiResult listInput(Integer page,Integer limit,String name,Integer status){
        DataApiResult result=new DataApiResult();
        Sort sort=Sort.by("id").descending();
        Pageable pageable= PageRequest.of(page,limit,sort);
        Specification specification=Specification.where(InputSpecification.hasInputByUserName(name));
        Specification specification1=Specification.where(InputSpecification.hasInputByUserName(name).and(
                InputSpecification.hasInputByStatus(status)
        ));
        Page<Input> inputs;
        if(status==0){
            inputs=inputRepository.findAll(specification,pageable);
        }else{
            inputs=inputRepository.findAll(specification1,pageable);
        }
        List<InputResponse> responseList=new ArrayList<>();
        for (Input i:inputs) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = formatter.format(i.getImport_date());
            InputResponse inputResponse=new InputResponse();
            inputResponse.setCode(i.getCode());
            inputResponse.setImportDate(strDate);
            inputResponse.setUserName(i.getUser().getProfile().getFullName());
            inputResponse.setWarehouseAddress(i.getWarehouse().getAddress());
            inputResponse.setStatus(i.getStatus());
            inputResponse.setMoney(i.getMoney());
            List<InputDetailResponse> list=new ArrayList<>();
            for (InputDetail ipd:i.getInputDetails()) {
                String value="";
                InputDetailResponse detailResponse=new InputDetailResponse();
                detailResponse.setActualAmount(ipd.getActualAmount());
                detailResponse.setTheoreticalAmount(ipd.getTheoreticalAmount());
                if(ipd.getProduct().getCategoryAttributeValues().size()>0){
                    value+=" ("+ipd.getProduct().getCategoryAttributeValues().get(0).getValue()+")";
                }else{
                    value="";
                }
                detailResponse.setProductName(ipd.getProduct().getName()+value);
                detailResponse.setCodeTag(ipd.getCodeTag());
                detailResponse.setSupplierName(ipd.getSupplier().getName());
                detailResponse.setImage(ipd.getProduct().getMainImage());
                list.add(detailResponse);
            }
            inputResponse.setInputDetailResponses(list);
            responseList.add(inputResponse);
        }
        result.setSuccess(true);
        result.setData(responseList);
        result.setTotalItem(inputs.getTotalElements());
        result.setMessage("List Input");
        return result;
    }
}

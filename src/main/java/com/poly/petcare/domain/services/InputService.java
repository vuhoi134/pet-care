package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.InputDTO;
import com.poly.petcare.app.dtos.InputDetailDTO;
import com.poly.petcare.app.result.BaseApiResult;
import com.poly.petcare.domain.entites.*;
import com.poly.petcare.domain.mapper.CategoryAttributeValueMapper;
import com.poly.petcare.domain.mapper.ProductMapper;
import com.poly.petcare.domain.mapper.ProductStoreMapper;
import com.poly.petcare.domain.repository.InputRepository;
import com.poly.petcare.domain.repository.ProductRepository;
import com.poly.petcare.domain.utils.ConverCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
                ProductWarehouse productWarehouse=new ProductWarehouse();
                productWarehouse.setCodeTag(codeTag);
                productWarehouse.setProducts(product);
                productWarehouse.setExpiryDate(item.getExpiryDate().getTime());
//                productWarehouse.setExpiryDate(new Date().getTime());
                productWarehouse.setQuantityWarehouse(item.getActualAmount());
                productWarehouse.setWarehouse(warehouse);
                    inputDetailRepository.save(inputDetail);
                    productWarehouseRepository.save(productWarehouse);
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
}

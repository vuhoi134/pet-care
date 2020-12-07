package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.InputDTO;
import com.poly.petcare.app.dtos.InputDetailDTO;
import com.poly.petcare.app.result.BaseApiResult;
import com.poly.petcare.domain.entites.Input;
import com.poly.petcare.domain.entites.InputDetail;
import com.poly.petcare.domain.entites.Product;
import com.poly.petcare.domain.entites.Supplier;
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
            Input input = new Input();
            input.setCode(ConverCode.convertCode(codeMax, "", "IP"));
            input.setImport_date(new Date());
            input.setUser(userRepository.getOne(inputDTO.getUserId()));
            input.setWarehouse(warehouseRepository.getOne(inputDTO.getWarehouseId()));
            input.setTransporter(inputDTO.getTransporter());
            input.setPhoneTransporter(inputDTO.getPhoneTransporter());
            Input input1 = inputRepository.save(input);

            // Tạo phiêu nhập chi tiết
            for (InputDetailDTO item:inputDTO.getInputDetailDTOS()) {
                Product product = productRepository.getOne(item.getProductId());
                Supplier supplier = supplierRepository.getOne(item.getSupplierId());
                InputDetail inputDetail = new InputDetail();
                inputDetail.setActualAmount(item.getActualAmount());
                inputDetail.setTheoreticalAmount(item.getTheoreticalAmount());
                inputDetail.setImport_price(item.getImportPrice());
                inputDetail.setStatus(1);
                inputDetail.setProduct(product);
                inputDetail.setSupplier(supplier);
                inputDetail.setInput(input1);
                inputDetail.setCodeTag(product.getCode() + new Date().getTime());
//                try {
                    inputDetailRepository.save(inputDetail);
//                }catch(Exception e){
//
//                }
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

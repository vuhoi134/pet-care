package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.OutputDTO;
import com.poly.petcare.app.dtos.OutputDetailDTO;
import com.poly.petcare.app.result.BaseApiResult;
import com.poly.petcare.domain.entites.*;
import com.poly.petcare.domain.repository.InputRepository;
import com.poly.petcare.domain.repository.OutputRepository;
import com.poly.petcare.domain.repository.ProductWarehouseRepository;
import com.poly.petcare.domain.utils.ConverCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
                OutputDetail outputDetail1 = outputDetailRepository.save(outputDetail);
                // trừ số lượng trong kho sản phẩm
                ProductWarehouse productWarehouse = productWarehouseRepository.findByCodeTag(outputDetail1.getCodeTag());
                productWarehouse.setQuantityWarehouse(productWarehouse.getQuantityWarehouse() - outputDetail1.getActualAmount());
                productWarehouseRepository.saveAndFlush(productWarehouse);
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
}

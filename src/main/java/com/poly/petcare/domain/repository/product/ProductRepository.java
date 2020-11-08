package com.poly.petcare.domain.repository.product;

import com.poly.petcare.app.responses.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProductRepository {
    List<ProductResponse> listProduct(int page,int limit);
}

package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.ProductDTO;
import com.poly.petcare.domain.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/products")
public class ProductControllerApi {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(
            @Valid @RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @GetMapping(value = "detail/{productID}")
    public ResponseEntity<?> detail(@PathVariable Long productID) {
        return productService.detail(productID);
    }

    @GetMapping(value = "/select")
    public ResponseEntity<?> select(@RequestParam(name = "page") Optional<Integer> page,
                                    @RequestParam(name = "limit") Optional<Integer> limit
    ) {
        return productService.listProduct(page.orElse(0),limit.orElse(0)
        );
    }

    @PutMapping(value = "{productID}")
    public ResponseEntity<?> edit(@PathVariable Long productID,
                                  @RequestBody ProductDTO dto) {
        return productService.edit(productID, dto);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<?> search(@RequestParam(name = "page") Optional<Integer> page,
                                    @RequestParam(name = "limit") Optional<Integer> limit,
                                    @RequestParam("content") String content) {
        return productService.searchByName(page.orElse(0),limit.orElse(0),content);
    }

    @GetMapping(value = "findByQuantity")
    public ResponseEntity<?> findByQuantity(@RequestParam(name = "page") Optional<Integer> page,
                                    @RequestParam(name = "limit") Optional<Integer> limit,
                                    @RequestParam(name = "quantity1") Integer quantity1,
                                    @RequestParam(name = "quantity2") Integer quantity2){
        return productService.findByQuantity(page.orElse(0),limit.orElse(0),quantity1,quantity2);
    }

    @GetMapping(value = "findByPrice")
    public ResponseEntity<?> findByPrice(@RequestParam(name = "page") Optional<Integer> page,
                                     @RequestParam(name = "limit") Optional<Integer> limit,
                                     @RequestParam(name = "price1") Integer price1,
                                     @RequestParam(name = "price2") Integer price2){
        return productService.findByPrice(page.orElse(0),limit.orElse(0),price1,price2);
    }
}

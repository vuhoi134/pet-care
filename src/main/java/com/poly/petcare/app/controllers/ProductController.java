package com.poly.petcare.app.controllers;

import com.poly.petcare.app.dtos.ProductDTO;
import com.poly.petcare.domain.repository.ProductRepository;
import com.poly.petcare.domain.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

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
    public ResponseEntity<?> select() {
        return productService.listProduct();
    }

//    @GetMapping(value = "/page")
//    public ResponseEntity<?> page(@PageableDefault(size = 3, sort = "price", direction = Sort.Direction.DESC) Pageable pageable) {
//        return productService.page(pageable);
//    }
    @GetMapping(value = "/page")
    public ResponseEntity<?> page(
            @RequestParam(name = "curunpage") Integer curunpage,
            @RequestParam(name = "totalpage") Integer totalpage) {
        return productService.pageProduct(curunpage, totalpage);
    }

    @GetMapping(value = "/totalItem")
    public int total() {
        return productRepository.totalItem();
    }

    @PutMapping(value = "{productID}")
    public ResponseEntity<?> edit(@PathVariable Long productID,
                                  @RequestBody ProductDTO dto) {
        return productService.edit(productID, dto);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<?> search(@RequestParam("name") String name) {
        return productService.search(name);

    }

}

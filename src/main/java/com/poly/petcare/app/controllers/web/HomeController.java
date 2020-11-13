package com.poly.petcare.app.controllers.web;

import com.poly.petcare.domain.services.CategoryServices;
import com.poly.petcare.domain.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController extends BaseController{
    @Autowired
    private CategoryServices categoryServices;
    @Autowired
    private ProductService productService;

}

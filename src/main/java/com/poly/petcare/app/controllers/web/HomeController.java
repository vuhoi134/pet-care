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

//
//    @GetMapping(value = "")
//    public String home(Model model,
//                       @Valid @ModelAttribute("productname") ProductVM productVM,
//                       @RequestParam(name = "categoryId", required = false) Integer categoryId,
//                       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
//                       @RequestParam(name = "size", required = false, defaultValue = "12") Integer size,
//                       @RequestParam(name = "sortByPrice", required = false) String sort,
//                       HttpServletResponse response,
//                       HttpServletRequest request,
//                       final Principal principal) {
//        this.checkCookie(response,request,principal);
//
//    }

}

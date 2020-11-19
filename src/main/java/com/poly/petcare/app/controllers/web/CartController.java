package com.poly.petcare.app.controllers.web;

import com.poly.petcare.app.commom.CartProductVM;
import com.poly.petcare.app.commom.CartVM;
import com.poly.petcare.app.commom.ProductVM;
import com.poly.petcare.domain.entites.Cart;
import com.poly.petcare.domain.entites.CartProduct;
import com.poly.petcare.domain.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
@Controller
@RequestMapping(path = "/cart")
@CrossOrigin
public class CartController extends BaseController{
    @Autowired
    private CartRepository cartRepository;

    @GetMapping("")
    public String cart(Model model,
                       @Valid @ModelAttribute("productname") ProductVM productName,
                       HttpServletResponse response,
                       HttpServletRequest request,
                       final Principal principal) {

        this.checkCookie(response,request,principal);

        CartVM vm = new CartVM();

        int productAmount = 0;
        double totalPrice = 0;
        List<CartProductVM> cartProductVMS = new ArrayList<>();

        String guid = getGuid(request);

        DecimalFormat df = new DecimalFormat("####0.00");

        try {
            if(guid != null) {
                Cart cartEntity = cartRepository.findFirstCartByGuid(guid);
                if(cartEntity != null) {
                    productAmount = cartEntity.getListCartProducts().size();
                    for(CartProduct cartProduct : cartEntity.getListCartProducts()) {
                        CartProductVM cartProductVM = new CartProductVM();
                        cartProductVM.setId(cartProduct.getId());
                        cartProductVM.setProductId(cartProduct.getProduct().getId());
                        cartProductVM.setProductName(cartProduct.getProduct().getName());
                        cartProductVM.setMainImage(cartProduct.getProduct().getMainImage());
                        cartProductVM.setAmount(cartProduct.getAmount());
                        Double price = cartProduct.getAmount()*cartProduct.getProduct().getPrice().doubleValue();
                        cartProductVM.setPrice(df.format(price));
                        totalPrice += price;
                        cartProductVMS.add(cartProductVM);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        vm.setProductAmount(productAmount);
        vm.setCartProductVMS(cartProductVMS);
        vm.setTotalPrice(df.format(totalPrice));
        model.addAttribute("vm",vm);
        return "/cart";
    }




    public String getGuid(HttpServletRequest request) {
        Cookie cookie[] = request.getCookies();
        if(cookie!=null) {
            for(Cookie c :cookie) {
                if(c.getName().equals("guid"))  return c.getValue();
            }
        }
        return null;
    }
}

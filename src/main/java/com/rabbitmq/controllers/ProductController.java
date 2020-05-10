package com.rabbitmq.controllers;

import com.rabbitmq.domain.Product;
import com.rabbitmq.service.ProductService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.rabbitmq.constants.Constants.*;

@Controller
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/product/all";
    }

    @GetMapping("/product/all")
    public ModelAndView getProducts(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Object errors = session.getAttribute(ERROR);
        if (Objects.nonNull(errors)) {
            modelAndView.addObject(ERROR, errors);
            session.removeAttribute(ERROR);
        }
        modelAndView.setViewName(PRODUCTS);
        modelAndView.addObject(PRODUCTS_LIST, productService.getProducts());
        modelAndView.addObject(PRODUCT, new Product());
        return modelAndView;
    }

    @PostMapping("/product/create")
    public String createProduct(@Valid @ModelAttribute(PRODUCT) Product product, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) session.setAttribute(ERROR, bindingResult.getFieldErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
        else productService.create(product);
        return "redirect:/product/all";
    }

    @GetMapping("/product/send/{id}")
    public String sendProduct(@PathVariable(name = ID) Long id) {
        productService.sendMessage(id);
        return "redirect:/product/all";
    }
}

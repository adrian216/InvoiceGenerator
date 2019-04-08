package com.example.invoicesezam.product;

import com.example.invoicesezam.product.Product;
import com.example.invoicesezam.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController
{

    private ProductService productService;

    public  ProductController(ProductService theProductService)
    {
        productService=theProductService;
    }

    @RequestMapping("/list")
    public ModelAndView productList() {
        ModelAndView modelAndView = new ModelAndView("product/product-list");
        List<Product> products = productService.getAll();
        modelAndView.addObject("productList", products);
        return modelAndView;
    }



    @RequestMapping("/form")
    public String showCustomerForm(Model model)
    {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product/product-form";
    }


    @PostMapping ("/save")
    public String addProduct(@ModelAttribute("product") Product product)
    {
        productService.save(product);
        return "redirect:/customers/1";
    }
}

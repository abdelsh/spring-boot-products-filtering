package com.abdelsh.filtering.controllers;

import com.abdelsh.filtering.entities.Product;
import com.abdelsh.filtering.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class productController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/")
    public String seeIndexPage(Model model, @Param("keyword") String keyword){
        List<Product> productList = productService.listAll(keyword);

        model.addAttribute("productList", productList);
        model.addAttribute("keyword", keyword);

        return "index";
    }

    @RequestMapping("/new")
    public String showAddProductForm(Model model){
        Product product = new Product();
        model.addAttribute("product", product);

        return "add_product";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") Product product){
        productService.save(product);

        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditProductForm(@PathVariable(name = "id") Long id){
        ModelAndView model = new ModelAndView("edit_product");

        Product product = productService.get(id);
        model.addObject("product", product);

        return model;
    }

   @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        productService.delete(id);

        return "redirect:/";
   }

}

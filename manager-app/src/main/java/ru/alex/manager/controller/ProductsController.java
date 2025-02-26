package ru.alex.manager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.alex.manager.client.BadRequestException;
import ru.alex.manager.client.ProductsRestClient;
import ru.alex.manager.controller.payload.NewProductPayload;
import ru.alex.manager.entity.Product;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/products")
public class ProductsController {
    private final ProductsRestClient productsRestClient;

    @GetMapping("list")
    public String getProductList(Model model) {
        model.addAttribute("products", this.productsRestClient.findAllProducts());
        return "catalogue/products/list";
    }

    @GetMapping("create")
    public String getNewProductPage() {
        return "catalogue/products/new_product";
    }

    @PostMapping("create")
    public String createNewProduct(NewProductPayload payload,
                                   Model model) {

        try{
            Product product = this.productsRestClient.createProduct(payload.title(), payload.details());
            return "redirect:/catalogue/products/%d".formatted(product.id());
        } catch (BadRequestException e){
            model.addAttribute("payload", payload);
            model.addAttribute("errors", e.getErrors());
            return "catalogue/products/new_product";
        }
    }
}

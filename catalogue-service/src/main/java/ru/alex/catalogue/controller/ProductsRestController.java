package ru.alex.catalogue.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.alex.catalogue.controller.payload.NewProductPayload;
import ru.alex.catalogue.entity.Product;
import ru.alex.catalogue.service.ProductService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("catalogue-api/products")
public class ProductsRestController {
    private final ProductService productService;

    @GetMapping
    public Iterable<Product> findProducts(@RequestParam(name = "filter", required = false) String filter) {
        return this.productService.findAllProducts(filter);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody NewProductPayload payload,
                                                BindingResult bindingResult,
                                                 UriComponentsBuilder uriBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if(bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else{
            Product product = this.productService.createProduct(payload.title(), payload.details());
            return ResponseEntity.created(uriBuilder.replacePath("/catalogue/products/{productId}").
                            build(Map.of("productId", product.getId()))).
                    body(product);
        }
    }


}

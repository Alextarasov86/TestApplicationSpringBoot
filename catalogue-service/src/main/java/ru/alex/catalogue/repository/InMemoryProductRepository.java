//package ru.alex.catalogue.repository;
//
//import org.springframework.stereotype.Repository;
//import ru.alex.catalogue.entity.Product;
//
//
//import java.util.*;
//
//@Repository
//public class InMemoryProductRepository implements ProductRepository {
//    private final List<Product> products = Collections.synchronizedList(new LinkedList<>());
//
//    @Override
//    public List<Product> findAll() {
//        return Collections.unmodifiableList(this.products);
//    }
//
//    @Override
//    public Product save(Product product) {
//        product.setId(this.products.stream()
//                .max(Comparator.comparingInt(Product::getId))
//                .map(Product::getId).orElse(0) + 1);
//        this.products.add(product);
//        return product;
//    }
//
//    @Override
//    public Optional<Product> findById(Integer productId) {
//        return this.products.stream()
//                .filter(product -> Objects.equals(productId, product.getId()))
//                .findFirst();
//    }
//
//    @Override
//    public void deleteById(Integer id) {
//        this.products.removeIf(product -> Objects.equals(product.getId(), id));
//    }
//}

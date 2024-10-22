package br.com.passos.product_api.controller;

import br.com.passos.product_api.dto.ProductDTO;
import br.com.passos.product_api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDTO> newProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO product = this.productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll(){
        List<ProductDTO> products = this.productService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductByCategory(@PathVariable long categoryId) {
        List<ProductDTO> products = this.productService.getProductByCategoryId(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/{productIdentifier}")
    public ResponseEntity<ProductDTO> findById(@PathVariable String productIdentifier) {
        ProductDTO product = this.productService.getProductByIdentifier(productIdentifier);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        this.productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<ProductDTO> editProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        ProductDTO product = this.productService.editProduct(id, productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<ProductDTO>> getProductsPage(Pageable pageable) {
        Page<ProductDTO> products = this.productService.getAllPage(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

}

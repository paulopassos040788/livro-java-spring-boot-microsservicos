package br.com.passos.product_api.service;

import br.com.passos.product_api.dto.ProductDTO;
import br.com.passos.product_api.model.Product;
import br.com.passos.product_api.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAll(){
        return this.productRepository.findAll()
                .stream()
                .map(ProductDTO::convert)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductByCategoryId(Long categoryId){
        return this.productRepository.getProductsByCategory(categoryId)
                .stream()
                .map(ProductDTO::convert)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductByIdentifier(String productIdentifier){
        Product product = this.productRepository.findByProductIdentifier(productIdentifier);
        if(product != null) {
            return ProductDTO.convert(product);
        }
        return null;
    }

    public ProductDTO createProduct(ProductDTO productDTO){
        Product product = Product.convert(productDTO);
        return ProductDTO.convert(this.productRepository.save(product));
    }

    public void delete(long productId){
        Optional<Product> product = this.productRepository.findById(productId);
        product.ifPresent(this.productRepository::delete);
    }

    public ProductDTO editProduct(long id, ProductDTO dto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found"));
        if (dto.getNome() != null || !dto.getNome().isEmpty()) {
            product.setNome(dto.getNome());
        }
        if (dto.getPreco() != null) {
            product.setPreco(dto.getPreco());
        }
        return ProductDTO.convert(productRepository.save(product));
    }

    public Page<ProductDTO> getAllPage(Pageable page) {
        Page<Product> users = productRepository.findAll(page);
        return users
                .map(ProductDTO::convert);
    }

}

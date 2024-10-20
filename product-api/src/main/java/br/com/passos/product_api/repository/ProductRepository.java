package br.com.passos.product_api.repository;

import br.com.passos.product_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select p "
            + "from product p "
            + "join category c on p.category.id = c.id "
            + "where c.id = :categoryId ")
    public List<Product> getProductsByCategory(@Param("categoryId") Long categoryId);

    public Product findByProductIdentifier(String productIdentifier);

}

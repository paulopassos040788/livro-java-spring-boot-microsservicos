package br.com.passos.shopping_api.repository;

import br.com.passos.shopping_api.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    public List<Shop> findAllByUserIdentifier (String userIdentifier);

    public List<Shop> findAllByTotalGreaterThan (Float total);

    List<Shop> findAllByDateGreaterThan (LocalDateTime date);

}

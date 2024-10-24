package br.com.passos.shopping_api.service;

import br.com.passos.shopping_api.dto.ItemDTO;
import br.com.passos.shopping_api.dto.ShopDTO;
import br.com.passos.shopping_api.model.Shop;
import br.com.passos.shopping_api.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopService {

    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public List<ShopDTO> getAll(){
        List<Shop> shops = this.shopRepository.findAll();
        return shops
                .stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }

    public List<ShopDTO> getByUser(String userIdentifier){
        List<Shop> shops = this.shopRepository.findAllByUserIdentifier(userIdentifier);
        return shops
                .stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }

    public List<ShopDTO> getByDate(ShopDTO shopDTO) {
        List<Shop> shops = this.shopRepository.findAllByDateGreaterThan(shopDTO.getDate());
        return shops
                .stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }

    public ShopDTO findById(long ProductId){
        Optional<Shop> shop = this.shopRepository.findById(ProductId);
        return shop.map(ShopDTO::convert).orElse(null);
    }

    public ShopDTO save(ShopDTO shopDTO){
        shopDTO.setTotal(shopDTO.getItems()
                .stream()
                .map(ItemDTO::getPrice)
                .reduce((float) 0, Float::sum)
        );

        Shop shop = Shop.convert(shopDTO);
        shop.setDate(LocalDateTime.now());

        shop = this.shopRepository.save(shop);
        return ShopDTO.convert(shop);
    }

}

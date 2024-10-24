package br.com.passos.shopping_api.controller;

import br.com.passos.shopping_api.dto.ShopDTO;
import br.com.passos.shopping_api.service.ShopService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/shopping")
    public ResponseEntity<List<ShopDTO>> getShops() {
        List<ShopDTO> shops = this.shopService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(shops);
    }

    @GetMapping("/shopping/shopByUser/{userIdentifier}")
    public ResponseEntity<List<ShopDTO>> getShops(@PathVariable String userIdentifier) {
        List<ShopDTO> shops = this.shopService.getByUser(userIdentifier);
        return ResponseEntity.status(HttpStatus.OK).body(shops);
    }

    @GetMapping("/shopping/shopByDate")
    public ResponseEntity<List<ShopDTO>> getShops(@RequestBody ShopDTO shopDTO) {
        List<ShopDTO> shops = this.shopService.getByDate(shopDTO);
        return ResponseEntity.status(HttpStatus.OK).body(shops);
    }

    @GetMapping("/shopping/{id}")
    public ResponseEntity<ShopDTO> findById(@PathVariable Long id) {
        ShopDTO shop = this.shopService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(shop);
    }

    @PostMapping("/shopping")
    public ResponseEntity<ShopDTO> newShop(@Valid @RequestBody ShopDTO shopDTO) {
        ShopDTO shop = this.shopService.save(shopDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(shop);
    }

}

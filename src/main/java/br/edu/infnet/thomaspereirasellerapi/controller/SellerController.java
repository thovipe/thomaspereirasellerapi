package br.edu.infnet.thomaspereirasellerapi.controller;

import br.edu.infnet.thomaspereirasellerapi.model.domain.Seller;
import br.edu.infnet.thomaspereirasellerapi.model.service.SellerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/sellers")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers() {
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.getSellers());
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.getSeller(id));
    }

    @PostMapping()
    public ResponseEntity<Seller> addSeller(@Valid @RequestBody Seller seller) {
        sellerService.addSeller(seller);
        return ResponseEntity.status(HttpStatus.CREATED).body(seller);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Seller> updateSeller(@Valid @PathVariable Long id, @RequestBody Seller seller) {
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.updateSeller(seller));
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        sellerService.deleteSeller(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

package br.edu.infnet.thomaspereirasellerapi.controller;

import br.edu.infnet.thomaspereirasellerapi.model.domain.Seller;
import br.edu.infnet.thomaspereirasellerapi.model.domain.dto.SellerRequestDTO;
import br.edu.infnet.thomaspereirasellerapi.model.domain.dto.SellerResponseDTO;
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
    public ResponseEntity<List<SellerResponseDTO>> getAllSellers() {
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.getSellers());
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<SellerResponseDTO> getSellerById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.getSeller(id));
    }

    @PostMapping()
    public ResponseEntity<SellerResponseDTO> addSeller(@Valid @RequestBody SellerRequestDTO seller) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sellerService.addSeller(seller));
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<SellerResponseDTO> updateSeller(@RequestBody SellerRequestDTO seller) {
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.updateSeller(seller));
    }

    @DeleteMapping(value="/{cnpj}")
    public ResponseEntity<Void> deleteSeller(@PathVariable String cnpj) {
        sellerService.deleteSeller(cnpj);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

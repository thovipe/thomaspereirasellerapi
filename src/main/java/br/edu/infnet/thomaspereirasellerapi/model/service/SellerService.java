package br.edu.infnet.thomaspereirasellerapi.model.service;

import br.edu.infnet.thomaspereirasellerapi.model.domain.Address;
import br.edu.infnet.thomaspereirasellerapi.model.domain.Seller;
import br.edu.infnet.thomaspereirasellerapi.model.domain.dto.AddressResponseDTO;
import br.edu.infnet.thomaspereirasellerapi.model.domain.dto.SellerRequestDTO;
import br.edu.infnet.thomaspereirasellerapi.model.domain.dto.SellerResponseDTO;
import br.edu.infnet.thomaspereirasellerapi.model.domain.repository.SellerRepository;
import br.edu.infnet.thomaspereirasellerapi.model.exception.SellerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;


@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final AddressService addressService;

    public SellerService(SellerRepository sellerRepository, AddressService addressService) {
        this.sellerRepository = sellerRepository;
        this.addressService = addressService;
    }

    public SellerResponseDTO copyFromSeller(Seller seller) {
        SellerResponseDTO sellerResponseDTO = new SellerResponseDTO();
        sellerResponseDTO.setId(seller.getId());
        sellerResponseDTO.setName(seller.getName());
        sellerResponseDTO.setEmail(seller.getEmail());
        sellerResponseDTO.setAddress(new AddressResponseDTO(seller.getAddress().getStreetName(), seller.getAddress().getNumber().toString(), seller.getAddress().getComplement(), seller.getAddress().getDistrict(), seller.getAddress().getCity(), seller.getAddress().getState(), seller.getAddress().getZipCode()));
        sellerResponseDTO.setCnpj(seller.getCnpj());
        return sellerResponseDTO;
    }

    public SellerResponseDTO getSeller(Long id)  {
        Seller seller = sellerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Seller with id %d not found", id)));
        return SellerService.this.copyFromSeller(seller);
    }

    public List<SellerResponseDTO> getSellers() {
        ArrayList<SellerResponseDTO> sellers = new ArrayList<>();
        for(Seller seller: sellerRepository.findAll()) {
                   sellers.add(SellerService.this.copyFromSeller(seller));
                }
        return sellers;
    }

    public SellerResponseDTO addSeller(SellerRequestDTO seller) {
        Seller newSeller = new Seller();
        newSeller.setName(seller.getName());
        newSeller.setEmail(seller.getEmail());
        newSeller.setCnpj(seller.getCnpj());
        newSeller.setActive(false);
        newSeller.setAddress(addressService.getSellerAddress(seller.getAddress().getZipCode(), seller.getAddress().getComplement(), seller.getAddress().getNumber()));
        sellerRepository.save(newSeller);

        return SellerService.this.copyFromSeller(newSeller);
    }

    public SellerResponseDTO addSeller(String name, String email, Address address, String cnpj) {
        Seller seller = new Seller();
        seller.setName(name);
        seller.setEmail(email);
        seller.setAddress(address);
        seller.setCnpj(cnpj);
        seller.setActive(false);
        sellerRepository.save(seller);
        return SellerService.this.copyFromSeller(seller);
    }

    public SellerResponseDTO activateSeller(Long id) {
        Seller sellerFromRepository = sellerRepository.findSellerById(id).orElseThrow( () -> new SellerNotFoundException("Seller with id: " +id+ " not found."));
        if (sellerFromRepository.isActive()) {
            return SellerService.this.copyFromSeller(sellerFromRepository);
        }
        sellerFromRepository.setActive(true);
        return SellerService.this.copyFromSeller(sellerFromRepository);
    }

    public SellerResponseDTO deactivateSeller(Long id) {
        Seller sellerFromRepository = sellerRepository.findSellerById(id).orElseThrow( () -> new SellerNotFoundException("Seller with id: "+id+" not found."));
        if (!sellerFromRepository.isActive()) {
            return SellerService.this.copyFromSeller(sellerFromRepository);
        }
        sellerFromRepository.setActive(false);
        return SellerService.this.copyFromSeller(sellerFromRepository);
    }

    public SellerResponseDTO updateSeller(SellerRequestDTO seller) {
        Seller sellerFromRepository = sellerRepository.findSellerByCnpj(seller.getCnpj()).orElseThrow( () -> new SellerNotFoundException("Seller not found."));

        sellerFromRepository.setName(seller.getName());
        sellerFromRepository.setEmail(seller.getEmail());
        sellerFromRepository.setAddress(addressService.getSellerAddress(seller.getAddress().getZipCode(), seller.getAddress().getComplement(), seller.getAddress().getNumber()));
        sellerFromRepository.setCnpj(seller.getCnpj());
        sellerFromRepository.setActive(seller.getIsActive());
        Seller updatedSeller = sellerRepository.save(sellerFromRepository);

        return SellerService.this.copyFromSeller(updatedSeller);
    }

    public void deleteSeller(Long id) {
        if(id==null) {
            throw new InvalidParameterException("Seller id cannot be null");
        }

        if(!sellerRepository.findSellerById(id).isPresent()) {
            throw new SellerNotFoundException("Seller with id: " + id + " not found.");
        }
        sellerRepository.delete(sellerRepository.findSellerById(id).get());
    }

}

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


    public SellerResponseDTO getSeller(Long id)  {
        Seller seller = sellerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Seller with id %d not found", id)));
        return new SellerResponseDTO().copyFromSeller(seller);
    }

    public List<SellerResponseDTO> getSellers() {
        ArrayList<SellerResponseDTO> sellers = new ArrayList<>();
        for(Seller seller: sellerRepository.findAll()) {
                    SellerResponseDTO sellerResponseDTO = new SellerResponseDTO();
                    sellerResponseDTO.copyFromSeller(seller);
                    sellers.add(sellerResponseDTO);
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

        return new SellerResponseDTO().copyFromSeller(newSeller);
    }

    public SellerResponseDTO addSeller(String name, String email, Address address, String cnpj) {
        Seller seller = new Seller();
        seller.setName(name);
        seller.setEmail(email);
        seller.setAddress(address);
        seller.setCnpj(cnpj);
        seller.setActive(false);
        sellerRepository.save(seller);
        return new SellerResponseDTO().copyFromSeller(seller);
    }

    public SellerResponseDTO activateSeller(Long id) {
        Seller sellerFromRepository = sellerRepository.findSellerById(id).orElseThrow( () -> new SellerNotFoundException("Seller with id: " +id+ " not found."));
        if (sellerFromRepository.isActive()) {
            return new SellerResponseDTO().copyFromSeller(sellerFromRepository);
        }
        sellerFromRepository.setActive(true);
        return new SellerResponseDTO().copyFromSeller(sellerFromRepository);
    }

    public SellerResponseDTO deactivateSeller(Long id) {
        Seller sellerFromRepository = sellerRepository.findSellerById(id).orElseThrow( () -> new SellerNotFoundException("Seller with id: "+id+" not found."));
        if (!sellerFromRepository.isActive()) {
            return new SellerResponseDTO().copyFromSeller(sellerFromRepository);
        }
        sellerFromRepository.setActive(false);
        return new  SellerResponseDTO().copyFromSeller(sellerFromRepository);
    }

    public SellerResponseDTO updateSeller(SellerRequestDTO seller) {
        Seller sellerFromRepository = sellerRepository.findSellerByCnpj(seller.getCnpj()).orElseThrow( () -> new SellerNotFoundException("Seller not found."));

        sellerFromRepository.setName(seller.getName());
        sellerFromRepository.setEmail(seller.getEmail());
        sellerFromRepository.setAddress(addressService.getSellerAddress(seller.getAddress().getZipCode(), seller.getAddress().getComplement(), seller.getAddress().getNumber()));
        sellerFromRepository.setCnpj(seller.getCnpj());
        sellerFromRepository.setActive(seller.getIsActive());
        Seller updatedSeller = sellerRepository.save(sellerFromRepository);

        return new SellerResponseDTO().copyFromSeller(updatedSeller);
    }

    public void deleteSeller(String cnpj) {
        if(cnpj==null) {
            throw new InvalidParameterException("Seller cnpj cannot be null");
        }

        if(!sellerRepository.findSellerByCnpj(cnpj).isPresent()) {
            throw new SellerNotFoundException("Seller with cnpj: " + cnpj + " not found.");
        }
        sellerRepository.delete(sellerRepository.findSellerByCnpj(cnpj).get());
    }

}

package br.edu.infnet.thomaspereirasellerapi.model.service;

import br.edu.infnet.thomaspereirasellerapi.model.domain.Address;
import br.edu.infnet.thomaspereirasellerapi.model.domain.Seller;
import br.edu.infnet.thomaspereirasellerapi.model.domain.repository.SellerRepository;
import br.edu.infnet.thomaspereirasellerapi.model.exception.SellerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.InvalidParameterException;
import java.util.List;


@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }


    public Seller getSeller(Long id)  {
       return sellerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Seller with id %d not found", id)));
    }

    public List<Seller> getSellers() {
        return sellerRepository.findAll();
    }

    public Seller addSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    public Seller addSeller(String name, String email, Address address, String cnpj) {
        Seller seller = new Seller();
        seller.setName(name);
        seller.setEmail(email);
        seller.setAddress(address);
        seller.setCnpj(cnpj);
        seller.setActive(false);
        sellerRepository.save(seller);
        return seller;
    }

    public Seller activateSeller(Long id) {
        Seller sellerFromRepository = sellerRepository.findSellerById(id).orElseThrow( () -> new SellerNotFoundException("Seller with id: " +id+ " not found."));
        if (sellerFromRepository.isActive()) {
            return sellerFromRepository;
        }
        sellerFromRepository.setActive(true);
        return sellerFromRepository;
    }

    public Seller deactivateSeller(Long id) {
        Seller sellerFromRepository = sellerRepository.findSellerById(id).orElseThrow( () -> new SellerNotFoundException("Seller with id: "+id+" not found."));
        if (!sellerFromRepository.isActive()) {
            return sellerFromRepository;
        }
        sellerFromRepository.setActive(false);
        return sellerFromRepository;
    }

    public Seller updateSeller(Seller seller) {
        Seller sellerFromRepository = sellerRepository.findSellerById(seller.getId()).orElseThrow( () -> new SellerNotFoundException("Seller not found."));
        sellerFromRepository.setName(seller.getName());
        sellerFromRepository.setEmail(seller.getEmail());
        sellerFromRepository.setAddress(seller.getAddress());
        sellerFromRepository.setCnpj(seller.getCnpj());
        sellerFromRepository.setActive(seller.isActive());
        sellerRepository.save(sellerFromRepository);
        return sellerRepository.save(sellerFromRepository);
    }

    public void deleteSeller(Long id) {
        if(id==null) {
            throw new InvalidParameterException("Seller id cannot be null");
        }
        if(!sellerRepository.existsById(id)) {
            throw new SellerNotFoundException("Seller with id: " + id + " not found.");
        }
        sellerRepository.deleteById(id);
    }

}

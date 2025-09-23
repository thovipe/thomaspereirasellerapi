package br.edu.infnet.thomaspereirasellerapi.model.service;

import br.edu.infnet.thomaspereirasellerapi.model.domain.Address;
import br.edu.infnet.thomaspereirasellerapi.model.domain.Seller;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    public Seller addSeller(String name, String email, Address address, String cnpj) {
        Seller seller = new Seller();
        seller.setName(name);
        seller.setEmail(email);
        seller.setAddress(address);
        seller.setCnpj(cnpj);
        seller.setActive(false);
        return seller;
    }

    public Seller activateSeller(Seller seller) {
        seller.setActive(true);
        return seller;
    }

    public Seller deactivateSeller(Seller seller) {
        if(seller == null) {
            throw new IllegalArgumentException("Seller is null");
        }
        seller.setActive(false);
        return seller;
    }

    public Seller updateSeller(Seller seller) {

    }

}

package br.edu.infnet.thomaspereirasellerapi.model.domain.dto;

import br.edu.infnet.thomaspereirasellerapi.model.domain.Address;
import br.edu.infnet.thomaspereirasellerapi.model.domain.Seller;

public class SellerResponseDTO {

    private Long id;
    private String name;
    private String email;
    private AddressResponseDTO address;
    private String cpnj;

    public SellerResponseDTO() {}

    public SellerResponseDTO(String name, String email, AddressResponseDTO address, String cnpj) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.cpnj = cnpj;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SellerResponseDTO copyFromSeller (Seller seller) {
        this.id = seller.getId();
        this.name = seller.getName();
        this.email = seller.getEmail();
        this.address = new AddressResponseDTO(seller.getAddress().getStreetName(), seller.getAddress().getNumber().toString(), seller.getAddress().getComplement(), seller.getAddress().getDistrict(), seller.getAddress().getCity(), seller.getAddress().getState(), seller.getAddress().getZipCode());;
        this.cpnj = seller.getCnpj();
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressResponseDTO getAddress() {
        return address;
    }

    public void setAddress(AddressResponseDTO address) {
        this.address = address;
    }

    public String getCpnj() {
        return cpnj;
    }

    public void setCpnj(String cpnj) {
        this.cpnj = cpnj;
    }
}

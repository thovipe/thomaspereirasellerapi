package br.edu.infnet.thomaspereirasellerapi.model.domain.dto;

public class SellerResponseDTO {

    private Long id;
    private String name;
    private String email;
    private AddressResponseDTO address;
    private String cnpj;

    public SellerResponseDTO() {}

    public SellerResponseDTO(String name, String email, AddressResponseDTO address, String cnpj) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.cnpj = cnpj;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}

package br.edu.infnet.thomaspereirasellerapi.model.domain.dto;

public class AddressResponseDTO {

    private String street;
    private String district;
    private String complement;
    private String city;
    private String state;
    private String zip;
    private String number;

    public AddressResponseDTO(String streetName, String number, String complement, String district, String city, String state, String zipCode) {
        this.street = streetName;
        this.number = number;
        this.district = district;
        this.complement = complement;
        this.city = city;
        this.state = state;
        this.zip = zipCode;

    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

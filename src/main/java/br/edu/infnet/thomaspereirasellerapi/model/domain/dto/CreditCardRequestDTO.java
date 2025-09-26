package br.edu.infnet.thomaspereirasellerapi.model.domain.dto;

import br.edu.infnet.thomaspereirasellerapi.model.domain.CardOnfile;

public class CreditCardRequestDTO {

    private String CardNumber;
    private String Holder;
    private String ExpirationDate;
    private String SecurityCode;
    private String Brand;
    private CardOnfile CardOnfile;


    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getHolder() {
        return Holder;
    }

    public void setHolder(String holder) {
        Holder = holder;
    }

    public String getExpirationDate() {
        return ExpirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        ExpirationDate = expirationDate;
    }

    public String getSecurityCode() {
        return SecurityCode;
    }

    public void setSecurityCode(String securityCode) {
        SecurityCode = securityCode;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public CardOnfile getCardOnfile() {
        return this.CardOnfile;
    }

    public void setCardOnfile(CardOnfile cardOnfile) {
        this.CardOnfile = cardOnfile;
    }
}
